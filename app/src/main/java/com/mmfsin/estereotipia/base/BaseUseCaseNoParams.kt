package com.mmfsin.estereotipia.base

abstract class BaseUseCaseNoParams<T> {
    abstract suspend fun execute(): T
}