package com.mmfsin.whoami.domain.usecases

import com.mmfsin.whoami.base.BaseUseCaseNoParams
import com.mmfsin.whoami.domain.interfaces.IDashboardRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ObserveDashboardFlowUseCase @Inject constructor(private val repository: IDashboardRepository) :
    BaseUseCaseNoParams<StateFlow<Pair<Boolean, String>>>() {

    override suspend fun execute(): StateFlow<Pair<Boolean, String>> = repository.observeFlow()
}