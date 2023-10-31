package com.hgtech.smartio.ui.fragment.assign.crop_sensor

import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hgtech.smartio.R
import com.hgtech.smartio.databinding.FragmentListCropBySensorBinding
import com.hgtech.smartio.network.model.response.assign.state.State
import com.hgtech.smartio.ui.manager.fragment.AssignFragment
import com.hgtech.smartio.ui.viewmodel.assign.sensor_crop.ListCropBySensorViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListCropBySensorFragment :
    AssignFragment<FragmentListCropBySensorBinding, State>(FragmentListCropBySensorBinding::inflate) {

    private val viewModel by viewModel<ListCropBySensorViewModel>()
    private val args by navArgs<ListCropBySensorFragmentArgs>()
    override fun setupListItems() {
        viewModel.getStates(args.sensorId)
    }

    override fun setupTopBar() {
        binding.topBar.setup(R.string.crop_sensor, R.drawable.assign_icon)
    }

    override fun addItem() {
        findNavController().navigate(
            ListCropBySensorFragmentDirections.actionListCropBySensorFragmentToListCropBySensorTypeFragment(
                args.sensorId,
                args.isManager
            )
        )
    }

    override fun observe() {
        viewModel.stateResponse.observe(viewLifecycleOwner) {
            if (it.data != null)
                binding.itemList.setup(it.data)
        }
    }

    override fun onBindItem(name: AppCompatTextView, icon: AppCompatImageView, item: State) {
        name.text = item.sensor_crop.crop.name
        icon.setImageResource(R.drawable.plant_white_24dp)
    }

    override fun onItemSelected(item: State) {
        findNavController().navigate(
            ListCropBySensorFragmentDirections.actionListCropBySensorFragmentToAssignCropSensorResultFragment(
                item.id,
                args.isManager
            )
        )
    }

    override fun onDeleteItem(item: State) = Unit

}