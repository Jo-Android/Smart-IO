package com.hgtech.smartio.ui.fragment.user

import android.content.Intent
import android.text.InputType
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.hgtech.smartio.R
import com.hgtech.smartio.databinding.FragmentLoginBinding
import com.hgtech.smartio.network.manager.UserWrapper.USER_ID
import com.hgtech.smartio.ui.activity.MainActivity
import com.hgtech.smartio.ui.manager.fragment.AuthenticationFragment
import com.hgtech.smartio.ui.manager.layout.getText
import com.hgtech.smartio.ui.manager.layout.hideKeyboard
import com.hgtech.smartio.ui.manager.layout.onNextClick
import com.hgtech.smartio.ui.manager.layout.resetLayout
import com.hgtech.smartio.ui.manager.layout.setup
import com.hgtech.smartio.ui.manager.layout.setupShowHidePass
import com.hgtech.smartio.ui.manager.layout.showHidePassword
import com.hgtech.smartio.ui.viewmodel.user.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : AuthenticationFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {


    private val loginViewModel: LoginViewModel by viewModel()

    override fun initViews() {
        with(binding) {
            root.setOnClickListener {
                hideKeyboard(requireActivity())
                username.resetLayout()
                password.resetLayout()
            }
            password.setupShowHidePass()
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
                InputType.TYPE_TEXT_VARIATION_PASSWORD,
                isLast = true
            ) {
                login()
            }


            login.setOnClickListener {
                login()
            }
            signUp.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignUPFragment())
            }
        }
    }


    private fun login() {
        with(binding) {
            if (isValid(username.contentText, password.contentText)) {
                hideKeyboard(requireActivity())
                loading.visibility = View.VISIBLE
                loginViewModel.login(
                    username.getText(),
                    password.getText()
                )
            }
        }
    }

    override fun setupPasswordView() {
        binding.password.showHidePassword(requireContext())
    }

    override fun observe() {
        with(loginViewModel) {
            loginResponse.observe(viewLifecycleOwner) {
                if (it.data != null) {
                    startActivity(Intent(requireContext(), MainActivity::class.java).apply {
                        putExtra(USER_ID, it.data.id)
                        requireActivity().finishAfterTransition()
                    })
                } else
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
            }
            isLoading.observe(viewLifecycleOwner) {
                binding.loading.isVisible = it
            }
        }

    }
}