package com.mmfsin.estereotipia.presentation.instructions.interfaces

import com.mmfsin.estereotipia.domain.models.Instruction

interface IInstructionsListener {
    fun onInstructionClick(instruction: Instruction)
}