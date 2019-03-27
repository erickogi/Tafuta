package ke.co.calista.tafuta.storage

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kogicodes.sokoni.models.custom.Resource
import com.kogicodes.sokoni.models.v1.oauth.Oauth
import com.kogicodes.sokoni.models.v1.oauth.Profile
import com.kogicodes.sokoni.network.NetworkUtils
import com.kogicodes.sokoni.network.RequestService
import ke.co.calista.tafuta.R
import ke.co.calista.tafuta.storage.dao.ProfileDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInRepository (application: Application) {

    private val profileDao: ProfileDao
    private val db: XDatabase

    val signInObservable = MutableLiveData<Resource<Oauth>>()
    val profileObservable = MutableLiveData<Resource<Oauth>>()
    private val context: Context


    init {
        db = XDatabase.getDatabase(application)!!
        profileDao = db.profileDao()
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
            call.enqueue(object : Callback<Oauth> {
                override fun onFailure(call: Call<Oauth>?, t: Throwable?) {
                    setIsError(t.toString())
                }

                override fun onResponse(call: Call<Oauth>?, response: Response<Oauth>?) {
                    if (response != null) {
                        if (response.isSuccessful) {

                            if (response.body()?.status == 1 && !response.body()?.error!!) {
                                setIsSuccesful(response.body()!!)
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

    private fun setIsSuccesful(parameters: Oauth) {
        signInObservable.postValue(Resource.success(parameters))


    }
    private fun setIsError( message: String) {
        signInObservable.postValue(Resource.error(message,null))

    }

    fun saveProfile(data: Oauth) {

        profileDao.delete()
        data.profile?.let { profileDao.insertProfile(it) }

    }

    fun getProfile(): LiveData<Profile> {

        return profileDao.getProfile()


    }

    fun fetchProfile(): Profile {
        return profileDao.fetch()

    }

}
