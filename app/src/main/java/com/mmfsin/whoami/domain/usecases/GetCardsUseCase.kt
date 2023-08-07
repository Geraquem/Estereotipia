package com.mmfsin.whoami.domain.usecases

import com.mmfsin.whoami.base.BaseUseCase
import com.mmfsin.whoami.domain.interfaces.ICardsRepository
import com.mmfsin.whoami.domain.models.Card
import javax.inject.Inject

class GetCardsUseCase @Inject constructor(private val repository: ICardsRepository) :
    BaseUseCase<GetCardsUseCase.Params, List<Card>?>() {

    override suspend fun execute(params: Params): List<Card>? = repository.getCards(params.deckId)

    class Params(
        val deckId: String
    )
}