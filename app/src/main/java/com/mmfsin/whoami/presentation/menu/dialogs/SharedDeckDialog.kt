package com.mmfsin.whoami.presentation.menu.dialogs

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.mmfsin.whoami.R
import com.mmfsin.whoami.base.BaseDialog
import com.mmfsin.whoami.databinding.DialogSharedDeckBinding
import com.mmfsin.whoami.utils.animateDialog
import com.mmfsin.whoami.utils.checkNotNulls
import com.mmfsin.whoami.utils.decodeFromBase64
import com.mmfsin.whoami.utils.getCards
import com.mmfsin.whoami.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SharedDeckDialog(
    private val uri: Uri,
    val endDialog: (added: Boolean) -> Unit
) : BaseDialog<DialogSharedDeckBinding>() {

    private val viewModel: SharedDeckViewModel by viewModels()
    private var firstAccess = true

    private var name: String? = null
    private var cards: List<String>? = null

    override fun inflateView(inflater: LayoutInflater) = DialogSharedDeckBinding.inflate(inflater)

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

        val decodedText = uri.lastPathSegment?.let { encoded -> decodeFromBase64(encoded) }
        val data = decodedText?.split("/")
        data?.let { d ->
            if (data.size == 2) {
                name = d[0]
                cards = d[1].getCards()
            }
        } ?: run { error() }
    }

    override fun setUI() {
        binding.apply {
            isCancelable = true
            checkNotNulls(name, cards) { n, c ->
                tvName.text = n
                tvCards.text = getString(R.string.shared_deck_cards, c.size.toString())
            } ?: run { error() }
        }
    }

    override fun setListeners() {
        binding.apply {
            btnCancel.setOnClickListener { closeDialog(added = false) }
            btnAdd.setOnClickListener {
                checkNotNulls(name, cards) { n, c -> viewModel.addDeck(n, c) } ?: run { error() }
            }
        }
    }

    private fun closeDialog(added: Boolean) {
        endDialog(added)
        dismiss()
    }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is SharedDeckEvent.AddedCompleted -> {
                    closeDialog(added = true)
                }

                is SharedDeckEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun error() = activity?.showErrorDialog(goBack = false)

    companion object {
        fun newInstance(uri: Uri, action: (added: Boolean) -> Unit): SharedDeckDialog {
            return SharedDeckDialog(uri, action)
        }
    }
}