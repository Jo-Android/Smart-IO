package com.hgtech.smartio.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hgtech.smartio.databinding.ItemsListStateIrrigationBinding
import com.hgtech.smartio.network.model.response.irrigation.Irrigation

class StateIrrigationAdapter(
    private val values: List<Irrigation>,
    private val onItemClicked: (id: String, isManager: Boolean) -> Unit
) :
    RecyclerView.Adapter<StateIrrigationAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemsListStateIrrigationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val irrigation = binding.root
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ItemsListStateIrrigationBinding.inflate(
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
            IrrigationAdapter(irrigation, state, onItemClicked).also {
                holder.irrigation.adapter = it
//                holder.irrigation.layoutManager=LinearLayoutManager(holder.)
            }
        }
    }

    private fun String.time(): String {
        return if (this.length > 11)
            "Today: ${this.substring(11, this.length)}"
        else
            this
    }
}