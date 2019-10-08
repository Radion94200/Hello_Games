package fr.epita.android.hellogames

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_game__details_.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Game_Details_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game__details_)

        val id = intent.getIntExtra("ID", 0)

        fun printPicture(dataGamesInfo : GameDetails) {
            Glide.with(this).load(dataGamesInfo.picture).into(findViewById(R.id.imageView4))
            name_game.setText(Html.fromHtml("<b>" + "NAME : " + "</b>" + dataGamesInfo.name))
            type_game.setText(Html.fromHtml("<b>" + "TYPE : " + "</b>" + dataGamesInfo.type))
            nb_player.setText(Html.fromHtml("<b>" + "NB PLAYERS : " + "</b>" + dataGamesInfo.players))
            year.setText(Html.fromHtml("<b>" + "YEAR : " + "</b>" + dataGamesInfo.year))
            description.text = dataGamesInfo.description_en
            internet_redirection.setOnClickListener{
                val openURL = Intent(Intent.ACTION_VIEW)
                openURL.data = Uri.parse(dataGamesInfo.url)
                startActivity(openURL)
            }
        }

        val baseURL = "http://androidlessonsapi.herokuapp.com/api/"

        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())

        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(jsonConverter)
            .build()

        val service: WebServiceInterface = retrofit.create(WebServiceInterface::class.java)

        val callback = object : Callback<GameDetails> {
            override fun onFailure(call: Call<GameDetails>?, t: Throwable?) {
                // Code here what happens if calling the WebService fails --------------------------
                Log.d("TAG", "WebService call failed")
            }
            override fun onResponse(call: Call<GameDetails>?, response: Response<GameDetails>?) {
                Log.d("TAG", "Test response")
                // Code here what happens when WebService responds
                if (response != null) {
                    Log.i("test", response.code().toString())
                    if (response.code() == 200) {
                        Log.d("TAG", "The service join the server")
                        // We got our data !
                        val responseData = response.body()
                        if (responseData != null) {
                            Log.i("test", responseData.toString())
                            printPicture(responseData)
                        }
                    }
                }
            }
        }

        service.GetDetails(id).enqueue(callback)
    }
}
