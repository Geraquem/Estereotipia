package com.mmfsin.whoami.domain.usecases

import com.mmfsin.whoami.base.BaseUseCase
import com.mmfsin.whoami.domain.interfaces.IQuestionsRepository
import javax.inject.Inject

class UpdateGameQuestionUseCase @Inject constructor(private val repository: IQuestionsRepository) :
    BaseUseCase<UpdateGameQuestionUseCase.Params, Unit>() {

    override suspend fun execute(params: Params) =
        repository.updateGameQuestion(params.idGameQuestion, params.answer)

    data class Params(
        val idGameQuestion: String,
        val answer: Boolean
    )
}