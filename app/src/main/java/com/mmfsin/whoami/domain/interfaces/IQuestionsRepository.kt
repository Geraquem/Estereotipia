package com.mmfsin.whoami.domain.interfaces

import com.mmfsin.whoami.domain.models.GameQuestion
import com.mmfsin.whoami.domain.models.Question

interface IQuestionsRepository {
    suspend fun getQuestions(): List<Question>?

    fun saveGameQuestion(question: String): GameQuestion
    fun updateGameQuestion(id: String, answer: Boolean)
}