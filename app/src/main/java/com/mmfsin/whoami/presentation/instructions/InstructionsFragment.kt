package com.mmfsin.whoami.presentation.instructions

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmfsin.whoami.base.BaseFragment
import com.mmfsin.whoami.databinding.FragmentInstructionsAaaaBinding
import com.mmfsin.whoami.domain.models.Instruction
import com.mmfsin.whoami.presentation.MainActivity
import com.mmfsin.whoami.presentation.instructions.adapter.InstructionsAdapter
import com.mmfsin.whoami.presentation.instructions.interfaces.IInstructionsListener
import com.mmfsin.whoami.utils.setExpandableView
import com.mmfsin.whoami.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InstructionsFragment :
    BaseFragment<FragmentInstructionsAaaaBinding, InstructionsViewModel>(), IInstructionsListener {

    override val viewModel: InstructionsViewModel by viewModels()

    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentInstructionsAaaaBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getInstructions()
    }

    override fun setUI() {}

    override fun setListeners() {
        binding.apply {
            tvClose.setOnClickListener { (activity as MainActivity).onBackPressed() }

//            cv1.setOnClickListener { setExpandableView(details1.linear, ll1) }
//            cv2.setOnClickListener { setExpandableView(details2.linear, ll2) }
//            cv3.setOnClickListener { setExpandableView(details3.linear, ll3) }
//            cv4.setOnClickListener { setExpandableView(details4.linear, ll4) }
//            cv5.setOnClickListener { setExpandableView(details5.linear, ll6) }
//            cv6.setOnClickListener { setExpandableView(details6.linear, ll6) }
//            cv7.setOnClickListener { setExpandableView(details7.linear, ll7) }
//            cv8.setOnClickListener { setExpandableView(details8.linear, ll8) }
//            cv9.setOnClickListener { setExpandableView(details9.linear, ll9) }
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

    override fun onInstructionClick(ll1: LinearLayout, details: View) {
        setExpandableView(details, ll1)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}