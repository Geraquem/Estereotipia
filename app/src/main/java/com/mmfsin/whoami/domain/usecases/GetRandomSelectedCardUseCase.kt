package com.mmfsin.whoami.domain.usecases

import com.mmfsin.whoami.base.BaseUseCase
import com.mmfsin.whoami.domain.interfaces.ICardsRepository
import javax.inject.Inject
import kotlin.random.Random

class GetRandomSelectedCardUseCase @Inject constructor(val repository: ICardsRepository) :
    BaseUseCase<GetRandomSelectedCardUseCase.Params, String?>() {

    override suspend fun execute(params: Params): String? {
//        val cards = when (params.type) {
//            DeckType.SYSTEM_DECK -> repository.getCardsByDeckId(params.deckId)
//            DeckType.CUSTOM_DECK -> repository.getCardsByCustomDeckId(params.deckId)
//        }
//
//        return try {
//            cards?.let {
//                val date = System.currentTimeMillis().toString()
//                val number = date.substring(date.length - 1, date.length)
//                val times = if (number.toInt() % 2 == 0) 2 else 1
//
//                var rnd = 0
//                for (i in 1..times) {
//                    rnd = getRandomNumber(cards.size)
//                }
//                cards[rnd].id
//            } ?: run { null }
//
//        } catch (e: Exception) {
//            cards?.let {
//                val rnd = getRandomNumber(cards.size)
//                cards[rnd].id
//            } ?: run { null }
//        }
        return "card1"
    }

    private fun getRandomNumber(max: Int): Int {
        return Random.nextInt(0, max)
    }

    class Params(
        val deckId: String,
    )
}