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
        fun bind(instruction: Instruction, listener: IInstructionsListener) {
            binding.apply {
                tvOrder.text = instruction.order.toString()
                tvTitle.text = instruction.title

                /** inflate layout description */
                vsDescription.layoutResource = instruction.layout

//                if (vsDescription.parent != null) {
//                    mViewStub.inflate();
//                } else {
//                    mViewStub.setVisibility(View.VISIBLE);
//                }

                val p = vsDescription.parent
                if (vsDescription.parent != null) {
                    val inflated = vsDescription.inflate()
                    inflated.visibility = View.GONE

                    binding.clMain.setOnClickListener {
                        listener.onInstructionClick(binding.llMain, inflated)
                    }
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
        holder.bind(instructions[position], listener)
//        holder.itemView.setOnClickListener { listener.onInstructionClick() }
    }
    
    override fun getItemCount(): Int = instructions.size
}