package com.mmfsin.whoami.domain.interfaces

import com.mmfsin.whoami.domain.models.Question

interface IQuestionsRepository {
    suspend fun getQuestions(): List<Question>?
}