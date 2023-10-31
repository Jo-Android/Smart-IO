package com.hgtech.smartio.ui.fragment

import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hgtech.smartio.R
import com.hgtech.smartio.databinding.FragmentHomeBinding
import com.hgtech.smartio.databinding.LayoutTopBarBinding
import com.hgtech.smartio.network.model.response.irrigation.Irrigation
import com.hgtech.smartio.ui.adapter.StateIrrigationAdapter
import com.hgtech.smartio.ui.manager.fragment.BaseFragment
import com.hgtech.smartio.ui.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel by viewModel<HomeViewModel>()

    override fun observe() {
        viewModel.apply {
            get()
            response.observe(viewLifecycleOwner) {
                if (it.data != null)
                    showIrrigations(it.data)
            }
        }
    }

    private fun showIrrigations(data: List<Irrigation>) {
        binding.apply {
            if (data.isNotEmpty()) {

                irrigationList.apply {
                    adapter = StateIrrigationAdapter(data) { id, isManager ->
                        findNavController().navigate(
                            HomeFragmentDirections.actionNavigationHomeToAssignCropSensorResultFragment(
                                assignId = id,
                                isManager = isManager
                            )
                        )
                    }
                    layoutManager = LinearLayoutManager(requireContext())
                }
            }
            irrigationList.isVisible = data.isNotEmpty()
            empty.isVisible = data.isEmpty()
        }
    }

    override fun initLayout() {
        binding.apply {
            topBar.setup()
            irrigationList.isVisible = false
            empty.isVisible = true
        }
    }

    fun LayoutTopBarBinding.setup() {
        title.text = getString(R.string.irrigation)
        back.isVisible = false
        save.apply {
            isVisible = true
            setImageResource(R.drawable.refresh_white_24dp)
            setOnClickListener {
                viewModel.get()
            }
        }
    }


}