package ke.co.calista.tafuta.storage

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.kogicodes.sokoni.models.custom.Resource
import com.kogicodes.sokoni.models.v1.oauth.Oauth
import com.kogicodes.sokoni.network.NetworkUtils
import com.kogicodes.sokoni.network.RequestService
import ke.co.calista.tafuta.R
import ke.co.calista.tafuta.model.oauth.LoginData
import ke.co.calista.tafuta.model.oauth.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpRepository  (application: Application) {

    val signUpObservable = MutableLiveData<Resource<LoginData>>()

    private val context: Context

    init {

        context = application.applicationContext
    }


    fun signUp(parameters: Oauth) {

        setIsLoading()

        if (NetworkUtils.isConnected(context)) {
            excuteSignUp(parameters)
        } else {
            setIsError(context.getString(R.string.no_connection))
        }


    }

    private fun excuteSignUp(parameters: Oauth) {
        GlobalScope.launch(context = Dispatchers.Main) {

            val call = RequestService.getService("").signUp(parameters.profile?.email, parameters.profile?.password, parameters.profile?.firstName, parameters.profile?.lastName, parameters.profile?.mobile)
            call.enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
                    setIsError(t.toString())
                }

                override fun onResponse(call: Call<LoginResponse>?, response: Response<LoginResponse>?) {
                    if (response != null) {
                        if (response.isSuccessful) {

                            if (response.body()?.error==false) {
                                response.body()!!.data?.let { setIsSuccesful(it) }
                            } else {
                                response.body()?.message?.let { setIsError(it) }
                            }

                        } else {
                            setIsError(response.toString())
                        }
                    } else {
                        setIsError("Error Registering You In")
                    }
                }
            })
        }
    }


    private fun setIsLoading() {
        signUpObservable.postValue(Resource.loading(null))
    }

    private fun setIsSuccesful(parameters: LoginData) {
        signUpObservable.postValue(Resource.success(parameters))


    }
    private fun setIsError( message: String) {
        signUpObservable.postValue(Resource.error(message,null))

    }


}
