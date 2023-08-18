package com.mmfsin.whoami.data.repository

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mmfsin.whoami.data.models.VersionDTO
import com.mmfsin.whoami.domain.interfaces.IMainRepository
import com.mmfsin.whoami.domain.interfaces.IRealmDatabase
import com.mmfsin.whoami.utils.VERSION
import io.realm.kotlin.where
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.CountDownLatch
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val realmDatabase: IRealmDatabase
) : IMainRepository {

    override suspend fun checkVersion(): Boolean {
        val savedVersion = realmDatabase.getObjectsFromRealm { where<VersionDTO>().findAll() }
        val actualVersion = if (savedVersion.isEmpty()) -1 else savedVersion.first().version
        val firebaseVersion = getVersionFromFirebase()
        return actualVersion == firebaseVersion
    }

    private suspend fun getVersionFromFirebase(): Long {
        val latch = CountDownLatch(1)
        var version: Long = -1
        val reference = Firebase.database.reference.child(VERSION)
        reference.get().addOnSuccessListener {
            version = it.value as Long
            realmDatabase.addObject { VersionDTO(VERSION, version) }
            latch.countDown()

        }.addOnFailureListener {
            latch.countDown()
        }

        withContext(Dispatchers.IO) {
            latch.await()
        }
        return version
    }
}