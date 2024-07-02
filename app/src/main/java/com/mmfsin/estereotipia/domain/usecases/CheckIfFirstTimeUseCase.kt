package com.mmfsin.estereotipia.domain.usecases

import com.mmfsin.estereotipia.base.BaseUseCaseNoParams
import com.mmfsin.estereotipia.domain.interfaces.IMainRepository
import javax.inject.Inject

class CheckIfFirstTimeUseCase @Inject constructor(private val repository: IMainRepository) :
    BaseUseCaseNoParams<Boolean>() {

    override suspend fun execute(): Boolean = repository.checkIfFirstTimeInApp()
}