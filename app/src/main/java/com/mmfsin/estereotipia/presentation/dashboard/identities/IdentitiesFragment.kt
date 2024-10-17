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
import android.widget.Toast
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
import com.mmfsin.estereotipia.presentation.dashboard.identities.dialogs.IdentityCardDialog
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
        restartAnimations()
    }

    private fun restartAnimations() {
        binding.apply {
//            llTexts.isVisible = false
//            llTexts.animateY(-500f, 10)
            image1.visibility = View.INVISIBLE
            image1.animateY(-500f, 10)
            image2.visibility = View.INVISIBLE
            image2.animateY(-500f, 10)
            image3.visibility = View.INVISIBLE
            image3.animateY(-500f, 10)

            btn.text = getString(R.string.identities_continue)
            clBtn.isVisible = false
            clBtn.animateY(500f, 10)
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

            llImage1.setOnDragListener(dragListenerImages)
            llImage2.setOnDragListener(dragListenerImages)
            llImage3.setOnDragListener(dragListenerImages)
        }
    }

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
            activity?.showFragmentDialog(IdentityCardDialog.newInstance(cards[pos].id))
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
//                    identity.text?.let { title ->
//                        tvTitle.text = title
//                        tvTitle.isVisible = true
//                    } ?: run { tvTitle.isVisible = false }
//                    tvText1.text = identity.text1
//                    tvText2.text = identity.text2
//                    tvText3.text = identity.text3
                }
            } else {
                Toast.makeText(mContext, "identities emptyyy", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun textsAnimations(cards: List<Card>) {
        binding.apply {
            countDown(750) {
//                llTexts.isVisible = true
//                llTexts.animateY(0f, 500)
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
                view.animateY(0f, 500)
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
                clBtn.isVisible = true
                clBtn.animateY(0f, 500)
            }
        }
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}