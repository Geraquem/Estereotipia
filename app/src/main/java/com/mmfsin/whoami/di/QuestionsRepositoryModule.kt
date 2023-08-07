package com.mmfsin.whoami.di

import com.mmfsin.whoami.data.repository.DeckRepository
import com.mmfsin.whoami.domain.interfaces.IDeckRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DeckRepositoryModule {
    @Binds
    fun bind(repository: DeckRepository): IDeckRepository
}