package com.mmfsin.estereotipia.data.repository

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mmfsin.estereotipia.data.mappers.createGameInfo
import com.mmfsin.estereotipia.data.mappers.toGameInfo
import com.mmfsin.estereotipia.data.models.CardDTO
import com.mmfsin.estereotipia.data.models.GameInfoDTO
import com.mmfsin.estereotipia.domain.interfaces.IMainRepository
import com.mmfsin.estereotipia.domain.interfaces.IRealmDatabase
import com.mmfsin.estereotipia.domain.models.GameInfo
import com.mmfsin.estereotipia.utils.CARDS
import com.mmfsin.estereotipia.utils.GAME_INFO
import com.mmfsin.estereotipia.utils.ID
import com.mmfsin.estereotipia.utils.INSTAGRAM_USER
import com.mmfsin.estereotipia.utils.MY_SHARED_PREFS
import com.mmfsin.estereotipia.utils.PHYSICAL_BOX
import com.mmfsin.estereotipia.utils.SAVED_VERSION
import com.mmfsin.estereotipia.utils.SERVER_DECKS
import com.mmfsin.estereotipia.utils.SERVER_IDENTITIES
import com.mmfsin.estereotipia.utils.SERVER_PHRASES
import com.mmfsin.estereotipia.utils.SERVER_QUESTIONS
import com.mmfsin.estereotipia.utils.SHARED_MAIN
import com.mmfsin.estereotipia.utils.SHOP_URL
import com.mmfsin.estereotipia.utils.VERSION
import dagger.hilt.android.qualifiers.ApplicationContext
import io.realm.kotlin.ext.query
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

                    /** Por si acaso tuviesemos que cambiar parámetros */
                    val shopUrl = it.child(SHOP_URL).value as String?
                    val physicalBox = it.child(PHYSICAL_BOX).value as String?
                    val instagramUser = it.child(INSTAGRAM_USER).value as String?
                    handleGameInfo(iU = instagramUser, sUrl = shopUrl, pBox = physicalBox)

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

    private fun handleGameInfo(iU: String?, sUrl: String?, pBox: String?) {
        saveGameInfo(GameInfo(instagramUser = iU, shopUrl = sUrl, physicalBox = pBox))
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
            putBoolean(SERVER_PHRASES, true)
            apply()
        }
    }

    private fun getSavedVersion(): Long = getSharedPreferences().getLong(SAVED_VERSION, -1)

    private fun getSharedPreferences() = context.getSharedPreferences(MY_SHARED_PREFS, MODE_PRIVATE)

    private fun saveCardInRealm(card: CardDTO) = realmDatabase.addObject { card }

    private fun saveGameInfo(info: GameInfo) = realmDatabase.addObject { createGameInfo(info) }

    override suspend fun getGameInfo(): GameInfo? {
        val info = realmDatabase.getObjectsFromRealm {
            query<GameInfoDTO>("$ID == $0", GAME_INFO).find()
        }.firstOrNull()
        return info?.toGameInfo()
    }
}