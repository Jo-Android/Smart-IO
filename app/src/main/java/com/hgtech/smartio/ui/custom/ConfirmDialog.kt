package com.hgtech.smartio.ui.custom

import android.app.Dialog
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.FragmentActivity
import com.hgtech.smartio.R
import com.hgtech.smartio.ui.manager.layout.createDialog


fun askConfirm(activity: FragmentActivity, ask: Int, icon: Int, onConfirm: () -> Unit) {
    setupConfirmDialog(
        activity,
        activity.getString(R.string.add),
        activity.getString(ask),
        icon
    ) { isConfirm: Boolean, _ ->
        if (isConfirm)
            onConfirm.invoke()
    }
}

fun setupConfirmDialog(
    activity: FragmentActivity,
    confirmText: String,
    alertText: String,
    confirmImage: Int = 0,
    cancelText: String = activity.getString(R.string.cancel),
    onConfirm: (isConfirm: Boolean, dialog: Dialog) -> Unit
) {
    val dialog = createDialog(activity, R.layout.alert_confirm)
    val alert = dialog.findViewById<TextView>(R.id.textView6)
    val confirmImg = dialog.findViewById<AppCompatImageView>(R.id.confirmImage)
    val confirmBtn = dialog.findViewById<AppCompatTextView>(R.id.confirmBtn)
    val cancelBtn = dialog.findViewById<AppCompatTextView>(R.id.cancelConfirm)
    val cancelImage = dialog.findViewById<AppCompatImageView>(R.id.imageView3)

    alert.text = alertText
    confirmBtn.text = confirmText
    confirmImg.setImageResource(confirmImage)
    cancelBtn.text = cancelText

    confirmBtn.setOnClickListener {
        onConfirm.invoke(true, dialog)
        dialog.dismiss()
    }

    cancelBtn.setOnClickListener {
        onConfirm.invoke(false, dialog)
        dialog.dismiss()
    }
    cancelImage.setOnClickListener {
        dialog.dismiss()
    }
}

fun setupErrorDialog(
    activity: FragmentActivity,
    alertText: String,
    confirmImage: Int = 0,
    onConfirm: () -> Unit
) {
    val dialog = createDialog(activity, R.layout.alert_error)
    val alert = dialog.findViewById<TextView>(R.id.textView6)
    val confirmImg = dialog.findViewById<AppCompatImageView>(R.id.confirmImage)
    val cancelImage = dialog.findViewById<AppCompatImageView>(R.id.imageView3)

    alert.text = alertText
    confirmImg.setImageResource(confirmImage)

    cancelImage.setOnClickListener {
        dialog.dismiss()
    }
    dialog.setOnDismissListener {
        onConfirm.invoke()
    }
}