package com.mmfsin.whoami.presentation.instructions.interfaces

import android.view.View
import android.widget.LinearLayout

interface IInstructionsListener {
    fun onInstructionClick(ll1: LinearLayout, details: View)
}