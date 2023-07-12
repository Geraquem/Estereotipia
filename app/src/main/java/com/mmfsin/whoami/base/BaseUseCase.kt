package com.mmfsin.whoami.base

abstract class BaseUseCase<params, T> {
    abstract suspend fun execute(params: params): T
}