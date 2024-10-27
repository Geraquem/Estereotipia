package com.mmfsin.estereotipia.data.repository

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mmfsin.estereotipia.data.mappers.toIdentityList
import com.mmfsin.estereotipia.data.models.IdentityDTO
import com.mmfsin.estereotipia.domain.interfaces.IIdentitiesRepository
import com.mmfsin.estereotipia.domain.interfaces.IRealmDatabase
import com.mmfsin.estereotipia.domain.models.Identity
import com.mmfsin.estereotipia.utils.IDENTITIES
import com.mmfsin.estereotipia.utils.SERVER_IDENTITIES
import com.mmfsin.estereotipia.utils.SHARED_MAIN
import dagger.hilt.android.qualifiers.ApplicationContext
import io.realm.kotlin.where
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.CountDownLatch
import javax.inject.Inject

class IdentitiesRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private val realmDatabase: IRealmDatabase
) : IIdentitiesRepository {

    override suspend fun getIdentities(): List<Identity>? {
        val latch = CountDownLatch(1)
        val sharedPrefs = context.getSharedPreferences(SHARED_MAIN, MODE_PRIVATE)

        if (sharedPrefs.getBoolean(SERVER_IDENTITIES, true)) {
            realmDatabase.deleteAllObjects(IdentityDTO::class.java)
            val identities = mutableListOf<IdentityDTO>()
            Firebase.database.reference.child(IDENTITIES).get().addOnSuccessListener {
                for (child in it.children) {
                    child.getValue(IdentityDTO::class.java)?.let { identity ->
                        saveIdentityInRealm(identity)
                        identities.add(identity)
                    }
                }
                sharedPrefs.edit().apply {
                    putBoolean(SERVER_IDENTITIES, false)
                    apply()
                }
                latch.countDown()

            }.addOnFailureListener {
                latch.countDown()
            }

            withContext(Dispatchers.IO) { latch.await() }
            return identities.toIdentityList()

        } else {
            val identities = realmDatabase.getObjectsFromRealm { where<IdentityDTO>().findAll() }
            return identities.toIdentityList()
        }
    }

    private fun saveIdentityInRealm(identity: IdentityDTO) = realmDatabase.addObject { identity }

}
