package com.mmfsin.whoami.presentation.dashboard.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mmfsin.whoami.R
import com.mmfsin.whoami.databinding.ItemCardBinding
import com.mmfsin.whoami.domain.models.Card
import com.mmfsin.whoami.presentation.dashboard.interfaces.ICardListener

class CardsAdapter(
    private val cards: List<Card>,
    private val listener: ICardListener
) : RecyclerView.Adapter<CardsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemCardBinding.bind(view)
        fun bind(card: Card) {
            binding.apply {
                ivDiscard.isVisible = card.discarded
                Glide.with(binding.root.context).load(card.image).into(expandedImageView)
                tvName.text = card.name
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateDiscardedCards() {
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cards[position])
        holder.itemView.setOnClickListener { listener.onCardClick(cards[position].id) }
    }

    override fun getItemCount(): Int = cards.size
}