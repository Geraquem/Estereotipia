package com.mmfsin.estereotipia.presentation.instructions

import com.mmfsin.estereotipia.domain.models.Instruction

sealed class InstructionsEvent {
    class GetInstructions(val instructions: List<Instruction>) : InstructionsEvent()
    object SomethingWentWrong : InstructionsEvent()
}