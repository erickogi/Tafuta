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
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.lishabora.Utils.OnRecyclerViewItemClick
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScannerBuilder
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.material.snackbar.Snackbar
import com.kogicodes.sokoni.adapters.V1.AssetsAdapter
import com.kogicodes.sokoni.adapters.V1.AssetsValueAdapter
import ke.co.calista.tafuta.ui.AddAssetActivity
import ke.co.calista.tafuta.ui.MainActivity
import ke.co.calista.tafuta.R
import ke.co.calista.tafuta.model.asset.Asset
import ke.co.calista.tafuta.model.asset.AssetsResponse
import ke.co.calista.tafuta.model.asset.RelatedData

import kotlinx.android.synthetic.main.fragment_asset.*


class AssetFragment : Fragment() {
    private lateinit var assetValueAdapter: AssetsValueAdapter
    private lateinit var relatedData: ArrayList<RelatedData>
    var asset: Asset?=null
    companion object {
        fun newInstance(asset: Asset) = AssetFragment().apply {
            arguments = Bundle().apply {
                putSerializable("asset", asset)

            }


        }
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_asset, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

       // billingMethod = arguments?.getSerializable("billingMethod") as BillingMethod


         asset=(arguments?.getSerializable("asset")) as Asset




        code.text=asset?.code
        asset_serial.text=asset?.sNo
        asset_name.text=asset?.names
        asset_condition.text=asset?.stateCondition
        status.text=asset?.statusName
        createdBy.text=asset?.createByNames
        typeName.text=asset?.typeName
        departmentName.text=asset?.departmentName
        categoryName.text=asset?.categoryName


        initValues()






    }

    private fun initValues() {

        if(asset!=null&&asset?.relatedData!=null) {
            relatedData = asset!!.relatedData!!
        }else{
            relatedData= ArrayList()
        }


        if(relatedData==null) {
            relatedData=ArrayList()
        }
        assetValueAdapter = context?.let {
            activity?.let { it1 ->

                AssetsValueAdapter(relatedData, object : OnRecyclerViewItemClick {
                    override fun onClickListener(position: Int) {


                    }

                    override fun onLongClickListener(position: Int) {
                    }
                })
            }
        }!!
        recycler_view.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycler_view.itemAnimator = DefaultItemAnimator()
        recycler_view.adapter = assetValueAdapter
        assetValueAdapter.notifyDataSetChanged()


    }


}
