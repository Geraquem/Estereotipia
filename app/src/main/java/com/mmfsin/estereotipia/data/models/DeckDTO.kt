package com.mmfsin.estereotipia.data.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class DeckDTO : RealmObject {
    @PrimaryKey
    var id: String = ""
    var name: String = ""
    var cards: String = ""
    var order: Long = 0
    var icon: String = ""
    var isCustom: Boolean = false
}
