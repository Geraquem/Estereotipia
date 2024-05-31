package com.mmfsin.whoami.presentation.dashboard.questions.dialogs

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mmfsin.whoami.R
import com.mmfsin.whoami.databinding.BsheetQuestionListBinding
import com.mmfsin.whoami.domain.models.Question
import com.mmfsin.whoami.presentation.dashboard.questions.dialogs.adapter.QuestionsListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionsListSheet(private val questions: List<Question>) : BottomSheetDialogFragment() {

    private lateinit var binding: BsheetQuestionListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BsheetQuestionListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogThemeNoFloating)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            val bottomSheet =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                val metrics = Resources.getSystem().displayMetrics
                val maxHeight = (metrics.heightPixels * 0.85).toInt()
                it.layoutParams.height = maxHeight
                behavior.peekHeight = maxHeight
                it.requestLayout()

                it.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_top_box)
            }
        }
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpQuestions()
        setListeners()
    }

    private fun setUpQuestions() {
        binding.apply {
            tvEmpty.isVisible = questions.isEmpty()
            if (questions.isNotEmpty()) {
                rvQuestions.apply {
                    layoutManager = LinearLayoutManager(activity?.applicationContext)
                    adapter = QuestionsListAdapter(questions)
                }
            }
        }
    }

    private fun setListeners() {
        binding.apply {
            ivClose.setOnClickListener { dismiss() }
        }
    }
}