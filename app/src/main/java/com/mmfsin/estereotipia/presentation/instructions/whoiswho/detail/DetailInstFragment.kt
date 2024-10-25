package com.mmfsin.estereotipia.presentation.instructions.whoiswho.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mmfsin.estereotipia.base.BaseFragment
import com.mmfsin.estereotipia.databinding.FragmentInstructionsWiwDetailBinding
import com.mmfsin.estereotipia.domain.models.Instruction
import com.mmfsin.estereotipia.presentation.instructions.whoiswho.WiWInstructionsEvent
import com.mmfsin.estereotipia.presentation.instructions.whoiswho.WiWInstructionsViewModel
import com.mmfsin.estereotipia.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailInstFragment(private val instruction: Instruction) :
    BaseFragment<FragmentInstructionsWiwDetailBinding, WiWInstructionsViewModel>() {

    override val viewModel: WiWInstructionsViewModel by viewModels()

    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentInstructionsWiwDetailBinding.inflate(inflater, container, false)

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
            ivBack.setOnClickListener { activity?.onBackPressedDispatcher?.onBackPressed() }
        }
    }


    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is WiWInstructionsEvent.GetWiWInstructions -> {}
                is WiWInstructionsEvent.SomethingWentWrong -> activity?.showErrorDialog()
            }
        }
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}