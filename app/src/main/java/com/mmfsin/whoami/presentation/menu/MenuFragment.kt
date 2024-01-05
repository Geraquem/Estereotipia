package com.mmfsin.whoami.presentation.menu

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.tabs.TabLayoutMediator
import com.mmfsin.whoami.R
import com.mmfsin.whoami.base.BaseFragment
import com.mmfsin.whoami.databinding.FragmentMenuBinding
import com.mmfsin.whoami.domain.models.Card
import com.mmfsin.whoami.presentation.MainActivity
import com.mmfsin.whoami.presentation.menu.MenuFragmentDirections.Companion.actionMenuToAllCards
import com.mmfsin.whoami.presentation.menu.MenuFragmentDirections.Companion.actionMenuToCreateDeck
import com.mmfsin.whoami.presentation.menu.MenuFragmentDirections.Companion.actionMenuToDashboard
import com.mmfsin.whoami.presentation.menu.MenuFragmentDirections.Companion.actionMenuToMyDecks
import com.mmfsin.whoami.presentation.menu.adapter.MenuViewPagerAdapter
import com.mmfsin.whoami.presentation.menu.decks.DecksDialog
import com.mmfsin.whoami.presentation.menu.listener.IMenuListener
import com.mmfsin.whoami.presentation.models.DeckType.SYSTEM_DECK
import com.mmfsin.whoami.utils.animateX
import com.mmfsin.whoami.utils.countDown
import com.mmfsin.whoami.utils.showErrorDialog
import com.mmfsin.whoami.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : BaseFragment<FragmentMenuBinding, MenuViewModel>(), IMenuListener {

    override val viewModel: MenuViewModel by viewModels()
    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentMenuBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val checkVersion = (activity as MainActivity).checkVersion
        if (checkVersion) {
            (activity as MainActivity).checkVersion = false
            viewModel.checkVersion()
        }
    }

    override fun setUI() {
        binding.apply {
            loading.root.visibility = View.VISIBLE
            tvTitle.visibility = View.INVISIBLE
            clBottom.visibility = View.INVISIBLE
            setToolbar()
        }
    }

    private fun setToolbar() {
        (activity as MainActivity).apply {
            this.inDashboard = false
            hideToolbar()
        }
    }

    override fun setListeners() {
        binding.apply {
            ivInstructions.setOnClickListener { (activity as MainActivity).openInstructions() }
            llPlay.setOnClickListener { activity?.showFragmentDialog(DecksDialog(this@MenuFragment)) }
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is MenuEvent.Completed -> getTopCardImage()
                is MenuEvent.MenuCards -> {
                    menuFlowCompleted(event.cards)
                }

                is MenuEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun getTopCardImage() = viewModel.getMenuCards()

    private fun menuFlowCompleted(cards: List<Card>) {
        binding.apply {
            loading.root.visibility = View.GONE
            val justOpened = (activity as MainActivity).justOpened
            if (justOpened) {
                setTopCardMenu(cards, true)
                (activity as MainActivity).justOpened = false
                tvTitle.animateX(-1000f, 10)
                countDown(100) {
                    tvTitle.visibility = View.VISIBLE
                    tvTitle.animateX(0f, 750)
                    setUpViewPager()
                    clBottom.visibility = View.VISIBLE
                }
            } else {
                setTopCardMenu(cards, false)
                setUpViewPager()
                tvTitle.visibility = View.VISIBLE
                clBottom.visibility = View.VISIBLE
            }
        }
    }

    private fun setTopCardMenu(cards: List<Card>, firstTime: Boolean) {
        binding.apply {
            ivTop.alpha = 0f
            if (cards.isEmpty()) ivTop.setImageResource(R.drawable.default_face)
            else {
                val image = cards.first().image
                Glide.with(requireContext()).load(image)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            val duration = if (firstTime) 1000 else 10
                            ivTop.animate().alpha(1f).duration = duration.toLong()
                            return false
                        }
                    }).into(ivTop)
            }
        }
    }

    private fun setUpViewPager() {
        binding.apply {
            activity?.let {
                viewPager.adapter = MenuViewPagerAdapter(it, this@MenuFragment)
                TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()
            }
        }
    }

    override fun startGame(deckId: String) = navigateTo(actionMenuToDashboard(deckId, SYSTEM_DECK))
    override fun openMyDecks() = navigateTo(actionMenuToMyDecks())
    override fun openCreateDeck() = navigateTo(actionMenuToCreateDeck())
    override fun openAllCards() = navigateTo(actionMenuToAllCards())

    private fun navigateTo(directions: NavDirections) = findNavController().navigate(directions)

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}