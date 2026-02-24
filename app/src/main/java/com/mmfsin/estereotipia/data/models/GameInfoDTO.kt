package com.mmfsin.estereotipia.data.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class GameInfoDTO : RealmObject {
    @PrimaryKey
    var id: String = ""
    var instagramUser: String? = null
    var shopUrl: String? = null
    var physicalBox: String? = null
}