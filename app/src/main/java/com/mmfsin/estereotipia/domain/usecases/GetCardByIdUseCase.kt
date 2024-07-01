package com.mmfsin.estereotipia.domain.usecases

import com.mmfsin.estereotipia.base.BaseUseCase
import com.mmfsin.estereotipia.domain.interfaces.ICardsRepository
import com.mmfsin.estereotipia.domain.models.Card
import javax.inject.Inject

class GetCardByIdUseCase @Inject constructor(private val repository: ICardsRepository) :
    BaseUseCase<GetCardByIdUseCase.Params, Card?>() {

    override suspend fun execute(params: Params): Card? = repository.getCardById(params.cardId)

    class Params(
        val cardId: String
    )
}