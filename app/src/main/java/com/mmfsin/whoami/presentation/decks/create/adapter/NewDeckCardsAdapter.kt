package com.mmfsin.whoami.presentation.decks.create.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mmfsin.whoami.R
import com.mmfsin.whoami.databinding.ItemNewDeckCardBinding
import com.mmfsin.whoami.domain.models.Card
import com.mmfsin.whoami.domain.models.CreateDeckCard
import com.mmfsin.whoami.presentation.decks.create.interfaces.ICreateDeckCardListener

class NewDeckCardsAdapter(
    private val cards: List<CreateDeckCard>,
    private val listener: ICreateDeckCardListener
) : RecyclerView.Adapter<NewDeckCardsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemNewDeckCardBinding.bind(view)
        fun bind(card: CreateDeckCard, position: Int, listener: ICreateDeckCardListener) {
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

        private fun updateBtnDiscardText(c: Context, card: Card) {
            val imgDiscarded = if (card.discarded) R.drawable.ic_redo
            else R.drawable.ic_discard_cross
//            binding.btnDiscard.setImageResource(imgDiscarded)
        }
    }

    fun updateDiscardedCards(id: String) {
//        var position: Int? = null
//        cards.forEachIndexed() { i, card ->
//            if (card.id == id) {
//                card.discarded = !card.discarded
//                position = i
//            }
//        }
//        position?.let { pos -> notifyItemChanged(pos) }
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