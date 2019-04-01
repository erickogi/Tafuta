package com.kogicodes.sokoni.adapters.V1


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.agenda.Adapters.AssetViewHolder
import com.dev.lishabora.Utils.OnRecyclerViewItemClick
import ke.co.calista.tafuta.R
import ke.co.calista.tafuta.model.asset.Asset

class AssetsAdapter(private var modelList: List<Asset>?, private val recyclerListener: OnRecyclerViewItemClick) : RecyclerView.Adapter<AssetViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetViewHolder {
        var itemView: View? = null

        itemView = LayoutInflater.from(parent.context).inflate(R.layout.asset_item, parent, false)



        return AssetViewHolder(itemView!!, recyclerListener)
    }


    override fun onBindViewHolder(holder: AssetViewHolder, position: Int) {

        val model = modelList!![position]
        holder.assetName.text = model.names
        holder.assetType.text = "" + model.typeName
        holder.assetStatus.text = "" + model.statusName


    }


    override fun getItemCount(): Int {
        return if (null != modelList) modelList!!.size else 0
    }


    fun refresh(modelList: List<Asset>) {
        this.modelList = modelList
        notifyDataSetChanged()

    }
}
