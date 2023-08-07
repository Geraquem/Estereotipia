package com.mmfsin.whoami.data.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class TwoPlayerModeDTO(
    @PrimaryKey
    var id: String = "",
    var activated: Boolean = false
) : RealmObject()
