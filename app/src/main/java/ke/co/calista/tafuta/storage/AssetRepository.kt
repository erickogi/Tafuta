package ke.co.calista.tafuta.storage

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.kogicodes.sokoni.models.custom.Resource
import com.kogicodes.sokoni.network.NetworkUtils
import com.kogicodes.sokoni.network.RequestService
import ke.co.calista.tafuta.R
import ke.co.calista.tafuta.model.asset.*
import ke.co.calista.tafuta.model.oauth.LoginData
import ke.co.calista.tafuta.storage.dao.LoginDataDao
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
    val usersObservable = MutableLiveData<Resource<UsersResponse>>()
    val assignAssetObservable = MutableLiveData<Resource<AssignResponse>>()
    val assetsSendObservable = MutableLiveData<Resource<SendAssetResponse>>()
    val assetObservable = MutableLiveData<Resource<AssetResponse>>()
    val dashboardData = MutableLiveData<Resource<DashboardResponse>>()
    private val context: Context


    init {
        db = XDatabase.getDatabase(application)!!
        profileDao = db.loginDataDao()
        context = application.applicationContext

    }

    fun loadAsset(rawValue: String) {
        setIsLoading(Observable.GET_ASSET)
        if (NetworkUtils.isConnected(context)) {
            excuteGetAsset(rawValue)
        } else {
            setIsError(Observable.GET_ASSET, context.getString(R.string.no_connection))
        }
    }

    fun sendAsset(asset: NewAsset) {
        setIsLoading(Observable.SEND_ASSET)
        if (NetworkUtils.isConnected(context)) {
            excuteSendAssets(asset)
        } else {
            setIsError(Observable.SEND_ASSET, context.getString(R.string.no_connection))
        }

    }

    fun getAssets(perPage : String,pageNo : String) {

        setIsLoading(Observable.GET_ASSETS)
        if (NetworkUtils.isConnected(context)) {
            excuteGetAssets(perPage,pageNo)
        } else {
            setIsError(Observable.GET_ASSETS, context.getString(R.string.no_connection))
        }


    }

    fun getDashboardData() {

        setIsLoading(Observable.GET_DASHBORD)
        if (NetworkUtils.isConnected(context)) {
            excuteGetDashboard()
        } else {
            setIsError(Observable.GET_DASHBORD, context.getString(R.string.no_connection))
        }


    }

    fun assignAsset(assetId: String, actionId: String, userId: String, description: String) {

        assignAssetObservable.postValue(null)
        setIsLoading(Observable.ASSIGN)
        if (NetworkUtils.isConnected(context)) {
            excuteAssign(assetId, actionId, userId, description)
        } else {
            setIsError(Observable.ASSIGN, context.getString(R.string.no_connection))
        }


    }

    fun getUsers(perPage: String, pageNo: String) {

        setIsLoading(Observable.USERS)
        if (NetworkUtils.isConnected(context)) {
            excuteUsers(perPage, pageNo)
        } else {
            setIsError(Observable.USERS, context.getString(R.string.no_connection))
        }


    }


    private fun excuteSendAssets(asset: NewAsset) {

        Log.d("fefdf", Gson().toJson(asset))
        GlobalScope.launch(Dispatchers.Main) {

            val call = RequestService.getService("").sendAsset(asset)
            call.enqueue(object : Callback<SendAssetResponse> {
                override fun onFailure(call: Call<SendAssetResponse>, t: Throwable) {
                    setIsError(Observable.SEND_ASSET, t.toString())
                }

                override fun onResponse(call: Call<SendAssetResponse>, response: Response<SendAssetResponse>) {
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()?.error == false) {

                                response.body()?.let { setIsSuccesful(Observable.SEND_ASSET, it) }

                            } else {
                                response.body()
                                    ?.let { it.message?.let { it1 -> setIsError(Observable.SEND_ASSET, it1) } }

                            }
                        }
                    }
                }
            })
        }
    }

    private fun excuteGetAsset(scanCode: String) {
        GlobalScope.launch(Dispatchers.Main) {

            val call = RequestService.getService("").scanAsset(scanCode)
            call.enqueue(object : Callback<AssetResponse> {
                override fun onFailure(call: Call<AssetResponse>, t: Throwable) {
                    setIsError(Observable.GET_ASSET, t.toString())
                }

                override fun onResponse(call: Call<AssetResponse>, response: Response<AssetResponse>) {
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()?.error == false) {

                                response.body()?.let { setIsSuccesful(Observable.GET_ASSET, it) }

                            } else {
                                response.body()
                                    ?.let { it.message?.let { it1 -> setIsError(Observable.GET_ASSET, it1) } }

                            }
                        }
                    }
                }
            })
        }
    }
    private fun excuteGetAssets(perPage : String,pageNo : String) {
        GlobalScope.launch(context = Dispatchers.Main) {

            val call = RequestService.getService("").assets("",perPage,pageNo)
            call.enqueue(object : Callback<AssetsResponse> {
                override fun onFailure(call: Call<AssetsResponse>?, t: Throwable?) {
                    setIsError(Observable.GET_ASSETS, t.toString())

                }

                override fun onResponse(call: Call<AssetsResponse>?, response: Response<AssetsResponse>?) {

                    if (response != null) {
                        if (response.isSuccessful) {

                            if (response.body()?.error == false) {
                                response.body()?.let { setIsSuccesful(Observable.GET_ASSETS, it) }
                            } else {
                                response.body()?.message?.let { setIsError(Observable.GET_ASSETS, it) }
                            }

                        } else {
                            setIsError(Observable.GET_ASSETS, response.toString())
                        }
                    } else {
                        setIsError(Observable.GET_ASSETS, "Error Logging In")
                    }
                }
            })
        }

    }

    private fun excuteGetDashboard() {
        GlobalScope.launch(context = Dispatchers.Main) {

            val call = RequestService.getService("").dashBoard()
            call.enqueue(object : Callback<DashboardResponse> {
                override fun onFailure(call: Call<DashboardResponse>?, t: Throwable?) {
                    setIsError(Observable.GET_DASHBORD, t.toString())

                }

                override fun onResponse(call: Call<DashboardResponse>?, response: Response<DashboardResponse>?) {

                    if (response != null) {
                        if (response.isSuccessful) {

                            if (response.body()?.error == false) {
                                response.body()?.let { setIsSuccesful(Observable.GET_DASHBORD, it) }
                            } else {
                                response.body()?.message?.let { setIsError(Observable.GET_DASHBORD, it) }
                            }

                        } else {
                            setIsError(Observable.GET_DASHBORD, response.toString())
                        }
                    } else {
                        setIsError(Observable.GET_DASHBORD, "Error  ")
                    }
                }
            })
        }

    }

    private fun excuteAssign(assetId: String, actionId: String, targetId: String, description: String) {
        GlobalScope.launch(context = Dispatchers.Main) {

            val call =
                RequestService.getService("").assignAsset(assetId, actionId, fetchProfile().id, targetId, description)
            call.enqueue(object : Callback<AssignResponse> {
                override fun onFailure(call: Call<AssignResponse>?, t: Throwable?) {
                    setIsError(Observable.ASSIGN, t.toString())

                }

                override fun onResponse(call: Call<AssignResponse>?, response: Response<AssignResponse>?) {

                    if (response != null) {
                        if (response.isSuccessful) {

                            if (response.body()?.error == false) {
                                response.body()?.let { setIsSuccesful(Observable.ASSIGN, it) }
                            } else {
                                response.body()?.message?.let { setIsError(Observable.ASSIGN, it) }
                            }

                        } else {
                            setIsError(Observable.ASSIGN, response.toString())
                        }
                    } else {
                        setIsError(Observable.ASSIGN, "Error  ")
                    }
                }
            })
        }

    }

    private fun excuteUsers(perPage: String, pageNo: String) {
        GlobalScope.launch(context = Dispatchers.Main) {

            val call = RequestService.getService("").users(perPage, pageNo)
            call.enqueue(object : Callback<UsersResponse> {
                override fun onFailure(call: Call<UsersResponse>?, t: Throwable?) {
                    setIsError(Observable.USERS, t.toString())

                }

                override fun onResponse(call: Call<UsersResponse>?, response: Response<UsersResponse>?) {

                    if (response != null) {
                        if (response.isSuccessful) {

                            if (response.body()?.error == false) {
                                response.body()?.let { setIsSuccesful(Observable.USERS, it) }
                            } else {
                                response.body()?.message?.let { setIsError(Observable.USERS, it) }
                            }

                        } else {
                            setIsError(Observable.USERS, response.toString())
                        }
                    } else {
                        setIsError(Observable.USERS, "Error  ")
                    }
                }
            })
        }

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


    private fun setIsLoading(observable: Observable) {
        when (observable) {
            Observable.GET_ASSETS -> assetsObservable.postValue(Resource.loading(null))
            Observable.GET_ASSET -> assetObservable.postValue(Resource.loading(null))
            Observable.SEND_ASSET -> assetsSendObservable.postValue(Resource.loading(null))
            //Observable.UPDATE_ASSET -> addresssActionsObservable.postValue(Resource.loading(null))
            Observable.GET_DASHBORD -> dashboardData.postValue(Resource.loading(null))
            Observable.ASSIGN -> assignAssetObservable.postValue(Resource.loading(null))
            Observable.USERS -> usersObservable.postValue(Resource.loading(null))

        }
    }


    private fun <T> setIsSuccesful(observable: Observable, data: T?) {

        when (observable) {

            Observable.GET_ASSETS -> assetsObservable.postValue(Resource.success(data as AssetsResponse))
            Observable.GET_ASSET -> assetObservable.postValue(Resource.success(data as AssetResponse))
            Observable.SEND_ASSET -> assetsSendObservable.postValue(Resource.success(data as SendAssetResponse))
            //Observable.UPDATE_ASSET -> addresssActionsObservable.postValue(Resource.success(data as AddressData))
            Observable.GET_DASHBORD -> dashboardData.postValue(Resource.success(data as DashboardResponse))
            Observable.ASSIGN -> assignAssetObservable.postValue(Resource.success(data as AssignResponse))
            Observable.USERS -> usersObservable.postValue(Resource.success(data as UsersResponse))

        }

    }

    private fun setIsError(observable: Observable, message: String) {
        when (observable) {


            Observable.GET_ASSETS -> assetsObservable.postValue(Resource.error(message, null))
            Observable.GET_ASSET -> assetObservable.postValue(Resource.error(message, null))
            Observable.SEND_ASSET -> assetsSendObservable.postValue(Resource.error(message, null))
            //Observable.UPDATE_ASSET -> defaultAddressObservable.postValue(Resource.error(message, null))
            Observable.GET_DASHBORD -> dashboardData.postValue(Resource.error(message, null))
            Observable.ASSIGN -> assignAssetObservable.postValue(Resource.error(message, null))
            Observable.USERS -> usersObservable.postValue(Resource.error(message, null))

        }
    }


    enum class Observable {
        GET_ASSETS,
        SEND_ASSET,
        UPDATE_ASSET,
        GET_DASHBORD,
        ASSIGN,
        USERS,
        GET_ASSET

    }

}
