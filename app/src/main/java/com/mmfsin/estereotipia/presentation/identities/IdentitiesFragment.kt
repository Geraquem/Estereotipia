package com.mmfsin.estereotipia.presentation.identities

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mmfsin.estereotipia.R
import com.mmfsin.estereotipia.base.BaseFragment
import com.mmfsin.estereotipia.base.bedrock.BedRockActivity
import com.mmfsin.estereotipia.databinding.FragmentIdentitiesBinding
import com.mmfsin.estereotipia.domain.models.Card
import com.mmfsin.estereotipia.domain.models.Identity
import com.mmfsin.estereotipia.presentation.allcards.dialogs.AllCardDialog
import com.mmfsin.estereotipia.utils.animateX
import com.mmfsin.estereotipia.utils.animateY
import com.mmfsin.estereotipia.utils.countDown
import com.mmfsin.estereotipia.utils.showErrorDialog
import com.mmfsin.estereotipia.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IdentitiesFragment : BaseFragment<FragmentIdentitiesBinding, IdentitiesViewModel>() {

    override val viewModel: IdentitiesViewModel by viewModels()
    private lateinit var mContext: Context

    private var identities = listOf<Identity>()
    private var cards = listOf<Card>()
    private var pos = 0

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentIdentitiesBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getIdentities()
    }

    override fun setUI() {
        (activity as BedRockActivity).apply {
            inDashboard = false
            setUpToolbar(getString(R.string.identities_toolbar))
        }
        restartAnimations()
    }

    private fun restartAnimations() {
        binding.apply {
            llTexts.isVisible = false
            llTexts.animateY(-500f, 10)
            image1.visibility = View.INVISIBLE
            image1.animateX(-500f, 10)
            image2.visibility = View.INVISIBLE
            image2.animateX(-500f, 10)
            image3.visibility = View.INVISIBLE
            image3.animateX(-500f, 10)
        }
    }

    override fun setListeners() {
        binding.apply {
            image1.setOnClickListener { showCardExpanded(0) }
            image2.setOnClickListener { showCardExpanded(1) }
            image3.setOnClickListener { showCardExpanded(2) }
        }
    }

    private fun showCardExpanded(pos: Int) {
        if (cards.isNotEmpty() && cards.size == 3) {
            activity?.showFragmentDialog(AllCardDialog.newInstance(cards[pos].id))
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is IdentitiesEvent.GetIdentities -> {
                    identities = event.identities
                    setUpTexts()
                    viewModel.getThreeRandomCards()
                }

                is IdentitiesEvent.GetThreeCards -> {
                    cards = event.cards
                    textsAnimations(event.cards)
                }

                is IdentitiesEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun setUpTexts() {
        binding.apply {
            if (identities.isNotEmpty()) {
                if (pos < identities.size) {
                    val identity = identities[pos]
                    identity.text?.let { title ->
                        tvTitle.text = title
                        tvTitle.isVisible = true
                    } ?: run { tvTitle.isVisible = false }
                    tvText1.text = identity.text1
                    tvText2.text = identity.text2
                    tvText3.text = identity.text3
                }
            } else {
                Toast.makeText(mContext, "identities emptyyy", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun textsAnimations(cards: List<Card>) {
        binding.apply {
            countDown(750) {
                llTexts.isVisible = true
                llTexts.animateY(0f, 500)
            }
            countDown(1000) { imagesAnimations(cards) }
        }
    }

    private fun imagesAnimations(cards: List<Card>) {
        binding.apply {
            if (cards.size == 3) {
                setImage(cards[0].image, image1)
                setImage(cards[1].image, image2)
                setImage(cards[2].image, image3)
            } else error()
        }
    }

    private fun setImage(image: String, view: ImageView) {
        Glide.with(mContext).load(image).listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean
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
                view.visibility = View.VISIBLE
                view.animateX(0f, 500)
                return false
            }
        }).into(view)
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}