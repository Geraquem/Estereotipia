package com.mmfsin.estereotipia.presentation.instructions.identities

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.mmfsin.estereotipia.R
import com.mmfsin.estereotipia.base.BaseFragmentNoVM
import com.mmfsin.estereotipia.base.bedrock.BedRockActivity
import com.mmfsin.estereotipia.databinding.FragmentIdentitiesInstructionsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IdentitiesInstructionsFragment : BaseFragmentNoVM<FragmentIdentitiesInstructionsBinding>() {

    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentIdentitiesInstructionsBinding.inflate(inflater, container, false)

    override fun setUI() {
        setUpToolbar()
    }

    private fun setUpToolbar() {
        (activity as BedRockActivity).apply {
            inDashboard = false
            setUpToolbar(
                getString(R.string.identities_instructions_title),
                instructionsVisible = false
            )
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}