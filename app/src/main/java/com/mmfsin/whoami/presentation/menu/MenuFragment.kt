package com.mmfsin.whoami.presentation.menu

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mmfsin.whoami.R
import com.mmfsin.whoami.base.BaseFragment
import com.mmfsin.whoami.databinding.FragmentMenuBinding
import com.mmfsin.whoami.domain.models.Card
import com.mmfsin.whoami.presentation.MainActivity
import com.mmfsin.whoami.presentation.menu.decks.DecksSheet
import com.mmfsin.whoami.presentation.menu.listener.IMenuListener
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
            loading.visibility = View.VISIBLE
            ivTop.alpha = 0f
        }
    }

    override fun setListeners() {
        binding.apply {
            ivInstructions.setOnClickListener {
                (activity as MainActivity).apply {
                    openInstructions()
                    changeStatusBar(R.color.white)
                }
            }
            llPlay.setOnClickListener { activity?.showFragmentDialog(DecksSheet(this@MenuFragment)) }

            menuDecks.tvMyDecks.setOnClickListener { navigateTo(R.navigation.nav_graph_my_decks) }
            menuDecks.tvCreateDeck.setOnClickListener {
                navigateTo(
                    navGraph = R.navigation.nav_graph_my_decks,
                    booleanArgs = true
                )
            }
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is MenuEvent.Completed -> getTopCardImage()
                is MenuEvent.MenuCards -> menuFlowCompleted(event.cards)
                is MenuEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun getTopCardImage() = viewModel.getMenuCards()

    private fun menuFlowCompleted(cards: List<Card>) {
        binding.apply {
            loading.visibility = View.GONE
            setTopCardMenu(cards)
            tvTitle.animateX(-1000f, 10)
            countDown(100) {
                tvTitle.visibility = View.VISIBLE
                tvTitle.animateX(0f, 750)
                clBottom.visibility = View.VISIBLE
            }
        }
    }

    private fun setTopCardMenu(cards: List<Card>) {
        binding.apply {
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
                            ivTop.animate().alpha(1f).duration = 1000
                            return false
                        }
                    }).into(ivTop)
            }
        }
    }

    override fun startGame(deckId: String) = navigateTo(R.navigation.nav_graph_dashboard, deckId)

    private fun navigateTo(navGraph: Int, strArgs: String? = null, booleanArgs: Boolean? = null) {
        (activity as MainActivity).openBedRockActivity(
            navGraph = navGraph,
            strArgs = strArgs,
            booleanArgs = booleanArgs
        )
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}