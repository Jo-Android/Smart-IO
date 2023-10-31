package com.hgtech.smartio.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.hgtech.smartio.R
import com.hgtech.smartio.databinding.ItemsListUserBinding
import com.hgtech.smartio.network.manager.UserWrapper.checkActive
import com.hgtech.smartio.network.model.response.user.manager.Users

class UserRecyclerViewAdapter(
    private val values: ArrayList<Users>,
    private val isRegular: Boolean,
    private val onActiveClicked: (user: Users, position: Int) -> Unit,
    private val onAssignClicked: (user: Users, position: Int) -> Unit,
) :
    RecyclerView.Adapter<UserRecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemsListUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        with(holder) {
            with(isActive) {
                isVisible = !isRegular
                setImageResource(getState(item.isActive.checkActive()))
                setOnClickListener {
                    onActiveClicked(item, absoluteAdapterPosition)
                }
            }
            assign.setImageResource(getAssign(item.managerID == null))
            assign.setOnClickListener {
                onAssignClicked.invoke(item, absoluteAdapterPosition)
            }
            name.text = StringBuilder(item.firstName)
                .append(" ")
                .append(item.lastName)
            email.text = item.userName

        }
    }

    private fun getAssign(isAssigned: Boolean): Int {
        return if (isAssigned)
            R.drawable.person_add_white_24dp
        else
            R.drawable.person_remove_white_24dp
    }

    override fun getItemCount(): Int = values.size

    fun remove(position: Int) {
        values.removeAt(position)
        notifyItemRemoved(position)
    }


    inner class ViewHolder(binding: ItemsListUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name = binding.name
        val email = binding.userName
        val isActive = binding.isActive
        val assign = binding.add
    }

}

private fun getState(isActive: Boolean): Int {
    return if (isActive)
        R.drawable.user_active
    else
        R.drawable.user_not_active
}
