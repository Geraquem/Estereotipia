package com.mmfsin.whoami.domain.usecases

import com.mmfsin.whoami.base.BaseUseCase
import com.mmfsin.whoami.domain.interfaces.ICardsRepository
import com.mmfsin.whoami.domain.models.Card
import javax.inject.Inject

class GetCardByIdUseCase @Inject constructor(private val repository: ICardsRepository) :
    BaseUseCase<GetCardByIdUseCase.Params, Card?>() {

    override suspend fun execute(params: Params): Card? = repository.getCardById(params.cardId)

    class Params(
        val cardId: String
    )
}