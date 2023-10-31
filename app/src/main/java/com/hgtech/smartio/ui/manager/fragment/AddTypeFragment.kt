package com.hgtech.smartio.ui.manager.fragment

import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.viewbinding.ViewBinding
import com.hgtech.smartio.databinding.LayoutAddItemBinding
import com.hgtech.smartio.ui.custom.SpinnerItem
import com.hgtech.smartio.ui.manager.layout.getText
import com.hgtech.smartio.ui.manager.layout.resetLayout
import com.hgtech.smartio.ui.manager.layout.setup

abstract class AddTypeFragment<B : ViewBinding, T : Any>(
    bindingFactory: (LayoutInflater) -> B
) : BaseBottomSheet<B>(bindingFactory) {


    lateinit var spinner: SpinnerItem<T>

    fun LayoutAddItemBinding.setupLayout(hint: Int) {
        spinner = SpinnerItem(requireActivity(), binding = type)
        getList()
        itemName.setup(
            requireActivity(),
            getString(hint),
            EditorInfo.IME_ACTION_DONE
        ) {
            resetLayout()
        }
        root.setOnClickListener {
            resetLayout()
        }
    }


    private fun LayoutAddItemBinding.resetLayout() {
        com.hgtech.smartio.ui.manager.layout.hideKeyboard(requireActivity())
        itemName.resetLayout()
        spinner.showSpinnerList(false)
    }

    fun LayoutAddItemBinding.setupTopLayout(title: Int) {
        topBar.setupLayout(title)
    }

    fun LayoutAddItemBinding.setListeners(
        errorType: Int,
        errorItem: Int,
        isValid: () -> Unit
    ) {
        type.root.setOnClickListener {
            getList()
        }
        add.setOnClickListener {
            if (isDataValid(errorType, errorItem))
                isValid.invoke()
        }
    }

    abstract fun getList()


    fun setupSpinner(list: List<T>, title: Int, add: Int) {
        spinner.setup(
            list,
            getString(title),
            getString(add),
            bindText = { view, item ->
                bindTypeToList(view, item)
            },
            onItemClicked = {
                onTypeClicked(it)
            },
            onAddClicked = {
                addTypeLayout()
            })
    }

    abstract fun addTypeLayout()

    abstract fun onTypeClicked(it: T)

    abstract fun bindTypeToList(view: AppCompatTextView, item: T)

    private fun LayoutAddItemBinding.isDataValid(
        errorType: Int,
        errorItem: Int,
    ): Boolean {
        return if (itemName.getText().length > 3 && type.contentText.text.toString().length > 3) {
            true
        } else {
            if (itemName.getText().length <= 3)
                Toast.makeText(requireContext(), getString(errorItem), Toast.LENGTH_LONG)
                    .show()
            else
                Toast.makeText(
                    requireContext(),
                    getString(errorType),
                    Toast.LENGTH_LONG
                ).show()
            false
        }
    }
}