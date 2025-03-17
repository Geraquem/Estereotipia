package com.mmfsin.estereotipia.presentation.dashboard.whoiswho.cards.dialogs.discard

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.isVisible
import com.mmfsin.estereotipia.R
import com.mmfsin.estereotipia.base.BaseDialog
import com.mmfsin.estereotipia.databinding.DialogCardDiscardBinding
import com.mmfsin.estereotipia.domain.models.Card
import com.mmfsin.estereotipia.presentation.dashboard.whoiswho.cards.dialogs.discard.DiscardDialog.ActionType.FIRST_BUTTONS
import com.mmfsin.estereotipia.presentation.dashboard.whoiswho.cards.dialogs.discard.DiscardDialog.ActionType.SECOND_BUTTONS
import com.mmfsin.estereotipia.presentation.dashboard.whoiswho.cards.interfaces.ICardsListener
import com.mmfsin.estereotipia.utils.animateDialog
import com.mmfsin.estereotipia.utils.setGlideImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscardDialog(
    private val card: Card,
    private val opportunities: Int,
    val listener: ICardsListener
) : BaseDialog<DialogCardDiscardBinding>() {


    private var firstAccess = true
    private var actionType = FIRST_BUTTONS

    override fun inflateView(inflater: LayoutInflater) = DialogCardDiscardBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerCustomViewDialog(dialog)

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
            requireContext().setGlideImage(card.image, ivImage, loading.image)
            tvName.text = card.name
            if (card.discarded) setDiscardedCard() else setNotDiscardedCard()
            if (card.suspicious) setSuspiciousCard() else setNotSuspiciousCard()

            when (actionType) {
                FIRST_BUTTONS -> {
                    if (opportunities == 0) buttons.root.visibility = View.GONE
                    else {
                        val choices = when (opportunities) {
                            1 -> getString(R.string.discard_card_choice_one_try)
                            else -> getString(R.string.discard_card_choice_two_try)
                        }
                        buttons.tvOpportunities.text = choices
                    }
                    choice.root.visibility = View.GONE
                }

                SECOND_BUTTONS -> {
                    /** already handled.
                     * This is for when the user blocks screen */
                }
            }
        }
    }

    override fun setListeners() {
        binding.apply {
            buttons.apply {
                ivFinalAnswer.setOnClickListener {
                    actionType = SECOND_BUTTONS
                    buttons.root.visibility = View.GONE
                    choice.root.visibility = View.VISIBLE
                }

                ivSuspicious.setOnClickListener {
                    listener.markSuspicious(card.id)
                    dismiss()
                }

                ivDiscard.setOnClickListener {
                    listener.markDiscarded(card.id)
                    dismiss()
                }
            }

            choice.btnNo.setOnClickListener {
                actionType = FIRST_BUTTONS
                choice.root.visibility = View.GONE
                buttons.root.visibility = View.VISIBLE
            }
            choice.btnYes.setOnClickListener {
                listener.makeChoice(card.id)
                dismiss()
            }
        }
    }

    private fun setDiscardedCard() {
        binding.apply {
            tvDiscarded.isVisible = true
            buttons.apply {
                ivDiscard.setImageResource(R.drawable.ic_redo)
                var color: Int? = null
                activity?.let { a -> color = getColor(a.applicationContext, R.color.black) }
                color?.let { c -> buttons.ivDiscard.setColorFilter(c) }
            }
        }
    }

    private fun setNotDiscardedCard() {
        binding.apply {
            tvDiscarded.isVisible = false
            buttons.apply {
                ivDiscard.setImageResource(R.drawable.ic_discard_cross)
                var color: Int? = null
                activity?.let { a -> color = getColor(a.applicationContext, R.color.red) }
                color?.let { c -> buttons.ivDiscard.setColorFilter(c) }
            }
        }
    }

    private fun setSuspiciousCard() {
        binding.apply {
            tvSuspicious.isVisible = true
            buttons.apply {
                ivSuspicious.setImageResource(R.drawable.ic_eye_hidden)
                var color: Int? = null
                activity?.let { a -> color = getColor(a.applicationContext, R.color.black) }
                color?.let { c -> buttons.ivSuspicious.setColorFilter(c) }
            }
        }
    }

    private fun setNotSuspiciousCard() {
        binding.apply {
            tvSuspicious.isVisible = false
            buttons.apply {
                ivSuspicious.setImageResource(R.drawable.ic_eye)
                var color: Int? = null
                activity?.let { a -> color = getColor(a.applicationContext, R.color.orange) }
                color?.let { c -> buttons.ivSuspicious.setColorFilter(c) }
            }
        }
    }

    companion object {
        fun newInstance(
            card: Card, opportunities: Int, listener: ICardsListener
        ): DiscardDialog {
            return DiscardDialog(card, opportunities, listener)
        }
    }

    enum class ActionType {
        FIRST_BUTTONS,
        SECOND_BUTTONS
    }
}