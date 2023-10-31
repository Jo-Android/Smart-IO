package com.hgtech.smartio.ui.manager.fragment

import android.app.Activity
import android.app.Dialog
import android.graphics.Insets
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.hgtech.smartio.R
import com.hgtech.smartio.databinding.LayoutTopBarDialogBinding
import com.hgtech.smartio.ui.custom.LoadingDialog
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheet<B : ViewBinding>(
    private val bindingFactory: (LayoutInflater) -> B,
    private val shouldBeFullHeight: Boolean = false,
) : BottomSheetDialogFragment() {

    private var _binding: B? = null
    val binding get() = _binding!!
    protected val loadingDialog: LoadingDialog by lazy {
        LoadingDialog(requireContext())
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
                setOnShowListener {
                    setup(it as BottomSheetDialog)
                }

        }
    }

    private fun setup(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet =
            bottomSheetDialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
        val behavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheet.setBackgroundResource(R.drawable.background_round_top_only)
        if (shouldBeFullHeight) {
            val layoutParams = bottomSheet.layoutParams
            val windowHeight = getWindowHeight()
            if (layoutParams != null) {
                layoutParams.height = windowHeight
            }
            bottomSheet.layoutParams = layoutParams
        }
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun getWindowHeight(): Int {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            val windowMetrics = requireActivity().windowManager.currentWindowMetrics
            val insets: Insets = windowMetrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            windowMetrics.bounds.width() - insets.left - insets.right
        } else {
            val displayMetrics = DisplayMetrics()
            requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.heightPixels
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingFactory(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTopLayout()
        setupLayout()
        setListeners()
        observe()
        hideKeyboard()
    }

    abstract fun setupLayout()

    private fun hideKeyboard() {
        val inputMethodManager =
            requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            (requireActivity().currentFocus ?: View(
                requireContext()
            )).windowToken, 0
        )
    }


    abstract fun setupTopLayout()

    fun LayoutTopBarDialogBinding.setupLayout(text: Int) {
        title.text = getString(text)
        back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    abstract fun setListeners()

    abstract fun observe()

}