package com.mmfsin.estereotipia.data.repository

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mmfsin.estereotipia.data.models.CardDTO
import com.mmfsin.estereotipia.data.models.DeckDTO
import com.mmfsin.estereotipia.data.models.IdentityDTO
import com.mmfsin.estereotipia.data.models.QuestionDTO
import com.mmfsin.estereotipia.domain.interfaces.IMainRepository
import com.mmfsin.estereotipia.domain.interfaces.IRealmDatabase
import com.mmfsin.estereotipia.utils.CARDS
import com.mmfsin.estereotipia.utils.DECKS
import com.mmfsin.estereotipia.utils.ID
import com.mmfsin.estereotipia.utils.IDENTITIES
import com.mmfsin.estereotipia.utils.IS_CUSTOM_DECK
import com.mmfsin.estereotipia.utils.MY_SHARED_PREFS
import com.mmfsin.estereotipia.utils.QUESTIONS
import com.mmfsin.estereotipia.utils.SAVED_VERSION
import com.mmfsin.estereotipia.utils.VERSION
import dagger.hilt.android.qualifiers.ApplicationContext
import io.realm.kotlin.where
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.CountDownLatch
import javax.inject.Inject

class MainRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private val realmDatabase: IRealmDatabase
) : IMainRepository {

    private val reference = Firebase.database.reference

    override suspend fun checkVersion() {
        getDataFromFirebase(getSavedVersion())
    }

    private suspend fun getDataFromFirebase(savedVersion: Long): List<DeckDTO> {
        val latch = CountDownLatch(1)
        val decks = mutableListOf<DeckDTO>()
        reference.get().addOnSuccessListener {
            val version = it.child(VERSION).value as Long
            if (version == savedVersion) {
                latch.countDown()
            } else {
                saveVersion(newVersion = version)
                deleteAllSystemData()

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

                val fbIdentities = it.child(IDENTITIES)
                for (child in fbIdentities.children) {
                    child.getValue(IdentityDTO::class.java)
                        ?.let { identity -> saveIdentityInRealm(identity) }
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


    private fun saveVersion(newVersion: Long) {
        val editor = getSharedPreferences().edit()
        editor.putLong(SAVED_VERSION, newVersion)
        editor.apply()
    }

    private fun deleteAllSystemData() {
        val systemDecks = realmDatabase.getObjectsFromRealm {
            where<DeckDTO>().equalTo(IS_CUSTOM_DECK, false).findAll()
        }

        systemDecks.forEach { deck -> realmDatabase.deleteObject(DeckDTO::class.java, ID, deck.id) }
        realmDatabase.deleteAllObjects(CardDTO::class.java)
        realmDatabase.deleteAllObjects(QuestionDTO::class.java)
    }

    private fun getSavedVersion(): Long = getSharedPreferences().getLong(SAVED_VERSION, -1)

    private fun getSharedPreferences() = context.getSharedPreferences(MY_SHARED_PREFS, MODE_PRIVATE)

    private fun saveDeckInRealm(deck: DeckDTO) = realmDatabase.addObject { deck }
    private fun saveCardInRealm(card: CardDTO) = realmDatabase.addObject { card }
    private fun saveQuestionInRealm(question: QuestionDTO) = realmDatabase.addObject { question }
    private fun saveIdentityInRealm(identity: IdentityDTO) = realmDatabase.addObject { identity }
}