package com.mmfsin.estereotipia.presentation.dashboard.cards.dialogs.discard

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.mmfsin.estereotipia.R
import com.mmfsin.estereotipia.base.BaseDialog
import com.mmfsin.estereotipia.databinding.DialogCardDiscardBinding
import com.mmfsin.estereotipia.domain.models.Card
import com.mmfsin.estereotipia.presentation.dashboard.cards.dialogs.discard.DiscardDialog.ActionType.FIRST_BUTTONS
import com.mmfsin.estereotipia.presentation.dashboard.cards.dialogs.discard.DiscardDialog.ActionType.SECOND_BUTTONS
import com.mmfsin.estereotipia.presentation.dashboard.cards.interfaces.ICardsListener
import com.mmfsin.estereotipia.presentation.models.CardState
import com.mmfsin.estereotipia.presentation.models.CardState.DISCARDED
import com.mmfsin.estereotipia.presentation.models.CardState.NONE
import com.mmfsin.estereotipia.utils.animateDialog
import com.mmfsin.estereotipia.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscardDialog(
    private val cardId: String, private val opportunities: Int, val listener: ICardsListener
) : BaseDialog<DialogCardDiscardBinding>() {

    private val viewModel: DiscardDialogViewModel by viewModels()

    private var card: Card? = null

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
        viewModel.getCardById(cardId)
    }

    override fun setUI() {
        isCancelable = true
        binding.apply {
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
                    listener.markSuspicious(cardId)
                    dismiss()
                }

                ivDiscard.setOnClickListener { viewModel.discardCard(cardId) }
            }

            choice.btnNo.setOnClickListener {
                actionType = FIRST_BUTTONS
                choice.root.visibility = View.GONE
                buttons.root.visibility = View.VISIBLE
            }
            choice.btnYes.setOnClickListener {
                listener.makeChoice(cardId)
                dismiss()
            }
        }
    }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is DiscardDialogEvent.GetCard -> {
                    this.card = event.card
                    setCardInfo()
                }

                is DiscardDialogEvent.DiscardCard -> {
                    event.discarded?.let {
                        if (it) dismiss()
                        else setNotDiscardedCard()
                    } ?: run { error() }
                }

                is DiscardDialogEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun setCardInfo() {
        binding.apply {
            card?.let {
                Glide.with(requireContext()).load(it.image).into(ivImage)
                tvName.text = it.name
                if (it.discarded) setDiscardedCard() else setNotDiscardedCard()
            }
        }
    }

    private fun setDiscardedCard() {
        setState(DISCARDED)
        binding.apply {
            buttons.apply {
                ivDiscard.setImageResource(R.drawable.ic_redo)
                var color: Int? = null
                activity?.let { a -> color = getColor(a.applicationContext, R.color.black) }
                color?.let { c -> buttons.ivDiscard.setColorFilter(c) }
            }
        }
    }

    private fun setNotDiscardedCard() {
        setState(NONE)
        binding.apply {
            buttons.apply {
                ivDiscard.setImageResource(R.drawable.ic_discard_cross)

                var color: Int? = null
                activity?.let { a -> color = getColor(a.applicationContext, R.color.red) }
                color?.let { c -> buttons.ivDiscard.setColorFilter(c) }
            }
        }
    }

    private fun setState(state: CardState) {
        binding.apply {

            when (state) {
                NONE -> cvState.visibility = View.GONE
                DISCARDED -> {
                    tvState.text = getString(R.string.discard_card_info_state_discard)
                }
            }
        }
    }

    private fun error() = activity?.showErrorDialog(goBack = false)

    companion object {
        fun newInstance(
            cardId: String, opportunities: Int, listener: ICardsListener
        ): DiscardDialog {
            return DiscardDialog(cardId, opportunities, listener)
        }
    }

    enum class ActionType {
        FIRST_BUTTONS,
        SECOND_BUTTONS
    }
}