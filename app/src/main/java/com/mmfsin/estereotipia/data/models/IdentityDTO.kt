package com.mmfsin.estereotipia.data.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class IdentityDTO(
    @PrimaryKey
    var id: String = "",
    var text: String = "",
    var text1: String = "",
    var text2: String = "",
    var text3: String = "",
) : RealmObject()