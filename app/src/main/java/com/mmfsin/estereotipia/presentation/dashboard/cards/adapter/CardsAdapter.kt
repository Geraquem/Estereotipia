package com.mmfsin.estereotipia.presentation.dashboard.cards.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mmfsin.estereotipia.R
import com.mmfsin.estereotipia.databinding.ItemCardBinding
import com.mmfsin.estereotipia.domain.models.Card
import com.mmfsin.estereotipia.presentation.dashboard.cards.interfaces.ICardsListener

class CardsAdapter(
    private var columns: Int,
    private val cards: List<Card>,
    private val listener: ICardsListener
) : RecyclerView.Adapter<CardsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemCardBinding.bind(view)
        private val c = binding.root.context

        fun bind(listener: ICardsListener, card: Card, columns: Int) {
            binding.apply {
                ivDiscard.isVisible = card.discarded
                ivQuestion.isVisible = card.suspicious

                Glide.with(c).load(card.image).into(ivImage)
                tvName.text = card.name

                if (card.rivalCard) {
                    val golden = ResourcesCompat.getDrawable(
                        c.resources, R.drawable.bg_white_box_golden_line, null
                    )
                    clBackground.background = golden
                }

                /** change image dp */
                if (columns == 3) setImageSize(110)
                else setImageSize(156)

                root.setOnClickListener { listener.onCardClick(card) }
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

    fun updateDiscardedCard(id: String) {
        var position: Int? = null
        cards.forEachIndexed { i, card ->
            if (card.id == id) {
                card.discarded = !card.discarded
                if (card.discarded) {
                    card.suspicious = false
                }
                position = i
            }
        }
        position?.let { pos -> notifyItemChanged(pos) }
    }

    fun updateSuspiciousCard(id: String) {
        var position: Int? = null
        cards.forEachIndexed { i, card ->
            if (card.id == id) {
                card.suspicious = !card.suspicious
                if (card.suspicious) {
                    card.discarded = false
                }
                position = i
            }
        }
        position?.let { pos -> notifyItemChanged(pos) }
    }

    fun updateRivalCard(id: String) {
        var position: Int? = null
        cards.forEachIndexed() { i, card ->
            if (card.id == id) {
                card.suspicious = false
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
        holder.bind(listener, cards[position], columns)
    }

    override fun getItemCount(): Int = cards.size
}