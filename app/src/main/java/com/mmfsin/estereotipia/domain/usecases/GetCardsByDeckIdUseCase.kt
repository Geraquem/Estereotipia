package com.mmfsin.estereotipia.domain.usecases

import com.mmfsin.estereotipia.base.BaseUseCase
import com.mmfsin.estereotipia.domain.interfaces.ICardsRepository
import com.mmfsin.estereotipia.domain.models.Card
import javax.inject.Inject

class GetCardsByDeckIdUseCase @Inject constructor(private val repository: ICardsRepository) :
    BaseUseCase<GetCardsByDeckIdUseCase.Params, List<Card>?>() {

    override suspend fun execute(params: Params): List<Card>? =
        repository.getCardsByDeckId(params.deckId)

    class Params(
        val deckId: String,
    )
}