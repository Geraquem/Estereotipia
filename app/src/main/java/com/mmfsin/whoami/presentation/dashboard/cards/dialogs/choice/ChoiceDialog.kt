package com.mmfsin.whoami.presentation.dashboard.cards.dialogs.choice

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.mmfsin.whoami.R
import com.mmfsin.whoami.base.BaseDialog
import com.mmfsin.whoami.databinding.DialogCardChoiceBinding
import com.mmfsin.whoami.domain.models.Card
import com.mmfsin.whoami.presentation.dashboard.cards.dialogs.choice.ChoiceDialog.ActionType.FIRST_BUTTONS
import com.mmfsin.whoami.presentation.dashboard.cards.dialogs.choice.ChoiceDialog.ActionType.SECOND_BUTTONS
import com.mmfsin.whoami.presentation.dashboard.cards.dialogs.choice.ChoiceDialog.ResultType.LOOSER
import com.mmfsin.whoami.presentation.dashboard.cards.dialogs.choice.ChoiceDialog.ResultType.WINNER
import com.mmfsin.whoami.presentation.dashboard.cards.interfaces.ICardsListener
import com.mmfsin.whoami.utils.animateDialog
import com.mmfsin.whoami.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChoiceDialog(
    private val cardId: String, private val opportunities: Int, val listener: ICardsListener
) : BaseDialog<DialogCardChoiceBinding>() {

    private val viewModel: ChoiceDialogViewModel by viewModels()

    private var card: Card? = null

    private var firstAccess = true
    private var actionType = FIRST_BUTTONS
    private var result: ResultType? = null

    override fun inflateView(inflater: LayoutInflater) = DialogCardChoiceBinding.inflate(inflater)

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
        isCancelable = false
        binding.apply {
            if (actionType == FIRST_BUTTONS) {
                llFinalWinner.visibility = View.GONE
                llFinalLooser.visibility = View.GONE
                tvBack.visibility = View.GONE
            }
        }
    }

    override fun setListeners() {
        binding.apply {
            buttons.tvYes.setOnClickListener { finalAnswer(winner = true) }
            buttons.tvNo.setOnClickListener { finalAnswer(winner = false) }
            tvBack.setOnClickListener { dismiss() }
        }
    }

    private fun finalAnswer(winner: Boolean) {
        actionType = SECOND_BUTTONS
        binding.apply {
            llCard.visibility = View.INVISIBLE
            buttons.root.visibility = View.GONE
            tvBack.visibility = View.VISIBLE
            if (winner) {
                result = WINNER
                lottieWinner.setAnimation(R.raw.lottie_trophy)
                llFinalWinner.isVisible = true
                lottieWinner.playAnimation()
            } else {
                result = LOOSER
                if (opportunities == 2) {
                    tvLooserTitle.text = getString(R.string.choice_dialog_looser_title_1)
                    tvLooserDescription.text =
                        getString(R.string.choice_dialog_looser_opportunities)
                } else {
                    tvLooserTitle.text = getString(R.string.choice_dialog_looser_title_2)
                    tvLooserDescription.text =
                        getString(R.string.choice_dialog_looser_zero_opportunities)
                }
                nope.root.alpha = 0f
                card?.let { c -> Glide.with(requireContext()).load(c.name).into(ivLooserCard) }
                llFinalLooser.isVisible = true
                nope.root.animate().alpha(1f).duration = 750
            }
        }
        listener.choiceComplete(winner, cardId)
    }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is ChoiceDialogEvent.GetCard -> {
                    this.card = event.card
                    setCardInfo()
                }

                is ChoiceDialogEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun setCardInfo() {
        binding.apply {
            card?.let {
                Glide.with(requireContext()).load(it.image).into(ivImage)
                tvName.text = getString(R.string.choice_dialog_title, it.name)
            }
        }
    }

    private fun error() = activity?.showErrorDialog(goBack = false)

    companion object {
        fun newInstance(
            cardId: String, opportunities: Int, listener: ICardsListener
        ): ChoiceDialog {
            return ChoiceDialog(cardId, opportunities, listener)
        }
    }

    enum class ActionType {
        FIRST_BUTTONS, SECOND_BUTTONS
    }

    enum class ResultType {
        WINNER, LOOSER
    }
}