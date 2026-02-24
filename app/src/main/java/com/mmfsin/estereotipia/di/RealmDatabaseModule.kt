package com.mmfsin.estereotipia.di

import com.mmfsin.estereotipia.data.database.RealmDatabase
import com.mmfsin.estereotipia.data.models.CardDTO
import com.mmfsin.estereotipia.data.models.DeckDTO
import com.mmfsin.estereotipia.data.models.GameInfoDTO
import com.mmfsin.estereotipia.data.models.IdentityDTO
import com.mmfsin.estereotipia.data.models.PhraseDTO
import com.mmfsin.estereotipia.data.models.QuestionDTO
import com.mmfsin.estereotipia.domain.interfaces.IRealmDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.components.ViewModelComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

@Module
@InstallIn(ViewModelComponent::class, ServiceComponent::class)
object RealmDatabaseModule {

    @Provides
    fun provideRealmDatabase(): IRealmDatabase {
        val config = RealmConfiguration.create(
            schema = setOf(
                CardDTO::class,
                DeckDTO::class,
                IdentityDTO::class,
                PhraseDTO::class,
                QuestionDTO::class,
                GameInfoDTO::class
            )
        )

        val realm = Realm.open(config)
        return RealmDatabase(realm)
    }
}