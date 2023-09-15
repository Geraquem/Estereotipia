package com.mmfsin.whoami.presentation.instructions


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.mmfsin.whoami.base.BaseFragmentNoVM
import com.mmfsin.whoami.databinding.FragmentInstructionsBinding
import com.mmfsin.whoami.presentation.MainActivity
import com.mmfsin.whoami.utils.setExpandableView
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

            cv1.setOnClickListener { setExpandableView(details1.linear, ll1) }
            cv2.setOnClickListener { setExpandableView(details2.linear, ll2) }
            cv3.setOnClickListener { setExpandableView(details3.linear, ll3) }
            cv4.setOnClickListener { setExpandableView(details4.linear, ll4) }
            cv5.setOnClickListener { setExpandableView(details5.linear, ll6) }
            cv6.setOnClickListener { setExpandableView(details6.linear, ll6) }
            cv7.setOnClickListener { setExpandableView(details7.linear, ll7) }
            cv8.setOnClickListener { setExpandableView(details8.linear, ll8) }
            cv9.setOnClickListener { setExpandableView(details9.linear, ll9) }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}