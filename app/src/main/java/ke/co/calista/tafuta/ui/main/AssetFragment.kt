package ke.co.calista.tafuta.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.lishabora.Utils.OnRecyclerViewItemClick
import com.google.android.material.snackbar.Snackbar
import com.kogicodes.sokoni.adapters.V1.AssetsLogAdapter
import com.kogicodes.sokoni.adapters.V1.AssetsValueAdapter
import com.kogicodes.sokoni.models.custom.Status
import ke.co.calista.tafuta.R
import ke.co.calista.tafuta.model.asset.Asset
import ke.co.calista.tafuta.model.asset.AssetLog
import ke.co.calista.tafuta.model.asset.RelatedData
import kotlinx.android.synthetic.main.fragment_asset.*


class AssetFragment : Fragment() {
    private lateinit var assetValueAdapter: AssetsValueAdapter
    private lateinit var assetLogAdapter: AssetsLogAdapter
    private lateinit var relatedData: ArrayList<RelatedData>
    private lateinit var assetLog: ArrayList<AssetLog>
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


         asset=(arguments?.getSerializable("asset")) as Asset





        loadData()
        if (asset?.assetLog == null || (asset?.assetLog as ArrayList<AssetLog>).size > 0) {
            asset?.scanCode?.let { viewModel.loadAsset(it) }
        }

        viewModel.observeAsset().observe(this, Observer {

            setStatus(it.message, it.status)
            asset = it.data?.asset
            loadData()
        })


    }

    private fun setStatus(message: String?, status: Status) {

        if (status == Status.LOADING) {
            avi.visibility = View.VISIBLE
            avi.smoothToShow()
            activity?.window?.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        } else if (status == Status.ERROR || status == Status.SUCCESS) {
            avi.smoothToHide()
            avi.visibility = View.GONE
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }

        if (status == Status.ERROR) {
            if (message != null) {
                view?.let { Snackbar.make(it, message, Snackbar.LENGTH_LONG).show() }
            }
        }


    }


    private fun loadData() {

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
        initValuesLogs()


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

    private fun initValuesLogs() {

        if (asset != null && asset?.assetLog != null) {
            assetLog = asset!!.assetLog!!
        } else {
            assetLog = ArrayList()
        }


        if (assetLog == null) {
            assetLog = ArrayList()
        }
        assetLogAdapter = context?.let {
            activity?.let { it1 ->

                AssetsLogAdapter(assetLog, object : OnRecyclerViewItemClick {
                    override fun onClickListener(position: Int) {


                    }

                    override fun onLongClickListener(position: Int) {
                    }
                })
            }
        }!!
        recycler_view_logs.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycler_view_logs.itemAnimator = DefaultItemAnimator()
        recycler_view_logs.adapter = assetLogAdapter
        assetLogAdapter.notifyDataSetChanged()


    }


}
