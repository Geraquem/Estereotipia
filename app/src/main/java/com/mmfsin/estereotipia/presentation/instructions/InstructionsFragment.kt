package com.mmfsin.estereotipia.presentation.instructions

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.mmfsin.estereotipia.R
import com.mmfsin.estereotipia.base.BaseFragment
import com.mmfsin.estereotipia.databinding.FragmentInstructionsBinding
import com.mmfsin.estereotipia.domain.models.Instruction
import com.mmfsin.estereotipia.presentation.MainActivity
import com.mmfsin.estereotipia.presentation.instructions.adapter.InstructionsAdapter
import com.mmfsin.estereotipia.presentation.instructions.detail.DetailInstFragment
import com.mmfsin.estereotipia.presentation.instructions.interfaces.IInstructionsListener
import com.mmfsin.estereotipia.utils.INSTRUCTIONS_DETAIL
import com.mmfsin.estereotipia.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InstructionsFragment(val openHTP: Boolean = false) :
    BaseFragment<FragmentInstructionsBinding, InstructionsViewModel>(),
    IInstructionsListener {

    override val viewModel: InstructionsViewModel by viewModels()

    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentInstructionsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getInstructions()
    }

    override fun setUI() {}

    override fun setListeners() {
        binding.apply {
            tvClose.setOnClickListener {
                if (activity is MainActivity) (activity as MainActivity).onBackPressed()
                else activity?.onBackPressedDispatcher?.onBackPressed()
            }
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is InstructionsEvent.GetInstructions -> setUpInstructions(event.instructions)
                is InstructionsEvent.SomethingWentWrong -> activity?.showErrorDialog()
            }
        }
    }

    private fun setUpInstructions(instructions: List<Instruction>) {
        val finalList = mutableListOf<Any>()
        val group1 = instructions.filter { it.group == 1 }
        finalList.addItem(group1)
        finalList.add(SPACE)
        val group2 = instructions.filter { it.group == 2 }
        finalList.addItem(group2)
        finalList.add(SPACE)
        val group3 = instructions.filter { it.group == 3 }
        finalList.addItem(group3)

        setUpRecyclerView(finalList)
    }

    private fun MutableList<Any>.addItem(list: List<Any>) {
        for (item in list) this.add(item)
    }

    private fun setUpRecyclerView(instructions: List<Any>) {
        binding.rvInstructions.apply {
            (this.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            layoutManager = LinearLayoutManager(mContext)
            adapter = InstructionsAdapter(instructions, this@InstructionsFragment)
        }

        if (openHTP) {
            instructions.forEach { inst ->
                if (inst is Instruction && inst.layout == R.layout.inst_how_to_play) {
                    onInstructionClick(inst)
                }
            }
        }
    }

    override fun onInstructionClick(instruction: Instruction) {
        activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(INSTRUCTIONS_DETAIL)
            ?.setCustomAnimations(R.anim.slide_from_left, 0, 0, R.anim.slide_to_right)
            ?.add(R.id.fc_instructions, DetailInstFragment(instruction))?.commit()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    companion object {
        const val SPACE = "instruction_space"
    }
}