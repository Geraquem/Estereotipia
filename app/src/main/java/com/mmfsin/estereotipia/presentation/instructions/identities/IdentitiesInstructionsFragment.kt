package com.mmfsin.estereotipia.presentation.instructions.identities

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.mmfsin.estereotipia.R
import com.mmfsin.estereotipia.base.BaseFragmentNoVM
import com.mmfsin.estereotipia.base.bedrock.BedRockActivity
import com.mmfsin.estereotipia.databinding.FragmentInstructionsIdentitiesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IdentitiesInstructionsFragment : BaseFragmentNoVM<FragmentInstructionsIdentitiesBinding>() {

    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentInstructionsIdentitiesBinding.inflate(inflater, container, false)

    override fun setUI() {
        setUpToolbar()
        binding.apply {
            tvTopText.text = getText(R.string.identities_card_example_title)
            tvOptionOne.text = getText(R.string.identities_card_example_option1)
            tvOptionTwo.text = getText(R.string.identities_card_example_option2)
            tvOptionThree.text = getText(R.string.identities_card_example_option3)
        }
    }

    private fun setUpToolbar() {
        (activity as BedRockActivity).apply {
            inDashboard = false
            setUpToolbar(
                getString(R.string.identities_instructions_title),
                instructionsVisible = false,
                instructionsNavGraph = R.navigation.nav_graph_instructions_identities
            )
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}