package com.hgtech.smartio.ui.fragment.assign.crop_sensor

import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hgtech.smartio.R
import com.hgtech.smartio.databinding.FragmentListCropBySensorTypeBinding
import com.hgtech.smartio.network.model.response.assign.sensor_crop.CropSensorDetail
import com.hgtech.smartio.ui.manager.fragment.AssignFragment
import com.hgtech.smartio.ui.viewmodel.assign.sensor_crop.ListCropBySensorViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListCropBySensorTypeFragment :
    AssignFragment<FragmentListCropBySensorTypeBinding, CropSensorDetail>(
        FragmentListCropBySensorTypeBinding::inflate
    ) {

    private val viewModel by viewModel<ListCropBySensorViewModel>()
    private val args by navArgs<ListCropBySensorTypeFragmentArgs>()

    override fun setupListItems() {
        viewModel.getCropBySensorType(args.sensorId)
    }

    override fun setupTopBar() {
        binding.topBar.setup(R.string.crop_sensor_type, R.drawable.assign_icon, args.isManager)
    }

    override fun addItem() {
        findNavController().navigate(
            ListCropBySensorTypeFragmentDirections.actionListCropBySensorTypeFragmentToCropListFragment(
                args.isManager,
                false,
                args.sensorId
            )
        )
    }

    override fun observe() {
        viewModel.cropsBySensorType.observe(viewLifecycleOwner) {
            if (it.data != null)
                binding.itemList.setup(it.data)
        }
    }

    override fun onBindItem(
        name: AppCompatTextView,
        icon: AppCompatImageView,
        item: CropSensorDetail
    ) {
        name.text = item.crop.name
        icon.setImageResource(R.drawable.plant_white_24dp)
    }

    override fun onItemSelected(item: CropSensorDetail) {
        findNavController().navigate(
            ListCropBySensorTypeFragmentDirections.actionListCropBySensorTypeFragmentToAssignCropSensorFragment(
                item.crop,
                args.isManager,
                args.sensorId
            )
        )
    }

    override fun onDeleteItem(item: CropSensorDetail) = Unit

}