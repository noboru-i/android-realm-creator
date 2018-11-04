package hm.orz.chaos114.android.realmcreator.entity

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Country(
        @PrimaryKey open var name: String = "",
        open var pool: String = ""
) : RealmObject()
