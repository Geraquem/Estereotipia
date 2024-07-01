package com.mmfsin.estereotipia.base

abstract class BaseUseCase<params, T> {
    abstract suspend fun execute(params: params): T
}