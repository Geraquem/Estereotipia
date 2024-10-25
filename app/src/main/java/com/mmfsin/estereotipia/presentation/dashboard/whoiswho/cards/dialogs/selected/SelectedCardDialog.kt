package com.mmfsin.estereotipia.presentation.dashboard.whoiswho.cards.dialogs.selected

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.mmfsin.estereotipia.base.BaseDialog
import com.mmfsin.estereotipia.databinding.DialogCardSelectBinding
import com.mmfsin.estereotipia.domain.models.Card
import com.mmfsin.estereotipia.utils.animateDialog
import com.mmfsin.estereotipia.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectedCardDialog(private val cardId: String) : BaseDialog<DialogCardSelectBinding>() {

    private val viewModel: SelectedCardDialogViewModel by viewModels()

    private var card: Card? = null
    private var firstAccess = true

    override fun inflateView(inflater: LayoutInflater) = DialogCardSelectBinding.inflate(inflater)

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
            card?.let {
                Glide.with(requireContext()).load(it.image).into(ivImage)
                tvName.text = it.name
            }
        }
    }

    override fun setListeners() {
        binding.apply {
            btnOk.setOnClickListener { dismiss() }
        }
    }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is SelectedCardDialogEvent.GetCard -> {
                    this.card = event.card
                    setUI()
                }

                is SelectedCardDialogEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun error() = activity?.showErrorDialog()

    companion object {
        fun newInstance(cardId: String): SelectedCardDialog {
            return SelectedCardDialog(cardId)
        }
    }
}