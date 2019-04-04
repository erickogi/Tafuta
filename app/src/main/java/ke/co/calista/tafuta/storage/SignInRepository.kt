package ke.co.calista.tafuta.storage

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kogicodes.sokoni.models.custom.Resource
import com.kogicodes.sokoni.models.v1.oauth.Oauth
import com.kogicodes.sokoni.network.NetworkUtils
import com.kogicodes.sokoni.network.RequestService
import ke.co.calista.tafuta.R
import ke.co.calista.tafuta.model.oauth.LoginData
import ke.co.calista.tafuta.model.oauth.LoginResponse
import ke.co.calista.tafuta.storage.dao.LoginDataDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInRepository (application: Application) {

    private val profileDao: LoginDataDao
    private val db: XDatabase

    val signInObservable = MutableLiveData<Resource<LoginData>>()
    private val context: Context


    init {
        db = XDatabase.getDatabase(application)!!
        profileDao = db.loginDataDao()
        context = application.applicationContext

    }

    fun signIn(parameters: Oauth) {

        setIsLoading()
        if (NetworkUtils.isConnected(context)) {
            excuteSignIn(parameters)
        } else {
            setIsError(context.getString(R.string.no_connection))
        }


    }

    private fun excuteSignIn(parameters: Oauth) {
        GlobalScope.launch(context = Dispatchers.Main) {

            val call = RequestService.getService("").signIn(parameters.profile?.email, parameters.profile?.password)
            call.enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
                    setIsError(t.toString())
                }

                override fun onResponse(call: Call<LoginResponse>?, response: Response<LoginResponse>?) {
                    if (response != null) {
                        if (response.isSuccessful) {

                            if (!response.body()?.error !!) {
                                response.body()!!.data?.let { setIsSuccesful(it) }
                            } else {
                                response.body()?.message?.let { setIsError(it) }
                            }

                        } else {
                            setIsError(response.toString())
                        }
                    } else {
                        setIsError("Error Logging In")
                    }
                }
            })
        }

    }

    private fun setIsLoading() {
        signInObservable.postValue(Resource.loading(null))
    }

    private fun setIsSuccesful(parameters: LoginData) {
        signInObservable.postValue(Resource.success(parameters))


    }
    private fun setIsError( message: String) {
        signInObservable.postValue(Resource.error(message,null))

    }

    fun saveProfile(data: LoginData) {

        profileDao.delete()
        data.let { profileDao.insertLoginData(it) }

    }

    fun getProfile(): LiveData<LoginData> {

        return profileDao.getLoginData()


    }

    fun fetchProfile(): LoginData {
        return profileDao.fetch()

    }

}
