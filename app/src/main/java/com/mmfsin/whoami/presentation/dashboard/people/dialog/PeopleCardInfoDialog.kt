package com.mmfsin.whoami.presentation.dashboard.people.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.mmfsin.whoami.R
import com.mmfsin.whoami.base.BaseDialog
import com.mmfsin.whoami.databinding.DialogCardPeopleInfoBinding
import com.mmfsin.whoami.domain.models.Card
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
                ivDiscard.isVisible = it.discarded
                Glide.with(requireContext()).load(it.image).into(ivImage)
                tvName.text = it.name
                val btnText = if (it.discarded) getString(R.string.card_people_info_dis_discard)
                else getString(R.string.card_people_info_discard)
                btnDiscard.text = btnText
            }
        }
    }

    override fun setListeners() {
        binding.apply {
            btnDiscard.setOnClickListener { viewModel.discardCard(cardId) }
        }
    }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is PeopleCardInfoEvent.GetPeopleCard -> {
                    this.card = event.card
                    setUI()
                }
                is PeopleCardInfoEvent.DiscardPeopleCard -> dismiss()
                is PeopleCardInfoEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun error() = activity?.showErrorDialog(goBack = false)

    companion object {
        fun newInstance(cardId: String): PeopleCardInfoDialog {
            return PeopleCardInfoDialog(cardId)
        }
    }
}