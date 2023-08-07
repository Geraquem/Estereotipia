package com.mmfsin.whoami.domain.usecases

import com.mmfsin.whoami.base.BaseUseCaseNoParams
import com.mmfsin.whoami.domain.interfaces.IQuestionsRepository
import com.mmfsin.whoami.domain.models.Question
import javax.inject.Inject

class GetQuestionsUseCase @Inject constructor(private val repository: IQuestionsRepository) :
    BaseUseCaseNoParams<List<Question>?>() {

    override suspend fun execute(): List<Question>? = repository.getQuestions()
}