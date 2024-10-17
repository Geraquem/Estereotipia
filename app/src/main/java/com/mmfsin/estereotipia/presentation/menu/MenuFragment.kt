package com.mmfsin.estereotipia.presentation.menu

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mmfsin.estereotipia.R
import com.mmfsin.estereotipia.base.BaseFragment
import com.mmfsin.estereotipia.databinding.FragmentMenuBinding
import com.mmfsin.estereotipia.domain.models.Card
import com.mmfsin.estereotipia.presentation.MainActivity
import com.mmfsin.estereotipia.presentation.firstaccess.FirstAccessDialog
import com.mmfsin.estereotipia.presentation.firstaccess.interfaces.IFirstAccessListener
import com.mmfsin.estereotipia.presentation.menu.adapter.MenuCardsAdapter
import com.mmfsin.estereotipia.presentation.menu.decks.DecksSheet
import com.mmfsin.estereotipia.presentation.menu.interfaces.IMenuCardsListener
import com.mmfsin.estereotipia.presentation.menu.interfaces.IMenuListener
import com.mmfsin.estereotipia.utils.animateX
import com.mmfsin.estereotipia.utils.animateY
import com.mmfsin.estereotipia.utils.countDown
import com.mmfsin.estereotipia.utils.showErrorDialog
import com.mmfsin.estereotipia.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : BaseFragment<FragmentMenuBinding, MenuViewModel>(), IMenuListener,
    IMenuCardsListener, IFirstAccessListener {

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

        countDown(256) { (activity as MainActivity).openIdentitiesActivity() }
    }

    override fun setUI() {
        binding.apply {
            loading.visibility = View.VISIBLE
            tvTitle.visibility = View.INVISIBLE
            ivTop.alpha = 0f
            clBottom.visibility = View.INVISIBLE
        }
    }

    override fun setListeners() {
        binding.apply {
            ivInstructions.setOnClickListener { openInstructions() }

            llPlay.setOnClickListener { activity?.showFragmentDialog(DecksSheet(this@MenuFragment)) }
            llIdentities.setOnClickListener { (activity as MainActivity).openIdentitiesActivity() }

            menuDecks.tvCustomDecks.setOnClickListener { navigateTo(R.navigation.nav_graph_custom_decks) }
            menuDecks.tvCreateDeck.setOnClickListener {
                navigateTo(
                    navGraph = R.navigation.nav_graph_custom_decks,
                    booleanArgs = true
                )
            }

            menuCards.container.setOnClickListener { onMenuCardClick() }
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is MenuEvent.Completed -> viewModel.getMenuCards()
                is MenuEvent.MenuCards -> {
                    setUpMenuCards(event.cards)
                    viewModel.checkIfFirstTimeInApp()
                }

                is MenuEvent.FirstTime -> {
                    if (event.isFirstTime) {
                        countDown(1250) {
                            activity?.showFragmentDialog(FirstAccessDialog.newInstance(this@MenuFragment))
                        }
                    }
                }

                is MenuEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun setUpMenuCards(cards: List<Card>) {
        binding.menuCards.rvMenuCards.apply {
            layoutManager = LinearLayoutManager(mContext, HORIZONTAL, false)
            adapter = MenuCardsAdapter(cards.take(6), this@MenuFragment)
        }
        try {
            setTopCardMenu(cards.last().image)
        } catch (e: Exception) {
            Log.e("Error", "no cards available")
        }
        menuFlowCompleted()
    }

    private fun menuFlowCompleted() {
        binding.apply {
            loading.visibility = View.GONE
            tvTitle.animateX(-1000f, 10)
            clBottom.animateY(1500f, 10)
            countDown(500) {
                tvTitle.visibility = View.VISIBLE
                tvTitle.animateX(0f, 750)
                clBottom.visibility = View.VISIBLE
                clBottom.animateY(0f, 750)
            }
        }
    }

    override fun onMenuCardClick() = navigateTo(R.navigation.nav_graph_all_cards)

    private fun setTopCardMenu(topCardUrl: String) {
        binding.apply {
            Glide.with(requireContext()).load(topCardUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        ivTop.setImageResource(R.drawable.default_face)
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        countDown(200) { ivTop.animate().alpha(1f).duration = 1500 }
                        return false
                    }
                }).into(ivTop)
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

    override fun firstAccessOpenInstructions() = openInstructions(openHTP = true)

    private fun openInstructions(openHTP: Boolean = false) {
        (activity as MainActivity).apply {
            openInstructions(openHTP)
            changeStatusBar(R.color.white)
        }
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}