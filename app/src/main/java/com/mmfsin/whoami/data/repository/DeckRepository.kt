package com.mmfsin.whoami.data.repository

import android.content.Context
import com.mmfsin.whoami.data.mappers.createCustomDeckDTO
import com.mmfsin.whoami.data.mappers.toDeck
import com.mmfsin.whoami.data.mappers.toDeckList
import com.mmfsin.whoami.data.models.DeckDTO
import com.mmfsin.whoami.domain.interfaces.IDeckRepository
import com.mmfsin.whoami.domain.interfaces.IRealmDatabase
import com.mmfsin.whoami.domain.models.Deck
import com.mmfsin.whoami.utils.ID
import dagger.hilt.android.qualifiers.ApplicationContext
import io.realm.kotlin.where
import javax.inject.Inject

class DeckRepository @Inject constructor(
    @ApplicationContext val context: Context, private val realmDatabase: IRealmDatabase
) : IDeckRepository {

    override fun getSystemDecks(): List<Deck>? {
        val decks = realmDatabase.getObjectsFromRealm { where<DeckDTO>().findAll() }
        return if (decks.isEmpty()) null
        else decks.sortedBy { it.order }.toDeckList()
    }

    override fun getSystemDeckById(id: String): Deck? {
        /** hacer que busque en la pdef y si no se vaya a los creados */
        val deck = realmDatabase.getObjectFromRealm(DeckDTO::class.java, ID, id)
        return deck?.toDeck()
    }

    override fun createDeck(name: String, cards: List<String>) {
        createCustomDeckDTO(name, cards)

    }

    override fun getCustomDecks(): List<Deck> {
//        val decks = realmDatabase.getObjectsFromRealm { where<DeckDTO>().findAll() }
//        return if (decks.isEmpty()) emptyList()
//        else decks.sortedBy { it.order }.toCustomDeckList()
        return emptyList()
    }

    override fun getCustomDeckById(id: String): Deck? {
        return null
    }

    override fun editCustomDeckNameById(id: String, name: String) {
        val deck = realmDatabase.getObjectFromRealm(DeckDTO::class.java, ID, id)
        deck?.let {
            it.name = name
            realmDatabase.addObject { deck }
        }
    }

    override fun deleteCustomDeck(id: String) =
        realmDatabase.deleteObject(DeckDTO::class.java, ID, id)
}