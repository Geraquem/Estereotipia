package com.mmfsin.estereotipia.data.repository

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.widget.Toast
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mmfsin.estereotipia.data.models.CardDTO
import com.mmfsin.estereotipia.domain.interfaces.IMainRepository
import com.mmfsin.estereotipia.domain.interfaces.IRealmDatabase
import com.mmfsin.estereotipia.utils.CARDS
import com.mmfsin.estereotipia.utils.MY_SHARED_PREFS
import com.mmfsin.estereotipia.utils.SAVED_VERSION
import com.mmfsin.estereotipia.utils.SERVER_DECKS
import com.mmfsin.estereotipia.utils.SERVER_IDENTITIES
import com.mmfsin.estereotipia.utils.SERVER_QUESTIONS
import com.mmfsin.estereotipia.utils.SHARED_MAIN
import com.mmfsin.estereotipia.utils.VERSION
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeout
import javax.inject.Inject
import kotlin.coroutines.resume

class MainRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private val realmDatabase: IRealmDatabase
) : IMainRepository {

    private val reference = Firebase.database.reference

    override suspend fun checkVersion() {
        getDataFromFirebase(getSavedVersion())
    }

    private suspend fun getDataFromFirebase(savedVersion: Long) {
        val fetchBlock: suspend () -> Unit = {
            suspendCancellableCoroutine { coroutine ->
                reference.get().addOnSuccessListener {
                    val version = it.child(VERSION).value as Long
                    if (version != savedVersion) {
                        saveVersion(newVersion = version)
                        restartSystemData()

                        val fbCards = it.child(CARDS)
                        for (child in fbCards.children) {
                            child.getValue(CardDTO::class.java)
                                ?.let { card -> saveCardInRealm(card) }
                        }
                    }
                    coroutine.resume(Unit)
                }
            }
        }

        try {
            if (savedVersion == -1L) fetchBlock()
            else {
                withTimeout(7000) {
                    fetchBlock()
                }
            }
        } catch (e: TimeoutCancellationException) {
            println("**** FirebaseTimeout **** -> Se agotó el tiempo de espera")
        } catch (e: Exception) {
            println("FirebaseError -> Error al obtener datos: ${e.message}")
        }
    }

    private fun saveVersion(newVersion: Long) {
        val editor = getSharedPreferences().edit()
        editor.putLong(SAVED_VERSION, newVersion)
        editor.apply()
    }

    private fun restartSystemData() {
        val sharedPrefs = context.getSharedPreferences(SHARED_MAIN, MODE_PRIVATE)
        sharedPrefs.edit().apply {
            putBoolean(SERVER_DECKS, true)
            putBoolean(SERVER_QUESTIONS, true)
            putBoolean(SERVER_IDENTITIES, true)
            apply()
        }
    }

    private fun getSavedVersion(): Long = getSharedPreferences().getLong(SAVED_VERSION, -1)

    private fun getSharedPreferences() = context.getSharedPreferences(MY_SHARED_PREFS, MODE_PRIVATE)

    private fun saveCardInRealm(card: CardDTO) = realmDatabase.addObject { card }
}