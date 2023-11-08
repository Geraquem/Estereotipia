package com.mmfsin.whoami.presentation.menu

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.mmfsin.whoami.base.BaseFragmentNoVM
import com.mmfsin.whoami.databinding.FragmentMenuTopBinding
import com.mmfsin.whoami.presentation.menu.listener.IMenuListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuTopFragment(val listener: IMenuListener) : BaseFragmentNoVM<FragmentMenuTopBinding>() {

    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentMenuTopBinding.inflate(inflater, container, false)


    override fun setUI() {}

    override fun setListeners() {
        binding.apply {
            ivInstructions.setOnClickListener { listener.openInstructions() }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}