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
    private var columns: Int,
    private val cards: List<Card>,
    private val listener: IAllCardsListener
) : RecyclerView.Adapter<SeeCardsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemAllCardBinding.bind(view)
        private val c = binding.root.context

        fun bind(card: Card, columns: Int) {
            binding.apply {
                Glide.with(binding.root.context).load(card.image).into(ivImage)
                tvName.text = card.name

                /** change image dp */
                if (columns == 3) setImageSize(110)
                else setImageSize(156)
            }
        }

        private fun setImageSize(size: Int) {
            val image = binding.ivImage

            val scale = c.resources.displayMetrics.density
            val newWidthInPx = (size * scale + 0.5f).toInt()
            val newHeightInPx = (size * scale + 0.5f).toInt()

            val layoutParams = image.layoutParams
            layoutParams.width = newWidthInPx
            layoutParams.height = newHeightInPx
            image.layoutParams = layoutParams
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_all_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cards[position], columns)
        holder.itemView.setOnClickListener { listener.onCardClick(cards[position].id) }
    }

    override fun getItemCount(): Int = cards.size
}