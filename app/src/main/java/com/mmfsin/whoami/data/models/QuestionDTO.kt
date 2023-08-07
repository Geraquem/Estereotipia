package com.mmfsin.whoami.data.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class QuestionDTO(
    @PrimaryKey
    var id: String = "",
    var question: String = ""
) : RealmObject()
