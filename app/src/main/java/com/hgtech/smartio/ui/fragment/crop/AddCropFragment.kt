package com.hgtech.smartio.ui.fragment.crop

import android.app.Dialog
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.navigation.fragment.findNavController
import com.hgtech.smartio.R
import com.hgtech.smartio.databinding.FragmentAddCropBinding
import com.hgtech.smartio.network.model.response.crop.type.CropType
import com.hgtech.smartio.ui.manager.fragment.AddTypeFragment
import com.hgtech.smartio.ui.manager.layout.createDialog
import com.hgtech.smartio.ui.manager.layout.getText
import com.hgtech.smartio.ui.viewmodel.crop.AddCropViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddCropFragment :
    AddTypeFragment<FragmentAddCropBinding, CropType>(FragmentAddCropBinding::inflate) {
    private val viewModel by viewModel<AddCropViewModel>()
    override fun setupLayout() {
        binding.addItem.setupLayout(R.string.add_crops)
    }


    override fun setupTopLayout() {
        binding.addItem.setupTopLayout(R.string.add_crops)
    }

    override fun setListeners() {
        with(binding.addItem) {
            setListeners(R.string.invalid_crop_type, R.string.invalid_crop) {
                if (viewModel.typeId != null)
                    viewModel.addCrop(itemName.getText())
                else
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.error_get_crop_type),
                        Toast.LENGTH_LONG
                    )
                        .show()
            }
        }
    }

    override fun getList() {
        viewModel.getCropTypeList()
    }

    override fun bindTypeToList(view: AppCompatTextView, item: CropType) {
        view.text = item.Category
    }

    override fun onTypeClicked(it: CropType) {
        viewModel.typeId = it.id
        spinner.addSelectedItem(it.Category)
    }

    override fun addTypeLayout() {
        createDialog(requireActivity(), R.layout.layout_add_crop_type).apply {
            findViewById<AppCompatImageView>(R.id.imageView3).setOnClickListener {
                dismiss()
            }
            val type = findViewById<View>(R.id.type)
            type.findViewById<AppCompatTextView>(R.id.hintText).text =
                getString(R.string.add_crop_type)
            val content = type.findViewById<AppCompatEditText>(R.id.contentText)
            content.setOnEditorActionListener { _, _, _ ->
                content.isAddValid(this)
                false
            }
            findViewById<Button>(R.id.add).setOnClickListener {
                content.isAddValid(this)
            }
        }
    }

    private fun AppCompatEditText.isAddValid(dialog: Dialog) {
        if (text.toString().length > 3) {
            spinner.reset()
            viewModel.addType(text.toString())
            dialog.dismiss()
        } else
            error = context.getString(R.string.invalid_text)
    }

    override fun observe() {
        with(viewModel) {
            cropTypeList.observe(viewLifecycleOwner) {
                setupSpinner(it.data ?: ArrayList(), R.string.crop_type, R.string.add_crop_type)
            }
            addTypeResponse.observe(viewLifecycleOwner) {
                if (it.data != null) {
                    viewModel.typeId = it.data.id
                    spinner.addSelectedItem(it.data.Category)
                }
            }
            addCropResponse.observe(viewLifecycleOwner) {
                if (it.data != null) {
                    findNavController().apply {
                        previousBackStackEntry?.savedStateHandle?.set(CROP_ADDED, "")
                        popBackStack()
                    }
                }
            }
            isLoading.observe(viewLifecycleOwner) {
                loadingDialog.shouldShowDialog(it)
            }
        }
    }

    companion object {
        const val CROP_ADDED = "Add Crop Dialog"
    }

}