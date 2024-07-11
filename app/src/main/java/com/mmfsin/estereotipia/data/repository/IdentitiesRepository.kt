package com.mmfsin.estereotipia.data.repository

import android.content.Context
import com.mmfsin.estereotipia.data.mappers.toIdentityList
import com.mmfsin.estereotipia.data.models.IdentityDTO
import com.mmfsin.estereotipia.domain.interfaces.IIdentitiesRepository
import com.mmfsin.estereotipia.domain.interfaces.IRealmDatabase
import com.mmfsin.estereotipia.domain.models.Identity
import dagger.hilt.android.qualifiers.ApplicationContext
import io.realm.kotlin.where
import javax.inject.Inject

class IdentitiesRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private val realmDatabase: IRealmDatabase
) : IIdentitiesRepository {

    override suspend fun getIdentities(): List<Identity>? {
        val identities = realmDatabase.getObjectsFromRealm { where<IdentityDTO>().findAll() }
        return if (identities.isEmpty()) null else identities.toIdentityList()

    }
}
