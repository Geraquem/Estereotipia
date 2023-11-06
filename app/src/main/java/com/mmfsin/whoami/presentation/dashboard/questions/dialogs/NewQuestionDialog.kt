package com.mmfsin.whoami.presentation.dashboard.questions.dialogs

import android.app.Dialog
import android.view.LayoutInflater
import com.mmfsin.whoami.R
import com.mmfsin.whoami.base.BaseDialog
import com.mmfsin.whoami.databinding.DialogNewQuestionBinding
import com.mmfsin.whoami.domain.models.Question
import com.mmfsin.whoami.presentation.dashboard.questions.dialogs.interfaces.INewQuestionListener
import com.mmfsin.whoami.utils.animateDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewQuestionDialog(
    private val listener: INewQuestionListener,
    private val question: Question? = null
) : BaseDialog<DialogNewQuestionBinding>() {

    private var firstAccess = true

    override fun inflateView(inflater: LayoutInflater) = DialogNewQuestionBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerViewDialog(dialog)

    override fun onResume() {
        super.onResume()
        if(firstAccess){
            firstAccess = false
                   if(firstAccess){
            firstAccess = false
            requireDialog().animateDialog()
        }
        }
    }

    override fun setUI() {
        isCancelable = true
        binding.apply {
            val text = question?.question ?: getString(R.string.new_question_empty)
            tvQuestion.text = text
        }
    }


    override fun setListeners() {
        binding.apply {
            tvAllQuestions.setOnClickListener {
                listener.goToAllQuestions()
                dismiss()
            }
            tvCards.setOnClickListener {
                listener.viewCards()
                dismiss()
            }
        }
    }

    companion object {
        fun newInstance(
            listener: INewQuestionListener,
            question: Question? = null
        ): NewQuestionDialog {
            return NewQuestionDialog(listener, question)
        }
    }
}