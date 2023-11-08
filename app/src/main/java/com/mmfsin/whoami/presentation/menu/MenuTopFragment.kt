package com.mmfsin.whoami.presentation.menu

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.mmfsin.whoami.R
import com.mmfsin.whoami.base.BaseFragment
import com.mmfsin.whoami.databinding.FragmentMenuTopBinding
import com.mmfsin.whoami.domain.models.Card
import com.mmfsin.whoami.presentation.menu.listener.IMenuListener
import com.mmfsin.whoami.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuTopFragment(val listener: IMenuListener) :
    BaseFragment<FragmentMenuTopBinding, MenuViewModel>() {

    override val viewModel: MenuViewModel by viewModels()
    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentMenuTopBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMenuCards()
    }

    override fun setUI() {}

    override fun setListeners() {
        binding.apply {
            ivInstructions.setOnClickListener { listener.openInstructions() }
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is MenuEvent.Completed -> {}
                is MenuEvent.MenuCards -> setMenuTopCard(event.cards)
                is MenuEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun setMenuTopCard(cards: List<Card>) {
        binding.apply {
            if (cards.isEmpty()) {
                ivTop.setImageResource(R.drawable.default_face)
            } else {
                val image = cards.first().image
                Glide.with(requireContext()).load(image).into(ivTop)
            }
        }
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}