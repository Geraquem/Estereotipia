package com.mmfsin.estereotipia.presentation.menu.decks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mmfsin.estereotipia.base.BaseBottomSheet
import com.mmfsin.estereotipia.databinding.BsheetDecksBinding
import com.mmfsin.estereotipia.domain.models.Deck
import com.mmfsin.estereotipia.presentation.menu.decks.adapter.DecksAdapter
import com.mmfsin.estereotipia.presentation.menu.decks.interfaces.IDeckListener
import com.mmfsin.estereotipia.presentation.menu.interfaces.IMenuListener
import com.mmfsin.estereotipia.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DecksSheet(val listener: IMenuListener) : BaseBottomSheet<BsheetDecksBinding>(), IDeckListener {

    private val viewModel: DecksViewModel by viewModels()

    override fun inflateView(inflater: LayoutInflater) = BsheetDecksBinding.inflate(inflater)

    override fun onStart() {
        super.onStart()
        val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.let {
            val behavior = BottomSheetBehavior.from(it)
            val layoutParams = it.layoutParams
            layoutParams.height = (resources.displayMetrics.heightPixels * 0.95).toInt()
            it.layoutParams = layoutParams
            behavior.peekHeight = layoutParams.height
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        viewModel.getDecks()
    }

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
            layoutManager = LinearLayoutManager(context)
            adapter = DecksAdapter(decks, this@DecksSheet)
        }
    }

    override fun onDeckClick(deckId: String) {
        listener.startGame(deckId)
        dismiss()
    }

    private fun error() = activity?.showErrorDialog(goBack = false)
}