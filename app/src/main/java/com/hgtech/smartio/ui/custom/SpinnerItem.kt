package com.hgtech.smartio.ui.custom

import android.util.Log
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hgtech.smartio.R
import com.hgtech.smartio.databinding.LayoutSpinnerBinding
import com.hgtech.smartio.ui.adapter.SpinnerListAdapter
import com.hgtech.smartio.ui.manager.layout.hideKeyboard

class SpinnerItem<T : Any>(
    private val activity: FragmentActivity,
    private val binding: LayoutSpinnerBinding,
) {

    private var isSelected = false

    init {
        showSpinnerList(false)
    }

    fun setup(
        list: List<T>,
        hint: String,
        hintAdd: String,
        bindText: (view: AppCompatTextView, item: T) -> Unit,
        onItemClicked: (item: T) -> Unit,
        onAddClicked: () -> Unit
    ) {
        with(binding) {
            hintText.text = hint
            addSpinnerItem.text = hintAdd
            spinnerItem.setupAdapter(list, bindText, onItemClicked)
            spinner.setOnClickListener {
                Log.e("Spinner", "Root Clicked")
                showSpinnerList(!isSelected)
            }
            addSpinnerItem.setOnClickListener {
                onAddClicked.invoke()
            }
        }
    }

    fun showSpinnerList(isVisible: Boolean) {
        isSelected = isVisible
        Log.e("Spinner", "is List Visible $isSelected")
        binding.soinnerArrow.setImageResource(
            if (isSelected) {
                hideKeyboard(activity)
                R.drawable.ic_baseline_keyboard_arrow_up_24
            } else
                R.drawable.ic_baseline_keyboard_arrow_down_24
        )
        binding.spinnerChild.isVisible = isSelected
    }

    private fun RecyclerView.setupAdapter(
        list: List<T>,
        bindText: (view: AppCompatTextView, item: T) -> Unit,
        onItemClicked: (item: T) -> Unit
    ) {
        SpinnerListAdapter(
            list,
            bindText,
            onItemClicked
        ).apply {
            this@setupAdapter.adapter = this
        }
        layoutManager = LinearLayoutManager(this@SpinnerItem.activity)
    }

    fun addSelectedItem(text: String) {
        with(binding) {
            hintText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
            contentText.isVisible = true
            contentText.text = text
            showSpinnerList(false)
        }
    }

    fun reset() {
        with(binding) {
            hintText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
            contentText.isVisible = false
            contentText.text = ""
            showSpinnerList(false)
        }
    }


}