package com.mmfsin.whoami.presentation.dashboard.cards.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mmfsin.whoami.R
import com.mmfsin.whoami.databinding.ItemCardBinding
import com.mmfsin.whoami.domain.models.Card
import com.mmfsin.whoami.presentation.dashboard.cards.interfaces.ICardsListener

class CardsAdapter(
    private val cards: List<Card>, private val listener: ICardsListener
) : RecyclerView.Adapter<CardsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemCardBinding.bind(view)
        private val c = binding.root.context

        fun bind(card: Card) {
            binding.apply {
                ivDiscard.isVisible = card.discarded
                Glide.with(c).load(card.image).into(ivImage)
                tvName.text = card.name

                if (card.rivalCard) {
                    val golden = ResourcesCompat.getDrawable(
                        c.resources, R.drawable.bg_white_box_golden_line, null
                    )
                    clBackground.background = golden
                }
            }
        }
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

    fun updateRivalCard(id: String) {
        var position: Int? = null
        cards.forEachIndexed() { i, card ->
            if (card.id == id) {
                card.discarded = false
                card.rivalCard = true
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