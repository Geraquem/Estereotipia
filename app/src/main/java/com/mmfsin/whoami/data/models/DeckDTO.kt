package com.mmfsin.whoami.data.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class DeckDTO(
    @PrimaryKey
    var id: String = "",
    var image: String = "",
    var name: String = "",
) : RealmObject()
