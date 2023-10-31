package com.hgtech.smartio.ui.fragment.assign.crop_sensor

import android.util.Log
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.hgtech.smartio.R
import com.hgtech.smartio.databinding.FragmentAssignCropSensorResultBinding
import com.hgtech.smartio.databinding.LayoutSensorValuesBinding
import com.hgtech.smartio.databinding.LayoutTopBarBinding
import com.hgtech.smartio.network.manager.UserWrapper.checkActive
import com.hgtech.smartio.network.model.response.assign.state.State
import com.hgtech.smartio.network.model.response.values.SensorValues
import com.hgtech.smartio.ui.adapter.SensorValuesAdapter
import com.hgtech.smartio.ui.custom.server.CropInfo
import com.hgtech.smartio.ui.custom.server.SensorInfo
import com.hgtech.smartio.ui.custom.setupConfirmDialog
import com.hgtech.smartio.ui.manager.fragment.BaseFragment
import com.hgtech.smartio.ui.viewmodel.assign.sensor_crop.AssignCropSensorViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AssignCropSensorResultFragment :
    BaseFragment<FragmentAssignCropSensorResultBinding>(FragmentAssignCropSensorResultBinding::inflate) {

    private var sensorCrop: String? = null
    private val TAG = this::class.java.name

    private var isExpanded = true

    private lateinit var cropInfo: CropInfo
    private lateinit var sensorInfo: SensorInfo

    private val args by navArgs<AssignCropSensorResultFragmentArgs>()
    private val viewModel by viewModel<AssignCropSensorViewModel>()

    override fun initLayout() {
        with(binding) {
            topBar.setup()
            sensorValues.setup()
            showHide.setupShowHideInfo()
            sensorInfo = SensorInfo(
                stateInfo.sensorInfo,
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
                    if (it.isNotEmpty()) {
                        if (sensorCrop != null)
                            viewModel.updateMinValue(it, sensorCrop!!)
                        else {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.error_get_sensor_type_crop),
                                Toast.LENGTH_LONG
                            ).show()
                            Log.e(TAG, "Couldn't Update Min Value -> Id is null")
                        }
                    } else
                        viewModel.getState(args.assignId)
                },
                updateMax = {
                    if (it.isNotEmpty()) {
                        if (sensorCrop != null)
                            viewModel.updateMaxValue(it, sensorCrop!!)
                        else {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.error_get_sensor_type_crop),
                                Toast.LENGTH_LONG
                            ).show()
                            Log.e(TAG, "Couldn't Update Max Value -> Id is null")
                        }
                    } else
                        viewModel.getState(args.assignId)
                }
            )
            cropInfo = CropInfo(
                stateInfo.cropInfo,
                requireActivity(),
                updateName = {
                    viewModel.updateCropName(it)
                },
                updateType = {

                }
            )
            cropInfo.setupLayout()
        }
    }

    override fun observe() {
        with(viewModel) {
            getState(args.assignId)
            stateResponse.observe(viewLifecycleOwner) {
                binding.sensorValues.root.isVisible = it.data != null
                binding.showHide.isVisible = it.data != null
                if (it.data != null)
                    displayStateInfo(it.data[0])
            }
            valuesResponse.observe(viewLifecycleOwner) {
                displaySensorValues(it.data)
            }
            updateState.observe(viewLifecycleOwner) {
                if (it.data != null) {
                    isStateActive = !isStateActive
                    this@AssignCropSensorResultFragment.changeActiveState(isStateActive)
                    getValues(it.data.id)
                } else {
                    Log.e(TAG, "Couldn't Change Activation of State")
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.error_server),
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

    private fun displaySensorValues(data: List<SensorValues>?) {
        binding.sensorValues.emptyValues.isVisible = data == null
        with(binding.sensorValues.sensorValues) {
            isVisible = data != null
            if (data != null) {
                adapter = SensorValuesAdapter(data)
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    private fun displayStateInfo(state: State) {
        sensorInfo.displayInfo(state.sensor_user.sensor, args.isManager)
        sensorInfo.displayMinMax(state.sensor_crop.minValue, state.sensor_crop.maxValue)
        cropInfo.displayInfo(state.sensor_crop.crop, args.isManager)
        state.isActive.checkActive().apply {
            viewModel.isStateActive = this
            changeActiveState(!this)
            binding.state.text = if (this)
                getString(R.string.state_activated)
            else
                getString(R.string.state_deactivated)
        }
        sensorCrop = state.sensor_crop.id
        viewModel.getValues(state.id)
    }

    private fun changeActiveState(isPreviouslyActive: Boolean) {
        binding.topBar.add.apply {
            isVisible = true
            setImageResource(
                if (isPreviouslyActive)
                    R.drawable.recover_icon
                else
                    R.drawable.deactivate_24dp
            )
        }
    }


    private fun RelativeLayout.setupShowHideInfo() {
        showHideInfo()
        isVisible = false
        setOnClickListener {
            showHideInfo()
        }
    }

    private fun showHideInfo() {
        isExpanded = !isExpanded
        with(binding) {
            stateInfo.root.isVisible = isExpanded
            showHideIcon.setImageResource(
                if (isExpanded)
                    R.drawable.expand_icon
                else
                    R.drawable.collapse_icon
            )
        }
    }

    private fun LayoutTopBarBinding.setup() {
        title.text = getString(R.string.crop_sensor)
        back.setOnClickListener {
            findNavController().popBackStack()
        }
        add.setOnClickListener {
            setupConfirmDialog(
                requireActivity(),
                getString(R.string.change),
                if (viewModel.isStateActive)
                    getString(R.string.ask_deactivate_state)
                else
                    getString(R.string.ask_recover_state)
            ) { isConfirm, _ ->
                if (isConfirm)
                    viewModel.changeActiveState(args.assignId)
            }
        }
    }

    private fun LayoutSensorValuesBinding.setup() {
        root.isVisible = false
        refresh.setOnClickListener {
            viewModel.getValues(args.assignId)
        }
    }
}