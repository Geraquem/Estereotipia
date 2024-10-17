package com.mmfsin.estereotipia.presentation.dashboard.questions.dialogs

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mmfsin.estereotipia.R
import com.mmfsin.estereotipia.base.BaseBottomSheet
import com.mmfsin.estereotipia.databinding.BsheetMiniHelpBinding
import com.mmfsin.estereotipia.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MiniHelpSheet : BaseBottomSheet<BsheetMiniHelpBinding>() {

    override fun inflateView(inflater: LayoutInflater) = BsheetMiniHelpBinding.inflate(inflater)

    override fun onStart() {
        super.onStart()
        val bottomSheet =
            dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.let {
            val behavior = BottomSheetBehavior.from(it)
            val layoutParams = it.layoutParams
            layoutParams.height = resources.displayMetrics.heightPixels
            it.layoutParams = layoutParams
            behavior.peekHeight = layoutParams.height
        }
    }

    override fun setListeners() {
        binding.apply {
            llWhatNow.setOnClickListener {
                detailsWhatNow.root.isVisible = !detailsWhatNow.root.isVisible
                onItemClick(detailsWhatNow.root.isVisible, tvWhatNow)
            }

            llWeirdQuestions.setOnClickListener {
                detailsWeirdQuestions.root.isVisible = !detailsWeirdQuestions.root.isVisible
                onItemClick(detailsWeirdQuestions.root.isVisible, tvWeirdQuestions)
            }

            llHowStart.setOnClickListener {
                detailsHowStart.root.isVisible = !detailsHowStart.root.isVisible
                onItemClick(detailsHowStart.root.isVisible, tvHowStart)
            }

            llButtons.setOnClickListener {
                detailsButtons.root.isVisible = !detailsButtons.root.isVisible
                onItemClick(detailsButtons.root.isVisible, tvButtons)
            }

            llWhenEnds.setOnClickListener {
                detailsWhenEnds.root.isVisible = !detailsWhenEnds.root.isVisible
                onItemClick(detailsWhenEnds.root.isVisible, tvWhenEnds)
            }
        }
    }

    private fun onItemClick(opened: Boolean, textView: TextView) {
        val arrow = if (opened) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down
        textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, arrow, 0);
    }

    private fun error() = activity?.showErrorDialog(goBack = false)
}