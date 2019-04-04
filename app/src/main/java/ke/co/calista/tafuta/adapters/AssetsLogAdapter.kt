package com.kogicodes.sokoni.adapters.V1


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.agenda.Adapters.AssetLogViewHolder
import com.dev.lishabora.Utils.OnRecyclerViewItemClick
import ke.co.calista.tafuta.R
import ke.co.calista.tafuta.model.asset.AssetLog

class AssetsLogAdapter(private var modelList: List<AssetLog>?, private val recyclerListener: OnRecyclerViewItemClick) :
    RecyclerView.Adapter<AssetLogViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetLogViewHolder {
        var itemView: View? = null

        itemView = LayoutInflater.from(parent.context).inflate(R.layout.asset_log_item, parent, false)



        return AssetLogViewHolder(itemView!!, recyclerListener)
    }


    override fun onBindViewHolder(holder: AssetLogViewHolder, position: Int) {

        val model = modelList!![position]
        holder.actionNames.text = model.actionNames
        holder.initNames.text = model.initNames
        holder.targetName.text = model.targetNames
        holder.description.text = model.description
        holder.timeStamp.text = model.timeStamp


    }


    override fun getItemCount(): Int {
        return if (null != modelList) modelList!!.size else 0
    }


    fun refresh(modelList: List<AssetLog>) {
        this.modelList = modelList
        notifyDataSetChanged()

    }
}
