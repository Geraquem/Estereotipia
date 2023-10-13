package com.mmfsin.whoami.presentation.decks.create.dialog

import android.animation.Animator
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import com.mmfsin.whoami.R
import com.mmfsin.whoami.base.BaseDialog
import com.mmfsin.whoami.databinding.DialogCreateDeckNameBinding
import com.mmfsin.whoami.domain.models.MyDeck
import com.mmfsin.whoami.presentation.decks.create.interfaces.ICreateDeckCardListener
import com.mmfsin.whoami.utils.animateDialog
import com.mmfsin.whoami.utils.closeKeyboardFromDialog
import com.mmfsin.whoami.utils.countDown
import com.mmfsin.whoami.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeckNameDialog(val cards: String, private val listener: ICreateDeckCardListener) :
    BaseDialog<DialogCreateDeckNameBinding>() {

    private val viewModel: DeckNameViewModel by viewModels()

    override fun inflateView(inflater: LayoutInflater) =
        DialogCreateDeckNameBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerViewDialog(dialog)

    override fun onResume() {
        super.onResume()
        requireDialog().animateDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
    }

    override fun setUI() {
        isCancelable = true
        binding.apply {
            tvError.visibility = View.GONE
            llFlowEnd.visibility = View.GONE
        }
    }

    override fun setListeners() {
        binding.apply {
            btnAccept.setOnClickListener {
                val name = etTitle.text.toString()
                if (name.isNotEmpty() && name.isNotBlank()) {
                    countDown(300) {
                        viewModel.createDeck(MyDeck(name = name, cards = cards))
                    }

                } else tvError.visibility = View.VISIBLE
            }
        }
    }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is DeckNameEvent.CreatedCompleted -> endFlow()
                is DeckNameEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun endFlow() {
        binding.apply {
            llMain.visibility = View.INVISIBLE
            llFlowEnd.visibility = View.VISIBLE
            lottie.addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {}
                override fun onAnimationEnd(animation: Animator?) {
                    listener.flowCompleted()
                    dismiss()
                }

                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationRepeat(animation: Animator?) {}
            })

            lottie.setAnimation(R.raw.lottie_ok)
            lottie.playAnimation()
        }

    }

    private fun error() = activity?.showErrorDialog()

    companion object {
        fun newInstance(cards: String, listener: ICreateDeckCardListener): DeckNameDialog {
            return DeckNameDialog(cards, listener)
        }
    }
}