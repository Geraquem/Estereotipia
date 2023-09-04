package com.mmfsin.whoami.presentation.dashboard.people.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.mmfsin.whoami.R
import com.mmfsin.whoami.base.BaseDialog
import com.mmfsin.whoami.databinding.DialogCardPeopleInfoBinding
import com.mmfsin.whoami.domain.models.Card
import com.mmfsin.whoami.presentation.models.PeopleCardState
import com.mmfsin.whoami.presentation.models.PeopleCardState.*
import com.mmfsin.whoami.utils.animateDialog
import com.mmfsin.whoami.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PeopleCardInfoDialog(private val cardId: String) : BaseDialog<DialogCardPeopleInfoBinding>() {

    private val viewModel: PeopleCardInfoViewModel by viewModels()

    private var card: Card? = null

    override fun inflateView(inflater: LayoutInflater) =
        DialogCardPeopleInfoBinding.inflate(inflater)

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

                val discardText: String
                val discardSrc: Int
                if (it.discarded) {
                    discardText = getString(R.string.card_people_info_dis_discard)
                    discardSrc = R.drawable.ic_redo
                    setState(DISCARDED)
                } else {
                    discardText = getString(R.string.card_people_info_discard)
                    discardSrc = R.drawable.ic_discard_cross
                    setState(NONE)
                }
                buttons.tvDiscard.text = discardText
                buttons.ivDiscard.setImageResource(discardSrc)
            }
        }
    }

    override fun setListeners() {
        binding.apply {
            buttons.btnDiscard.setOnClickListener { viewModel.discardCard(cardId) }
        }
    }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is PeopleCardInfoEvent.GetPeopleCard -> {
                    this.card = event.card
                    setUI()
                }
                is PeopleCardInfoEvent.DiscardPeopleCard -> {
                    event.discarded?.let {
                        if (it) dismiss()
                        else setNotDiscardedCard()
                    } ?: run { error() }
                }
                is PeopleCardInfoEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun setNotDiscardedCard() {
        setState(NONE)
        binding.apply {
            buttons.apply {
                tvDiscard.text = getString(R.string.card_people_info_discard)
                ivDiscard.setImageResource(R.drawable.ic_discard_cross)
            }
        }
    }

    private fun setState(state: PeopleCardState) {
        binding.apply {
            val color: Int
            val text: String
            when (state) {
                NONE -> {
                    color = R.color.state_none
                    text = getString(R.string.card_people_info_state_none)
                }
                DISCARDED -> {
                    color = R.color.state_discarded
                    text = getString(R.string.card_people_info_state_discard)
                }
                COULD_BE -> {
                    color = R.color.state_could_be
                    text = getString(R.string.card_people_info_state_could_be)
                }
            }
            activity?.let { tvState.setTextColor(ContextCompat.getColor(it, color)) }
            tvState.text = text
        }
    }

    private fun error() = activity?.showErrorDialog(goBack = false)

    companion object {
        fun newInstance(cardId: String): PeopleCardInfoDialog {
            return PeopleCardInfoDialog(cardId)
        }
    }
}