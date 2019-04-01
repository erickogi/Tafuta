package com.dev.agenda.Adapters


import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.dev.lishabora.Utils.OnRecyclerViewItemClick
import ke.co.calista.tafuta.R

import java.lang.ref.WeakReference


class AssetViewHolder(itemView: View, listener: OnRecyclerViewItemClick) : RecyclerView.ViewHolder(itemView), View.OnClickListener,View.OnLongClickListener {
    override fun onLongClick(p0: View?): Boolean {
        listenerWeakReference.get()?.onLongClickListener(adapterPosition)

        return true
    }

    override fun onClick(v: View?) {
        listenerWeakReference.get()?.onClickListener(adapterPosition)

    }

    private val listenerWeakReference: WeakReference<OnRecyclerViewItemClick> = WeakReference(listener)

    var itemVew: View
    var assetName: TextView
    var assetType: TextView
    var assetStatus: TextView


    init {

        this.itemVew = itemView

        assetName = itemView.findViewById(R.id.asset_name)
        assetType = itemView.findViewById(R.id.asset_type)
        assetStatus = itemView.findViewById(R.id.asset_status)


        itemView.setOnClickListener(this)
    }


}
