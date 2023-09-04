package com.mmfsin.whoami.presentation.instructions


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.mmfsin.whoami.base.BaseFragmentNoVM
import com.mmfsin.whoami.databinding.FragmentInstructionsBinding
import com.mmfsin.whoami.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InstructionsFragment : BaseFragmentNoVM<FragmentInstructionsBinding>() {

    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentInstructionsBinding.inflate(inflater, container, false)

    override fun setUI() {}

    override fun setListeners() {
        binding.apply {
            tvClose.setOnClickListener { (activity as MainActivity).onBackPressed() }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}