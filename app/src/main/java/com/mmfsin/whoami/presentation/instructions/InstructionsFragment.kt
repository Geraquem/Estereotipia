package com.mmfsin.whoami.presentation.instructions


import android.animation.LayoutTransition.CHANGING
import android.content.Context
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
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

            cvHowToPlay.setOnClickListener { setExpandableView(htpDetails, llHtp) }
        }
    }

    private fun setExpandableView(expandable: View, linear: LinearLayout) {
        val v = if (expandable.isVisible) View.GONE else View.VISIBLE
        TransitionManager.beginDelayedTransition(linear, AutoTransition())
        expandable.visibility = v
        linear.layoutTransition.enableTransitionType(CHANGING)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}