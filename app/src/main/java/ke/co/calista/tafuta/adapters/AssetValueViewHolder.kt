package com.dev.agenda.Adapters


import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.dev.lishabora.Utils.OnRecyclerViewItemClick
import ke.co.calista.tafuta.R

import java.lang.ref.WeakReference


class AssetValueViewHolder(itemView: View, listener: OnRecyclerViewItemClick) : RecyclerView.ViewHolder(itemView), View.OnClickListener,View.OnLongClickListener {
    override fun onLongClick(p0: View?): Boolean {
        listenerWeakReference.get()?.onLongClickListener(adapterPosition)

        return true
    }

    override fun onClick(v: View?) {
        listenerWeakReference.get()?.onClickListener(adapterPosition)

    }

    private val listenerWeakReference: WeakReference<OnRecyclerViewItemClick> = WeakReference(listener)

    var itemVew: View
    var label: TextView
    var value: TextView


    init {

        this.itemVew = itemView

        label = itemView.findViewById(R.id.label)
        value = itemView.findViewById(R.id.value)


    }


}
