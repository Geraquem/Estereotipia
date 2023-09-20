package com.mmfsin.whoami.data.repository

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mmfsin.whoami.data.models.CardDTO
import com.mmfsin.whoami.data.models.DeckDTO
import com.mmfsin.whoami.data.models.QuestionDTO
import com.mmfsin.whoami.data.models.VersionDTO
import com.mmfsin.whoami.domain.interfaces.IMainRepository
import com.mmfsin.whoami.domain.interfaces.IRealmDatabase
import com.mmfsin.whoami.utils.CARDS
import com.mmfsin.whoami.utils.DECKS
import com.mmfsin.whoami.utils.QUESTIONS
import com.mmfsin.whoami.utils.VERSION
import io.realm.kotlin.where
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.CountDownLatch
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val realmDatabase: IRealmDatabase
) : IMainRepository {

    private val reference = Firebase.database.reference

    override suspend fun checkVersion() {
        val savedVersion = realmDatabase.getObjectsFromRealm { where<VersionDTO>().findAll() }
        val actualVersion = if (savedVersion.isEmpty()) -1 else savedVersion.first().version
        getDataFromFirebase(actualVersion)
    }

    private suspend fun getDataFromFirebase(savedVersion: Long): List<DeckDTO> {
        val latch = CountDownLatch(1)
        val decks = mutableListOf<DeckDTO>()
        reference.get().addOnSuccessListener {
            val version = it.child(VERSION).value as Long
            realmDatabase.addObject { VersionDTO(VERSION, version) }
            if (version == savedVersion) {
                latch.countDown()
            } else {
                val fbDecks = it.child(DECKS)
                for (child in fbDecks.children) {
                    child.getValue(DeckDTO::class.java)?.let { deck ->
                        decks.add(deck)
                        saveDeckInRealm(deck)
                    }
                }

                val fbCards = it.child(CARDS)
                for (child in fbCards.children) {
                    child.getValue(CardDTO::class.java)?.let { card -> saveCardInRealm(card) }
                }

                val fbQuestions = it.child(QUESTIONS)
                for (child in fbQuestions.children) {
                    val question = QuestionDTO(
                        id = child.key.toString(),
                        question = child.value.toString()
                    )
                    saveQuestionInRealm(question)
                }

                latch.countDown()
            }

        }.addOnFailureListener {
            latch.countDown()
        }

        withContext(Dispatchers.IO) {
            latch.await()
        }
        return decks
    }

    private fun saveDeckInRealm(deck: DeckDTO) = realmDatabase.addObject { deck }
    private fun saveCardInRealm(card: CardDTO) = realmDatabase.addObject { card }
    private fun saveQuestionInRealm(question: QuestionDTO) = realmDatabase.addObject { question }
}