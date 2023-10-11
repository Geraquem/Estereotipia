package com.mmfsin.whoami.presentation.instructions.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.whoami.R
import com.mmfsin.whoami.databinding.ItemInstructionBinding
import com.mmfsin.whoami.domain.models.Instruction
import com.mmfsin.whoami.presentation.instructions.interfaces.IInstructionsListener
import com.mmfsin.whoami.utils.setExpandableView

class InstructionsAdapter(
    private val instructions: List<Instruction>, private val listener: IInstructionsListener
) : RecyclerView.Adapter<InstructionsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemInstructionBinding.bind(view)
        private val c = binding.root.context
        fun bind(instruction: Instruction, position: Int, listener: IInstructionsListener) {
            binding.apply {
                tvTitle.text = instruction.title

                instruction.text?.let { text -> tvText.text = c.getText(text) }

                val view = instruction.layout?.let { View.VISIBLE } ?: run { View.GONE }
                ivArrow.visibility = view

                tvText.visibility = View.GONE

                cvMain.setOnClickListener {
                    if (instruction.text != null && instruction.layout == null) {
//                        val isVisible = tvText.isVisible
//                        tvText.isVisible = !isVisible

                        setExpandableView(tvText, llMain)

                    } else listener.onInstructionClick(instruction)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_instruction, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(instructions[position], position, listener)
    }

    override fun getItemCount(): Int = instructions.size
}