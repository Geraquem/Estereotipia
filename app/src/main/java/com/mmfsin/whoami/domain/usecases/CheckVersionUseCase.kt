package com.mmfsin.whoami.domain.usecases

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.mmfsin.whoami.base.BaseUseCaseNoParams
import com.mmfsin.whoami.domain.interfaces.IMainRepository
import com.mmfsin.whoami.utils.CALL_DECKS
import com.mmfsin.whoami.utils.CALL_FIREBASE
import com.mmfsin.whoami.utils.CALL_QUESTIONS
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CheckVersionUseCase @Inject constructor(
    @ApplicationContext val context: Context,
    private val repository: IMainRepository
) : BaseUseCaseNoParams<Boolean>() {

    override suspend fun execute(): Boolean {
        val result = repository.checkVersion()
        val sharedPrefs = context.getSharedPreferences(CALL_FIREBASE, MODE_PRIVATE)
        sharedPrefs.edit().apply {
            val update = !result
            putBoolean(CALL_DECKS, update)
            putBoolean(CALL_QUESTIONS, update)
            apply()
        }
        return result
    }
}