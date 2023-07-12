package com.mmfsin.whoami.base

abstract class BaseUseCaseNoParams<T> {
    abstract suspend fun execute(): T
}