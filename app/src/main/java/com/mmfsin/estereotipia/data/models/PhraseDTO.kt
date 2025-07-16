package com.mmfsin.estereotipia.data.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class PhraseDTO(
    @PrimaryKey
    var id: String = "",
    var question: String = ""
) : RealmObject()
