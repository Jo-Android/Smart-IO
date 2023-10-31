package com.hgtech.smartio.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hgtech.smartio.R
import com.hgtech.smartio.databinding.ItemsListIrrigationBinding
import com.hgtech.smartio.network.manager.UserWrapper.checkActive
import com.hgtech.smartio.network.model.response.assign.state.State
import com.hgtech.smartio.network.model.response.irrigation.IrrigationInfo

class IrrigationAdapter(
    private val values: List<IrrigationInfo>,
    private val state: State,
    private val onItemClicked: (id: String, isManager: Boolean) -> Unit
) :
    RecyclerView.Adapter<IrrigationAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemsListIrrigationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val plant = binding.cropName
        val sensor = binding.sensorName
        val time = binding.date
        val staus = binding.status
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ItemsListIrrigationBinding.inflate(
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
            holder.plant.text = state.sensor_crop.crop.name
            holder.sensor.text = state.sensor_user.sensor.type.type
            holder.staus.setImageResource(
                if (endDate == null)
                    R.drawable.watering_can
                else
                    R.drawable.plant_white_24dp
            )
            holder.time.text = (endDate ?: startDate).time()
            holder.itemView.setOnClickListener {
                onItemClicked.invoke(state.id, state.sensor_user.user.isManager.checkActive())
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