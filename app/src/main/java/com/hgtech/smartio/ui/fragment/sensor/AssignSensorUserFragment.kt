package com.hgtech.smartio.ui.fragment.sensor

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hgtech.smartio.R
import com.hgtech.smartio.databinding.FragmentAssignSensorUserBinding
import com.hgtech.smartio.ui.activity.BarCodeScannerActivity
import com.hgtech.smartio.ui.custom.server.SensorInfo
import com.hgtech.smartio.ui.manager.fragment.BaseBottomSheet
import com.hgtech.smartio.ui.viewmodel.sensor.AssignSensorUserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AssignSensorUserFragment :
    BaseBottomSheet<FragmentAssignSensorUserBinding>(FragmentAssignSensorUserBinding::inflate) {

    private lateinit var sensorInfo: SensorInfo
    private val viewModel by viewModel<AssignSensorUserViewModel>()
    private val args by navArgs<AssignSensorUserFragmentArgs>()

    private var qrCode: String? = null
    private var sensorId: String? = null

    override fun setupLayout() {
        sensorInfo = SensorInfo(
            binding.sensor,
            requireActivity(),
            updateName = {
                viewModel.updateSensorName(it)
            },
            updateType = {

            },
            updateUof = {
                viewModel.updateUof(it)
            }
        )
        sensorInfo.setupLayout()
        binding.addSensor.isVisible = false
    }


    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                result.data?.getStringExtra(BarCodeScannerActivity.QR_SCANNER).apply {
                    qrCode = this
                    if (this != null)
                        viewModel.getSensorByQr(this)
                    else
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.error_qr),
                            Toast.LENGTH_LONG
                        ).show()
                }
            }
        }

    override fun setupTopLayout() {
        with(binding.topBar) {
            title.text = getString(R.string.assign_sensor)
            back.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun setListeners() {
        binding.getSensor.setOnClickListener {
            resultLauncher.launch(Intent(requireContext(), BarCodeScannerActivity::class.java))
        }

        binding.addSensor.setOnClickListener {
            if (sensorId != null)
                viewModel.assign(args.userID, sensorId!!)
            else
                Toast.makeText(
                    requireContext(),
                    getString(R.string.error_server),
                    Toast.LENGTH_LONG
                ).show()
        }
    }

    override fun observe() {
        with(viewModel) {
            sensor.observe(viewLifecycleOwner) {
                binding.getSensor.isVisible = it.data == null
                if (it.data != null) {
                    sensorInfo.displayInfo(it.data, args.isManager)
                    sensorId = it.data.id
                    binding.addSensor.isVisible = true
                } else
                    Toast.makeText(requireContext(), R.string.error_get_sensor, Toast.LENGTH_LONG)
                        .show()
            }
            assignResponse.observe(viewLifecycleOwner) {
                if (it.data != null)
                    findNavController().popBackStack()
                else
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
            }
        }
    }

}