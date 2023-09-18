package com.mmfsin.whoami.presentation.instructions.interfaces

import com.mmfsin.whoami.domain.models.Instruction

interface IInstructionsListener {
    fun onInstructionClick(instruction: Instruction)
}