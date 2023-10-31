package com.hgtech.smartio.ui.manager.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.hgtech.smartio.R
import com.hgtech.smartio.ui.custom.LoadingDialog
import com.hgtech.smartio.ui.manager.layout.*
import com.hgtech.smartio.ui.manager.layout.isPasswordValid
import com.hgtech.smartio.ui.manager.layout.isPaswordMatch
import com.hgtech.smartio.ui.manager.layout.isTextValid
import com.hgtech.smartio.ui.manager.layout.isUserNameValid
import com.hgtech.smartio.ui.manager.layout.showError

abstract class AuthenticationFragment<B : ViewBinding>(
    private val bindingFactory: (LayoutInflater) -> B
) : Fragment() {

    private var _binding: B? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    val binding get() = _binding!!
    val loadingDialog by lazy {
        LoadingDialog(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = bindingFactory(inflater)
        initViews()
        setupPasswordView()
        observe()
        return binding.root
    }

    abstract fun initViews()

    abstract fun setupPasswordView()

    abstract fun observe()

    fun isValid(username: AppCompatEditText, password: AppCompatEditText): Boolean {
        val isUser = username.isUserNameValid()
        val isPass = password.isPasswordValid()

        return if (isUser && isPass)
            true
        else {
            if (!isUser && !isPass)
                Toast.makeText(
                    requireContext(),
                    getString(R.string.invalid_email_password),
                    Toast.LENGTH_SHORT
                ).show()
            else if (!isUser)
                username.showError(requireContext(), R.string.invalid_email_format)
            else
                password.showError(requireContext(), R.string.invalid_pasword_format)
            false
        }
    }

    fun isValid(
        fname: AppCompatEditText,
        lname: AppCompatEditText,
        date: AppCompatEditText,
        username: AppCompatEditText,
        password: AppCompatEditText,
        confirmPassword: AppCompatEditText
    ): Boolean {
        val isFname = fname.isTextValid()
        val isLname = lname.isTextValid()
        val isDate = date.text.toString().isNotEmpty()
        val isUser = username.isUserNameValid()
        val isPass = password.isPasswordValid()
        val isConfirmPass = confirmPassword.isPasswordValid()
        val isPassMatch = isPaswordMatch(password, confirmPassword)

        return if (isFname && isLname && isDate && isUser && isPass && isConfirmPass && isPassMatch)
            true
        else {
            if (!isFname)
                fname.showError(requireContext(), R.string.fname_invalid)
            else if (!isLname)
                lname.showError(requireContext(), R.string.fname_invalid)
            else if (!isDate)
                date.showError(requireContext(), R.string.date_invalid)
            else if (!isUser && !isPass)
                Toast.makeText(
                    requireContext(),
                    getString(R.string.invalid_email_password),
                    Toast.LENGTH_SHORT
                ).show()
            else if (!isUser)
                username.showError(requireContext(), R.string.invalid_email_format)
            else if (!isPass)
                password.showError(requireContext(), R.string.invalid_pasword_format)
            else if (!isConfirmPass)
                confirmPassword.showError(requireContext(), R.string.invalid_pasword_format)
            else if (!isPassMatch) {
                password.showError(requireContext(), R.string.password_not_matching)
                confirmPassword.error = getString(R.string.password_not_matching)
            }
            false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}