package com.mmfsin.whoami.presentation.menu.decks

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mmfsin.whoami.base.BaseDialog
import com.mmfsin.whoami.databinding.DialogDecksBinding
import com.mmfsin.whoami.domain.models.Deck
import com.mmfsin.whoami.presentation.menu.decks.adapter.DecksAdapter
import com.mmfsin.whoami.presentation.menu.decks.interfaces.IDeckListener
import com.mmfsin.whoami.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DecksDialog(val navigate: (deckId: String) -> Unit) : BaseDialog<DialogDecksBinding>(),
    IDeckListener {

    private val viewModel: DecksViewModel by viewModels()

    override fun inflateView(inflater: LayoutInflater) = DialogDecksBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = bottomCustomViewDialog(dialog, 0.9)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
        viewModel.getDecks()
    }

    override fun setUI() {
        isCancelable = true
        binding.apply { }
    }

    override fun setListeners() {}

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is DecksEvent.GetDecks -> setUpDecks(event.decks)
                is DecksEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun setUpDecks(decks: List<Deck>) {
        binding.rvDecks.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = DecksAdapter(decks, this@DecksDialog)
        }
    }

    override fun onDeckClick(deckId: String) {
        navigate(deckId)
        dismiss()
    }

    private fun error() = activity?.showErrorDialog(goBack = false)

}