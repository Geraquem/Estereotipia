package com.mmfsin.estereotipia.data.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class PhraseDTO : RealmObject {
    @PrimaryKey
    var id: String = ""
    var question: String = ""
}