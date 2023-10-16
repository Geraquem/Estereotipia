package com.mmfsin.whoami.data.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class CardDTO(
    @PrimaryKey
    var id: String = "",
    var image: String = "",
    var name: String = "",
    var discard: Boolean = false,
    var rivalCard: Boolean = false
) : RealmObject()
