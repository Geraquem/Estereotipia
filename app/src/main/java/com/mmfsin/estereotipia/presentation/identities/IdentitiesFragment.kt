package com.mmfsin.estereotipia.presentation.identities

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.mmfsin.estereotipia.R
import com.mmfsin.estereotipia.base.BaseFragment
import com.mmfsin.estereotipia.base.bedrock.BedRockActivity
import com.mmfsin.estereotipia.databinding.FragmentIdentitiesBinding
import com.mmfsin.estereotipia.domain.models.Card
import com.mmfsin.estereotipia.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IdentitiesFragment : BaseFragment<FragmentIdentitiesBinding, IdentitiesViewModel>() {

    override val viewModel: IdentitiesViewModel by viewModels()
    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentIdentitiesBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getThreeRandomCards()
    }

    override fun setUI() {
        (activity as BedRockActivity).apply {
            inDashboard = false
            setUpToolbar(getString(R.string.identities_toolbar))
        }
    }

    override fun setListeners() {
        binding.apply {
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is IdentitiesEvent.GetThreeCards -> setUpCards(event.cards)
                is IdentitiesEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun setUpCards(cards: List<Card>) {
        binding.apply {
            if (cards.size == 3) {
                Glide.with(mContext).load(cards[0].image).into(image1)
                Glide.with(mContext).load(cards[1].image).into(image2)
                Glide.with(mContext).load(cards[2].image).into(image3)
            } else error()
        }
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}