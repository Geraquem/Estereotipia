package com.mmfsin.whoami.data.repository

import android.content.Context
import com.mmfsin.whoami.data.mappers.*
import com.mmfsin.whoami.data.models.DeckDTO
import com.mmfsin.whoami.data.models.CustomDeckDTO
import com.mmfsin.whoami.domain.interfaces.IDeckRepository
import com.mmfsin.whoami.domain.interfaces.IRealmDatabase
import com.mmfsin.whoami.domain.models.Deck
import com.mmfsin.whoami.domain.models.MyDeck
import com.mmfsin.whoami.utils.DEFAULT_DECK_IMAGE
import com.mmfsin.whoami.utils.ID
import com.mmfsin.whoami.utils.MY_SHARED_PREFS
import dagger.hilt.android.qualifiers.ApplicationContext
import io.realm.kotlin.where
import javax.inject.Inject

class DeckRepository @Inject constructor(
    @ApplicationContext val context: Context, private val realmDatabase: IRealmDatabase
) : IDeckRepository {

    override fun getDecks(): List<Deck>? {
        val decks = realmDatabase.getObjectsFromRealm { where<DeckDTO>().findAll() }
        return if (decks.isEmpty()) null
        else decks.sortedBy { it.order }.toDeckList()
    }

    override fun getDeckById(id: String): Deck? {
        /** hacer que busque en la pdef y si no se vaya a los creados */
        val decks = realmDatabase.getObjectsFromRealm {
            where<DeckDTO>().equalTo("id", id).findAll()
        }
        return if (decks.isEmpty()) null else decks.first().toDeck()
    }

    override fun createDeck(deck: MyDeck) {
        val sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFS, Context.MODE_PRIVATE)
        val imageSaved = sharedPreferences.getString(DEFAULT_DECK_IMAGE, "")
        val image = imageSaved ?: ""

        val myDecks = realmDatabase.getObjectsFromRealm { where<CustomDeckDTO>().findAll() }
        val order = myDecks.size.toLong()

        val myDeckDTO = deck.toMyDeckDTO(image, order)
        realmDatabase.addObject { myDeckDTO }
    }

    override fun getMyDecks(): List<MyDeck> {
        val decks = realmDatabase.getObjectsFromRealm { where<CustomDeckDTO>().findAll() }
        return if (decks.isEmpty()) emptyList()
        else decks.sortedBy { it.order }.toMyDeckList()
    }

    private fun getMyDeckDTO(id: String): CustomDeckDTO? {
        val decks = realmDatabase.getObjectsFromRealm {
            where<CustomDeckDTO>().equalTo("id", id).findAll()
        }
        return if (decks.isEmpty()) null else decks.first()
    }

    override fun getMyDeckById(id: String): MyDeck? = getMyDeckDTO(id)?.toMyDeck()

    override fun editMyDeckNameById(id: String, name: String) {
        val deck = getMyDeckDTO(id)
        deck?.let {
            it.name = name
            realmDatabase.addObject { deck }
        }
    }

    override fun deleteMyDeck(id: String) =
        realmDatabase.deleteObject(CustomDeckDTO::class.java, ID, id)
}