package com.mmfsin.whoami.domain.usecases

import com.mmfsin.whoami.base.BaseUseCase
import com.mmfsin.whoami.domain.interfaces.ICardsRepository
import javax.inject.Inject

class DiscardCardUseCase @Inject constructor(private val repository: ICardsRepository) :
    BaseUseCase<DiscardCardUseCase.Params, Boolean?>() {

    override suspend fun execute(params: Params): Boolean? =
        repository.discardCard(params.cardId, params.updateFlow)

    class Params(
        val cardId: String,
        val updateFlow: Boolean = true
    )
}