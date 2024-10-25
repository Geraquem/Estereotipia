package com.mmfsin.estereotipia.presentation.dashboard.whoiswho.questions.dialogs

import android.app.Dialog
import android.view.LayoutInflater
import com.mmfsin.estereotipia.base.BaseDialog
import com.mmfsin.estereotipia.databinding.DialogNewQuestionBinding
import com.mmfsin.estereotipia.domain.models.GameQuestion
import com.mmfsin.estereotipia.presentation.dashboard.whoiswho.questions.dialogs.interfaces.INewQuestionListener
import com.mmfsin.estereotipia.utils.animateDialog
import com.mmfsin.estereotipia.utils.countDown
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewQuestionDialog(
    private val question: GameQuestion,
    private val listener: INewQuestionListener
) : BaseDialog<DialogNewQuestionBinding>() {

    private var firstAccess = true

    override fun inflateView(inflater: LayoutInflater) = DialogNewQuestionBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerViewDialog(dialog)

    override fun onResume() {
        super.onResume()
        if (firstAccess) {
            firstAccess = false
            requireDialog().animateDialog()
        }
    }

    override fun setUI() {
        isCancelable = true
        binding.apply {
            val text = question.question
            tvQuestion.text = text
        }
    }


    override fun setListeners() {
        binding.apply {
            ivClose.setOnClickListener { dismiss() }

            tvCards.setOnClickListener {
                listener.viewCards()
                dismiss()
            }

            countDown(500) {
                rlYes.setOnClickListener { answerQuestion(answer = true) }
                rlNo.setOnClickListener { answerQuestion(answer = false) }
            }
        }
    }

    private fun answerQuestion(answer: Boolean) {
        listener.answer(question, answer)
        dismiss()
    }

    companion object {
        fun newInstance(
            question: GameQuestion,
            listener: INewQuestionListener
        ): NewQuestionDialog {
            return NewQuestionDialog(question, listener)
        }
    }
}