package com.hgtech.smartio.ui.fragment.assign.crop_sensor

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hgtech.smartio.R
import com.hgtech.smartio.databinding.FragmentAssignCropSensorBinding
import com.hgtech.smartio.ui.activity.BarCodeScannerActivity
import com.hgtech.smartio.ui.custom.askConfirm
import com.hgtech.smartio.ui.custom.server.SensorInfo
import com.hgtech.smartio.ui.custom.setupErrorDialog
import com.hgtech.smartio.ui.fragment.sensor.AddSensorFragment.Companion.SENSOR
import com.hgtech.smartio.ui.manager.fragment.BaseBottomSheet
import com.hgtech.smartio.ui.manager.layout.getText
import com.hgtech.smartio.ui.viewmodel.assign.sensor_crop.AssignCropSensorViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AssignCropSensorFragment :
    BaseBottomSheet<FragmentAssignCropSensorBinding>(FragmentAssignCropSensorBinding::inflate) {

    private val TAG = AssignCropSensorFragment::class.java.name

    private lateinit var sensorInfo: SensorInfo
    private val args by navArgs<AssignCropSensorFragmentArgs>()

    private val viewModel by viewModel<AssignCropSensorViewModel>()


    private var sensorUser: String? = null
    private var cropSensorType: String? = null
    private var qr: String? = null
    private var sensorType: String? = null

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                result.data?.getStringExtra(BarCodeScannerActivity.QR_SCANNER).apply {
                    qr = this
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
        sensorInfo.setupMinMaxLayout(
            updateMin = {
                if (cropSensorType != null)
                    viewModel.updateMinValue(it, cropSensorType!!)
                else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.error_get_sensor_type_crop),
                        Toast.LENGTH_LONG
                    ).show()
                    Log.e(TAG, "Couldn't Update Min Value -> Id is null")
                }
            },
            updateMax = {
                if (cropSensorType != null)
                    viewModel.updateMaxValue(it, cropSensorType!!)
                else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.error_get_sensor_type_crop),
                        Toast.LENGTH_LONG
                    ).show()
                    Log.e(TAG, "Couldn't Update Max Value -> Id is null")
                }
            }
        )
        if (args.sensor != null)
            viewModel.getSensor(args.sensor!!)
        else
            resultLauncher.launch(Intent(requireContext(), BarCodeScannerActivity::class.java))
    }

    override fun setupTopLayout() {
        binding.topBar.setupLayout(R.string.assign_crop_sensor)
    }

    override fun setListeners() {
        binding.apply {
            getSensor.setOnClickListener {
                resultLauncher.launch(Intent(requireContext(), BarCodeScannerActivity::class.java))
            }
            addSensor.setOnClickListener {
                requestAssign()
            }
        }
    }

    private fun requestAssign() {
        if (cropSensorType != null && sensorUser != null)
            viewModel.assignState(cropSensorType!!, sensorUser!!)
        else if (cropSensorType == null)
            requestAddSensorTypeCrop()
        else
            Toast.makeText(
                requireContext(),
                getString(R.string.error_get_sensor),
                Toast.LENGTH_LONG
            ).show()
    }

    override fun observe() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(SENSOR)
            ?.observe(
                viewLifecycleOwner
            ) { sensorId ->
                if (sensorId != null)
                    viewModel.getSensor(sensorId)
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
            sensor.observe(viewLifecycleOwner) {
                if (it.data != null) {
                    sensorType = it.data.type.id
                    sensorInfo.displayInfo(it.data, args.isManager)
                    isSensorUserAssigned(it.data.id)
                } else
                    requestAddSensor()
            }

            isSensorUserAssign.observe(viewLifecycleOwner) {
                if (it.data != null) {
                    sensorUser = it.data.id
                    isSensorTypeCopAssigned(it.data.sensor.type.id, args.crop.id)
                } else
                    requestAddSensorUser()
            }

            isSensorTypeCropAssign.observe(viewLifecycleOwner) {
                if (it.data != null) {
                    cropSensorType = it.data.id
                    sensorInfo.displayMinMax(it.data.minValue, it.data.maxValue)
                    if (sensorType != null)
                        isCropSensorTypeAssigned(it.data.id, sensorType!!)
                    else {
                        Log.e(TAG, "sensorType=null -> isSensorTypeCropAssign")
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.error_get_sensor),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else if (args.isManager)
                    sensorInfo.requestAddMinMax()
            }

            isCropSensorTypeAssign.observe(viewLifecycleOwner) {
                if (it.isSuccess) {
                    if (it.isTypeAssigned) {
                        setupErrorDialog(
                            requireActivity(),
                            getString(R.string.crop_type_assigned),
                            R.drawable.assign_icon
                        ) {
                            findNavController().popBackStack()
                        }
                    }
                } else {
                    Log.e(TAG, "Error Getting Respsonse -> isCropSensorTypeAssign")
                }
            }

            assignSensorUser.observe(viewLifecycleOwner) {
                if (it.data != null) {
                    sensorUser = it.data.id
                    if (sensorType != null)
                        isSensorTypeCopAssigned(sensorType!!, args.crop.id)
                    else {
                        Log.e(TAG, "Sensor Type is Null")
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.error_get_sensor),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Log.e(TAG, "Couldn't Assign Sensor To User")
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.error_server),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            assignSensorTypeCrop.observe(viewLifecycleOwner) {
                if (it.data != null) {
                    if (sensorUser != null)
                        assignState(it.data.id, sensorUser!!)
                    else {
                        Log.e(TAG, "Couldn't Assign State. Sensor User is null")
                        Toast.makeText(
                            requireContext(),
                            R.string.error_get_sensor,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Log.e(TAG, "Couldn't Assign Type to This Crop")
                    Toast.makeText(
                        requireContext(),
                        R.string.error_server,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            assign.observe(viewLifecycleOwner) {
                if (it.data != null)
                    openAssignResult(it.data.id)
                else {
                    Log.e(TAG, "Couldn't Assign Sensor to This Crop")
                    Toast.makeText(
                        requireContext(),
                        R.string.error_server,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            updateValue.observe(viewLifecycleOwner) {
                if (it.isSuccess)
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.updade_value_success),
                        Toast.LENGTH_LONG
                    ).show()
                else
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.error_server),
                        Toast.LENGTH_LONG
                    ).show()
            }
        }
    }

    private fun openAssignResult(id: String) {
        findNavController().navigate(
            AssignCropSensorFragmentDirections.actionAssignCropSensorFragmentToAssignCropSensorResultFragment(
                id,
                args.isManager
            )
        )
    }

    private fun assignCropSensor() {
        if (sensorInfo.checkMinMaxValue())
            with(binding.sensor.minMax) {
                sensorInfo.getSensor()?.let {
                    viewModel.assignCropSensorType(
                        args.crop.id,
                        it.type.id,
                        minValue.getText(),
                        maxValue.getText()
                    )
                }
            }
    }

    private fun requestAddSensor() {
        if (args.isManager) {
            askConfirm(requireActivity(), R.string.ask_add_sensor, R.drawable.sensors_white_24dp) {
                findNavController().navigate(
                    AssignCropSensorFragmentDirections.actionAssignCropSensorFragmentToAddSensorFragment(
                        qr
                    )
                )
            }
        } else {
            Toast.makeText(requireContext(), getString(R.string.deny_add_sensor), Toast.LENGTH_LONG)
                .show()
            findNavController().popBackStack()
        }
    }


    private fun requestAddSensorTypeCrop() {
        if (args.isManager) {
            askConfirm(
                requireActivity(),
                R.string.ask_assign_sensor_type_crop,
                R.drawable.assign_icon
            ) {
                assignCropSensor()
            }
        } else {
            Toast.makeText(requireContext(), getString(R.string.deny_add_sensor), Toast.LENGTH_LONG)
                .show()
            findNavController().popBackStack()
        }
    }

    private fun requestAddSensorUser() {
        sensorInfo.getSensor().apply {
            if (this != null)
                viewModel.assignSensorUser(this.id)
            else
                Log.e(TAG, "Couldn't Assign SensorUser , Sensor is null")
        }
    }
}