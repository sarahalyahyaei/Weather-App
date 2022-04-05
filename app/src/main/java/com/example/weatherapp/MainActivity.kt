package com.example.weatherapp
/*
@Author: Sarah Alyahyaei
@Date: 19 Mar 2022
@Aim: to create a weather app by searching a city and showing temperature.
Using OpenWeather API key.
 */
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weatherapp.model.weather
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    val apiKey = "c32f0b00d25f7bf270c7d47338fd00ee"
    val gson = Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.searchButton)

        button.setOnClickListener{
            getWeather()
        }
    }

    private fun getWeather() {
        val searchInput = findViewById<EditText>(R.id.searchInput)
        try {
            if(searchInput.text.isNotEmpty()){
                var fullURL = "https://api.openweathermap.org/data/2.5/weather?q=${searchInput.text}&units=metric&appid=$apiKey"
                val queue = Volley.newRequestQueue(this)
                val stringRequest = StringRequest(
                    Request.Method.POST , fullURL, { response ->
                    var results = gson.fromJson(response, weather::class.java)
                    findViewById<TextView>(R.id.temp).text = results.main.temp.toString()

                        findViewById<TextView>(R.id.address).text = results.name + ", " + results.sys.country
                    findViewById<TextView>(R.id.temp_min).text = "Min Temp: " + results.main.temp_min+ " C"
                        findViewById<TextView>(R.id.temp_max).text = "Min Max: " + results.main.temp_max+ " C"
                        println(response.toString())

                    }, {
                        println(it.message)
                    }
                )
                queue.add(stringRequest)
            }
        }
        catch (err:Exception) {

        }
    }
}