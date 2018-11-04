package hm.orz.chaos114.android.realmcreator

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import hm.orz.chaos114.android.realmcreator.entity.Country
import hm.orz.chaos114.android.realmcreator.entity.Match
import io.realm.Realm
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVRecord
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        realm = Realm.getDefaultInstance()

        createCountries()
        createMatch()
    }

    private fun createCountries() {
        val reader = BufferedReader(InputStreamReader(resources.assets.open("match.csv")))
        reader.use {
            val records = CSVFormat.EXCEL.parse(reader)

            var poolName: String? = null
            realm.beginTransaction()
            records.records.forEach loop@{ record ->
                if (record.get(0).isEmpty()) {
                    poolName = null
                    return@loop
                }
                if (poolName == null) {
                    poolName = record.get(0)
                    return@loop
                }

                val obj = Country(
                        name = record.get(0),
                        pool = poolName!!
                )
                realm.copyToRealm(obj)
            }
            realm.commitTransaction()
        }
    }

    private fun createMatch() {
        val reader = BufferedReader(InputStreamReader(resources.assets.open("match.csv")))
        reader.use {
            val records = CSVFormat.EXCEL.parse(reader)

            var header: CSVRecord? = null
            realm.beginTransaction()
            records.records.forEach loop@{ record ->
                if (record.get(0).isEmpty()) {
                    header = null
                    return@loop
                }
                if (header == null) {
                    header = record
                    return@loop
                }

                val country1: Country = getCountry(record.get(0))
                record.forEachIndexed colLoop@{ index, colmn ->
                    if (index == 0) {
                        return@colLoop
                    }
                    if (TextUtils.isEmpty(colmn) || colmn.equals("-")) {
                        return@colLoop
                    }
                    val obj = Match(
                            country1 = country1,
                            country2 = getCountry(header!!.get(index)),
                            result = colmn
                    )
                    realm.copyToRealm(obj)
                }
            }
            realm.commitTransaction()
        }
    }

    private fun getCountry(name: String): Country {
        return realm.where(Country::class.java).equalTo("name", name).findFirst()!!
    }
}
