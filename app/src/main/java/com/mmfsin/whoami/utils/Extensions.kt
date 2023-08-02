package com.mmfsin.whoami.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentActivity
import com.mmfsin.whoami.base.dialog.ErrorDialog

fun FragmentActivity.showErrorDialog(goBack: Boolean = true) {
    val dialog = ErrorDialog(goBack)
    this.let { dialog.show(it.supportFragmentManager, "") }
}

fun Activity.closeKeyboard() {
    this.currentFocus?.let { view ->
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun Dialog.animateDialog() {
    val dialogView = this.window?.decorView
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

//fun FragmentActivity.shouldShowInterstitial(position: Int) =
//    (this as MainActivity).showInterstitial(position)
