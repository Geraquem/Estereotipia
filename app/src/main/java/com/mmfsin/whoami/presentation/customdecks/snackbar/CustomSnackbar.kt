package com.mmfsin.whoami.presentation.customdecks.snackbar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.mmfsin.whoami.R

class CustomSnackbar(
    parent: ViewGroup,
    content: View,
    contentViewCallback: com.google.android.material.snackbar.ContentViewCallback
) : BaseTransientBottomBar<CustomSnackbar>(parent, content, contentViewCallback) {

    init {
        this.getView().setPadding(0, 0, 0, 0)
        this.getView()
            .setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
    }

    companion object {
        fun make(
            view: View,
            duration: Int
        ): CustomSnackbar {
            return createCustomSnackbar(view).apply { setDuration(duration) }
        }

        private fun createCustomSnackbar(view: View): CustomSnackbar {
            val parent = findSuitableParent(view)
                ?: throw IllegalArgumentException("No suitable parent found from the given view")

            val inflater = LayoutInflater.from(view.context)
            val content = inflater.inflate(
                R.layout.snackbar_deck_created,
                parent,
                false
            )

            val contentViewCallback =
                object : com.google.android.material.snackbar.ContentViewCallback {
                    override fun animateContentIn(delay: Int, duration: Int) {
                        content.alpha = 0f
                        content.animate().alpha(1f).setDuration(duration.toLong())
                            .setStartDelay(delay.toLong()).start()
                    }

                    override fun animateContentOut(delay: Int, duration: Int) {
                        content.alpha = 1f
                        content.animate().alpha(0f).setDuration(duration.toLong())
                            .setStartDelay(delay.toLong()).start()
                    }
                }
            return CustomSnackbar(parent, content, contentViewCallback)
        }

        private fun findSuitableParent(view: View?): ViewGroup? {
            var mView = view
            var fallback: ViewGroup? = null
            do {
                if (mView is CoordinatorLayout) {
                    return mView
                } else if (mView is FrameLayout) {
                    if (mView.id == android.R.id.content) return mView else fallback = mView
                }

                if (mView != null) {
                    val parent = mView.parent
                    mView = if (parent is View) parent else null
                }
            } while (mView != null)
            return fallback
        }
    }
}