package com.mmfsin.whoami.presentation.customdecks.editcards.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mmfsin.whoami.R
import com.mmfsin.whoami.databinding.ItemNewDeckCardBinding
import com.mmfsin.whoami.domain.models.CreateDeckCard
import com.mmfsin.whoami.presentation.customdecks.editcards.interfaces.IEditCardsListener

class EditCardsAdapter(
    private val cards: List<CreateDeckCard>,
    private val listener: IEditCardsListener
) : RecyclerView.Adapter<EditCardsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemNewDeckCardBinding.bind(view)
        fun bind(card: CreateDeckCard, position: Int, listener: IEditCardsListener) {
            binding.apply {
                Glide.with(binding.root.context).load(card.card.image).into(ivImage)
                tvName.text = card.card.name

                val image = if (card.selected) R.drawable.ic_check_ok else R.drawable.ic_check_ko
                ivSelect.setImageResource(image)

                ivSelect.setOnClickListener {
                    card.selected = !card.selected
                    listener.onCheckClick(position, card.selected, card.card.id)
                }
            }
        }
    }

    fun getPositionByName(cardId: String?): Int {
        cards.forEachIndexed { i, card ->
            if (cardId == card.card.id) return i
        }
        return -1
    }

    fun checkPreviousCard(position: Int) {
        cards[position].selected = true
        notifyItemChanged(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_new_deck_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cards[position], position, listener)
        holder.itemView.setOnClickListener { listener.onCardClick(cards[position].card.id) }
    }

    override fun getItemCount(): Int = cards.size
}