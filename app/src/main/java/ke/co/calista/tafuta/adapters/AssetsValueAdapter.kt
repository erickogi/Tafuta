package com.kogicodes.sokoni.adapters.V1


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.agenda.Adapters.AssetValueViewHolder
import com.dev.agenda.Adapters.AssetViewHolder
import com.dev.lishabora.Utils.OnRecyclerViewItemClick
import ke.co.calista.tafuta.R
import ke.co.calista.tafuta.model.asset.Asset
import ke.co.calista.tafuta.model.asset.RelatedData

class AssetsValueAdapter(private var modelList: List<RelatedData>?, private val recyclerListener: OnRecyclerViewItemClick) : RecyclerView.Adapter<AssetValueViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetValueViewHolder {
        var itemView: View? = null

        itemView = LayoutInflater.from(parent.context).inflate(R.layout.asset_values_item, parent, false)



        return AssetValueViewHolder(itemView!!, recyclerListener)
    }


    override fun onBindViewHolder(holder: AssetValueViewHolder, position: Int) {

        val model = modelList!![position]
        holder.label.text = model.label
        holder.value.text = "" + model.value


    }


    override fun getItemCount(): Int {
        return if (null != modelList) modelList!!.size else 0
    }


    fun refresh(modelList: List<RelatedData>) {
        this.modelList = modelList
        notifyDataSetChanged()

    }
}
