package com.mmfsin.whoami.presentation.dialogs.questions

import android.app.Dialog
import android.view.LayoutInflater
import com.mmfsin.whoami.base.BaseDialog
import com.mmfsin.whoami.databinding.DialogNewQuestionBinding
import com.mmfsin.whoami.domain.models.Question
import com.mmfsin.whoami.utils.animateDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewQuestionDialog(private val question: Question) :
    BaseDialog<DialogNewQuestionBinding>() {

    override fun inflateView(inflater: LayoutInflater) = DialogNewQuestionBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerViewDialog(dialog)

    override fun onResume() {
        super.onResume()
        requireDialog().animateDialog()
    }

    override fun setUI() {
        isCancelable = true
        binding.apply {
            tvQuestion.text = question.question
        }
    }


    override fun setListeners() {
        binding.apply {
        }
    }

    companion object {
        fun newInstance(question: Question): NewQuestionDialog {
            return NewQuestionDialog(question)
        }
    }
}