package com.mmfsin.estereotipia.presentation.customdecks.seecards.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mmfsin.estereotipia.R
import com.mmfsin.estereotipia.databinding.ItemAllCardBinding
import com.mmfsin.estereotipia.domain.models.Card
import com.mmfsin.estereotipia.presentation.allcards.interfaces.IAllCardsListener

class SeeCardsAdapter(
    private val cards: List<Card>,
    private val listener: IAllCardsListener
) : RecyclerView.Adapter<SeeCardsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemAllCardBinding.bind(view)
        fun bind(card: Card) {
            binding.apply {
                Glide.with(binding.root.context).load(card.image).into(ivImage)
                tvName.text = card.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_all_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cards[position])
        holder.itemView.setOnClickListener { listener.onCardClick(cards[position].id) }
    }

    override fun getItemCount(): Int = cards.size
}