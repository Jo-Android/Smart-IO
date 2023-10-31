package com.hgtech.smartio.ui.manager.fragment

import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.hgtech.smartio.R
import com.hgtech.smartio.databinding.LayoutTopBarBinding
import com.hgtech.smartio.ui.adapter.ItemsListAdapter

abstract class AssignFragment<B : ViewBinding, T : Any>(
    bindingFactory: (LayoutInflater) -> B,
) : BaseFragment<B>(bindingFactory) {

    override fun initLayout() {
        setupTopBar()
    }


    override fun onResume() {
        super.onResume()
        setupListItems()
    }

    abstract fun setupListItems()

    abstract fun setupTopBar()

    fun LayoutTopBarBinding.setup(
        name: Int,
        addIcon: Int = R.drawable.ic_baseline_add_box_24,
        isAddVisible: Boolean = true
    ) {
        title.text = getString(name)
        add.isVisible = isAddVisible
        add.setImageResource(addIcon)
        add.setOnClickListener {
            addItem()
        }
        back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    abstract fun addItem()

    fun RecyclerView.setup(list: List<T>, isDeleteEnable: Boolean = false) {
        layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = ItemsListAdapter(
            list,
            isDeleteEnable,
            onAdd = { name, icon, item ->
                onBindItem(name, icon, item)
            },
            onItemClicked = {
                onItemSelected(it)
            },
            onDelete = {}
        )
    }

    abstract fun onBindItem(name: AppCompatTextView, icon: AppCompatImageView, item: T)

    abstract fun onItemSelected(item: T)

    abstract fun onDeleteItem(item: T)
}
