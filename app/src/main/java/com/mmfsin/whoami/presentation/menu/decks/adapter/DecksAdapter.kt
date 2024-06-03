package com.mmfsin.whoami.presentation.menu.decks.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mmfsin.whoami.R
import com.mmfsin.whoami.databinding.ItemDeckBinding
import com.mmfsin.whoami.domain.models.Deck
import com.mmfsin.whoami.presentation.menu.decks.interfaces.IDeckListener

class DecksAdapter(
    private val decks: List<Deck>, private val listener: IDeckListener
) : RecyclerView.Adapter<DecksAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemDeckBinding.bind(view)
        private val c = binding.root.context
        fun bind(deck: Deck) {
            binding.apply {
                tvName.text = deck.name
                tvCards.text = c.getString(R.string.decks_dialog_cards, deck.numOfCards.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_deck, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(decks[position])
        holder.itemView.setOnClickListener { listener.onDeckClick(decks[position].id) }
    }

    override fun getItemCount(): Int = decks.size
}