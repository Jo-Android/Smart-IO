package com.hgtech.smartio.ui.manager.fragment

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.hgtech.smartio.databinding.LayoutTextWithHintBinding
import com.hgtech.smartio.ui.manager.layout.show
import com.hgtech.smartio.ui.viewmodel.user.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class UserFragmentDialog<B : ViewBinding>(
    bindingFactory: (LayoutInflater) -> B
) : BaseBottomSheet<B>(bindingFactory) {

    val viewModel by viewModel<UserViewModel>()


    override fun setupLayout() {
        setupLayoutText()
        showHidePassword()
    }


    abstract fun showHidePassword()

    abstract fun setupLayoutText()

    fun LayoutTextWithHintBinding.addEmail(email: String) {
        show()
        with(contentText) {
            isFocusable = false
            isFocusableInTouchMode = false
            setText(email)
        }


    }

}