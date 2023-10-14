package com.mmfsin.whoami.presentation.instructions.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.whoami.R
import com.mmfsin.whoami.databinding.ItemInstructionBinding
import com.mmfsin.whoami.domain.models.Instruction
import com.mmfsin.whoami.presentation.instructions.interfaces.IInstructionsListener

class InstructionsAdapter(
    private val instructions: List<Instruction>, private val listener: IInstructionsListener
) : RecyclerView.Adapter<InstructionsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemInstructionBinding.bind(view)
        private val c = binding.root.context
        fun bind(instruction: Instruction, listener: IInstructionsListener) {
            binding.apply {
                ivIcon.setImageResource(instruction.icon)
                tvTitle.text = instruction.title
                instruction.text?.let { text -> tvText.text = c.getText(text) }

                val view = instruction.layout?.let { View.VISIBLE } ?: run { View.GONE }
                ivArrow.visibility = view

                tvText.isVisible = instruction.textOpened
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_instruction, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val instruction = instructions[position]
        holder.bind(instruction, listener)
        holder.itemView.setOnClickListener {
            if (instruction.text != null && instruction.layout == null) {
                instruction.textOpened = !instruction.textOpened
                notifyItemChanged(position)
            } else listener.onInstructionClick(instruction)
        }
    }

    override fun getItemCount(): Int = instructions.size
}