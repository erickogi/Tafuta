package ke.co.calista.tafuta.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.kogicodes.sokoni.models.custom.Resource
import com.kogicodes.sokoni.models.v1.oauth.Oauth
import ke.co.calista.tafuta.model.asset.AssetsResponse
import ke.co.calista.tafuta.model.oauth.LoginData
import ke.co.calista.tafuta.storage.AssetRepository
import ke.co.calista.tafuta.storage.RecoverPasswordUpRepository
import ke.co.calista.tafuta.storage.SignInRepository
import ke.co.calista.tafuta.storage.SignUpRepository

class MainViewModel (application: Application) : AndroidViewModel(application){

    internal var assetRepository: AssetRepository


    private val assetObservable = MediatorLiveData<Resource<AssetsResponse>>()

    init {

        assetRepository = AssetRepository(application)


        assetObservable.addSource(assetRepository.assetsObservable) { data -> assetObservable.setValue(data) }

    }



    fun getAssets(perPage : String,pageNo : String) {
        assetRepository.getAssets(perPage,pageNo)
    }

    fun observeAssets(): LiveData<Resource<AssetsResponse>> {
        return assetObservable
    }

    fun loadAsset() {
        assetRepository.loadAsset()
    }


}

