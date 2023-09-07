package com.mmfsin.whoami.presentation.dashboard.tpm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mmfsin.whoami.R
import com.mmfsin.whoami.databinding.ItemTpmCardBinding
import com.mmfsin.whoami.domain.models.Card
import com.mmfsin.whoami.presentation.dashboard.tpm.interfaces.ITpmListener

class TpmAdapter(
    private val cards: List<Card>,
    private val listener: ITpmListener
) : RecyclerView.Adapter<TpmAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemTpmCardBinding.bind(view)
        fun bind(card: Card) {
            val c = binding.root.context
            binding.apply {
                val color = if (card.discarded) R.color.card_selected else R.color.white
                background.background.setTint(ContextCompat.getColor(c, color))
                Glide.with(binding.root.context).load(card.image).into(ivImage)
                tvName.text = card.name
            }
        }
    }

    fun updateSelectedCard(id: String) {
        var position: Int? = null
        cards.forEachIndexed() { i, card ->
            if (card.id == id) {
                card.discarded = true
                position = i
            }
        }
        position?.let { pos -> notifyItemChanged(pos) }
    }

    fun updateDiscardedCards(id: String) {
        var position: Int? = null
        cards.forEachIndexed() { i, card ->
            if (card.id == id) {
                card.discarded = !card.discarded
                position = i
            }
        }
        position?.let { pos -> notifyItemChanged(pos) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_tpm_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cards[position])
        holder.itemView.setOnClickListener { listener.onCardClick(cards[position].id) }
    }

    override fun getItemCount(): Int = cards.size
}