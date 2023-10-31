package com.hgtech.smartio.ui.fragment.user

import androidx.core.view.isVisible
import com.hgtech.smartio.R
import com.hgtech.smartio.databinding.FragmentRegularUserBinding
import com.hgtech.smartio.databinding.LayoutTopBarBinding
import com.hgtech.smartio.network.manager.UserWrapper
import com.hgtech.smartio.network.model.response.user.manager.Users
import com.hgtech.smartio.ui.custom.setupConfirmDialog
import com.hgtech.smartio.ui.manager.fragment.ManagerFragment

class RegularUserFragment : ManagerFragment<FragmentRegularUserBinding>(
    FragmentRegularUserBinding::inflate
) {
    override fun setupLayout() {
        binding.root.setup()
    }

    override fun customBackPress() = Unit
    override fun LayoutTopBarBinding.actionTopBar() {
        title.text = getString(R.string.list_regular_user)
        add.isVisible = false
    }

    override fun getUserList() {
        viewModel.getRegularUsers()
    }

    override fun passUserList(data: List<Users>) {
        binding.root.setupAdapter(data, true)
    }

    override fun assignClicked(user: Users, position: Int) {
        setupConfirmDialog(
            requireActivity(),
            getString(R.string.add_assign),
            getString(R.string.ask_assign),
            R.drawable.person_add_white_24dp
        ) { isConfirm, _ ->
            if (isConfirm) {
                viewModel.assign(user, position, UserWrapper.getID())
            }
        }
    }

}