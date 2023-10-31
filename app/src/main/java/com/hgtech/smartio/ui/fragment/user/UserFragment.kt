package com.hgtech.smartio.ui.fragment.user

import android.content.Intent
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hgtech.smartio.R
import com.hgtech.smartio.databinding.FragmentUserBinding
import com.hgtech.smartio.databinding.LayoutTopBarBinding
import com.hgtech.smartio.network.manager.UserWrapper.checkActive
import com.hgtech.smartio.network.manager.UserWrapper.setID
import com.hgtech.smartio.ui.activity.AuthenticationActivity
import com.hgtech.smartio.ui.custom.setupConfirmDialog
import com.hgtech.smartio.ui.manager.fragment.EditTextFragment
import com.hgtech.smartio.ui.manager.layout.getText
import com.hgtech.smartio.ui.manager.layout.resetLayout
import com.hgtech.smartio.ui.manager.layout.setup
import com.hgtech.smartio.ui.manager.layout.text
import com.hgtech.smartio.ui.viewmodel.user.UserViewModel


class UserFragment : EditTextFragment<FragmentUserBinding>(FragmentUserBinding::inflate) {

    private val viewModel by viewModels<UserViewModel>()


    override fun observe() {
        with(viewModel) {
            userInfo.observe(viewLifecycleOwner) {
                if (it.data != null) {
                    isEditInfoLL(false)
                    with(binding) {
                        firstName.text(it.data[0].firstName, requireActivity())
                        lastName.text(it.data[0].lastName, requireActivity())
                        email.text(it.data[0].userName, requireActivity())
                        phone.text(it.data[0].phone, requireActivity())
                        if (it.data[0].isManager.checkActive())
                            manageUser.isVisible = true
                    }
                }
            }
            logout.observe(viewLifecycleOwner) {
                if (it.isSuccess)
                    logout()
                else
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
            }
            isLoading.observe(viewLifecycleOwner) {
                loadingDialog.shouldShowDialog(it)
            }
        }
    }

    override fun initViews() {
        with(binding) {
            topBar.setup()
            firstName.setup(getString(R.string.fname))
            lastName.setup(getString(R.string.lname))
            phone.setup(getString(R.string.phone), EditorInfo.TYPE_CLASS_NUMBER) {
                updatePhone()
            }

            email.setup(getString(R.string.usernane), true)
            email.edit.setOnClickListener {
                findNavController().navigate(
                    UserFragmentDirections.actionUserFragmentToUserEditEmailFragment(
                        email.getText()
                    )
                )
            }

            resetPassword.setOnClickListener {
                findNavController().navigate(
                    UserFragmentDirections.actionUserFragmentToUserEditPasswordFragment(
                        email.getText()
                    )
                )
            }

            manageUser.setOnClickListener {
                findNavController().navigate(
                    UserFragmentDirections.actionUserFragmentToManageUserFragment()
                )
            }

            deactivate.setOnClickListener {
                setupConfirmDialog(
                    requireActivity(),
                    getString(R.string.delete_account_1),
                    getString(R.string.ask_delete_account),
                    R.drawable.ic_baseline_no_accounts_24
                ) { isConfirm, _ ->
                    if (isConfirm)
                        viewModel.deleteUser()
                }
            }

            logOut.setOnClickListener {
                logout()
            }
        }
        viewModel.getUserInfo()
    }

    private fun logout() {
        setID(null, true)
        requireActivity().finish()
        startActivity(Intent(requireContext(), AuthenticationActivity::class.java))
    }

    private fun LayoutTopBarBinding.setup() {
        add.isVisible = false
        title.text = getString(R.string.user_info)
        back.setOnClickListener {
            findNavController().popBackStack()
        }
        save.setOnClickListener {
            updatePhone()
        }
        cancel.setOnClickListener {
            viewModel.getUserInfo()
        }
    }

    private fun updatePhone() {
        with(binding.phone) {
            if (isInfoValid(contentText)) {
                isEditInfoLL(false)
                viewModel.updatePhone(getText())
            }
        }
    }

    override fun setupPasswordView() = Unit

    override fun onRootClickedRestLayout() {
        with(binding) {
            rootLL.setOnClickListener {
                viewModel.getUserInfo()
            }
        }
    }

    private fun resetLayout() {
        with(binding) {
            firstName.resetLayout(requireActivity())
            lastName.resetLayout(requireActivity())
            email.resetLayout(requireActivity())
            phone.resetLayout(requireActivity())
        }
    }

    private fun isEditInfoLL(isVisible: Boolean) {
        if (!isVisible)
            resetLayout()
        with(binding) {
            with(topBar) {
                save.isVisible = isVisible
                cancel.isVisible = isVisible
                back.isVisible = !isVisible
                add.isVisible = false
            }
            email.edit.isVisible = !isVisible
            otherOptions.isVisible = !isVisible
        }
    }

    override fun enableSaveButton() {
        isEditInfoLL(true)
    }

}