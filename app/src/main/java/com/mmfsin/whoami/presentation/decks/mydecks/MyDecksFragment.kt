package com.mmfsin.whoami.presentation.decks.mydecks

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mmfsin.whoami.R
import com.mmfsin.whoami.base.BaseFragment
import com.mmfsin.whoami.databinding.FragmentMenuBinding
import com.mmfsin.whoami.presentation.MainActivity
import com.mmfsin.whoami.presentation.decks.dialogs.MyDecksDialog
import com.mmfsin.whoami.presentation.menu.MenuFragmentDirections.Companion.actionMenuToAllCards
import com.mmfsin.whoami.presentation.menu.MenuFragmentDirections.Companion.actionMenuToDashboard
import com.mmfsin.whoami.presentation.menu.decks.DecksDialog
import com.mmfsin.whoami.utils.showErrorDialog
import com.mmfsin.whoami.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyDecksFragment : BaseFragment<FragmentMenuBinding, MyDecksViewModel>() {

    override val viewModel: MyDecksViewModel by viewModels()
    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentMenuBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun setUI() {
        binding.apply {
            setToolbar()
        }
    }

    private fun setToolbar() {
        (activity as MainActivity).apply {
            setUpToolbar(showBack = false, getString(R.string.my_decks_toolbar))
        }
    }

    override fun setListeners() {
        binding.apply {

        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is MyDecksEvent.Completed -> binding.loading.root.visibility = View.GONE
                is MyDecksEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}