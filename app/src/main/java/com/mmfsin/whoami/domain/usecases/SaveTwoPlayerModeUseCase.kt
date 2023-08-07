package com.mmfsin.whoami.domain.usecases

import com.mmfsin.whoami.base.BaseUseCase
import com.mmfsin.whoami.domain.interfaces.IDeckRepository
import javax.inject.Inject

class SaveTwoPlayerModeUseCase @Inject constructor(private val repository: IDeckRepository) :
    BaseUseCase<SaveTwoPlayerModeUseCase.Params, Unit>() {

    override suspend fun execute(params: Params): Unit =
        repository.saveTwoPlayerMode(params.activated)

    data class Params(
        val activated: Boolean
    )
}