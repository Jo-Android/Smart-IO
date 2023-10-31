package com.hgtech.smartio.ui.manager.layout

import android.app.DatePickerDialog
import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import com.hgtech.smartio.R
import com.hgtech.smartio.databinding.LayoutTextWithHintBinding

fun LayoutTextWithHintBinding.getText(): String {
    return contentText.text.toString()
}


private fun LayoutTextWithHintBinding.setHint(hint: String) {
    hintText.text = hint
}

private fun LayoutTextWithHintBinding.setIme(ime: Int = EditorInfo.IME_ACTION_DONE) {
    contentText.imeOptions = ime
}

private fun LayoutTextWithHintBinding.setInputType(type: Int = InputType.TYPE_CLASS_TEXT) {
    contentText.inputType = type
}

private fun LayoutTextWithHintBinding.onEditorActionListener(
    isLast: Boolean = false,
    onClicked: () -> Unit
) {
    contentText.setOnEditorActionListener { _, _, _ ->
        if (isLast) resetLayout()
        onClicked.invoke()
        true
    }
}

fun LayoutTextWithHintBinding.resetLayout() {
    if (getText().isEmpty()) {
        hintText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
        contentText.isVisible = false
    }
}


fun LayoutTextWithHintBinding.openDatePicker(
    phone: LayoutTextWithHintBinding?,
    activity: FragmentActivity
) {
    hideKeyboard(activity)
    phone?.resetLayout()
    val datePickerDialog = DatePickerDialog(
        activity,
        { _, year, monthOfYear, dayOfMonth ->
            if (year != 0) {
                with(contentText) {
                    visibility = View.VISIBLE
                    setText(
                        StringBuilder(dayOfMonth.toString())
                            .append("/")
                            .append(monthOfYear + 1)
                            .append("/")
                            .append(year)
                    )
                }
            }
        },
        1990,
        0,
        1
    )
    datePickerDialog.show()
}

fun LayoutTextWithHintBinding.onClick(activity: FragmentActivity) {
    activity.currentFocus?.clearFocus()
    show()
    contentText.requestFocus()
    contentText.setSelection(getText().length)
    val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(contentText, InputMethodManager.SHOW_IMPLICIT)
}

fun LayoutTextWithHintBinding.show() {
    hintText.visibility = View.VISIBLE
    hintText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
    contentText.visibility = View.VISIBLE
}

fun onNextClick(layout: LayoutTextWithHintBinding, activity: FragmentActivity) {
    layout.onClick(activity)
}

fun LayoutTextWithHintBinding.setup(
    activity: FragmentActivity,
    hint: String,
    ime: Int = EditorInfo.IME_ACTION_DONE,
    type: Int = InputType.TYPE_CLASS_TEXT
) {
    root.setOnClickListener {
        onClick(activity)
    }
    setHint(hint)
    setIme(ime)
    setInputType(type)
}

fun LayoutTextWithHintBinding.setup(
    activity: FragmentActivity,
    hint: String,
    ime: Int = EditorInfo.IME_ACTION_DONE,
    type: Int = InputType.TYPE_CLASS_TEXT,
    isLast: Boolean = false,
    onClicked: () -> Unit
) {
    setup(activity, hint, ime, type)
    onEditorActionListener(isLast, onClicked)
}


fun LayoutTextWithHintBinding.setup(
    hint: String,
    ime: Int = EditorInfo.IME_ACTION_DONE,
    type: Int = InputType.TYPE_CLASS_TEXT,
    isLast: Boolean = false,
    onClicked: () -> Unit
) {
    setHint(hint)
    setIme(ime)
    setInputType(type)
    onEditorActionListener(isLast, onClicked)
}

fun LayoutTextWithHintBinding.showHidePassword(context: Context) {
    contentText.inputType =
        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
    showPassword.setOnClickListener {
        val start: Int
        val end: Int
        if (showPassword.text.toString() == context.getString(R.string.hide)) {
            start = contentText.selectionStart
            end = contentText.selectionEnd
            contentText.transformationMethod = PasswordTransformationMethod()
            contentText.setSelection(start, end)
            showPassword.text = context.getString(R.string.show)
        } else {
            start = contentText.selectionStart
            end = contentText.selectionEnd
            contentText.transformationMethod = null
            contentText.setSelection(start, end)
            showPassword.text = context.getString(R.string.hide)
        }
    }
}

fun LayoutTextWithHintBinding.setupShowHidePass() {
    contentText.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(
            s: CharSequence,
            start: Int,
            count: Int,
            after: Int
        ) = Unit

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            showPassword.visibility =
                if (s.isEmpty())
                    View.GONE
                else
                    View.VISIBLE
        }

        override fun afterTextChanged(s: Editable) = Unit
    })
}

// A placeholder username validation check
fun AppCompatEditText.isUserNameValid(): Boolean {
    return if (text.toString().contains("@")) {
        Patterns.EMAIL_ADDRESS.matcher(text.toString()).matches()
    } else
        false
}

fun AppCompatEditText.isTextValid(): Boolean {
    return text.toString().length > 2
}

fun isPaswordMatch(pass: AppCompatEditText, confirmPassword: AppCompatEditText): Boolean {
    return pass.text.toString() == confirmPassword.text.toString()
}

// A placeholder password validation check
fun AppCompatEditText.isPasswordValid(): Boolean {
    return text.toString().length > 5
}


