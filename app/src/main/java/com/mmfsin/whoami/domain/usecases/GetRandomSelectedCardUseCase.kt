package com.mmfsin.whoami.domain.usecases

import com.mmfsin.whoami.base.BaseUseCase
import com.mmfsin.whoami.domain.interfaces.ICardsRepository
import com.mmfsin.whoami.presentation.models.DeckType
import javax.inject.Inject

class GetRandomSelectedCardUseCase @Inject constructor(val repository: ICardsRepository) :
    BaseUseCase<GetRandomSelectedCardUseCase.Params, String?>() {

    override suspend fun execute(params: Params): String? {
        val cards = when (params.type) {
            DeckType.SYSTEM_DECK -> repository.getCardsByDeckId(params.deckId)
            DeckType.CUSTOM_DECK -> repository.getCardsByCustomDeckId(params.deckId)
        }

        return try {
            cards?.let {
                val date = System.currentTimeMillis().toString()
                val number = date.substring(date.length - 1, date.length)
                var intNumber = number.toInt()
                if (intNumber >= cards.size) intNumber = 0
                cards[intNumber].id
            } ?: run { null }

        } catch (e: Exception) {
            cards?.let {
                val rand = (cards.indices).random()
                cards[rand].id
            } ?: run { null }
        }
    }

    class Params(
        val deckId: String,
        val type: DeckType,
    )
}