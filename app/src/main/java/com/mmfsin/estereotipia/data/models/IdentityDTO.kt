package com.mmfsin.estereotipia.data.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class IdentityDTO : RealmObject {
    @PrimaryKey
    var id: String = ""
    var text: String? = null
    var text1: String = ""
    var text2: String = ""
    var text3: String = ""
}