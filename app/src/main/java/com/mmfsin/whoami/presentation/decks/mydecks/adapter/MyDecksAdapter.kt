package com.mmfsin.whoami.presentation.decks.mydecks.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mmfsin.whoami.R
import com.mmfsin.whoami.databinding.ItemDeckBinding
import com.mmfsin.whoami.domain.models.MyDeck
import com.mmfsin.whoami.presentation.decks.mydecks.interfaces.IMyDeckListener

class MyDecksAdapter(
    private val decks: List<MyDeck>,
    private val listener: IMyDeckListener
) : RecyclerView.Adapter<MyDecksAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemDeckBinding.bind(view)
        val c = binding.root.context
        fun bind(deck: MyDeck) {
            binding.apply {
                Glide.with(binding.root.context).load(deck.image).into(ivImage)
                tvName.text = deck.name
                myDeck.tvCards.text = deck.numOfCards.toString()
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
        holder.itemView.setOnClickListener { listener.onMyDeckClick(decks[position].id) }
    }

    override fun getItemCount(): Int = decks.size
}