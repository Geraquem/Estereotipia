package com.mmfsin.whoami.presentation.dashboard.cards.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mmfsin.whoami.R
import com.mmfsin.whoami.databinding.ItemCardBinding
import com.mmfsin.whoami.domain.models.Card
import com.mmfsin.whoami.presentation.dashboard.cards.interfaces.ICardsListener

class CardsAdapter(
    private val cards: List<Card>,
    private val listener: ICardsListener
) : RecyclerView.Adapter<CardsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemCardBinding.bind(view)
        fun bind(card: Card) {
            val c = binding.root.context
            binding.apply {
                ivDiscard.isVisible = card.discarded
                val color = if (card.selected) R.color.card_selected else R.color.white
                background.background.setTint(ContextCompat.getColor(c, color))
                Glide.with(binding.root.context).load(card.image).into(ivImage)
                tvName.text = card.name
            }
        }

        private fun updateBtnDiscardText(c: Context, card: Card) {
            val imgDiscarded = if (card.discarded) R.drawable.ic_redo
            else R.drawable.ic_discard_cross
//            binding.btnDiscard.setImageResource(imgDiscarded)
        }
    }

    fun updateSelectedCard(id: String) {
        var position: Int? = null
        cards.forEachIndexed() { i, card ->
            if (card.id == id) {
                card.selected = true
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
            LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cards[position])
        holder.itemView.setOnClickListener { listener.onCardClick(cards[position].id) }
    }

    override fun getItemCount(): Int = cards.size
}