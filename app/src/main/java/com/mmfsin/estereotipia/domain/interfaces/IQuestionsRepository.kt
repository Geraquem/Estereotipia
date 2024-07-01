package com.mmfsin.estereotipia.domain.interfaces

import com.mmfsin.estereotipia.domain.models.Question

interface IQuestionsRepository {
    suspend fun getQuestions(): List<Question>?
}