package com.mmfsin.whoami.domain.usecases

import com.mmfsin.whoami.base.BaseUseCase
import com.mmfsin.whoami.domain.interfaces.ICardsRepository
import com.mmfsin.whoami.domain.models.Card
import javax.inject.Inject

class GetCardsByDeckIdUseCase @Inject constructor(private val repository: ICardsRepository) :
    BaseUseCase<GetCardsByDeckIdUseCase.Params, List<Card>?>() {

    override suspend fun execute(params: Params): List<Card>? {
//        return when (params.type) {
//            SYSTEM_DECK -> repository.getCardsByDeckId(params.deckId)
//            CUSTOM_DECK -> repository.getCardsByCustomDeckId(params.deckId)
//        }
        return emptyList()
    }

    class Params(
        val deckId: String,
    )
}