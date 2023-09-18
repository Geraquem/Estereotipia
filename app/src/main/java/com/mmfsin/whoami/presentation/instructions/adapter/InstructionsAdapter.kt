package com.mmfsin.whoami.presentation.instructions.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.whoami.R
import com.mmfsin.whoami.databinding.ItemInstructionBinding
import com.mmfsin.whoami.domain.models.Instruction
import com.mmfsin.whoami.presentation.instructions.interfaces.IInstructionsListener

class InstructionsAdapter(
    private val instructions: List<Instruction>,
    private val listener: IInstructionsListener
) : RecyclerView.Adapter<InstructionsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemInstructionBinding.bind(view)
        fun bind(instruction: Instruction) {
            binding.apply {
                tvTitle.text = instruction.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_instruction, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(instructions[position])
        holder.itemView.setOnClickListener { listener.onInstructionClick(instructions[position]) }
    }

    override fun getItemCount(): Int = instructions.size
}