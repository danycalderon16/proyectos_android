package com.calderon.midinero

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.GsonBuilder

import kotlinx.android.synthetic.main.activity_main.*

abstract class MainActivity : AppCompatActivity() {

    private val preferences = getSharedPreferences("MODEL_PREFERENCES", Context.MODE_PRIVATE)
    private val editor = preferences.edit()
    private val gson = GsonBuilder().create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val recyclerView = findViewById<RecyclerView>(R.id.rv)

        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        var myAdapter = MyAdapter(this,DummyData.getDummyData())
        recyclerView.adapter = myAdapter

        fab.setOnClickListener { view ->
            putObject("rgs",Registro(300,"16/11/1999",100,100,100))
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

        }
    }

    class DummyData{
        companion object{
            fun <ArrayList> getDummyData(): ArrayList {
                var dummyData = ArrayList<Registro>()
                dummyData.add(Registro(200,"16/11/1999",100,100,200))
                dummyData.add(Registro(200,"16/11/1999",100,100,200))
                dummyData.add(Registro(200,"16/11/1999",100,100,200))
                dummyData.add(Registro(200,"16/11/1999",100,100,200))
                dummyData.add(Registro(200,"16/11/1999",100,100,200))
                dummyData.add(Registro(200,"16/11/1999",100,100,200))
                dummyData.add(Registro(200,"16/11/1999",100,100,200))
                dummyData.add(Registro(200,"16/11/1999",100,100,200))
                dummyData.add(Registro(200,"16/11/1999",100,100,200))
                dummyData.add(Registro(200,"16/11/1999",100,100,200))
                dummyData.add(Registro(200,"16/11/1999",100,100,200))
                dummyData.add(Registro(200,"16/11/1999",100,100,200))
                dummyData.add(Registro(200,"16/11/1999",100,100,200))
                dummyData.add(Registro(200,"16/11/1999",100,100,200))
                dummyData.add(Registro(200,"16/11/1999",100,100,200))
                dummyData.add(Registro(200,"16/11/1999",100,100,200))
                dummyData.add(Registro(200,"16/11/1999",100,100,200))
                dummyData.add(Registro(200,"16/11/1999",100,100,200))
                dummyData.add(Registro(200,"16/11/1999",100,100,200))

                return dummyData as ArrayList
            }
        }
    }

    fun <Registro> putObject(key: String, y: Registro) {
        //Convert object to JSON String.
        val inString = gson.toJson(y)
        //Save that String in SharedPreferences
        editor.putString(key, inString).commit()
    }

    /**
     * Saves collection of objects into the Preferences.
     * Only the fields are stored. Methods, Inner classes, Nested classes and inner interfaces are not stored.
     **/

    fun <Registro> getObject(key: String, c: Class<Registro>): Registro {
        //We read JSON String which was saved.
        val value = preferences.getString(key, null)
        if (value != null) {
            //JSON String was found which means object can be read.
            //We convert this JSON String to model object. Parameter "c" (of
            // type Class<T>" is used to cast.
            return gson.fromJson(value, c)
        } else {
            //No JSON String with this key was found which means key is invalid or object was not saved.
            throw IllegalArgumentException("No object with key: $key was saved")
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

}

