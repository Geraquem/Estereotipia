package com.mmfsin.estereotipia.presentation.instructions.whoiswho.adapter.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.estereotipia.databinding.ItemInstructionSpaceBinding

class InstructionSpaceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemInstructionSpaceBinding.bind(view)
    private val c = binding.root.context

    companion object {
        const val ITEM_SPACE_TYPE = 2
    }

    fun bind() {
        binding.apply { }
    }
}