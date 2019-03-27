package ke.co.calista.tafuta.storage

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.kogicodes.sokoni.models.custom.Resource
import com.kogicodes.sokoni.models.v1.oauth.Oauth
import ke.co.calista.tafuta.model.oauth.PasswordRecoveryModel


class RecoverPasswordUpRepository (application: Application) {

    val recoverPasswordObservable = MutableLiveData<Resource<PasswordRecoveryModel>>()


    fun recoverPassword(parameters: Oauth) {

        setIsLoading()
        excuteRecoverPassword(parameters)


    }

    private fun excuteRecoverPassword(parameters: Oauth) {
    }


    private fun setIsLoading() {
        recoverPasswordObservable.postValue(Resource.loading(null))
    }
    private fun setIsSuccesful(parameters: PasswordRecoveryModel) {
        recoverPasswordObservable.postValue(Resource.success(parameters))


    }
    private fun setIsError( message: String) {
        recoverPasswordObservable.postValue(Resource.error(message,null))

    }


}

