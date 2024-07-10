package com.mmfsin.estereotipia.presentation.characteristics

import com.mmfsin.estereotipia.base.BaseViewModel
import com.mmfsin.estereotipia.domain.usecases.CheckIfFirstTimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharsViewModel @Inject constructor(
) : BaseViewModel<CharsEvent>() {

}