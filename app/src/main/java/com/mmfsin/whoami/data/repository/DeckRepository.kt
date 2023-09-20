package com.mmfsin.whoami.data.repository

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mmfsin.whoami.data.mappers.toDeck
import com.mmfsin.whoami.data.mappers.toDeckList
import com.mmfsin.whoami.data.models.CardDTO
import com.mmfsin.whoami.data.models.DeckDTO
import com.mmfsin.whoami.data.models.QuestionDTO
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

    override suspend fun getDecks(): List<Deck>? {
        val decks = realmDatabase.getObjectsFromRealm { where<DeckDTO>().findAll() }
        return if (decks.isEmpty()) null
        else decks.sortedBy { it.order }.toDeckList()
    }

    override suspend fun getDeckById(id: String): Deck? {
        val decks = realmDatabase.getObjectsFromRealm {
            where<DeckDTO>().equalTo("id", id).findAll()
        }
        return if (decks.isEmpty()) null else decks.first().toDeck()
    }
}