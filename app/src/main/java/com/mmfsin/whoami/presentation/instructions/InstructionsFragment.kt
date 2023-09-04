package com.mmfsin.whoami.presentation.instructions


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import com.mmfsin.whoami.base.BaseFragmentNoVM
import com.mmfsin.whoami.databinding.FragmentInstructionsBinding
import com.mmfsin.whoami.presentation.MainActivity
import com.mmfsin.whoami.utils.INSTRUCTIONS
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
            btn.setOnClickListener {
                (activity as MainActivity).supportFragmentManager.popBackStack(
                    INSTRUCTIONS, POP_BACK_STACK_INCLUSIVE
                )
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}