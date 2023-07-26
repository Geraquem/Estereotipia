package com.mmfsin.whoami.presentation.dashboard.dialog

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.mmfsin.whoami.base.BaseDialog
import com.mmfsin.whoami.databinding.DialogCardInfoBinding

class CardInfoDialog : BaseDialog<DialogCardInfoBinding>() {

    override fun inflateView(inflater: LayoutInflater) = DialogCardInfoBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerViewDialog(dialog)

    override fun setUI() {
        isCancelable = true
    }

    override fun setListeners() {
        binding.apply {

        }
    }

    override fun onResume() {
        super.onResume()
        animateDialog()
    }

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
        fun newInstance(): CardInfoDialog {
            return CardInfoDialog()
        }
    }
}