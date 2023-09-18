package com.mmfsin.whoami.presentation.instructions

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmfsin.whoami.R
import com.mmfsin.whoami.base.BaseFragment
import com.mmfsin.whoami.databinding.FragmentInstructionsBinding
import com.mmfsin.whoami.domain.models.Instruction
import com.mmfsin.whoami.presentation.MainActivity
import com.mmfsin.whoami.presentation.instructions.adapter.InstructionsAdapter
import com.mmfsin.whoami.presentation.instructions.detail.DetailInstFragment
import com.mmfsin.whoami.presentation.instructions.interfaces.IInstructionsListener
import com.mmfsin.whoami.utils.INSTRUCTIONS_DETAIL
import com.mmfsin.whoami.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InstructionsFragment : BaseFragment<FragmentInstructionsBinding, InstructionsViewModel>(),
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
            tvClose.setOnClickListener { (activity as MainActivity).onBackPressed() }
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
        binding.rvInstructions.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = InstructionsAdapter(instructions, this@InstructionsFragment)
        }
    }

    override fun onInstructionClick(instruction: Instruction) {
        (activity as MainActivity).supportFragmentManager.beginTransaction()
            .addToBackStack(INSTRUCTIONS_DETAIL)
            .setCustomAnimations(R.anim.slide_from_left, 0, 0, R.anim.slide_to_right)
            .add(R.id.fc_instructions, DetailInstFragment(instruction)).commit()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}