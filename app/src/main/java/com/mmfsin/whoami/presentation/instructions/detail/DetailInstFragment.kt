package com.mmfsin.whoami.presentation.instructions.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mmfsin.whoami.base.BaseFragment
import com.mmfsin.whoami.databinding.FragmentInstructionsDetailBinding
import com.mmfsin.whoami.domain.models.Instruction
import com.mmfsin.whoami.presentation.instructions.InstructionsEvent
import com.mmfsin.whoami.presentation.instructions.InstructionsViewModel
import com.mmfsin.whoami.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailInstFragment(private val instruction: Instruction) :
    BaseFragment<FragmentInstructionsDetailBinding, InstructionsViewModel>() {

    override val viewModel: InstructionsViewModel by viewModels()

    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentInstructionsDetailBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getInstructions()
    }

    override fun setUI() {
        binding.apply {
            tvTitle.text = instruction.title
            instruction.layout?.let { layout ->
                val inflater = LayoutInflater.from(mContext)
                val inflatedView = inflater.inflate(layout, null, false)
                llDescription.addView(inflatedView)
            } ?: run { error() }
        }
    }

    override fun setListeners() {
        binding.apply {
            ivBack.setOnClickListener { activity?.onBackPressed() }
        }
    }


    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is InstructionsEvent.GetInstructions -> {}
                is InstructionsEvent.SomethingWentWrong -> activity?.showErrorDialog()
            }
        }
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}