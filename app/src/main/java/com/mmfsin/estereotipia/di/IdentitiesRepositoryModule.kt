package com.mmfsin.estereotipia.di

import com.mmfsin.estereotipia.data.repository.IdentitiesRepository
import com.mmfsin.estereotipia.domain.interfaces.IIdentitiesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface IdentitiesRepositoryModule {
    @Binds
    fun bind(repository: IdentitiesRepository): IIdentitiesRepository
}