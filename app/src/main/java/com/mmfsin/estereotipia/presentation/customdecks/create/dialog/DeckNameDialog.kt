package com.mmfsin.estereotipia.presentation.customdecks.create.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.mmfsin.estereotipia.R
import com.mmfsin.estereotipia.base.BaseDialog
import com.mmfsin.estereotipia.databinding.DialogCreateDeckNameBinding
import com.mmfsin.estereotipia.presentation.customdecks.create.interfaces.ICreateDeckCardListener
import com.mmfsin.estereotipia.utils.animateDialog
import com.mmfsin.estereotipia.utils.countDown
import com.mmfsin.estereotipia.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeckNameDialog(val cards: List<String>, private val listener: ICreateDeckCardListener) :
    BaseDialog<DialogCreateDeckNameBinding>() {

    private val viewModel: DeckNameViewModel by viewModels()

    private var firstAccess = true

    override fun inflateView(inflater: LayoutInflater) =
        DialogCreateDeckNameBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerViewDialog(dialog)

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
    }

    override fun setUI() {
        isCancelable = true
        binding.apply {
        }
    }

    override fun setListeners() {
        binding.apply {
            btnAccept.setOnClickListener {
                val name = etName.text.toString()
                if (name.isNotEmpty() && name.isNotBlank()) {
                    countDown(300) { viewModel.createDeck(name, cards) }
                } else {
                    tilName.error = getString(R.string.custom_decks_create_new_name_error)
                    tilName.isErrorEnabled = true
                }
            }
        }
    }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is DeckNameEvent.CreatedCompleted -> endFlow()
                is DeckNameEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun endFlow() {
        binding.apply {
            listener.flowCompleted()
            dismiss()
        }
    }

    private fun error() = activity?.showErrorDialog()

    companion object {
        fun newInstance(cards: List<String>, listener: ICreateDeckCardListener): DeckNameDialog {
            return DeckNameDialog(cards, listener)
        }
    }
}