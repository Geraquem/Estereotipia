package com.mmfsin.whoami.data.repository

import com.mmfsin.whoami.data.mappers.toDeck
import com.mmfsin.whoami.data.mappers.toDeckList
import com.mmfsin.whoami.data.models.DeckDTO
import com.mmfsin.whoami.domain.interfaces.IDeckRepository
import com.mmfsin.whoami.domain.interfaces.IRealmDatabase
import com.mmfsin.whoami.domain.models.Deck
import io.realm.kotlin.where
import javax.inject.Inject

class DeckRepository @Inject constructor(
    private val realmDatabase: IRealmDatabase
) : IDeckRepository {

//    private val reference = Firebase.database.reference.child(CARDS)

    override suspend fun getDecks(): List<Deck> {
        val decks = realmDatabase.getObjectsFromRealm { where<DeckDTO>().findAll() }
        return if (decks.isEmpty()) getDecksFromFirebase().toDeckList()
        else decks.toDeckList()
    }

    private suspend fun getDecksFromFirebase(): List<DeckDTO> {
        val list = listOf(
            DeckDTO("1", "", "deck 1"),
            DeckDTO("2", "", "deck 2"),
            DeckDTO("3", "", "deck 3"),
            DeckDTO("4", "", "deck 4")
        )
        list.forEach { saveDeckInRealm(it) }
        return list


//        val latch = CountDownLatch(1)
//        val categories = mutableListOf<Deck>()
//        reference.get().addOnSuccessListener {
//            for (child in it.children) {
//                child.getValue(DeckDTO::class.java)?.let { category ->
//                    categories.add(category)
//                    saveCategory(category)
//                }
//            }
//            latch.countDown()
//        }.addOnFailureListener { latch.countDown() }
//
//        withContext(Dispatchers.IO) {
//            latch.await()
//        }
//        return categories
    }

    private fun saveDeckInRealm(deck: DeckDTO) = realmDatabase.addObject { deck }

    override suspend fun getDeckById(id: String): Deck? {
        val decks = realmDatabase.getObjectsFromRealm {
            where<DeckDTO>().equalTo("id", id).findAll()
        }
        return if (decks.isEmpty()) null else decks.first().toDeck()
    }
}