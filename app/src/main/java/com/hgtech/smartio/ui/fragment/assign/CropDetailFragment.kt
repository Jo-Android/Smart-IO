package com.hgtech.smartio.ui.fragment.assign

import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hgtech.smartio.R
import com.hgtech.smartio.databinding.FragmentCropDetailBinding
import com.hgtech.smartio.network.manager.UserWrapper.checkActive
import com.hgtech.smartio.network.model.response.assign.state.State
import com.hgtech.smartio.ui.custom.server.CropInfo
import com.hgtech.smartio.ui.manager.fragment.AssignFragment
import com.hgtech.smartio.ui.viewmodel.crop.CropDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CropDetailFragment :
    AssignFragment<FragmentCropDetailBinding, State>(FragmentCropDetailBinding::inflate) {

    private lateinit var cropInfo: CropInfo
    private val viewModel by viewModel<CropDetailViewModel>()
    private var isManager = false

    private val args by navArgs<CropDetailFragmentArgs>()

    override fun observe() {
        viewModel.apply {
            userResposne.observe(viewLifecycleOwner) {
                if (it.data != null) {
                    isManager = it.data[0].isManager.checkActive()
                    cropInfo.displayInfo(args.crop, isManager)
                    getCropDetail(args.crop.id)
                } else
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.error_get_user),
                        Toast.LENGTH_LONG
                    ).show()
            }
            cropDetail.observe(viewLifecycleOwner) {
                if (it.data != null)
                    binding.sensorTypeList.setup(it.data)
                else
                    Log.e("TAG", getString(R.string.empty_crop_sensor))
//                    Toast.makeText(
//                        requireContext(),
//                        getString(R.string.empty_crop_sensor),
//                        Toast.LENGTH_LONG
//                    ).show()
            }
        }
    }

    override fun initLayout() {
        cropInfo = CropInfo(
            binding.cropInfo,
            requireActivity(),
            updateName = {
                viewModel.updateName(it)
            },
            updateType = {

            }
        )
        cropInfo.setupLayout()
        setupTopBar()
    }


    override fun setupTopBar() {
        binding.topBar.setup(R.string.crop_sensors)
    }

    override fun setupListItems() {
        viewModel.getCredentials()
    }

    override fun addItem() {
        findNavController().navigate(
            CropDetailFragmentDirections.actionCropDetailFragmentToAssignCropSensorFragment(
                args.crop,
                isManager
            )
        )
    }

    override fun onBindItem(name: AppCompatTextView, icon: AppCompatImageView, item: State) {
        name.text = java.lang.StringBuilder(item.sensor_user.sensor.type.type)
        icon.setImageResource(
            if (item.isActive.checkActive())
                R.drawable.sensors_white_24dp
            else
                R.drawable.sensors_off_white_24dp
        )
    }

    override fun onItemSelected(item: State) {
        findNavController().navigate(
            CropDetailFragmentDirections.actionCropDetailFragmentToAssignCropSensorResultFragment(
                item.id,
                isManager
            )
        )
    }

    override fun onDeleteItem(item: State) {

    }

}