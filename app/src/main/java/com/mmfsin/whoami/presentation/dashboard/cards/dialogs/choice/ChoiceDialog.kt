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

    override fun inflateView(inflater: LayoutInflater) = DialogCardChoiceBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerCustomViewDialog(dialog)

    override fun onResume() {
        super.onResume()
        requireDialog().animateDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
        viewModel.getCardById(cardId)
    }

    override fun setUI() {
        isCancelable = false
        binding.apply {
            llFinalWinner.visibility = View.GONE
            llFinalLooser.visibility = View.GONE
            tvBack.visibility = View.GONE
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
        binding.apply {
            llCard.visibility = View.INVISIBLE
            buttons.root.visibility = View.GONE
            tvBack.visibility = View.VISIBLE
            if (winner) {
                lottieWinner.setAnimation(R.raw.lottie_trophy)
                llFinalWinner.isVisible = true
                lottieWinner.playAnimation()
            } else {
                val text = if (opportunities == 2) R.string.choice_dialog_looser_opportunities
                else R.string.choice_dialog_looser_zero_opportunities
                tvLooser.text = getString(text)
                lottieLooser.setAnimation(R.raw.lottie_sad_face)
                llFinalLooser.isVisible = true
                lottieLooser.playAnimation()
            }
        }
        listener.choiceComplete(winner)
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
}