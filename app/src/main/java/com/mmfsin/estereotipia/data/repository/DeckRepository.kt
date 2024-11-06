package com.mmfsin.estereotipia.data.repository

import android.content.Context
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mmfsin.estereotipia.data.mappers.createCustomDeckDTO
import com.mmfsin.estereotipia.data.mappers.toDeck
import com.mmfsin.estereotipia.data.mappers.toDeckList
import com.mmfsin.estereotipia.data.models.DeckDTO
import com.mmfsin.estereotipia.data.models.IdentityDTO
import com.mmfsin.estereotipia.domain.interfaces.IDeckRepository
import com.mmfsin.estereotipia.domain.interfaces.IRealmDatabase
import com.mmfsin.estereotipia.domain.models.AllDecks
import com.mmfsin.estereotipia.domain.models.Deck
import com.mmfsin.estereotipia.utils.DECKS
import com.mmfsin.estereotipia.utils.ID
import com.mmfsin.estereotipia.utils.IS_CUSTOM_DECK
import com.mmfsin.estereotipia.utils.SERVER_DECKS
import com.mmfsin.estereotipia.utils.SHARED_MAIN
import com.mmfsin.estereotipia.utils.toCardList
import dagger.hilt.android.qualifiers.ApplicationContext
import io.realm.kotlin.where
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.CountDownLatch
import javax.inject.Inject

class DeckRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private val realmDatabase: IRealmDatabase
) : IDeckRepository {

    override suspend fun getAllDecks(): AllDecks {
        return AllDecks(
            systemDecks = getSystemDecks(),
            customDecks = getCustomDecks()
        )
    }

    private suspend fun getSystemDecks(): List<Deck> {
        val latch = CountDownLatch(1)
        val sharedPrefs = context.getSharedPreferences(SHARED_MAIN, Context.MODE_PRIVATE)

        if (sharedPrefs.getBoolean(SERVER_DECKS, true)) {
            realmDatabase.deleteAllObjects(DeckDTO::class.java)
            val decks = mutableListOf<DeckDTO>()
            Firebase.database.reference.child(DECKS).get().addOnSuccessListener {
                for (child in it.children) {
                    child.getValue(DeckDTO::class.java)?.let { deckDTO ->
                        saveDeckInRealm(deckDTO)
                        decks.add(deckDTO)
                    }
                }
                sharedPrefs.edit().apply {
                    putBoolean(SERVER_DECKS, false)
                    apply()
                }
                latch.countDown()

            }.addOnFailureListener {
                latch.countDown()
            }

            withContext(Dispatchers.IO) { latch.await() }
            return decks.toDeckList()

        } else {
            val decks = realmDatabase.getObjectsFromRealm {
                where<DeckDTO>().equalTo(IS_CUSTOM_DECK, false).findAll()
            }
            return decks.toDeckList()
        }
    }

    private fun saveDeckInRealm(deck: DeckDTO) = realmDatabase.addObject { deck }

    override fun getDeckById(id: String): Deck? {
        return realmDatabase.getObjectFromRealm(DeckDTO::class.java, ID, id)?.toDeck()
    }

    override fun createDeck(name: String, cards: List<String>) {
        realmDatabase.addObject { createCustomDeckDTO(name, cards) }
    }

    override fun getCustomDecks(): List<Deck> {
        val decks = realmDatabase.getObjectsFromRealm {
            where<DeckDTO>().equalTo(IS_CUSTOM_DECK, true).findAll()
        }
        return decks.toDeckList()
    }

    override fun editCustomDeckName(id: String, name: String) {
        val deck = realmDatabase.getObjectFromRealm(DeckDTO::class.java, ID, id)
        deck?.let {
            it.name = name
            realmDatabase.addObject { it }
        }
    }

    override fun editCustomDeckCards(id: String, cards: List<String>) {
        val deck = realmDatabase.getObjectFromRealm(DeckDTO::class.java, ID, id)
        deck?.let {
            it.cards = cards.toCardList()
            realmDatabase.addObject { it }
        }
    }

    override fun deleteCustomDeck(id: String) =
        realmDatabase.deleteObject(DeckDTO::class.java, ID, id)
}