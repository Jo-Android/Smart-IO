package com.hgtech.smartio.ui.manager.fragment

import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.hgtech.smartio.R
import com.hgtech.smartio.databinding.LayoutManagerUserBinding
import com.hgtech.smartio.databinding.LayoutTopBarBinding
import com.hgtech.smartio.network.manager.UserWrapper.checkActive
import com.hgtech.smartio.network.model.response.user.manager.Users
import com.hgtech.smartio.ui.adapter.UserRecyclerViewAdapter
import com.hgtech.smartio.ui.custom.setupConfirmDialog
import com.hgtech.smartio.ui.viewmodel.user.ManageUserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class ManagerFragment<B : ViewBinding>(
    bindingFactory: (LayoutInflater) -> B
) : BaseFragment<B>(bindingFactory) {

    private lateinit var adapter: UserRecyclerViewAdapter
    val viewModel by viewModel<ManageUserViewModel>()


    fun LayoutManagerUserBinding.setup() {
        topBar.setup()
    }

    private fun LayoutTopBarBinding.setup() {
        back.isVisible = true
        back.setOnClickListener {
            findNavController().popBackStack()
        }
        actionTopBar()
    }

    abstract fun LayoutTopBarBinding.actionTopBar()


    override fun onResume() {
        super.onResume()
        getUserList()
    }

    abstract fun getUserList()

    override fun observe() {
        with(viewModel) {
            userList.observe(viewLifecycleOwner) {
                if (it.data != null)
                    passUserList(it.data)
            }
            isLoading.observe(viewLifecycleOwner) {
                loadingDialogVisibility(it)
            }
            isActive.observe(viewLifecycleOwner) {
                adapter.notifyItemChanged(position)
            }
            isAssign.observe(viewLifecycleOwner) {
                adapter.remove(position)
            }
        }
    }

    abstract fun passUserList(data: List<Users>)

    fun LayoutManagerUserBinding.setupAdapter(data: List<Users>, isRegularLL: Boolean) {
        adapter = UserRecyclerViewAdapter(
            ArrayList<Users>().apply {
                addAll(data)
            },
            isRegularLL,
            onActiveClicked = { user, position ->
                if (user.isActive.checkActive()) {
                    setupConfirmDialog(
                        requireActivity(),
                        getString(R.string.delete_account_1),
                        getString(R.string.ask_delete_account),
                        R.drawable.ic_baseline_no_accounts_24
                    ) { isConfirm, _ ->
                        if (isConfirm) {
                            viewModel.setActive(user, position, false)
                        }
                    }
                } else
                    viewModel.setActive(user, position, true)
            },
            onAssignClicked = { user, position ->
                assignClicked(user, position)

            }
        )
        userList.layoutManager = LinearLayoutManager(requireContext())
        userList.adapter = adapter

    }

    abstract fun assignClicked(user: Users, position: Int)

    override fun initLayout() {
        setupLayout()
    }

    abstract fun setupLayout()
    abstract fun customBackPress()
}