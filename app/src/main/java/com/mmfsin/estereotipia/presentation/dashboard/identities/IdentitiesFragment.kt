package com.mmfsin.estereotipia.presentation.dashboard.identities

import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.graphics.drawable.Drawable
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
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.core.view.size
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.textview.MaterialTextView
import com.mmfsin.estereotipia.R
import com.mmfsin.estereotipia.base.BaseFragment
import com.mmfsin.estereotipia.databinding.FragmentIdentitiesBinding
import com.mmfsin.estereotipia.domain.models.Card
import com.mmfsin.estereotipia.domain.models.IdentitiesPhase
import com.mmfsin.estereotipia.domain.models.IdentitiesPhase.PHASE_ONE
import com.mmfsin.estereotipia.domain.models.IdentitiesPhase.PHASE_TWO
import com.mmfsin.estereotipia.domain.models.IdentitiesSolution
import com.mmfsin.estereotipia.domain.models.Identity
import com.mmfsin.estereotipia.presentation.dashboard.identities.dialogs.card.IdentitiesCardSheet
import com.mmfsin.estereotipia.presentation.dashboard.identities.dialogs.character.IdentityCharacterDialog
import com.mmfsin.estereotipia.presentation.dashboard.identities.dialogs.initial.IInitialListener
import com.mmfsin.estereotipia.presentation.dashboard.identities.dialogs.initial.InitialIdentitiesDialog
import com.mmfsin.estereotipia.utils.animateX
import com.mmfsin.estereotipia.utils.animateY
import com.mmfsin.estereotipia.utils.countDown
import com.mmfsin.estereotipia.utils.hideAlpha
import com.mmfsin.estereotipia.utils.showAlpha
import com.mmfsin.estereotipia.utils.showErrorDialog
import com.mmfsin.estereotipia.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IdentitiesFragment : BaseFragment<FragmentIdentitiesBinding, IdentitiesViewModel>(),
    IInitialListener {

    override val viewModel: IdentitiesViewModel by viewModels()
    private lateinit var mContext: Context

    private var identities = listOf<Identity>()
    private var actualIdentity: Identity? = null
    private var cards = listOf<Card>()
    private var pos = 0

    private var cardDialog: IdentitiesCardSheet? = null

    private var phase: IdentitiesPhase = PHASE_ONE
    private var solution: IdentitiesSolution? = null

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentIdentitiesBinding.inflate(inflater, container, false)

    override fun setUI() {
        binding.apply {
            loading.root.isVisible = true
            btnContinue.animateX(500f, 10)
        }
        restartAnimations()
        showInitialDialog()
    }

    private fun showInitialDialog() {
        activity?.let { InitialIdentitiesDialog(this).show(it.supportFragmentManager, "") }
    }

    override fun openInstructions() {

    }

    override fun startGame() = viewModel.getIdentities()

    private fun restartAnimations() {
        binding.apply {
            image1.hideAlpha(ANIMATION_FAST_TIME)
            image1.animateY(-500f, 10)
            image2.hideAlpha(ANIMATION_FAST_TIME)
            image2.animateY(-500f, 10)
            image3.hideAlpha(ANIMATION_FAST_TIME)
            image3.animateY(-500f, 10)

            llTxtOne.hideAlpha(10)
            llTxtTwo.hideAlpha(10)
            llTxtThree.hideAlpha(10)
        }
    }

    override fun setListeners() {
        binding.apply {
//            ivBack.setOnClickListener { activity?.onBackPressedDispatcher?.onBackPressed() }

            image1.setOnClickListener { showCardExpanded(0) }
            image2.setOnClickListener { showCardExpanded(1) }
            image3.setOnClickListener { showCardExpanded(2) }

            tvOne.setOnLongClickListener {
                setDragSettings(it)
                true
            }
            tvTwo.setOnLongClickListener {
                setDragSettings(it)
                true
            }
            tvThree.setOnLongClickListener {
                setDragSettings(it)
                true
            }

            llImage1.setOnDragListener(dragListenerImages)
            llImage2.setOnDragListener(dragListenerImages)
            llImage3.setOnDragListener(dragListenerImages)

            btnShowCard.setOnClickListener {
                it.isEnabled = false
                openCardDialog()
                countDown(1000) { it.isEnabled = true }
            }

            btnContinue.setOnClickListener {
                setDecision()
                when (phase) {
                    PHASE_ONE -> setSecondPhase()
                    PHASE_TWO -> checkSolutions()
                }
            }
        }
    }

    private fun openCardDialog() {
        actualIdentity?.let {
            cardDialog = IdentitiesCardSheet(it)
            activity?.showFragmentDialog(IdentitiesCardSheet(it))
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
                    setPhaseOne()

                }

                is IdentitiesEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun setActualIdentity() {
        binding.apply {
            if (identities.isNotEmpty()) actualIdentity = identities[pos]
            else error()
        }
    }

    private fun setPhaseOne() {
        binding.apply {
            phase = PHASE_ONE
            ivPhase.setImageResource(R.drawable.ic_circle_check)
            countDown(500) {
                imagesAnimations(cards)
                llTxtOne.showAlpha(ANIMATION_TIME)
                llTxtTwo.showAlpha(ANIMATION_TIME)
                llTxtThree.showAlpha(ANIMATION_TIME)
            }

            countDown(2000) { if (cardDialog == null) openCardDialog() }
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
                view.animateY(0f, ANIMATION_TIME)
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
                btnContinue.animateX(0f, ANIMATION_TIME)
            }
        }
    }

    private fun setDecision() {
        binding.apply {
            if (llImage1.size == 2 && llImage2.size == 2 && llImage3.size == 2) {
                val image1solution = llImage1.getChildAt(1) as MaterialTextView
                val image2solution = llImage2.getChildAt(1) as MaterialTextView
                val image3solution = llImage3.getChildAt(1) as MaterialTextView
                solution = IdentitiesSolution(
                    solution1 = image1solution.text.toString(),
                    solution2 = image2solution.text.toString(),
                    solution3 = image3solution.text.toString()
                )
            }
        }
    }

    private fun setSecondPhase() {
        binding.apply {
            phase = PHASE_TWO
            btnContinue.animateX(500f, 10)
            countDown(500) { ivPhase.setImageResource(R.drawable.ic_question) }
            if (llImage1.size == 2 && llImage2.size == 2 && llImage3.size == 2) {
                val txt1 = llImage1.getChildAt(1)
                llImage1.removeViewAt(1)
                llTxtOne.addView(txt1)

                val txt2 = llImage2.getChildAt(1)
                llImage2.removeViewAt(1)
                llTxtTwo.addView(txt2)

                val txt3 = llImage3.getChildAt(1)
                llImage3.removeViewAt(1)
                llTxtThree.addView(txt3)
            }
        }
    }

    private fun checkSolutions() {
        binding.apply {
            btnContinue.isEnabled = false
            solution?.let { sol ->
                if (llImage1.size == 2 && llImage2.size == 2 && llImage3.size == 2) {
                    val image1solution = llImage1.getChildAt(1) as MaterialTextView
                    val image2solution = llImage2.getChildAt(1) as MaterialTextView
                    val image3solution = llImage3.getChildAt(1) as MaterialTextView

                    if (sol.solution1 == image1solution.text) {

                        val clonedTextView = MaterialTextView(mContext)
                        clonedTextView.layoutParams = image1solution.layoutParams
                        clonedTextView.text = image1solution.text
                        clonedTextView.textSize = image1solution.textSize
                        clonedTextView.setTextColor(image1solution.currentTextColor)

                        clonedTextView.background = image1solution.background

                        llImage1.addView(clonedTextView)

                    } else {
                    }
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