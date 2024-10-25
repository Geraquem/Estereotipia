package com.mmfsin.estereotipia.presentation.menu.decks.adapter.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.estereotipia.R
import com.mmfsin.estereotipia.databinding.ItemCustomDeckBinding
import com.mmfsin.estereotipia.domain.models.Deck
import com.mmfsin.estereotipia.presentation.menu.decks.interfaces.IDeckListener

class CustomDeckViewHolder(private val binding: ItemCustomDeckBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private val c = binding.root.context
    fun bind(deck: Deck, listener: IDeckListener) {
        binding.apply {
            val initial = if (deck.name.isNotEmpty()) deck.name.first().toString() else "?"
            tvInitial.text = initial
            tvName.text = deck.name
            tvCards.text = c.getString(R.string.decks_dialog_cards, deck.numOfCards.toString())

            root.setOnClickListener { listener.onDeckClick(deckId = deck.id) }
        }
    }
}