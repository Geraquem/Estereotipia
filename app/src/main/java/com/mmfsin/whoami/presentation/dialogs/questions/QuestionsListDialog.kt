package com.mmfsin.whoami.presentation.dialogs.questions

import android.app.Dialog
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmfsin.whoami.base.BaseDialog
import com.mmfsin.whoami.databinding.DialogQuestionListBinding
import com.mmfsin.whoami.domain.models.Question
import com.mmfsin.whoami.presentation.dialogs.questions.adapter.QuestionsListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionsListDialog(private val questions: List<Question>) :
    BaseDialog<DialogQuestionListBinding>() {

    override fun inflateView(inflater: LayoutInflater) = DialogQuestionListBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = bottomViewDialog(dialog)

    override fun setUI() {
        isCancelable = true
        binding.apply { }
        setUpQuestions()
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

    override fun setListeners() {
        binding.apply {
        }
    }
}