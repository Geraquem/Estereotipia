package com.mmfsin.estereotipia.presentation.dashboard.phrases.phrases.dialogs

import android.app.Dialog
import android.view.LayoutInflater
import com.mmfsin.estereotipia.base.BaseDialog
import com.mmfsin.estereotipia.databinding.DialogNewPhraseBinding
import com.mmfsin.estereotipia.domain.models.GamePhrase
import com.mmfsin.estereotipia.presentation.dashboard.phrases.phrases.listeners.INewPhraseListener
import com.mmfsin.estereotipia.utils.animateDialog
import com.mmfsin.estereotipia.utils.countDown
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewPhraseDialog(
    private val phrase: GamePhrase,
    private val listener: INewPhraseListener
) : BaseDialog<DialogNewPhraseBinding>() {

    private var firstAccess = true

    override fun inflateView(inflater: LayoutInflater) = DialogNewPhraseBinding.inflate(inflater)

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
            val text = phrase.question
            tvQuestion.text = text
            etAnswer.isEnabled = false
        }
    }


    override fun setListeners() {
        binding.apply {
            ivClose.setOnClickListener { dismiss() }

            countDown(500) {
                etAnswer.isEnabled = true
                btnAccept.setOnClickListener { answerQuestion(etAnswer.text.toString()) }
            }
        }
    }

    private fun answerQuestion(answer: String) {
        listener.answerPhrase(phrase, answer)
        dismiss()
    }

    companion object {
        fun newInstance(
            phrase: GamePhrase,
            listener: INewPhraseListener
        ): NewPhraseDialog {
            return NewPhraseDialog(phrase, listener)
        }
    }
}