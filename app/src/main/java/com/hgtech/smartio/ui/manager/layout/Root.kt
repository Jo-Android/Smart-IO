package com.hgtech.smartio.ui.manager.layout

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Insets
import android.os.Build
import android.util.DisplayMetrics
import android.view.Window
import android.view.WindowInsets
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.FragmentActivity
import com.hgtech.smartio.R


fun AppCompatEditText.showError(context: Context, errorMessage: Int) {
//    if (isVisible)
    error = context.getString(errorMessage)
//    else
    Toast.makeText(
        context,
        context.getString(errorMessage),
        Toast.LENGTH_SHORT
    ).show()
}



fun hideKeyboard(activity: FragmentActivity) {
    val inputMethodManager: InputMethodManager = activity.getSystemService(
        Activity.INPUT_METHOD_SERVICE
    ) as InputMethodManager
    if (inputMethodManager.isAcceptingText) {
        inputMethodManager.hideSoftInputFromWindow(
            (activity.currentFocus ?: return).windowToken,
            0
        )
    }
    activity.currentFocus?.clearFocus()
}

fun createDialog(activity: FragmentActivity, layoutId: Int): Dialog {
    val dialog = Dialog(activity)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.window!!.decorView.setBackgroundResource(R.drawable.area_selection_background)
    dialog.window!!.decorView.setPadding(0, 0, 0, 0)
    dialog.window!!.attributes.windowAnimations = R.style.DialogSlide1

    dialog.window!!.attributes.height =
        LinearLayout.LayoutParams.WRAP_CONTENT
    dialog.window!!.attributes.width =
        ((getWidth(activity) - activity.resources.getDimension(R.dimen.size_40)).toInt())
    dialog.setContentView(layoutId)
    dialog.show()
    return dialog
}

@Suppress("DEPRECATION")
fun getWidth(activity: Activity): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics = activity.windowManager.currentWindowMetrics
        val insets: Insets = windowMetrics.windowInsets
            .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
        windowMetrics.bounds.width() - insets.left - insets.right
    } else {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        displayMetrics.widthPixels
    }
}