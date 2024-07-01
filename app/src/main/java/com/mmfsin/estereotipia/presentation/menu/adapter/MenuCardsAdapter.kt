package com.mmfsin.estereotipia.presentation.menu.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mmfsin.estereotipia.R
import com.mmfsin.estereotipia.databinding.ItemMenuCardBinding
import com.mmfsin.estereotipia.domain.models.Card
import com.mmfsin.estereotipia.presentation.menu.interfaces.IMenuCardsListener

class MenuCardsAdapter(
    private val cards: List<Card>,
    private val listener: IMenuCardsListener
) : RecyclerView.Adapter<MenuCardsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemMenuCardBinding.bind(view)
        fun bind(card: Card) {
            binding.apply {
                Glide.with(binding.root.context).load(card.image).into(ivImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_menu_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cards[position])
        holder.itemView.setOnClickListener { listener.onMenuCardClick() }
    }

    override fun getItemCount(): Int = cards.size
}