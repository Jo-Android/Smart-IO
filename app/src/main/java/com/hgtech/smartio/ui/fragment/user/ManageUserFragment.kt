package com.hgtech.smartio.ui.fragment.user

import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.hgtech.smartio.R
import com.hgtech.smartio.databinding.FragmentManageUserBinding
import com.hgtech.smartio.databinding.LayoutTopBarBinding
import com.hgtech.smartio.network.model.response.user.manager.Users
import com.hgtech.smartio.ui.custom.setupConfirmDialog
import com.hgtech.smartio.ui.manager.fragment.ManagerFragment

class ManageUserFragment :
    ManagerFragment<FragmentManageUserBinding>(FragmentManageUserBinding::inflate) {

    override fun setupLayout() {
        with(binding.root) {
            setup()
        }
    }

    override fun customBackPress() = Unit
    override fun LayoutTopBarBinding.actionTopBar() {
        title.text = getString(R.string.list_user)
        add.isVisible = true
        add.setOnClickListener {
            findNavController().navigate(ManageUserFragmentDirections.actionManageUserFragmentToRegularUserFragment())
        }
    }

    override fun passUserList(data: List<Users>) {
        binding.root.setupAdapter(data, false)
    }

    override fun getUserList() {
        viewModel.getUserList()
    }

    override fun assignClicked(user: Users, position: Int) {
        setupConfirmDialog(
            requireActivity(),
            getString(R.string.remove_assign),
            getString(R.string.ask_remove_assign),
            R.drawable.person_remove_white_24dp
        ) { isConfirm, _ ->
            if (isConfirm) {
                viewModel.assign(user, position, null)
            }
        }

    }

}