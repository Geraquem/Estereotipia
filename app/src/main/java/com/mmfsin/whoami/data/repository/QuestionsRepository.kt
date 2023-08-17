package com.mmfsin.whoami.data.repository

import android.content.Context
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mmfsin.whoami.data.mappers.toQuestionList
import com.mmfsin.whoami.data.models.QuestionDTO
import com.mmfsin.whoami.domain.interfaces.IQuestionsRepository
import com.mmfsin.whoami.domain.interfaces.IRealmDatabase
import com.mmfsin.whoami.domain.models.Question
import com.mmfsin.whoami.utils.CALL_FIREBASE
import com.mmfsin.whoami.utils.CALL_QUESTIONS
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

    private val reference = Firebase.database.reference.child(QUESTIONS)

    override suspend fun getQuestions(): List<Question>? {
        val updateDecks = context.getSharedPreferences(CALL_FIREBASE, Context.MODE_PRIVATE)
        if (updateDecks.getBoolean(CALL_QUESTIONS, false)) {
            updateDecks.edit().apply {
                putBoolean(CALL_QUESTIONS, false)
                apply()
            }
            return getQuestionsFromFirebase().toQuestionList()
        }

        val questions = realmDatabase.getObjectsFromRealm { where<QuestionDTO>().findAll() }
        return if (questions.isEmpty()) getQuestionsFromFirebase().toQuestionList() else questions.toQuestionList()
    }

    private suspend fun getQuestionsFromFirebase(): List<QuestionDTO> {
        val latch = CountDownLatch(1)
        val questions = mutableListOf<QuestionDTO>()
        reference.get().addOnSuccessListener {
            for (child in it.children) {
                val question = QuestionDTO(
                    id = child.key.toString(),
                    question = child.value.toString()
                )
                questions.add(question)
                saveQuestionInRealm(question)
            }
            latch.countDown()

        }.addOnFailureListener {
            latch.countDown()
        }

        withContext(Dispatchers.IO) {
            latch.await()
        }
        return questions
    }

    private fun saveQuestionInRealm(card: QuestionDTO) = realmDatabase.addObject { card }
}