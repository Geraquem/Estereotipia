package com.mmfsin.estereotipia.presentation.menu

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
import com.mmfsin.estereotipia.R
import com.mmfsin.estereotipia.base.BaseFragment
import com.mmfsin.estereotipia.databinding.FragmentMenuBinding
import com.mmfsin.estereotipia.presentation.MainActivity
import com.mmfsin.estereotipia.presentation.menu.decks.DecksSheet
import com.mmfsin.estereotipia.presentation.menu.interfaces.IMenuCardsListener
import com.mmfsin.estereotipia.presentation.menu.interfaces.IMenuListener
import com.mmfsin.estereotipia.utils.PHRASES
import com.mmfsin.estereotipia.utils.QUESTIONS
import com.mmfsin.estereotipia.utils.animateX
import com.mmfsin.estereotipia.utils.animateY
import com.mmfsin.estereotipia.utils.countDown
import com.mmfsin.estereotipia.utils.showErrorDialog
import com.mmfsin.estereotipia.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : BaseFragment<FragmentMenuBinding, MenuViewModel>(), IMenuListener,
    IMenuCardsListener {

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

            if (shouldDoAnimations()) {
                tvTitle.visibility = View.INVISIBLE
                ivTop.alpha = 0f
                clBottom.visibility = View.INVISIBLE
            }
        }
    }

    override fun setListeners() {
        binding.apply {
            btnPlayWiw.setOnClickListener {
                activity?.showFragmentDialog(DecksSheet(true, this@MenuFragment))
            }
            btnInstWiw.setOnClickListener {
                navigateTo(
                    R.navigation.nav_graph_instructions_who_is_who,
                    strArgs = QUESTIONS
                )
            }

            btnPlayIdentities.setOnClickListener { (activity as MainActivity).openIdentitiesActivity() }
            btnInstIdentities.setOnClickListener { navigateTo(R.navigation.nav_graph_instructions_identities) }

            btnPlayPhrases.setOnClickListener {
                activity?.showFragmentDialog(DecksSheet(false, this@MenuFragment))
            }
            btnInstPhrases.setOnClickListener {
                navigateTo(
                    R.navigation.nav_graph_instructions_who_is_who,
                    strArgs = PHRASES
                )
            }
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is MenuEvent.Completed -> viewModel.getMenuTopCard()
                is MenuEvent.MenuCards -> {
                    event.card?.image?.let { img -> setTopCardMenu(img) }
                    menuFlowCompleted()
                }

                is MenuEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun menuFlowCompleted() {
        binding.apply {
            (activity as MainActivity).handleLoading(show = false)

            if (shouldDoAnimations()) {
                (activity as MainActivity).firstInitMenu = false

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
    }

    override fun onMenuCardClick(cardId: String?) {
        navigateTo(R.navigation.nav_graph_all_cards, strArgs = cardId)
    }

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
                        loading.visibility = View.INVISIBLE
                        ivTop.animate().alpha(1f).duration = 1500
                        return false
                    }
                }).into(ivTop)
        }
    }

    override fun startWhoIsWhoGame(deckId: String) =
        navigateTo(R.navigation.nav_graph_who_is_who, deckId)

    override fun startPhrasesGame(deckId: String) =
        navigateTo(R.navigation.nav_graph_phrases, deckId)

    private fun navigateTo(navGraph: Int, strArgs: String? = null, booleanArgs: Boolean? = null) {
        (activity as MainActivity).openBedRockActivity(
            navGraph = navGraph,
            strArgs = strArgs,
            booleanArgs = booleanArgs
        )
    }

    private fun shouldDoAnimations(): Boolean = (activity as MainActivity).firstInitMenu

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}