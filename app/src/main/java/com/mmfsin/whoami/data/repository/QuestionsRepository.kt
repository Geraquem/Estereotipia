package com.mmfsin.whoami.data.repository

import android.content.Context
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mmfsin.whoami.data.mappers.toQuestionList
import com.mmfsin.whoami.data.models.QuestionDTO
import com.mmfsin.whoami.domain.interfaces.IQuestionsRepository
import com.mmfsin.whoami.domain.interfaces.IRealmDatabase
import com.mmfsin.whoami.domain.models.Question
import com.mmfsin.whoami.utils.QUESTIONS
import dagger.hilt.android.qualifiers.ApplicationContext
import io.realm.kotlin.where
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.CountDownLatch
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