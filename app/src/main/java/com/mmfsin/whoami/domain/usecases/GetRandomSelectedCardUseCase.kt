package com.mmfsin.whoami.domain.usecases

import com.mmfsin.whoami.base.BaseUseCase
import com.mmfsin.whoami.domain.interfaces.ICardsRepository
import javax.inject.Inject

class GetRandomSelectedCardUseCase @Inject constructor(val repository: ICardsRepository) :
    BaseUseCase<GetRandomSelectedCardUseCase.Params, String?>() {

    override suspend fun execute(params: Params): String? {
        val cards = repository.getCards(params.deckId)
        try {
            cards?.let {
                val date = System.currentTimeMillis().toString()
                val number = date.substring(date.length - 1, date.length)
                var intNumber = number.toInt()

                if (intNumber >= cards.size) intNumber = 0
                cards[intNumber].id
            } ?: run { "" }
            return null

        } catch (e: Exception) {
            cards?.let {
                val rand = (cards.indices).random()
                return cards[rand].id
            } ?: run { return "" }
        }
    }

    class Params(
        val deckId: String,
    )
}