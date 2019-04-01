package ke.co.calista.tafuta.storage

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kogicodes.sokoni.models.custom.Resource
import com.kogicodes.sokoni.models.v1.oauth.Oauth
import com.kogicodes.sokoni.models.v1.oauth.Profile
import com.kogicodes.sokoni.network.NetworkUtils
import com.kogicodes.sokoni.network.RequestService
import ke.co.calista.tafuta.R
import ke.co.calista.tafuta.model.asset.AssetsResponse
import ke.co.calista.tafuta.model.oauth.LoginData
import ke.co.calista.tafuta.model.oauth.LoginResponse
import ke.co.calista.tafuta.storage.dao.LoginDataDao
import ke.co.calista.tafuta.storage.dao.ProfileDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AssetRepository (application: Application) {

    private val profileDao: LoginDataDao
    private val db: XDatabase

    val assetsObservable = MutableLiveData<Resource<AssetsResponse>>()
    private val context: Context


    init {
        db = XDatabase.getDatabase(application)!!
        profileDao = db.loginDataDao()
        context = application.applicationContext

    }

    fun getAssets(perPage : String,pageNo : String) {

        setIsLoading()
        if (NetworkUtils.isConnected(context)) {
            excuteGetAssets(perPage,pageNo)
        } else {
            setIsError(context.getString(R.string.no_connection))
        }


    }

    private fun excuteGetAssets(perPage : String,pageNo : String) {
        GlobalScope.launch(context = Dispatchers.Main) {

            val call = RequestService.getService("").assets("",perPage,pageNo)
            call.enqueue(object : Callback<AssetsResponse> {
                override fun onFailure(call: Call<AssetsResponse>?, t: Throwable?) {
                    setIsError(t.toString())

                    Log.d("assetListErr",t.toString())
                }

                override fun onResponse(call: Call<AssetsResponse>?, response: Response<AssetsResponse>?) {
                    Log.d("assetListErr",response.toString())

                    if (response != null) {
                        if (response.isSuccessful) {

                            if (response.body()?.error == false) {
                                response.body()?.let { setIsSuccesful(it) }
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
        assetsObservable.postValue(Resource.loading(null))
    }

    private fun setIsSuccesful(parameters: AssetsResponse) {
        assetsObservable.postValue(Resource.success(parameters))


    }
    private fun setIsError( message: String) {
        assetsObservable.postValue(Resource.error(message,null))

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

    fun loadAsset() {

    }

}
