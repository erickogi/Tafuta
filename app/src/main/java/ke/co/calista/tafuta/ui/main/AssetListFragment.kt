package ke.co.calista.tafuta.ui.main

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.lishabora.Utils.OnRecyclerViewItemClick
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScannerBuilder
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.material.snackbar.Snackbar
import com.kogicodes.sokoni.adapters.V1.AssetsAdapter
import com.kogicodes.sokoni.models.custom.Resource
import com.kogicodes.sokoni.models.custom.Status
import ke.co.calista.tafuta.ui.AddAssetActivity
import ke.co.calista.tafuta.ui.MainActivity
import ke.co.calista.tafuta.R
import ke.co.calista.tafuta.model.asset.Asset
import ke.co.calista.tafuta.model.asset.AssetsResponse
import kotlinx.android.synthetic.main.assetlist_fragment.*


class AssetListFragment : Fragment() {

    companion object {
        fun newInstance() = AssetListFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var assetsAdapter: AssetsAdapter
    private lateinit var assetResponse: AssetsResponse

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.assetlist_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        initView()
        init()
        observers()


    }
    private fun setUpAssets(data: AssetsResponse?) {
        assetResponse = data!!
        assetResponse?.let { it.assets?.let { it1 -> assetsAdapter.refresh(it1) } }

    }

    private fun initView() {
        assetResponse = AssetsResponse()
        if(assetResponse.assets==null) {
            assetResponse.assets=ArrayList()
        }
            assetsAdapter = context?.let {
                activity?.let { it1 ->

                    AssetsAdapter(assetResponse.assets as ArrayList<Asset>, object : OnRecyclerViewItemClick {
                        override fun onClickListener(position: Int) {
                            activity?.supportFragmentManager?.beginTransaction()
                                ?.add(R.id.container, AssetFragment.newInstance((assetResponse.assets as ArrayList<Asset>).get(position)))
                                ?.commitNow()

                        }

                        override fun onLongClickListener(position: Int) {
                        }
                    })
                }
            }!!
            recycler_view.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            recycler_view.itemAnimator = DefaultItemAnimator()
            recycler_view.adapter = assetsAdapter
            assetsAdapter.notifyDataSetChanged()






    }

    private fun setStatus(data: Resource<AssetsResponse>) {
        main.visibility = View.GONE
        main.visibility = View.VISIBLE

        val status: Status = data.status

        if (status == Status.LOADING) {
           // avi.visibility = View.VISIBLE
           // avi.smoothToShow()
            activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        } else if (status == Status.ERROR || status == Status.SUCCESS) {
           // avi.smoothToHide()
           // avi.visibility = View.GONE
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }

        if (status == Status.ERROR) {
            if (data.message != null) {
            //    empty_text.text = data.message
                view?.let { Snackbar.make(it, data.message.toString(), Snackbar.LENGTH_LONG).show() }
            }

           // empty_layout.visibility = View.VISIBLE
           // main.visibility = View.GONE
           // empty_button.setOnClickListener({ init() })


        }


    }

    fun init() {


        viewModel.getAssets("100","1")


    }

    fun observers() {
        viewModel.observeAssets().observe(this, androidx.lifecycle.Observer {
            setStatus(it)

            if (it.status == Status.SUCCESS) {
                setUpAssets(it.data)
            }

        })


    }


}
