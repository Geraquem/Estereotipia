package com.mmfsin.estereotipia.presentation.menu.decks.adapter.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.estereotipia.R
import com.mmfsin.estereotipia.databinding.ItemSystemDeckBinding
import com.mmfsin.estereotipia.domain.models.Deck
import com.mmfsin.estereotipia.presentation.menu.decks.interfaces.IDeckListener
import com.mmfsin.estereotipia.utils.setGlideImage

class SystemDeckViewHolder(private val binding: ItemSystemDeckBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private val c = binding.root.context
    fun bind(deck: Deck, position: Int, listener: IDeckListener) {
        binding.apply {

            numeration.tvNumber.text = position.toString()
            if (deck.icon.isNotBlank()) c.setGlideImage(deck.icon, numeration.icon)
            else numeration.icon.setImageResource(R.drawable.ic_question_circle)

            tvName.text = deck.name
            tvCards.text = c.getString(R.string.decks_dialog_cards, deck.numOfCards.toString())

            root.setOnClickListener { listener.onDeckClick(deckId = deck.id) }
        }
    }
}