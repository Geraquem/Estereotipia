package com.mmfsin.estereotipia.presentation.instructions.whoiswho

import com.mmfsin.estereotipia.domain.models.Instruction

sealed class WiWInstructionsEvent {
    class GetWiWInstructions(val instructions: List<Instruction>) : WiWInstructionsEvent()
    object SomethingWentWrong : WiWInstructionsEvent()
}