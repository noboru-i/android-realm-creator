package hm.orz.chaos114.android.realmcreator

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import hm.orz.chaos114.android.realmcreator.entity.Country
import hm.orz.chaos114.android.realmcreator.entity.Match
import io.realm.Realm
import io.realm.RealmConfiguration

class MainActivity : AppCompatActivity() {

    lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Realm.init(this)

        val config = RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .assetFile("default.realm")
                .readOnly()
                .build()
        realm = Realm.getInstance(config)

        val country1 = realm.where(Country::class.java).equalTo("name", "アルゼンチン").findFirst()!!
        val country2 = realm.where(Country::class.java).equalTo("name", "日本").findFirst()!!
        val result = realm.where(Match::class.java)
                .equalTo("country1.name", country1.name)
                .equalTo("country2.name", country2.name)
                .findFirst()!!

        val textView = findViewById<TextView>(R.id.text)
        textView.setText(country1.name + " x " + country2.name + " : " + result.result)
    }
}
