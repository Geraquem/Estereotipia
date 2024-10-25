package com.mmfsin.estereotipia.presentation.instructions.whoiswho

import com.mmfsin.estereotipia.base.BaseViewModel
import com.mmfsin.estereotipia.domain.usecases.GetInstructionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WiWInstructionsViewModel @Inject constructor(
    private val getInstructionsUseCase: GetInstructionsUseCase,
) : BaseViewModel<WiWInstructionsEvent>() {

    fun getInstructions() {
        executeUseCase(
            { getInstructionsUseCase.execute() },
            { result -> _event.value = WiWInstructionsEvent.GetWiWInstructions(result) },
            { _event.value = WiWInstructionsEvent.SomethingWentWrong }
        )
    }
}