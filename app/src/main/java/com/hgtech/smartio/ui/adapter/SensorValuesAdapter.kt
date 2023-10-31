package com.hgtech.smartio.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hgtech.smartio.databinding.ItemsListSensorValuesBinding
import com.hgtech.smartio.network.model.response.values.SensorValues

class SensorValuesAdapter(
    private val values: List<SensorValues>,
) :
    RecyclerView.Adapter<SensorValuesAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemsListSensorValuesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val value = binding.value
        val date = binding.date
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ItemsListSensorValuesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return values.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        values[holder.bindingAdapterPosition].apply {
            holder.date.text = date
            holder.value.text = value.toString()
        }
    }


}