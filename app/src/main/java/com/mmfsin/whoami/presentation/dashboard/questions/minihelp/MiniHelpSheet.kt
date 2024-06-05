package com.mmfsin.whoami.presentation.dashboard.questions.minihelp

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getDrawable
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mmfsin.whoami.R
import com.mmfsin.whoami.databinding.BsheetMiniHelpBinding
import com.mmfsin.whoami.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MiniHelpSheet : BottomSheetDialogFragment() {

    private lateinit var binding: BsheetMiniHelpBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BsheetMiniHelpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogThemeNoFloating)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState) as BottomSheetDialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            val bottomSheet =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)

                val metrics = Resources.getSystem().displayMetrics
                val maxHeight = (metrics.heightPixels * 1).toInt()
                it.layoutParams.height = maxHeight
                behavior.peekHeight = maxHeight
                it.requestLayout()

                it.background = getDrawable(requireContext(), R.drawable.bg_top_box)
            }
        }

        setListeners()
    }

    private fun setListeners() {
        binding.apply {
            llWhatNow.setOnClickListener {
                detailsWhatNow.root.isVisible = !detailsWhatNow.root.isVisible
            }

            llButtons.setOnClickListener {
                detailsButtons.root.isVisible = !detailsButtons.root.isVisible
            }

            llWhenEnds.setOnClickListener {
                detailsWhenEnds.root.isVisible = !detailsWhenEnds.root.isVisible
            }
        }
    }

    private fun error() = activity?.showErrorDialog(goBack = false)
}