package com.mmfsin.whoami.data.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class GameQuestionDTO(
    @PrimaryKey
    var id: String = "",
    var question: String = "",
    var answer: Boolean? = null,
) : RealmObject()
