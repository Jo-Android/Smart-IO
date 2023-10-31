package com.hgtech.smartio.ui.fragment.user

import android.text.InputType
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.hgtech.smartio.R
import com.hgtech.smartio.databinding.FragmentSignUBinding
import com.hgtech.smartio.ui.manager.fragment.AuthenticationFragment
import com.hgtech.smartio.ui.manager.layout.*
import com.hgtech.smartio.ui.viewmodel.user.SignUViewModel
import com.hgtech.smartio.ui.manager.layout.hideKeyboard
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUPFragment : AuthenticationFragment<FragmentSignUBinding>(FragmentSignUBinding::inflate) {

    private val viewModel: SignUViewModel by viewModel()


    override fun initViews() {
        with(binding) {
            container.setOnClickListener {
                resetLayout()
            }

            password.setupShowHidePass()
            confirmPassword.setupShowHidePass()

            fname.setup(
                requireActivity(),
                getString(R.string.fname),
                EditorInfo.IME_ACTION_NEXT
            ) {
                onNextClick(lname, requireActivity())
            }

            lname.setup(
                requireActivity(),
                getString(R.string.lname),
                EditorInfo.IME_ACTION_NEXT
            ) {
                onNextClick(phone, requireActivity())
            }

            phone.setup(
                requireActivity(),
                getString(R.string.phone),
                EditorInfo.IME_ACTION_NEXT,
                InputType.TYPE_CLASS_NUMBER
            ) {
                date.openDatePicker(phone, requireActivity())
            }

            date.setup(requireActivity(), getString(R.string.dob), InputType.TYPE_NULL)
            date.contentText.isFocusableInTouchMode = false
            username.setup(
                requireActivity(),
                getString(R.string.usernane),
                EditorInfo.IME_ACTION_NEXT,
                InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            ) {
                onNextClick(password, requireActivity())
            }

            password.setup(
                requireActivity(),
                getString(R.string.password),
                EditorInfo.IME_ACTION_NEXT
            ) {
                onNextClick(confirmPassword, requireActivity())
            }

            confirmPassword.setup(
                requireActivity(),
                getString(R.string.confirm_password),
                isLast = true
            ) {
                signUp()
            }


            date.root.setOnClickListener {
                date.openDatePicker(phone, requireActivity())
            }

            signUp.setOnClickListener {
                signUp()
            }
            login.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun resetLayout() {
        hideKeyboard(requireActivity())
        with(binding) {
            fname.resetLayout()
            lname.resetLayout()
            phone.resetLayout()
            username.resetLayout()
            password.resetLayout()
            confirmPassword.resetLayout()
        }
    }


    private fun signUp() {
        with(binding) {
            resetLayout()
            if (
                isValid(
                    fname.contentText,
                    lname.contentText,
                    date.contentText,
                    username.contentText,
                    password.contentText,
                    confirmPassword.contentText
                )
            ) {
                loading.visibility = View.VISIBLE
                viewModel.signUP(
                    fname.getText(),
                    lname.getText(),
                    date.getText(),
                    phone.getText(),
                    username.getText(),
                    password.getText()
                )
            }
        }
    }

    override fun setupPasswordView() {
        binding.password.showHidePassword(requireContext())
        binding.confirmPassword.showHidePassword(requireContext())
    }

    override fun observe() {
        with(viewModel) {
            signUpResponse.observe(viewLifecycleOwner) {
                if (it.data != null)
                    findNavController().popBackStack()
                else
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
            }
            isLoading.observe(viewLifecycleOwner) {
                binding.loading.isVisible = it
            }
        }
    }
}