package com.mmfsin.whoami.domain.usecases

import com.mmfsin.whoami.base.BaseUseCaseNoParams
import com.mmfsin.whoami.domain.interfaces.IQuestionsRepository
import com.mmfsin.whoami.domain.models.Question
import com.mmfsin.whoami.utils.NUM_OF_QUESTIONS
import javax.inject.Inject

class GetQuestionsUseCase @Inject constructor(private val repository: IQuestionsRepository) :
    BaseUseCaseNoParams<List<Question>?>() {

    override suspend fun execute(): List<Question>? {
        val questions = repository.getQuestions()
        if (questions != null) {
            return try {
                val date = System.currentTimeMillis().toString()
                val number = date.substring(date.length - 1, date.length)
                var intNumber = number.toInt()
                if (intNumber > 4) intNumber = 4

                var shuffled = emptyList<Question>()
                for (i in 1..intNumber) {
                    shuffled = questions.shuffled()
                }
                shuffled.take(NUM_OF_QUESTIONS)

            } catch (e: Exception) {
                questions.shuffled().take(NUM_OF_QUESTIONS)
            }
        } else return null
    }
}