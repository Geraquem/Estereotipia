package com.mmfsin.estereotipia.domain.usecases

import com.mmfsin.estereotipia.base.BaseUseCase
import com.mmfsin.estereotipia.domain.interfaces.ICardsRepository
import javax.inject.Inject

class DiscardCardUseCase @Inject constructor(private val repository: ICardsRepository) :
    BaseUseCase<DiscardCardUseCase.Params, Boolean?>() {

    override suspend fun execute(params: Params): Boolean? = repository.discardCard(params.cardId)

    class Params(
        val cardId: String
    )
}