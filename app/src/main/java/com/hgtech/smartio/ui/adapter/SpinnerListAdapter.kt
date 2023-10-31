package com.hgtech.smartio.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.hgtech.smartio.databinding.LayoutSpinnerItemBinding

class SpinnerListAdapter<T : Any>(
    private val values: List<T>,
    private val onAdd: (view: AppCompatTextView, item: T) -> Unit,
    private val onItemClicked: (item: T) -> Unit
) :
    RecyclerView.Adapter<SpinnerListAdapter<T>.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        return ViewHolder(
            LayoutSpinnerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        with(holder.name) {
            onAdd.invoke(this, item)
            setOnClickListener {
                onItemClicked.invoke(item)
            }
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: LayoutSpinnerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name = binding.root
    }
}