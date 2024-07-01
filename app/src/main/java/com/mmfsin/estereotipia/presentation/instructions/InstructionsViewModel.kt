package com.mmfsin.estereotipia.presentation.instructions

import com.mmfsin.estereotipia.base.BaseViewModel
import com.mmfsin.estereotipia.domain.usecases.GetInstructionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InstructionsViewModel @Inject constructor(
    private val getInstructionsUseCase: GetInstructionsUseCase,
) : BaseViewModel<InstructionsEvent>() {

    fun getInstructions() {
        executeUseCase(
            { getInstructionsUseCase.execute() },
            { result -> _event.value = InstructionsEvent.GetInstructions(result) },
            { _event.value = InstructionsEvent.SomethingWentWrong }
        )
    }
}