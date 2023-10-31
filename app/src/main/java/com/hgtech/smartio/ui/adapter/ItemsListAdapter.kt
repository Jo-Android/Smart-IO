package com.hgtech.smartio.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.hgtech.smartio.databinding.ItemsListItemBinding

class ItemsListAdapter<T : Any>(
    private val values: List<T>,
    private val isDeleteEnable: Boolean = false,
    private val onAdd: (name: AppCompatTextView, icon: AppCompatImageView, item: T) -> Unit,
    private val onItemClicked: (item: T) -> Unit,
    private val onDelete: (item: T) -> Unit,
) : RecyclerView.Adapter<ItemsListAdapter<T>.ViewHolder>() {

    inner class ViewHolder(binding: ItemsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name = binding.name
        val delete = binding.delete
        val icon = binding.icon
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemsListItemBinding.inflate(
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
        with(holder) {
            val item = values[absoluteAdapterPosition]
            onAdd.invoke(name, icon, item)
            delete.isVisible = isDeleteEnable
            delete.setOnClickListener {
                onDelete.invoke(item)
            }
            itemView.setOnClickListener {
                onItemClicked.invoke(item)
            }
        }
    }

}
