package com.mmfsin.whoami.presentation.customdecks.mydecks.dialogs.edit

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import com.mmfsin.whoami.R
import com.mmfsin.whoami.base.BaseDialog
import com.mmfsin.whoami.databinding.DialogCreateDeckNameBinding
import com.mmfsin.whoami.presentation.customdecks.mydecks.dialogs.MyDeckEvent
import com.mmfsin.whoami.presentation.customdecks.mydecks.dialogs.MyDeckViewModel
import com.mmfsin.whoami.presentation.customdecks.mydecks.interfaces.IMyDeckListener
import com.mmfsin.whoami.utils.animateDialog
import com.mmfsin.whoami.utils.countDown
import com.mmfsin.whoami.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditMyDeckDialog(private val myDeckId: String, val listener: IMyDeckListener) :
    BaseDialog<DialogCreateDeckNameBinding>() {

    private val viewModel: MyDeckViewModel by viewModels()

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
        viewModel.getMyDeck(myDeckId)
    }

    override fun setUI() {
        isCancelable = true
        binding.apply {
            tvTitle.text = getString(R.string.my_decks_dialog_edit_name)
            llFlowEnd.visibility = View.GONE
            tvError.visibility = View.GONE
            btnAccept.text = getString(R.string.my_decks_dialog_edit)
        }
    }

    override fun setListeners() {
        binding.apply {
            btnAccept.setOnClickListener {
                val name = etName.text.toString()
                if (name.isNotEmpty() && name.isNotBlank()) {
                    countDown(300) {
                        viewModel.editMyDeckName(myDeckId, name)
                    }
                } else tvError.visibility = View.VISIBLE
            }
        }
    }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is MyDeckEvent.GetDeck -> {
                    binding.apply {
                        val name = event.deck.name
                        etName.setText(name)
                    }
                }

                is MyDeckEvent.EditedCompleted -> {
                    listener.editCompleted()
                    dismiss()
                }

                is MyDeckEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun error() = activity?.showErrorDialog(goBack = false)

    companion object {
        fun newInstance(myDeckId: String, listener: IMyDeckListener): EditMyDeckDialog {
            return EditMyDeckDialog(myDeckId, listener)
        }
    }
}