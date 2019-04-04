package ke.co.calista.tafuta.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.kogicodes.sokoni.models.custom.Resource
import ke.co.calista.tafuta.model.asset.*
import ke.co.calista.tafuta.model.oauth.LoginData
import ke.co.calista.tafuta.storage.AssetRepository
import ke.co.calista.tafuta.storage.SignInRepository

class MainViewModel (application: Application) : AndroidViewModel(application){

    internal var assetRepository: AssetRepository


    private val assignAssetObservable = MediatorLiveData<Resource<AssignResponse>>()
    private val usersObservable = MediatorLiveData<Resource<UsersResponse>>()
    private val assetsObservable = MediatorLiveData<Resource<AssetsResponse>>()
    private val assetObservable = MediatorLiveData<Resource<AssetResponse>>()
    private val assetSendObservable = MediatorLiveData<Resource<SendAssetResponse>>()
    private val dashboardObservable = MediatorLiveData<Resource<DashboardResponse>>()
    internal var signInRepository: SignInRepository

    init {

        assetRepository = AssetRepository(application)
        signInRepository = SignInRepository(application)


        assignAssetObservable.addSource(assetRepository.assignAssetObservable) { data ->
            assignAssetObservable.setValue(
                data
            )
        }
        usersObservable.addSource(assetRepository.usersObservable) { data -> usersObservable.setValue(data) }
        assetsObservable.addSource(assetRepository.assetsObservable) { data -> assetsObservable.setValue(data) }
        assetObservable.addSource(assetRepository.assetObservable) { data -> assetObservable.setValue(data) }
        dashboardObservable.addSource(assetRepository.dashboardData) { data -> dashboardObservable.setValue(data) }
        assetSendObservable.addSource(assetRepository.assetsSendObservable) { data -> assetSendObservable.setValue(data) }

    }


    fun getProfile(): LiveData<LoginData> {
        return signInRepository.getProfile()
    }

    fun getProfilae(): LoginData {
        return signInRepository.fetchProfile()
    }
    fun getAssets(perPage : String,pageNo : String) {
        assetRepository.getAssets(perPage,pageNo)
    }

    fun observeAssets(): LiveData<Resource<AssetsResponse>> {
        return assetsObservable
    }

    fun getDashboard() {
        assetRepository.getDashboardData()
    }

    fun observeDashboard(): LiveData<Resource<DashboardResponse>> {
        return dashboardObservable
    }

    fun loadAsset(rawValue: String) {
        assetRepository.loadAsset(rawValue)
    }

    fun sendAsset(asset: NewAsset?): MediatorLiveData<Resource<SendAssetResponse>> {


        asset?.let { assetRepository.sendAsset(it) }

        return assetSendObservable
    }

    fun observeAsset(): LiveData<Resource<AssetResponse>> {
        return assetObservable
    }

    fun assign(
        assetId: String,
        actionId: String,
        userId: String,
        description: String
    ): MediatorLiveData<Resource<AssignResponse>> {
        //asset.id,"2",user.id,"Assinged to "+user.names
        assetRepository.assignAsset(assetId, actionId, userId, description)

        return assignAssetObservable

    }

    fun getUsers(perPage: String, pageNo: String): LiveData<Resource<UsersResponse>> {
        assetRepository.getUsers(perPage, pageNo)
        return usersObservable
    }


}

