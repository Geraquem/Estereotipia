package com.mmfsin.estereotipia.presentation.menu.decks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.estereotipia.databinding.ItemCustomDeckBinding
import com.mmfsin.estereotipia.databinding.ItemDecksSeparatorBinding
import com.mmfsin.estereotipia.databinding.ItemSystemDeckBinding
import com.mmfsin.estereotipia.domain.models.AllDecks
import com.mmfsin.estereotipia.presentation.menu.decks.adapter.viewholders.CustomDeckViewHolder
import com.mmfsin.estereotipia.presentation.menu.decks.adapter.viewholders.SeparatorViewHolder
import com.mmfsin.estereotipia.presentation.menu.decks.adapter.viewholders.SystemDeckViewHolder
import com.mmfsin.estereotipia.presentation.menu.decks.interfaces.IDeckListener

class DecksAdapter(
    private val decks: AllDecks,
    private val listener: IDeckListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_SEPARATOR = 0
        const val TYPE_SYSTEM_DECK = 1
        const val TYPE_CUSTOM_DECK = 2
    }

    override fun getItemViewType(position: Int): Int {
        val totalSystemDecks = decks.systemDecks.size
        return when {
            position < totalSystemDecks -> TYPE_SYSTEM_DECK
            position == totalSystemDecks -> TYPE_SEPARATOR
            else -> TYPE_CUSTOM_DECK
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_SYSTEM_DECK -> {
                val binding = ItemSystemDeckBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                SystemDeckViewHolder(binding)
            }

            TYPE_SEPARATOR -> {
                val binding = ItemDecksSeparatorBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                SeparatorViewHolder(binding)
            }

            TYPE_CUSTOM_DECK -> {
                val binding = ItemCustomDeckBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                CustomDeckViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val totalSystemDecks = decks.systemDecks.size
        when (holder) {
            is SystemDeckViewHolder -> holder.bind(
                decks.systemDecks[position],
                position + 1,
                listener
            )

            is SeparatorViewHolder -> holder.bind()
            is CustomDeckViewHolder -> holder.bind(
                decks.customDecks[position - totalSystemDecks - 1],
                listener
            )
        }
    }

    override fun getItemCount(): Int {
        return if (decks.customDecks.isNotEmpty()) decks.systemDecks.size + decks.customDecks.size + 1
        else decks.systemDecks.size
    }
}