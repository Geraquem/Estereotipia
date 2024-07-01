package com.mmfsin.estereotipia.domain.usecases

import android.content.Context
import com.mmfsin.estereotipia.base.BaseUseCaseNoParams
import com.mmfsin.estereotipia.domain.interfaces.IMainRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CheckVersionUseCase @Inject constructor(
    @ApplicationContext val context: Context,
    private val repository: IMainRepository
) : BaseUseCaseNoParams<Unit>() {

    override suspend fun execute() = repository.checkVersion()
}