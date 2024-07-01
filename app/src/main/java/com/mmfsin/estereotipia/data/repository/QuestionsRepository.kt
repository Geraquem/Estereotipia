package com.mmfsin.estereotipia.data.repository

import android.content.Context
import com.mmfsin.estereotipia.data.mappers.toQuestionList
import com.mmfsin.estereotipia.data.models.QuestionDTO
import com.mmfsin.estereotipia.domain.interfaces.IQuestionsRepository
import com.mmfsin.estereotipia.domain.interfaces.IRealmDatabase
import com.mmfsin.estereotipia.domain.models.Question
import dagger.hilt.android.qualifiers.ApplicationContext
import io.realm.kotlin.where
import javax.inject.Inject

class QuestionsRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private val realmDatabase: IRealmDatabase
) : IQuestionsRepository {

    override suspend fun getQuestions(): List<Question>? {
        val questions = realmDatabase.getObjectsFromRealm { where<QuestionDTO>().findAll() }
        return if (questions.isEmpty()) null else questions.toQuestionList()
    }
}