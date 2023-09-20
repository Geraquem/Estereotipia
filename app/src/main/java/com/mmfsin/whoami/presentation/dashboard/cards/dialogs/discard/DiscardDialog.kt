package com.mmfsin.whoami.presentation.dashboard.cards.dialogs.discard

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.mmfsin.whoami.R
import com.mmfsin.whoami.base.BaseDialog
import com.mmfsin.whoami.databinding.DialogCardDiscardBinding
import com.mmfsin.whoami.domain.models.Card
import com.mmfsin.whoami.presentation.models.PeopleCardState
import com.mmfsin.whoami.presentation.models.PeopleCardState.DISCARDED
import com.mmfsin.whoami.presentation.models.PeopleCardState.NONE
import com.mmfsin.whoami.utils.animateDialog
import com.mmfsin.whoami.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscardDialog(private val cardId: String) : BaseDialog<DialogCardDiscardBinding>() {

    private val viewModel: DiscardDialogViewModel by viewModels()

    private var card: Card? = null

    override fun inflateView(inflater: LayoutInflater) = DialogCardDiscardBinding.inflate(inflater)

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
        isCancelable = true
        binding.apply {
            card?.let {
                Glide.with(requireContext()).load(it.image).into(ivImage)
                tvName.text = it.name
                if (it.discarded) setDiscardedCard() else setNotDiscardedCard()
            }
        }
    }

    override fun setListeners() {
        binding.apply {
            buttons.ivDiscard.setOnClickListener { viewModel.discardCard(cardId) }
        }
    }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is DiscardDialogEvent.GetPeopleCard -> {
                    this.card = event.card
                    setUI()
                }
                is DiscardDialogEvent.DiscardPeopleCard -> {
                    event.discarded?.let {
                        if (it) dismiss()
                        else setNotDiscardedCard()
                    } ?: run { error() }
                }
                is DiscardDialogEvent.SomethingWentWrong -> error()
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

    private fun setState(state: PeopleCardState) {
        binding.apply {

            when (state) {
                NONE -> cvState.visibility = View.GONE
                DISCARDED -> {
                    tvState.text = getString(R.string.card_people_info_state_discard)
                }
            }
        }
    }

    private fun error() = activity?.showErrorDialog(goBack = false)

    companion object {
        fun newInstance(cardId: String): DiscardDialog {
            return DiscardDialog(cardId)
        }
    }
}