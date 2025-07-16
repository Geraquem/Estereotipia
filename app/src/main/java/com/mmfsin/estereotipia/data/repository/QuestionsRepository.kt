package com.mmfsin.estereotipia.data.repository

import android.content.Context
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mmfsin.estereotipia.data.mappers.toQuestionList
import com.mmfsin.estereotipia.data.models.PhraseDTO
import com.mmfsin.estereotipia.data.models.QuestionDTO
import com.mmfsin.estereotipia.domain.interfaces.IQuestionsRepository
import com.mmfsin.estereotipia.domain.interfaces.IRealmDatabase
import com.mmfsin.estereotipia.domain.models.Question
import com.mmfsin.estereotipia.utils.PHRASES
import com.mmfsin.estereotipia.utils.QUESTIONS
import com.mmfsin.estereotipia.utils.SERVER_PHRASES
import com.mmfsin.estereotipia.utils.SERVER_QUESTIONS
import com.mmfsin.estereotipia.utils.SHARED_MAIN
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

    override suspend fun getQuestions(): List<Question> {
        val latch = CountDownLatch(1)
        val sharedPrefs = context.getSharedPreferences(SHARED_MAIN, Context.MODE_PRIVATE)

        if (sharedPrefs.getBoolean(SERVER_QUESTIONS, true)) {
            realmDatabase.deleteAllObjects(QuestionDTO::class.java)
            val questions = mutableListOf<QuestionDTO>()
            Firebase.database.reference.child(QUESTIONS).get().addOnSuccessListener {
                for (child in it.children) {
                    val question = QuestionDTO(
                        id = child.key.toString(),
                        question = child.value.toString()
                    )
                    saveQuestionInRealm(question)
                    questions.add(question)
                }
                sharedPrefs.edit().apply {
                    putBoolean(SERVER_QUESTIONS, false)
                    apply()
                }
                latch.countDown()

            }.addOnFailureListener {
                latch.countDown()
            }

            withContext(Dispatchers.IO) { latch.await() }
            return questions.toQuestionList()

        } else {
            val questions = realmDatabase.getObjectsFromRealm { where<QuestionDTO>().findAll() }
            if (questions.isEmpty()) {
                sharedPrefs.edit().apply {
                    putBoolean(SERVER_QUESTIONS, true)
                    apply()
                }
            }
            return questions.toQuestionList()
        }
    }

    override suspend fun getPhrases(): List<String> {
        val latch = CountDownLatch(1)
        val sharedPrefs = context.getSharedPreferences(SHARED_MAIN, Context.MODE_PRIVATE)

        if (sharedPrefs.getBoolean(SERVER_PHRASES, true)) {
            realmDatabase.deleteAllObjects(PhraseDTO::class.java)
            val phrases = mutableListOf<PhraseDTO>()
            Firebase.database.reference.child(PHRASES).get().addOnSuccessListener {
                for (child in it.children) {
                    val phrase = PhraseDTO(
                        id = child.key.toString(),
                        question = child.value.toString()
                    )
                    savePhraseInRealm(phrase)
                    phrases.add(phrase)
                }
                sharedPrefs.edit().apply {
                    putBoolean(SERVER_PHRASES, false)
                    apply()
                }
                latch.countDown()

            }.addOnFailureListener {
                latch.countDown()
            }

            withContext(Dispatchers.IO) { latch.await() }
            return phrases.map { it.question }

        } else {
            val phrases = realmDatabase.getObjectsFromRealm { where<PhraseDTO>().findAll() }
            if (phrases.isEmpty()) {
                sharedPrefs.edit().apply {
                    putBoolean(SERVER_PHRASES, true)
                    apply()
                }
            }
            return phrases.map { it.question }
        }
    }

    private fun saveQuestionInRealm(question: QuestionDTO) = realmDatabase.addObject { question }
    private fun savePhraseInRealm(phrase: PhraseDTO) = realmDatabase.addObject { phrase }
}