package com.hgtech.smartio.ui.fragment

import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.hgtech.smartio.databinding.FragmentSettingsBinding
import com.hgtech.smartio.network.manager.UserWrapper.checkActive
import com.hgtech.smartio.ui.manager.fragment.BaseFragment
import com.hgtech.smartio.ui.viewmodel.SettingsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment :
    BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    private val viewModel by viewModel<SettingsViewModel>()


    override fun initLayout() {
        viewModel.getCredentials()
        with(binding) {
            sensorOption.isVisible = false
            userCredential.setOnClickListener {
                findNavController().navigate(SettingsFragmentDirections.actionNavigationSettingsToUserFragment())
            }
            addSensor.setOnClickListener {
                findNavController().navigate(SettingsFragmentDirections.actionNavigationSettingsToAddSensorFragment())
            }
        }
    }

    override fun observe() {
        with(viewModel) {
            user.observe(viewLifecycleOwner) {
                userInfo = it.data?.get(0)
                if (it.data != null) {
                    with(binding) {
                        userName.text = StringBuilder(it.data[0].firstName)
                            .append(" ")
                            .append(it.data[0].lastName)
                        userEmail.text = it.data[0].userName
                        profileText.text = StringBuilder(it.data[0].firstName.substring(0, 1))
                            .append(it.data[0].lastName.substring(0, 1))

                        it.data[0].isManager.checkActive().apply {
                            sensorOption.isVisible = this
                        }
                    }
                }
            }
            isLoading.observe(viewLifecycleOwner) {
                loadingDialogVisibility(it)
            }
        }
    }

}