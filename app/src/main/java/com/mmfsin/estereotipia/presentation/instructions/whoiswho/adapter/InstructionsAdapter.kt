package com.mmfsin.estereotipia.presentation.instructions.whoiswho.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mmfsin.estereotipia.R
import com.mmfsin.estereotipia.domain.models.Instruction
import com.mmfsin.estereotipia.presentation.instructions.whoiswho.adapter.viewholders.InstructionSpaceViewHolder
import com.mmfsin.estereotipia.presentation.instructions.whoiswho.adapter.viewholders.InstructionViewHolder
import com.mmfsin.estereotipia.presentation.instructions.whoiswho.interfaces.IInstructionsListener

class InstructionsAdapter(
    private val instructions: List<Any>,
    private val listener: IInstructionsListener
) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            InstructionViewHolder.ITEM_INST_TYPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_instruction, parent, false)
                InstructionViewHolder(view)
            }

            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_instruction_space, parent, false)
                InstructionSpaceViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is InstructionViewHolder -> {
                val instruction = instructions[position] as Instruction
                holder.bind(instruction)
                holder.itemView.setOnClickListener {
                    if (instruction.text != null && instruction.layout == null) {
                        instruction.textOpened = !instruction.textOpened
                        notifyItemChanged(position)
                    } else listener.onInstructionClick(instruction)
                }
            }

            is InstructionSpaceViewHolder -> holder.bind()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(instructions[position] is Instruction) InstructionViewHolder.ITEM_INST_TYPE
        else InstructionSpaceViewHolder.ITEM_SPACE_TYPE
    }

    override fun getItemCount(): Int = instructions.size
}