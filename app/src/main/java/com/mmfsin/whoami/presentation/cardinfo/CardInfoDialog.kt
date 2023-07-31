package com.mmfsin.whoami.presentation.cardinfo

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.mmfsin.whoami.base.BaseDialog
import com.mmfsin.whoami.databinding.DialogCardInfoBinding
import com.mmfsin.whoami.domain.models.Card
import com.mmfsin.whoami.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardInfoDialog(private val cardId: String) : BaseDialog<DialogCardInfoBinding>() {

    private val viewModel: CardInfoViewModel by viewModels()
    private var card: Card? = null

    override fun inflateView(inflater: LayoutInflater) = DialogCardInfoBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerViewDialog(dialog)

    override fun onResume() {
        super.onResume()
        animateDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
        viewModel.getCardById(cardId)
    }

    override fun setUI() {
        isCancelable = true
        binding.apply {
            card?.let {
                Glide.with(requireContext()).load(it.image).into(ivImage)
                tvName.text = it.name
            }
        }
    }

    override fun setListeners() {
        binding.apply {

        }
    }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is CardInfoEvent.GetCard -> {
                    this.card = event.card
                    setUI()
                }
                is CardInfoEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun error() = activity?.showErrorDialog(goBack = false)

    private fun animateDialog() {
        val dialogView = requireDialog().window?.decorView
        dialogView?.let {
            it.scaleX = 0f
            it.scaleY = 0f
            val scaleXAnimator = ObjectAnimator.ofFloat(it, View.SCALE_X, 1f)
            val scaleYAnimator = ObjectAnimator.ofFloat(it, View.SCALE_Y, 1f)
            AnimatorSet().apply {
                playTogether(scaleXAnimator, scaleYAnimator)
                duration = 400
                interpolator = AccelerateDecelerateInterpolator()
                start()
            }
        }
    }

    companion object {
        fun newInstance(cardId: String): CardInfoDialog {
            return CardInfoDialog(cardId)
        }
    }
}