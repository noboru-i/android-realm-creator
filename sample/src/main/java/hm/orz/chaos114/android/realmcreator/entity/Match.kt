package hm.orz.chaos114.android.realmcreator.entity

import io.realm.RealmObject

open class Match(
        open var country1: Country? = null,
        open var country2: Country? = null,
        open var result: String = ""
) : RealmObject()
