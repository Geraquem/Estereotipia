package com.mmfsin.estereotipia.domain.usecases

import com.mmfsin.estereotipia.base.BaseUseCaseNoParams
import com.mmfsin.estereotipia.domain.interfaces.IMainRepository
import javax.inject.Inject

class CheckVersionUseCase @Inject constructor(
    private val repository: IMainRepository
) : BaseUseCaseNoParams<Unit>() {

    override suspend fun execute() = repository.checkVersion()
}