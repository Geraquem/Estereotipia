package com.mmfsin.estereotipia.utils

import android.content.Context
import android.content.res.ColorStateList.valueOf
import android.graphics.PorterDuff.Mode.SRC_IN
import android.graphics.Typeface
import android.util.TypedValue.COMPLEX_UNIT_PX
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.getDrawable
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
        setColorFilter(getColor(this@setSolution, color), SRC_IN)
    }

    val scale = resources.displayMetrics.density
    val sizeDp = 40
    val sizePx = (sizeDp * scale + 0.5f).toInt()
    val params = LinearLayout.LayoutParams(sizePx, sizePx)
    params.topMargin = 24
    val textView = MaterialTextView(this).apply {
        layoutParams = params
        gravity = Gravity.CENTER
        text = correct
        setTypeface(null, Typeface.BOLD)
        setTextSize(COMPLEX_UNIT_PX, resources.getDimension(R.dimen.dimen_result_identity))
        background = getDrawable(this@setSolution, R.drawable.bg_identities_number)

    }

    return Pair(imageView, textView)
}

fun Context.setFirstPhaseLinear(
    imageLinear: LinearLayout,
    l1: LinearLayout,
    l2: LinearLayout,
    l3: LinearLayout
) {
    val textView = imageLinear.getChildAt(1) as MaterialTextView
    val text = textView.text.toString()
    imageLinear.removeViews(1, 3)
    textView.backgroundTintList = valueOf(getColor(this, R.color.orange))
    if (text == "1") l1.addView(textView)
    if (text == "2") l2.addView(textView)
    if (text == "3") l3.addView(textView)
}

