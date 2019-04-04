package com.dev.agenda.Adapters


import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dev.lishabora.Utils.OnRecyclerViewItemClick
import ke.co.calista.tafuta.R
import java.lang.ref.WeakReference


class AssetLogViewHolder(itemView: View, listener: OnRecyclerViewItemClick) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener, View.OnLongClickListener {
    override fun onLongClick(p0: View?): Boolean {
        listenerWeakReference.get()?.onLongClickListener(adapterPosition)

        return true
    }

    override fun onClick(v: View?) {
        listenerWeakReference.get()?.onClickListener(adapterPosition)

    }

    private val listenerWeakReference: WeakReference<OnRecyclerViewItemClick> = WeakReference(listener)

    var itemVew: View
    var actionNames: TextView
    var initNames: TextView
    var targetName: TextView
    var description: TextView
    var timeStamp: TextView


    init {

        this.itemVew = itemView

        actionNames = itemView.findViewById(R.id.actionNames)
        initNames = itemView.findViewById(R.id.initNames)
        targetName = itemView.findViewById(R.id.targetName)
        description = itemView.findViewById(R.id.description)
        timeStamp = itemView.findViewById(R.id.timeStamp)


    }


}
