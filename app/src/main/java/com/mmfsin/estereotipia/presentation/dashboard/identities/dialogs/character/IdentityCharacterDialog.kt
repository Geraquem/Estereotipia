package com.mmfsin.estereotipia.presentation.dashboard.identities.dialogs.character

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.mmfsin.estereotipia.base.BaseDialog
import com.mmfsin.estereotipia.databinding.DialogIdentityCardBinding
import com.mmfsin.estereotipia.domain.models.Card
import com.mmfsin.estereotipia.utils.animateDialog
import com.mmfsin.estereotipia.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IdentityCharacterDialog(private val cardId: String) : BaseDialog<DialogIdentityCardBinding>() {

    private val viewModel: IdentityCharacterDialogViewModel by viewModels()

    private var card: Card? = null
    private var firstAccess = true

    override fun inflateView(inflater: LayoutInflater) = DialogIdentityCardBinding.inflate(inflater)

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
            card?.let { Glide.with(requireContext()).load(it.image).into(ivImage) }
        }
    }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is IdentityCharacterDialogEvent.GetCharacter -> {
                    this.card = event.card
                    setUI()
                }

                is IdentityCharacterDialogEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun error() = activity?.showErrorDialog()

    companion object {
        fun newInstance(cardId: String): IdentityCharacterDialog {
            return IdentityCharacterDialog(cardId)
        }
    }
}