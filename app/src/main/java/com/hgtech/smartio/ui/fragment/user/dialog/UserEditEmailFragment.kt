package com.hgtech.smartio.ui.fragment.user.dialog

import android.content.Intent
import android.text.InputType
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.hgtech.smartio.R
import com.hgtech.smartio.databinding.LayoutUserBottomDialogBinding
import com.hgtech.smartio.network.manager.UserWrapper
import com.hgtech.smartio.ui.activity.AuthenticationActivity
import com.hgtech.smartio.ui.manager.fragment.UserFragmentDialog
import com.hgtech.smartio.ui.manager.layout.getText
import com.hgtech.smartio.ui.manager.layout.isPasswordValid
import com.hgtech.smartio.ui.manager.layout.isUserNameValid
import com.hgtech.smartio.ui.manager.layout.onNextClick
import com.hgtech.smartio.ui.manager.layout.setup
import com.hgtech.smartio.ui.manager.layout.setupShowHidePass
import com.hgtech.smartio.ui.manager.layout.showError
import com.hgtech.smartio.ui.manager.layout.showHidePassword

class UserEditEmailFragment :
    UserFragmentDialog<LayoutUserBottomDialogBinding>(LayoutUserBottomDialogBinding::inflate) {

    private val args by navArgs<UserEditEmailFragmentArgs>()

    override fun setupTopLayout() {
        binding.topBar.setupLayout(R.string.update_username)
    }

    override fun showHidePassword() {
        binding.thirdLL.showHidePassword(requireContext())
    }

    override fun setupLayoutText() {
        with(binding) {
            firstLL.setup(
                requireActivity(),
                getString(R.string.usernane)
            )
            firstLL.addEmail(args.oldEmail)
            secondLL.setup(
                requireActivity(),
                getString(R.string.new_username),
                EditorInfo.IME_ACTION_NEXT,
                InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            ) {
                onNextClick(thirdLL, requireActivity())
            }
            thirdLL.setup(
                requireActivity(),
                getString(R.string.password),
                EditorInfo.IME_ACTION_DONE,
                InputType.TYPE_TEXT_VARIATION_PASSWORD,
                isLast = true
            ) {
                update()
            }
            thirdLL.setupShowHidePass()
        }
    }

    override fun setListeners() {
        binding.update.setOnClickListener {
            update()
        }
    }

    private fun update() {
        with(binding) {
            if (isValid())
                viewModel.updateUserName(
                    firstLL.getText(),
                    secondLL.getText(),
                    thirdLL.getText()
                )
        }

    }

    private fun LayoutUserBottomDialogBinding.isValid(): Boolean {

        val isEmail = firstLL.contentText.isUserNameValid()
        val isEmailNew = secondLL.contentText.isUserNameValid()
        val isPassword = thirdLL.contentText.isPasswordValid()

        return if (isEmail && isEmailNew && isPassword)
            true
        else {
            if (!isEmail)
                firstLL.contentText.showError(requireContext(), R.string.invalid_email_format)
            else if (!isEmailNew)
                secondLL.contentText.showError(requireContext(), R.string.invalid_email_format)
            else
                thirdLL.contentText.showError(requireContext(), R.string.invalid_pasword_format)
            false
        }
    }

    override fun observe() {
        with(viewModel) {
            logout.observe(viewLifecycleOwner) {
                if (it.isSuccess)
                    startActivity(
                        Intent(
                            requireContext(),
                            AuthenticationActivity::class.java
                        ).apply {
                            UserWrapper.setID(null, true)
                            requireActivity().finish()
                        })
                else
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.wrong_password),
                        Toast.LENGTH_LONG
                    ).show()
            }
            isLoading.observe(viewLifecycleOwner) {
                loadingDialog.shouldShowDialog(it)
            }
        }
    }

}