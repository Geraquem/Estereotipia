package com.mmfsin.whoami.presentation.customdecks.customdecks.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.whoami.R
import com.mmfsin.whoami.databinding.ItemCustomDeckBinding
import com.mmfsin.whoami.domain.models.Deck
import com.mmfsin.whoami.presentation.customdecks.customdecks.interfaces.ICustomDeckListener

class CustomDecksAdapter(
    private val decks: List<Deck>,
    private val listener: ICustomDeckListener
) : RecyclerView.Adapter<CustomDecksAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemCustomDeckBinding.bind(view)
        val c = binding.root.context
        fun bind(deck: Deck) {
            binding.apply {
                val initial = if (deck.name.isNotEmpty()) deck.name.first().toString() else "?"
                tvInitial.text = initial
                tvName.text = deck.name
                tvCards.text = c.getString(R.string.decks_dialog_cards, deck.numOfCards.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_custom_deck, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(decks[position])
        holder.itemView.setOnClickListener { listener.onCustomDeckClick(decks[position].id) }
    }

    override fun getItemCount(): Int = decks.size
}