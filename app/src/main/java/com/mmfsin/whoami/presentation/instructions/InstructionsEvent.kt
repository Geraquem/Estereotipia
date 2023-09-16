package com.mmfsin.whoami.presentation.instructions

import com.mmfsin.whoami.domain.models.Instruction

sealed class InstructionsEvent {
    class GetInstructions(val instructions: List<Instruction>) : InstructionsEvent()
    object SomethingWentWrong : InstructionsEvent()
}