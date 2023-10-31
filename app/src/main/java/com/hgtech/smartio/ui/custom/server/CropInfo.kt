package com.hgtech.smartio.ui.custom.server

import android.text.InputType
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import com.hgtech.smartio.R
import com.hgtech.smartio.databinding.LayoutCropInfoBinding
import com.hgtech.smartio.databinding.LayoutTextEditBinding
import com.hgtech.smartio.network.model.response.crop.Crop
import com.hgtech.smartio.ui.manager.layout.edit
import com.hgtech.smartio.ui.manager.layout.getText
import com.hgtech.smartio.ui.manager.layout.resetLayout
import com.hgtech.smartio.ui.manager.layout.setupEdit

class CropInfo(
    private val binding: LayoutCropInfoBinding,
    private val activity: FragmentActivity,
    private val updateName: (name: String) -> Unit,
    private val updateType: () -> Unit,
) {

    private var isManager = false


    fun setupLayout() {
        with(binding) {
            root.isVisible = false
            cropName.setup(activity.getString(R.string.crop_name), EditorInfo.TYPE_CLASS_TEXT) {
                cropName.isDataValid(R.string.invalid_crop, updateName)
            }

            cropType.setup(
                activity.getString(R.string.crop_type),
                EditorInfo.TYPE_CLASS_TEXT,
                isType = true
            ) {
                updateType.invoke()
            }
        }
    }

    fun displayInfo(crop: Crop, isManager: Boolean) {
        this.isManager = isManager
        with(binding) {
            root.isVisible = true
            cropName.contentText.setText(crop.name)
            cropType.contentText.setText(crop.category.Category)
            cropType.edit.isVisible = false
            cropName.edit.isVisible = false
        }
    }

    private fun LayoutTextEditBinding.setup(
        hint: String,
        type: Int = InputType.TYPE_CLASS_TEXT,
        isEdit: Boolean = true,
        isType: Boolean = false,
        onCLick: () -> Unit,
    ) {
        setupEdit(hint, activity, onCLick, isEdit)
        edit.setOnClickListener {
            if (!isType) {
                edit(activity, type)
                isEditInfoLL(true)
            }
        }
    }

    private fun LayoutTextEditBinding.isDataValid(errorMessage: Int, save: (name: String) -> Unit) {
        if (getText().length > 3) {
            isEditInfoLL(false)
            save.invoke(getText())
        } else
            Toast.makeText(activity, activity.getString(errorMessage), Toast.LENGTH_LONG).show()
    }

    private fun isEditInfoLL(isVisible: Boolean) {
        if (!isVisible)
            resetLayout()
        with(binding) {
            if (isManager) {
                cropType.edit.isVisible = !isVisible
                cropName.edit.isVisible = !isVisible
            } else {
                cropType.edit.isVisible = false
                cropName.edit.isVisible = false
            }
        }
    }

    private fun resetLayout() {
        with(binding) {
            cropName.resetLayout(activity)
            cropType.resetLayout(activity)
        }
    }
}