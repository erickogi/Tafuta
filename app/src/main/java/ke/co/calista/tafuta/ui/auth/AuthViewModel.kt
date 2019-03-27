package ke.co.calista.tafuta.ui.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.kogicodes.sokoni.models.custom.Resource
import com.kogicodes.sokoni.models.v1.oauth.Oauth
import ke.co.calista.tafuta.model.oauth.PasswordRecoveryModel
import ke.co.calista.tafuta.storage.RecoverPasswordUpRepository
import ke.co.calista.tafuta.storage.SignInRepository
import ke.co.calista.tafuta.storage.SignUpRepository

class AuthViewModel (application: Application) : AndroidViewModel(application){

    internal var signInRepository: SignInRepository
    internal var signUpRepository: SignUpRepository
    internal var recoverPasswordUpRepository: RecoverPasswordUpRepository



    private val signInObservable = MediatorLiveData<Resource<Oauth>>()
    private val signUpObservable = MediatorLiveData<Resource<Oauth>>()
    private val recoverPasswordObservable = MediatorLiveData<Resource<PasswordRecoveryModel>>()

    init {

        signInRepository = SignInRepository(application)
        signUpRepository = SignUpRepository(application)
        recoverPasswordUpRepository = RecoverPasswordUpRepository(application)


        signInObservable.addSource(signInRepository.signInObservable) { data -> signInObservable.setValue(data) }
        signUpObservable.addSource(signUpRepository.signUpObservable) { data -> signUpObservable.setValue(data) }
        recoverPasswordObservable.addSource(recoverPasswordUpRepository.recoverPasswordObservable) { data -> recoverPasswordObservable.setValue(data) }

    }



    fun signIn(parameters: Oauth) {
        signInRepository.signIn(parameters)
    }

    fun observeSignIn(): LiveData<Resource<Oauth>> {
        return signInObservable
    }

    fun signUp(parameters: Oauth) {
        signUpRepository.signUp(parameters)

    }


    fun observeSignUp(): LiveData<Resource<Oauth>> {
        return signUpObservable
    }

    fun recoverPassword(parameters: Oauth) {
        recoverPasswordUpRepository.recoverPassword(parameters)

    }


    fun observeRecoverPassword(): LiveData<Resource<PasswordRecoveryModel>> {
        return recoverPasswordObservable
    }

    fun saveProfile(data: Oauth) {

        signInRepository.saveProfile(data)

    }

}

