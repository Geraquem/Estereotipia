package com.mmfsin.whoami.domain.usecases

import android.content.Context
import com.mmfsin.whoami.base.BaseUseCaseNoParams
import com.mmfsin.whoami.domain.interfaces.IMainRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CheckVersionUseCase @Inject constructor(
    @ApplicationContext val context: Context,
    private val repository: IMainRepository
) : BaseUseCaseNoParams<Unit>() {

    override suspend fun execute() = repository.checkVersion()
}