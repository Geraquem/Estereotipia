package com.mmfsin.whoami.domain.usecases

import com.mmfsin.whoami.base.BaseUseCaseNoParams
import com.mmfsin.whoami.domain.interfaces.IDeckRepository
import javax.inject.Inject

class GetTwoPlayerModeUseCase @Inject constructor(private val repository: IDeckRepository) :
    BaseUseCaseNoParams<Boolean>() {

    override suspend fun execute(): Boolean = repository.getTwoPlayerMode()
}