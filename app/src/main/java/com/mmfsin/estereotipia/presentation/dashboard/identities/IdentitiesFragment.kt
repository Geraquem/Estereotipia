package com.mmfsin.estereotipia.presentation.dashboard.identities

import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.DragEvent.ACTION_DRAG_ENDED
import android.view.DragEvent.ACTION_DRAG_ENTERED
import android.view.DragEvent.ACTION_DRAG_EXITED
import android.view.DragEvent.ACTION_DRAG_LOCATION
import android.view.DragEvent.ACTION_DRAG_STARTED
import android.view.DragEvent.ACTION_DROP
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.core.view.size
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mmfsin.estereotipia.R
import com.mmfsin.estereotipia.base.BaseFragment
import com.mmfsin.estereotipia.databinding.FragmentIdentitiesBinding
import com.mmfsin.estereotipia.domain.models.Card
import com.mmfsin.estereotipia.domain.models.Identity
import com.mmfsin.estereotipia.presentation.dashboard.identities.dialogs.card.IdentitiesCardSheet
import com.mmfsin.estereotipia.presentation.dashboard.identities.dialogs.character.IdentityCharacterDialog
import com.mmfsin.estereotipia.utils.animateX
import com.mmfsin.estereotipia.utils.animateY
import com.mmfsin.estereotipia.utils.countDown
import com.mmfsin.estereotipia.utils.hideAlpha
import com.mmfsin.estereotipia.utils.showAlpha
import com.mmfsin.estereotipia.utils.showErrorDialog
import com.mmfsin.estereotipia.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IdentitiesFragment : BaseFragment<FragmentIdentitiesBinding, IdentitiesViewModel>() {

    override val viewModel: IdentitiesViewModel by viewModels()
    private lateinit var mContext: Context

    private var identities = listOf<Identity>()
    private var actualIdentity: Identity? = null
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
        binding.apply {
            loading.root.isVisible = true
        }
        continueBtnVisibility(false)
        restartAnimations()
    }

    private fun restartAnimations() {
        binding.apply {
            image1.hideAlpha(ANIMATION_FAST_TIME)
            image1.animateX(-500f, 10)
            image2.hideAlpha(ANIMATION_FAST_TIME)
            image2.animateX(-500f, 10)
            image3.hideAlpha(ANIMATION_FAST_TIME)
            image3.animateX(-500f, 10)

            llChips.hideAlpha(ANIMATION_FAST_TIME)

            btnContinue.text = getString(R.string.identities_continue)
        }
    }

    override fun setListeners() {
        binding.apply {
//            tvText1.setOnLongClickListener {
//                setDragSettings(it)
//                true
//            }
//            tvText2.setOnLongClickListener {
//                setDragSettings(it)
//                true
//            }
//            tvText3.setOnLongClickListener {
//                setDragSettings(it)
//                true
//            }

            image1.setOnClickListener { showCardExpanded(0) }
            image2.setOnClickListener { showCardExpanded(1) }
            image3.setOnClickListener { showCardExpanded(2) }

            btnShowCard.setOnClickListener { showIdentitiesCard() }

            llImage1.setOnDragListener(dragListenerImages)
            llImage2.setOnDragListener(dragListenerImages)
            llImage3.setOnDragListener(dragListenerImages)
        }
    }

    private fun showIdentitiesCard() =
        actualIdentity?.let { activity?.showFragmentDialog(IdentitiesCardSheet(it)) }

    private fun setDragSettings(v: View) {
        val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
        val item = ClipData.Item("")
        val data = ClipData("", mimeTypes, item)

        val dragShadowBuilder = View.DragShadowBuilder(v)
        v.startDragAndDrop(data, dragShadowBuilder, v, 0)
        v.visibility = View.INVISIBLE
    }

    private fun showCardExpanded(pos: Int) {
        if (cards.isNotEmpty() && cards.size == 3) {
            activity?.showFragmentDialog(IdentityCharacterDialog.newInstance(cards[pos].id))
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is IdentitiesEvent.GetIdentities -> {
                    identities = event.identities
                    setActualIdentity()
                    viewModel.getThreeRandomCards()
                }

                is IdentitiesEvent.GetThreeCards -> {
                    cards = event.cards
                    setInitialPhase()

                }

                is IdentitiesEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun setActualIdentity() {
        if (identities.isNotEmpty()) actualIdentity = identities[pos]
        else error()
    }

    private fun setInitialPhase() {
        binding.apply {
            countDown(750) {
                imagesAnimations(cards)
                llChips.showAlpha(ANIMATION_TIME)
            }
            loading.root.isVisible = false
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
                view.showAlpha(ANIMATION_TIME)
                view.animateX(0f, ANIMATION_TIME)
                return false
            }
        }).into(view)
    }

    private val dragListenerImages = View.OnDragListener { view, event ->
        when (event.action) {
            ACTION_DRAG_STARTED -> {
                event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)
            }

            ACTION_DRAG_ENTERED, ACTION_DRAG_EXITED -> {
                view.invalidate()
                true
            }

            ACTION_DRAG_LOCATION -> true
            ACTION_DROP -> {
                view.invalidate()

                val v = event.localState as View
                val owner = v.parent as ViewGroup
                val destination = view as LinearLayout

                if (destination.size == 1) {
                    owner.removeView(v)
                    destination.addView(v)
                } else {
                    owner.removeView(v)
                    destination.addView(v)

                    val oldText = destination[1]
                    destination.removeView(oldText)
                    owner.addView(oldText)
                }

                checkIfReady()
                true
            }

            ACTION_DRAG_ENDED -> {
                val v = event.localState as View
                v.visibility = View.VISIBLE
                view.invalidate()
                true
            }

            else -> false
        }
    }

    private fun checkIfReady() {
        binding.apply {
            if (llImage1.size == 2 && llImage2.size == 2 && llImage3.size == 2) {
                continueBtnVisibility(show = true)
            }
        }
    }

    private fun continueBtnVisibility(show: Boolean) {
        binding.apply {
            clBtn.post {
                val btnHeight = clBtn.height.toFloat()
                if (show) {
                    clBtn.isEnabled = true
                    clBtn.showAlpha(ANIMATION_TIME)
                    clBtn.animateY(0f, ANIMATION_TIME)
                    btnShowCard.animateY(0f, ANIMATION_TIME)

                } else {
                    btnShowCard.animateY(btnHeight, ANIMATION_FAST_TIME)
                    clBtn.animateY(btnHeight, ANIMATION_FAST_TIME)
                    clBtn.hideAlpha(ANIMATION_FAST_TIME)
                    clBtn.isEnabled = false
                }
            }
        }
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    companion object {
        const val ANIMATION_TIME = 500L
        const val ANIMATION_FAST_TIME = 250L
    }
}