package com.mmfsin.whoami.domain.usecases

import com.mmfsin.whoami.base.BaseUseCase
import com.mmfsin.whoami.domain.models.Card
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import javax.inject.Inject

class GetRandomSelectedCardUseCase @Inject constructor() :
    BaseUseCase<GetRandomSelectedCardUseCase.Params, String>() {

    override suspend fun execute(params: Params): String {

        return try {
            val date = System.currentTimeMillis().toString()
            val number = date.substring(date.length - 1, date.length)
            var intNumber = number.toInt()

            if(intNumber >= params.cards.size) intNumber = 0
            params.cards[intNumber].id

        }catch (e: Exception){
            val rand = (0 until params.cards.size).random()
            return params.cards[rand].id
        }
    }

    class Params(
        val cards: List<Card>,
    )
}