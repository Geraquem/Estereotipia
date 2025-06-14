package com.mmfsin.estereotipia.data.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class DeckDTO(
    @PrimaryKey
    var id: String = "",
    var name: String = "",
    var cards: String = "",
    var order: Long = 0,
    var icon: String = "",
    var isCustom: Boolean = false
) : RealmObject()
