package com.hgtech.smartio.ui.manager.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.hgtech.smartio.ui.custom.LoadingDialog

abstract class BaseFragment<B : ViewBinding>(
    private val bindingFactory: (LayoutInflater) -> B,
) : Fragment() {

    private var _binding: B? = null
    val binding get() = _binding!!
    private val loadingDialog: LoadingDialog by lazy { LoadingDialog(requireContext()) }


    fun loadingDialogVisibility(isVisible: Boolean) {
        loadingDialog.shouldShowDialog(isVisible)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingFactory(inflater)
        initLayout()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        observe()
    }

    abstract fun observe()

    abstract fun initLayout()
}