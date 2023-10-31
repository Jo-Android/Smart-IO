package com.hgtech.smartio.ui.custom.server

import android.text.InputType
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import com.hgtech.smartio.R
import com.hgtech.smartio.databinding.LayoutSensorInfoBinding
import com.hgtech.smartio.databinding.LayoutTextEditBinding
import com.hgtech.smartio.network.model.response.sensor.Sensor
import com.hgtech.smartio.ui.manager.layout.edit
import com.hgtech.smartio.ui.manager.layout.getText
import com.hgtech.smartio.ui.manager.layout.resetLayout
import com.hgtech.smartio.ui.manager.layout.setupEdit

class SensorInfo(
    private val binding: LayoutSensorInfoBinding,
    private val activity: FragmentActivity,
    private val updateName: (name: String) -> Unit,
    private val updateType: () -> Unit,
    private val updateUof: (name: String) -> Unit,
) {

    private var isManager = false
    private var sensor: Sensor? = null

    fun getSensor(): Sensor? {
        return if (sensor != null)
            sensor
        else {
            Toast.makeText(
                activity,
                activity.getString(R.string.error_get_sensor),
                Toast.LENGTH_LONG
            ).show()
            null
        }
    }

    fun setupLayout() {
        with(binding) {
            root.isVisible = false
            sensorName.setup(activity.getString(R.string.sensor_name), EditorInfo.TYPE_CLASS_TEXT) {
                sensorName.update(R.string.invalid_sensor, updateName)
            }
            qrcode.setup(
                activity.getString(R.string.qr_code),
                EditorInfo.TYPE_CLASS_TEXT,
                false
            ) {}
            type.setup(
                activity.getString(R.string.sensor_type),
                EditorInfo.TYPE_CLASS_TEXT,
                isType = true
            ) {
                updateType.invoke()
            }
            uof.setup(
                activity.getString(R.string.uof),
                EditorInfo.TYPE_CLASS_TEXT,
            ) {
                uof.update(R.string.invalid_uof, updateUof)
            }
        }
    }


    fun displayInfo(sensor: Sensor, isManager: Boolean) {
        this.isManager = isManager
        this.sensor = sensor
        with(binding) {
            root.isVisible = true
            minMax.root.isVisible = false
            sensorName.contentText.setText(sensor.name)
            qrcode.contentText.setText(sensor.qr)
            type.contentText.setText(sensor.type.type)
            uof.contentText.setText(sensor.type.uof)
            type.edit.isVisible = false
            uof.edit.isVisible = false
            sensorName.edit.isVisible = false
        }
    }

    private var isMinUpdate = true
    private var isMaxUpdate = true

    fun setupMinMaxLayout(updateMin: (value: String) -> Unit, updateMax: (value: String) -> Unit) {
        with(binding.minMax) {
            root.isVisible = false
            minValue.setup(
                activity.getString(R.string.min_value),
                InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            ) {
                if (isMinUpdate)
                    minValue.update(R.string.invalid_min_value, updateMin)
                else {
                    minValue.isDataValid(R.string.invalid_min_value)
                    minValue.edit.setImageResource(R.drawable.ic_baseline_edit_24)
                    isMinUpdate = true
                }
            }
            maxValue.setup(
                activity.getString(R.string.max_value),
                InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            ) {
                if (isMaxUpdate)
                    maxValue.update(R.string.invalid_max_value, updateMax)
                else {
                    maxValue.isDataValid(R.string.invalid_max_value)
                    maxValue.edit.setImageResource(R.drawable.ic_baseline_edit_24)
                    isMaxUpdate = true
                }
            }
        }
    }


    fun requestAddMinMax() {
        with(binding.minMax) {
            root.isVisible = true
            minValue.edit.isVisible = true
            maxValue.edit.isVisible = true
            minValue.edit.setImageResource(R.drawable.add_white_24dp)
            isMinUpdate = false
            maxValue.edit.setImageResource(R.drawable.add_white_24dp)
            isMaxUpdate = false
        }
    }

    fun displayMinMax(miniValue: String, maxiValue: String) {
        with(binding.minMax) {
            root.isVisible = true
            minValue.contentText.setText((miniValue.toDoubleOrNull() ?: miniValue).toString())
            maxValue.contentText.setText((maxiValue.toDoubleOrNull() ?: maxiValue).toString())
            minValue.edit.isVisible = isManager
            maxValue.edit.isVisible = isManager
        }
    }

    private fun LayoutTextEditBinding.setup(
        hint: String,
        type: Int = InputType.TYPE_CLASS_TEXT,
        isEdit: Boolean = true,
        isType: Boolean = false,
        onCLick: () -> Unit,
    ) {
        setupEdit(hint, activity, onCLick, isEdit)
        edit.setOnClickListener {
            if (!isType) {
                edit(activity, type)
                isEditInfoLL(true)
            }
        }
    }

    private fun LayoutTextEditBinding.isDataValid(errorMessage: Int): Boolean {
        isEditInfoLL(false)
        return if (getText().length > 3)
            true
        else {
            Toast.makeText(activity, activity.getString(errorMessage), Toast.LENGTH_LONG).show()
            false
        }
    }

    private fun LayoutTextEditBinding.update(errorMessage: Int, update: (name: String) -> Unit) {
        isDataValid(errorMessage).also {
            if (!it) {
                resetLayout()
                update.invoke("")
            } else
                update.invoke(getText())
        }
    }

    private fun isEditInfoLL(isVisible: Boolean) {
        if (!isVisible)
            resetLayout()
        with(binding) {
            sensorName.edit.isVisible = !isVisible
            qrcode.edit.isVisible = false
            if (isManager) {
                type.edit.isVisible = !isVisible
                uof.edit.isVisible = !isVisible
                minMax.minValue.edit.isVisible = !isVisible
                minMax.maxValue.edit.isVisible = !isVisible
            } else {
                type.edit.isVisible = false
                uof.edit.isVisible = false
                minMax.minValue.edit.isVisible = false
                minMax.maxValue.edit.isVisible = false
            }
        }
    }

    private fun resetLayout() {
        with(binding) {
            sensorName.resetLayout(activity)
            qrcode.resetLayout(activity)
            type.resetLayout(activity)
            uof.resetLayout(activity)
            minMax.minValue.resetLayout(activity)
            minMax.maxValue.resetLayout(activity)
        }
    }

    fun checkMinMaxValue(): Boolean {
        return (binding.minMax.minValue.isDataValid(R.string.invalid_min_value)
                &&
                binding.minMax.maxValue.isDataValid(R.string.invalid_max_value))
    }
}