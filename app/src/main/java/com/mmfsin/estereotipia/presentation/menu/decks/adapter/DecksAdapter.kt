package com.mmfsin.estereotipia.presentation.menu.decks.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.estereotipia.R
import com.mmfsin.estereotipia.databinding.ItemSystemDeckBinding
import com.mmfsin.estereotipia.domain.models.Deck
import com.mmfsin.estereotipia.presentation.menu.decks.interfaces.IDeckListener

class DecksAdapter(
    private val decks: List<Deck>, private val listener: IDeckListener
) : RecyclerView.Adapter<DecksAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemSystemDeckBinding.bind(view)
        private val c = binding.root.context
        fun bind(deck: Deck, position: Int) {
            binding.apply {
                numeration.tvNumber.text = position.toString()
                tvName.text = deck.name
                tvCards.text = c.getString(R.string.decks_dialog_cards, deck.numOfCards.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_system_deck, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val deck = decks[position]
        holder.bind(deck, position + 1)
        holder.itemView.setOnClickListener { listener.onDeckClick(deck.id) }
    }

    override fun getItemCount(): Int = decks.size
}