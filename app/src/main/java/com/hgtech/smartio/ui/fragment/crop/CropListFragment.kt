package com.hgtech.smartio.ui.fragment.crop

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hgtech.smartio.R
import com.hgtech.smartio.databinding.FragmentCropListBinding
import com.hgtech.smartio.network.model.response.crop.Crop
import com.hgtech.smartio.ui.fragment.crop.AddCropFragment.Companion.CROP_ADDED
import com.hgtech.smartio.ui.manager.fragment.AssignFragment
import com.hgtech.smartio.ui.viewmodel.assign.sensor_crop.AssignCropSensorViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CropListFragment :
    AssignFragment<FragmentCropListBinding, Crop>(FragmentCropListBinding::inflate) {


    private val viewModel by viewModel<AssignCropSensorViewModel>()
    private val args by navArgs<CropListFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(CROP_ADDED)
            ?.observe(
                viewLifecycleOwner
            ) { _ ->
                // Do something with the result.
                setupListItems()
            }
    }


    override fun setupTopBar() {
        binding.topBar.setup(R.string.list_crops, isAddVisible = args.isManager)
    }

    override fun addItem() {
        findNavController().navigate(CropListFragmentDirections.actionCropListFragmentToAddCropFragment())
    }

    override fun onDeleteItem(item: Crop) = Unit

    override fun onItemSelected(item: Crop) {
        viewModel.crop = item
        if (args.isNavigationCropDetail) {
            findNavController().navigate(
                CropListFragmentDirections.actionCropListFragmentToCropDetailFragment(
                    item
                )
            )
        } else {
            findNavController().navigate(
                CropListFragmentDirections.actionCropListFragmentToAssignCropSensorFragment(
                    item,
                    args.isManager,
                    args.sensorId
                )
            )
        }
    }

    override fun onBindItem(name: AppCompatTextView, icon: AppCompatImageView, item: Crop) {
        name.text = item.name
        icon.setImageResource(R.drawable.plant_white_24dp)
    }

    override fun setupListItems() {
        viewModel.getAllCrops()
    }

    override fun observe() {
        with(viewModel) {
            allCrops.observe(viewLifecycleOwner) {
                if (it.data != null)
                    binding.itemList.setup(it.data)
                else
                    Toast.makeText(requireContext(), R.string.error_get_crop, Toast.LENGTH_LONG)
                        .show()
            }
        }
    }

}