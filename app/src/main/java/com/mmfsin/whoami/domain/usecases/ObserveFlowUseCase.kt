package com.mmfsin.whoami.domain.usecases

import com.mmfsin.whoami.base.BaseUseCaseNoParams
import com.mmfsin.whoami.domain.interfaces.ICardsRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ObserveFlowUseCase @Inject constructor(private val repository: ICardsRepository) :
    BaseUseCaseNoParams<StateFlow<Pair<Boolean, String>>>() {

    override suspend fun execute(): StateFlow<Pair<Boolean, String>> = repository.observeFlow()
}