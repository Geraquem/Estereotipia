package com.mmfsin.estereotipia.presentation.menu.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.estereotipia.R
import com.mmfsin.estereotipia.databinding.ItemMenuCardBinding
import com.mmfsin.estereotipia.domain.models.Card
import com.mmfsin.estereotipia.presentation.menu.interfaces.IMenuCardsListener
import com.mmfsin.estereotipia.utils.setGlideImage

class MenuCardsAdapter(
    private val cards: List<Card>,
    private val listener: IMenuCardsListener
) : RecyclerView.Adapter<MenuCardsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemMenuCardBinding.bind(view)
        val c = binding.root.context
        fun bind(card: Card) {
            binding.apply {
                c.setGlideImage(card.image, ivImage, loading.image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_menu_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val card = cards[position]
        holder.bind(card)
        holder.itemView.setOnClickListener { listener.onMenuCardClick(cardId = card.id) }
    }

    override fun getItemCount(): Int = cards.size
}