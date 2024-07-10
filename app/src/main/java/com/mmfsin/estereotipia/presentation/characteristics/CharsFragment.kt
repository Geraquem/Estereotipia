package com.mmfsin.estereotipia.presentation.characteristics

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mmfsin.estereotipia.base.BaseFragment
import com.mmfsin.estereotipia.base.bedrock.BedRockActivity
import com.mmfsin.estereotipia.databinding.FragmentCharsBinding
import com.mmfsin.estereotipia.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharsFragment : BaseFragment<FragmentCharsBinding, CharsViewModel>() {

    override val viewModel: CharsViewModel by viewModels()
    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentCharsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//            viewModel.getThreeCards()
    }

    override fun setUI() {
        (activity as BedRockActivity).apply {
            inDashboard = false
//            setUpToolbar(getString(R.string.custom_decks_toolbar))
            setUpToolbar("modo asignaciones" +
                    "")
        }
    }

    override fun setListeners() {
        binding.apply {
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is CharsEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}