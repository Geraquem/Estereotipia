package com.mmfsin.whoami.data.repository

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mmfsin.whoami.data.mappers.toDeck
import com.mmfsin.whoami.data.mappers.toDeckList
import com.mmfsin.whoami.data.models.CardDTO
import com.mmfsin.whoami.data.models.DeckDTO
import com.mmfsin.whoami.domain.interfaces.IDeckRepository
import com.mmfsin.whoami.domain.interfaces.IRealmDatabase
import com.mmfsin.whoami.domain.models.Deck
import com.mmfsin.whoami.utils.*
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

    private val reference = Firebase.database.reference.child(DECKS)

    override suspend fun getDecks(): List<Deck> {
        val updateDecks = context.getSharedPreferences(CALL_FIREBASE, MODE_PRIVATE)
        if (updateDecks.getBoolean(CALL_DECKS, false)) {
            updateDecks.edit().apply {
                putBoolean(CALL_DECKS, false)
                apply()
            }
            return getDecksFromFirebase().toDeckList()
        }

        val decks = realmDatabase.getObjectsFromRealm { where<DeckDTO>().findAll() }
        return if (decks.isEmpty()) getDecksFromFirebase().sortedBy { it.order }.toDeckList()
        else decks.sortedBy { it.order }.toDeckList()
    }

    private suspend fun getDecksFromFirebase(): List<DeckDTO> {
        val latch = CountDownLatch(1)
        val decks = mutableListOf<DeckDTO>()
        reference.get().addOnSuccessListener {
            for (child in it.children) {
                child.getValue(DeckDTO::class.java)?.let { deck ->
                    decks.add(deck)
                    for (card in child.child(DECK_CARDS).children) {
                        card.getValue(CardDTO::class.java)?.let { c ->
                            c.deckId = deck.id
                            saveCardInRealm(c)
                        }
                    }
                    saveDeckInRealm(deck)
                }
            }
            latch.countDown()

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

    override suspend fun getDeckById(id: String): Deck? {
        val decks = realmDatabase.getObjectsFromRealm {
            where<DeckDTO>().equalTo("id", id).findAll()
        }
        return if (decks.isEmpty()) null else decks.first().toDeck()
    }
}