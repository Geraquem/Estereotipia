package com.mmfsin.estereotipia.utils

import android.content.Context
import android.content.res.ColorStateList.valueOf
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import com.google.android.material.textview.MaterialTextView
import com.mmfsin.estereotipia.R

fun Context.setSecondPhaseLinear(
    imageLl: LinearLayout,
    l1: LinearLayout,
    l2: LinearLayout,
    l3: LinearLayout
) {
    val textView = imageLl.getChildAt(1) as MaterialTextView
    val text = textView.text.toString()
    imageLl.removeViewAt(1)
    textView.backgroundTintList = valueOf(getColor(this, R.color.soft_blue))
    if (text == "1") l1.addView(textView)
    if (text == "2") l2.addView(textView)
    if (text == "3") l3.addView(textView)
}

fun Context.setSolution(correct: String, answer: LinearLayout): Pair<ImageView, TextView> {
    val answerText = (answer.getChildAt(1) as MaterialTextView).text.toString()
    val icon = if (answerText == correct) R.drawable.ic_check else R.drawable.ic_discard_cross
    val color = if (answerText == correct) R.color.dark_green else R.color.red
    val imageView = ImageView(this).apply {
        setImageResource(icon)
        backgroundTintList = valueOf(getColor(this@setSolution, color))
    }
    val textView = MaterialTextView(this).apply {
        text = correct
    }

    return Pair(imageView, textView)
}