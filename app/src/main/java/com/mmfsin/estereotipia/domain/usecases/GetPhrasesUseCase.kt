package com.mmfsin.estereotipia.domain.usecases

import com.mmfsin.estereotipia.base.BaseUseCaseNoParams
import com.mmfsin.estereotipia.domain.interfaces.IQuestionsRepository
import com.mmfsin.estereotipia.domain.mappers.toGamePhraseList
import com.mmfsin.estereotipia.domain.models.GamePhrase
import com.mmfsin.estereotipia.utils.NUM_OF_QUESTIONS
import javax.inject.Inject

class GetPhrasesUseCase @Inject constructor(private val repository: IQuestionsRepository) :
    BaseUseCaseNoParams<List<GamePhrase>?>() {

    override suspend fun execute(): List<GamePhrase>? {
        val questions = repository.getPhrases()
        if (!questions.isNullOrEmpty()) {
            return try {
                val date = System.currentTimeMillis().toString()
                val number = date.substring(date.length - 1, date.length)
                var intNumber = number.toInt()
                if (intNumber > 4) intNumber = 4

                var shuffled = emptyList<String>()
                for (i in 1..intNumber) {
                    shuffled = questions.shuffled()
                }
                shuffled.take(NUM_OF_QUESTIONS).toGamePhraseList()
            } catch (e: Exception) {
                questions.shuffled().take(NUM_OF_QUESTIONS).toGamePhraseList()
            }
        } else return null
    }
}