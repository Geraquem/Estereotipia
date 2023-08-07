package com.mmfsin.whoami.di

import com.mmfsin.whoami.data.repository.CardsRepository
import com.mmfsin.whoami.domain.interfaces.ICardsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface CardsRepositoryModule {
    @Binds
    fun bind(repository: CardsRepository): ICardsRepository
}