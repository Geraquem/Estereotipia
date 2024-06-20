package com.mmfsin.whoami.domain.usecases

import com.mmfsin.whoami.base.BaseUseCase
import com.mmfsin.whoami.domain.interfaces.IQuestionsRepository
import com.mmfsin.whoami.domain.models.GameQuestion
import javax.inject.Inject

class SaveGameQuestionUseCase @Inject constructor(private val repository: IQuestionsRepository) :
    BaseUseCase<SaveGameQuestionUseCase.Params, GameQuestion>() {

    override suspend fun execute(params: Params): GameQuestion =
        repository.saveGameQuestion(params.question)

    data class Params(
        val question: String
    )
}