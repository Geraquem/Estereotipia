package com.mmfsin.estereotipia.di

import com.mmfsin.estereotipia.data.repository.DeckRepository
import com.mmfsin.estereotipia.domain.interfaces.IDeckRepository
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