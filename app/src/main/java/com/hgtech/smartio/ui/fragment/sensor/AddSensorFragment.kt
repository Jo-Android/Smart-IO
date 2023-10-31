package com.hgtech.smartio.ui.fragment.sensor

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hgtech.smartio.R
import com.hgtech.smartio.databinding.FragmentAddSensorBinding
import com.hgtech.smartio.databinding.LayoutTextEditBinding
import com.hgtech.smartio.network.model.response.sensor.type.SensorType
import com.hgtech.smartio.ui.activity.BarCodeScannerActivity
import com.hgtech.smartio.ui.manager.fragment.AddTypeFragment
import com.hgtech.smartio.ui.manager.layout.createDialog
import com.hgtech.smartio.ui.manager.layout.getText
import com.hgtech.smartio.ui.manager.layout.setup
import com.hgtech.smartio.ui.manager.layout.text
import com.hgtech.smartio.ui.viewmodel.sensor.AddSensorViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddSensorFragment :
    AddTypeFragment<FragmentAddSensorBinding, SensorType>(FragmentAddSensorBinding::inflate) {

    private val viewModel by viewModel<AddSensorViewModel>()
    private val args by navArgs<AddSensorFragmentArgs>()
    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                result.data?.getStringExtra(BarCodeScannerActivity.QR_SCANNER).apply {
                    if (this != null)
                        binding.addItem.qrCode.text(this, requireActivity())
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
        with(binding.addItem) {
            this.setupLayout(R.string.add_sensor)
            qrCode.setupQrLayout()
            args.qr.apply {
                if (this != null)
                    qrCode.text(this, requireActivity())
            }
        }
    }

    private fun LayoutTextEditBinding.setupQrLayout() {
        root.isVisible = true
        setup(getString(R.string.qr_code), isEdit = true, isContentVisible = false)
        edit.setImageResource(R.drawable.barcode_scanner)
    }

    override fun setupTopLayout() {
        binding.addItem.setupTopLayout(R.string.add_sensor)
    }

    override fun setListeners() {
        with(binding.addItem) {
            qrCode.root.setOnClickListener {
                resultLauncher.launch(Intent(requireContext(), BarCodeScannerActivity::class.java))
            }
            setListeners(R.string.invalid_sensor_type, R.string.invalid_sensor) {
                if (viewModel.typeId != null) {
                    if (qrCode.getText().length > 4)
                        viewModel.addSensor(itemName.getText(), qrCode.getText())
                    else
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.invalid_barcode),
                            Toast.LENGTH_LONG
                        )
                            .show()
                } else
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.error_get_crop_type),
                        Toast.LENGTH_LONG
                    )
                        .show()
            }
        }
    }

    override fun getList() {
        viewModel.getSensorTypeList()
    }

    override fun observe() {
        with(viewModel) {
            sensorTypeList.observe(viewLifecycleOwner) {
                setupSpinner(it.data ?: ArrayList(), R.string.sensor_type, R.string.add_sensor_type)
            }
            sensorTypeResponse.observe(viewLifecycleOwner) {
                if (it.data != null) {
                    viewModel.typeId = it.data.id
                    spinner.addSelectedItem(getSensorType(it.data))
                }
            }
            addSensorResponse.observe(viewLifecycleOwner) {
                if (it.data != null) {
                    findNavController().previousBackStackEntry?.savedStateHandle?.set(
                        SENSOR,
                        it.data.id
                    )
                    findNavController().popBackStack()
                }
            }
            isLoading.observe(viewLifecycleOwner) {
                loadingDialog.shouldShowDialog(it)
            }
        }
    }


    override fun bindTypeToList(view: AppCompatTextView, item: SensorType) {
        view.text = getSensorType(item)
    }

    override fun onTypeClicked(it: SensorType) {
        viewModel.typeId = it.id
        spinner.addSelectedItem(getSensorType(it))
    }

    private fun getSensorType(item: SensorType): String {
        return item.type + " - " + item.uof
    }

    override fun addTypeLayout() {
        createDialog(requireActivity(), R.layout.layout_add_sensor_type).apply {
            findViewById<AppCompatImageView>(R.id.imageView3).setOnClickListener {
                dismiss()
            }
            val uof = findViewById<View>(R.id.uof)
            uof.findViewById<AppCompatTextView>(R.id.hintText).text = getString(R.string.add_uof)
            val uofText = uof.findViewById<AppCompatEditText>(R.id.contentText)

            val type = findViewById<View>(R.id.type)
            type.findViewById<AppCompatTextView>(R.id.hintText).text =
                getString(R.string.add_sensor_type)
            val typeText = type.findViewById<AppCompatEditText>(R.id.contentText)
            typeText.imeOptions = EditorInfo.IME_ACTION_NEXT
            typeText.setOnEditorActionListener { _, _, _ ->
                typeText.clearFocus()
                uof.requestFocus()
                false
            }

            uofText.setOnEditorActionListener { _, _, _ ->
                isAddValid(this, typeText.text.toString(), uofText.text.toString())
                false
            }

            findViewById<Button>(R.id.add).setOnClickListener {
                isAddValid(this, typeText.text.toString(), uofText.text.toString())
            }
        }
    }

    private fun isAddValid(dialog: Dialog, type: String, uof: String) {
        if (type.length > 3 && uof.isNotEmpty()) {
            spinner.reset()
            viewModel.addType(type, uof)
            dialog.dismiss()
        } else if (type.length <= 3)
            Toast.makeText(
                requireContext(),
                getString(R.string.invalid_sensor_type),
                Toast.LENGTH_LONG
            ).show()
        else
            Toast.makeText(requireContext(), getString(R.string.invalid_uof), Toast.LENGTH_LONG)
                .show()
    }

    companion object {
        const val SENSOR = "Sensor "
    }

}