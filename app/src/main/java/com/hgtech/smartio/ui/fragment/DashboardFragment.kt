package com.hgtech.smartio.ui.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hgtech.smartio.R
import com.hgtech.smartio.databinding.FragmentDashboardBinding
import com.hgtech.smartio.ui.activity.BarCodeScannerActivity
import com.hgtech.smartio.ui.custom.askConfirm
import com.hgtech.smartio.ui.fragment.sensor.AddSensorFragment
import com.hgtech.smartio.ui.viewmodel.DashboardViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : Fragment() {

    private val viewModel by viewModel<DashboardViewModel>()
    private val TAG = this::class.java.name

    private var sensorId: String? = null
    private var qr: String? = null

    private var isFromQr = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDashboardBinding.inflate(inflater)

        val scanQr =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    // There are no request codes
                    result.data?.getStringExtra(BarCodeScannerActivity.QR_SCANNER).apply {
                        qr = this
                        if (this != null)
                            viewModel.getSensor(this)
                        else
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.error_qr),
                                Toast.LENGTH_LONG
                            ).show()
                    }
                }
            }

        with(binding) {
            getCrops.setOnClickListener {
                findNavController().navigate(
                    DashboardFragmentDirections.actionNavigationDashboardToCropListFragment(
                        viewModel.isManager,
                        true
                    )
                )
            }
            getSensor.setOnClickListener {
                isFromQr = true
                scanQr.launch(Intent(requireContext(), BarCodeScannerActivity::class.java))
            }
        }

        observe()

        return binding.root
    }

    private fun observe() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(
            AddSensorFragment.SENSOR
        )
            ?.observe(
                viewLifecycleOwner
            ) { sensorId ->
                if (sensorId != null)
                    viewModel.getSensorById(sensorId)
                else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.error_server),
                        Toast.LENGTH_LONG
                    ).show()
                    Log.e(TAG, "Couldn't Retrieve Sensor ID after Adding")
                }
            }

        viewModel.apply {
            isManager()
            sensorInfo.observe(viewLifecycleOwner) {
                if (it.data != null) {
                    sensorId = it.data.id
                    isAssign(it.data.id)
                } else
                    requestAddSensor()
            }
            isAssign.observe(viewLifecycleOwner) {
                if (it.data != null) {
                    if (isFromQr) {
                        isFromQr = false
                        findNavController().navigate(
                            DashboardFragmentDirections.actionNavigationDashboardToListCropBySensorFragment(
                                it.data.sensor.id,
                                isManager
                            )
                        )
                    }
                } else
                    requestAssignSensor()
            }

            assignResponse.observe(viewLifecycleOwner) {
                if (it.data != null)
                    findNavController().navigate(
                        DashboardFragmentDirections.actionNavigationDashboardToListCropBySensorFragment(
                            it.data.id,
                            isManager
                        )
                    )
                else {
                    Toast.makeText(requireContext(), R.string.error_get_sensor, Toast.LENGTH_LONG)
                        .show()
                    Log.e(TAG, "Sensor User -> Sensor Id null ")
                }
            }
        }
    }

    private fun requestAssignSensor() {
        askConfirm(requireActivity(), R.string.ask_assign_sensor_user, R.drawable.assign_icon) {
            if (sensorId != null)
                viewModel.assign(sensorId!!)
            else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.error_get_sensor),
                    Toast.LENGTH_LONG
                ).show()
                Log.e(TAG, "Confirm Sensor User Assignment -> Sensor Id Null ")
            }
        }
    }

    private fun requestAddSensor() {
        if (viewModel.isManager) {
            askConfirm(requireActivity(), R.string.ask_add_sensor, R.drawable.sensors_white_24dp) {
                findNavController().navigate(
                    DashboardFragmentDirections.actionNavigationDashboardToAddSensorFragment(
                        qr
                    )
                )
            }
        } else
            Toast.makeText(requireContext(), getString(R.string.deny_add_sensor), Toast.LENGTH_LONG)
                .show()
    }
}