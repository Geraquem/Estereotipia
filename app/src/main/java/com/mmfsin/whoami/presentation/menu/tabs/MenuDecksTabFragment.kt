package com.mmfsin.whoami.presentation.menu.tabs

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.mmfsin.whoami.base.BaseFragmentNoVM
import com.mmfsin.whoami.databinding.IncludeMenuDecksBinding
import com.mmfsin.whoami.presentation.menu.listener.IMenuListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuDecksTabFragment(val listener: IMenuListener) :
    BaseFragmentNoVM<IncludeMenuDecksBinding>() {

    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = IncludeMenuDecksBinding.inflate(inflater, container, false)

    override fun setUI() {}

    override fun setListeners() {
        binding.apply {
            tvMyDecks.setOnClickListener { listener.openMyDecks() }
            tvCreateDeck.setOnClickListener { listener.openCreateDeck() }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}