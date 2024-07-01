package com.mmfsin.estereotipia.data.repository

import android.content.Context
import com.mmfsin.estereotipia.data.mappers.createCustomDeckDTO
import com.mmfsin.estereotipia.data.mappers.toDeck
import com.mmfsin.estereotipia.data.mappers.toDeckList
import com.mmfsin.estereotipia.data.models.DeckDTO
import com.mmfsin.estereotipia.domain.interfaces.IDeckRepository
import com.mmfsin.estereotipia.domain.interfaces.IRealmDatabase
import com.mmfsin.estereotipia.domain.models.Deck
import com.mmfsin.estereotipia.utils.ID
import com.mmfsin.estereotipia.utils.IS_CUSTOM_DECK
import com.mmfsin.estereotipia.utils.toCardList
import dagger.hilt.android.qualifiers.ApplicationContext
import io.realm.kotlin.where
import javax.inject.Inject

class DeckRepository @Inject constructor(
    @ApplicationContext val context: Context, private val realmDatabase: IRealmDatabase
) : IDeckRepository {

    override fun getSystemDecks(): List<Deck> {
        val decks = realmDatabase.getObjectsFromRealm {
            where<DeckDTO>().equalTo(IS_CUSTOM_DECK, false).findAll()
        }
        return decks.toDeckList()
    }

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