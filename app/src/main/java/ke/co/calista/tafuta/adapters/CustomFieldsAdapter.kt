package com.kogicodes.sokoni.adapters.V1


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.agenda.Adapters.CustomFieldsViewHolder
import com.dev.lishabora.Utils.OnRecyclerViewItemClick
import ke.co.calista.tafuta.R
import ke.co.calista.tafuta.model.asset.CustomField

class CustomFieldsAdapter(private var modelList: List<CustomField>?, private val recyclerListener: OnRecyclerViewItemClick) : RecyclerView.Adapter<CustomFieldsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomFieldsViewHolder {
        var itemView: View? = null

        itemView = LayoutInflater.from(parent.context).inflate(R.layout.custom_field_item, parent, false)



        return CustomFieldsViewHolder(itemView!!, recyclerListener)
    }


    override fun onBindViewHolder(holder: CustomFieldsViewHolder, position: Int) {

        val model = modelList!![position]
        holder.label.text = model.customFieldLabel
        holder.value.text = "" + model.customFieldValue


    }


    override fun getItemCount(): Int {
        return if (null != modelList) modelList!!.size else 0
    }


    fun refresh(modelList: List<CustomField>) {
        this.modelList = modelList
        notifyDataSetChanged()

    }
}
