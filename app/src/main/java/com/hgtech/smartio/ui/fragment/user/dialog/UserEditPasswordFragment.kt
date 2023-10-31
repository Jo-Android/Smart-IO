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
import com.hgtech.smartio.ui.manager.layout.*
import com.hgtech.smartio.ui.manager.layout.showHidePassword

class UserEditPasswordFragment :
    UserFragmentDialog<LayoutUserBottomDialogBinding>(LayoutUserBottomDialogBinding::inflate) {

    private val args by navArgs<UserEditEmailFragmentArgs>()

    override fun setupTopLayout() {
        binding.topBar.setupLayout(R.string.update_password)
    }

    override fun showHidePassword() {
        with(binding) {
            secondLL.showHidePassword(requireContext())
            thirdLL.showHidePassword(requireContext())
        }
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
                getString(R.string.new_password),
                EditorInfo.IME_ACTION_NEXT,
                InputType.TYPE_TEXT_VARIATION_PASSWORD
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
            secondLL.setupShowHidePass()
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
                viewModel.updatePassword(
                    firstLL.getText(),
                    secondLL.getText()
                )
        }

    }

    private fun LayoutUserBottomDialogBinding.isValid(): Boolean {

        val isEmail = firstLL.contentText.isUserNameValid()
        val isPassword = secondLL.contentText.isPasswordValid()
        val isPasswordNew = thirdLL.contentText.isPasswordValid()
        val isMatching = isPaswordMatch(secondLL.contentText, thirdLL.contentText)

        return if (isEmail && isPasswordNew && isPassword && isMatching)
            true
        else {
            if (!isEmail)
                firstLL.contentText.showError(requireContext(), R.string.invalid_email_format)
            else if (!isPassword)
                secondLL.contentText.showError(requireContext(), R.string.invalid_pasword_format)
            else if (!isPasswordNew)
                thirdLL.contentText.showError(requireContext(), R.string.invalid_pasword_format)
            else {
                secondLL.contentText.showError(requireContext(), R.string.password_not_matching)
                thirdLL.contentText.error = getString(R.string.password_not_matching)
            }
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