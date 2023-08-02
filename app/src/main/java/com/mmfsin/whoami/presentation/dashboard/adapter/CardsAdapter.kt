package com.mmfsin.whoami.presentation.dashboard.adapter

import android.content.Context
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
    private val cards: List<Card>, private val listener: ICardListener
) : RecyclerView.Adapter<CardsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemCardBinding.bind(view)
        fun bind(card: Card, listener: ICardListener) {
            val c = binding.root.context
            binding.apply {
                ivDiscard.isVisible = card.discarded
                Glide.with(binding.root.context).load(card.image).into(expandedImageView)
                tvName.text = card.name
                updateBtnDiscardText(c, card)
                btnDiscard.setOnClickListener {
                    card.discarded = !card.discarded
                    ivDiscard.isVisible = card.discarded
                    updateBtnDiscardText(c, card)
                    listener.onDiscardClick(card.id)
                }
            }
        }

        private fun updateBtnDiscardText(c: Context, card: Card) {
            val btnText = if (card.discarded) c.getString(R.string.card_info_dis_discard)
            else c.getString(R.string.card_info_discard)
            binding.btnDiscard.text = btnText
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cards[position], listener)
        holder.itemView.setOnClickListener { listener.onCardClick(cards[position].id) }
    }

    override fun getItemCount(): Int = cards.size
}