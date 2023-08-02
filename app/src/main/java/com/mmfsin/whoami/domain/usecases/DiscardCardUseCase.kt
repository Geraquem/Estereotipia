package com.mmfsin.whoami.domain.usecases

import com.mmfsin.whoami.base.BaseUseCase
import com.mmfsin.whoami.domain.interfaces.IDashboardRepository
import javax.inject.Inject

class DiscardCardUseCase @Inject constructor(private val repository: IDashboardRepository) :
    BaseUseCase<DiscardCardUseCase.Params, Unit>() {

    override suspend fun execute(params: Params): Unit =
        repository.discardCard(params.cardId, params.updateFlow)

    class Params(
        val cardId: String,
        val updateFlow: Boolean = true
    )
}