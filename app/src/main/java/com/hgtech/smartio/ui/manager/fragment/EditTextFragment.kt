package com.hgtech.smartio.ui.manager.fragment

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.viewbinding.ViewBinding
import com.hgtech.smartio.R
import com.hgtech.smartio.databinding.LayoutTextEditBinding
import com.hgtech.smartio.ui.manager.layout.edit
import com.hgtech.smartio.ui.manager.layout.isTextValid
import com.hgtech.smartio.ui.manager.layout.setupEdit
import com.hgtech.smartio.ui.manager.layout.showError

abstract class EditTextFragment<B : ViewBinding>(
    bindingFactory: (LayoutInflater) -> B
) : AuthenticationFragment<B>(bindingFactory) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onRootClickedRestLayout()
        return binding.root
    }

    fun LayoutTextEditBinding.setup(
        hint: String,
        type: Int = InputType.TYPE_CLASS_TEXT,
        onCLick: () -> Unit
    ) {
        setupEdit(hint, requireActivity(), onCLick)
        edit.setOnClickListener {
            edit(requireActivity(), type)
            enableSaveButton()
        }
    }

    fun isInfoValid(
        fname: AppCompatEditText,
        lname: AppCompatEditText,
        phone: AppCompatEditText,
    ): Boolean {
        val isFname = fname.isTextValid()
        val isLname = lname.isTextValid()
        val isPhone = phone.isPhoneValid()

        return if (isFname && isLname && isPhone)
            true
        else {
            if (!isFname)
                fname.showError(requireContext(), R.string.fname_invalid)
            else if (!isLname)
                lname.showError(requireContext(), R.string.fname_invalid)
            else
                phone.showError(requireContext(), R.string.invalid_phone_format)
            false
        }
    }

    fun isInfoValid(
        phone: AppCompatEditText,
    ): Boolean {
        val isPhone = phone.isPhoneValid()

        return if (isPhone)
            true
        else {
            phone.showError(requireContext(), R.string.invalid_phone_format)
            false
        }
    }

    private fun AppCompatEditText.isPhoneValid(): Boolean {
        return (text.toString().length < 10 && text.toString().isNotEmpty()) || text.toString()
            .isEmpty()
    }

    abstract fun onRootClickedRestLayout()

    abstract fun enableSaveButton()
}