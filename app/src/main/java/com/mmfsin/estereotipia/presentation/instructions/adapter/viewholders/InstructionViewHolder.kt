package com.mmfsin.estereotipia.presentation.instructions.adapter.viewholders

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.estereotipia.databinding.ItemInstructionBinding
import com.mmfsin.estereotipia.domain.models.Instruction

class InstructionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemInstructionBinding.bind(view)
    private val c = binding.root.context

    companion object {
        const val ITEM_INST_TYPE = 1
    }

    fun bind(instruction: Instruction) {
        binding.apply {
            clMain.setBackgroundResource(instruction.background)
            ivIcon.setImageResource(instruction.icon)
            tvTitle.text = instruction.title
            instruction.text?.let { text -> tvText.text = c.getText(text) }

            val view = instruction.layout?.let { View.VISIBLE } ?: run { View.GONE }
            ivArrow.visibility = view

            tvText.isVisible = instruction.textOpened
        }
    }
}