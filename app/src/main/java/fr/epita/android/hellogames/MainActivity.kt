package fr.epita.android.hellogames

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fcklist = listOf(R.id.view_1, R.id.view_2, R.id.view_3, R.id.view_4)

        var listGamesUsed : MutableList<GameObject> = mutableListOf()

        fun print(dataGames : MutableList<GameObject>) {
            for (i in 0..3) {
                val randomNum = dataGames.random()
                // Test of the get picture ---------------------------------------------------------
                val toto = randomNum.picture
                Log.d("TAG", "list of game picture: $toto")
                // ---------------------------------------------------------------------------------
                Glide.with(this).load(randomNum.picture).into(findViewById(fcklist[i]))
                listGamesUsed.add(randomNum)
                dataGames.remove(randomNum)
            }
        }

        var dataGames: MutableList<GameObject>

        val baseURL = "http://androidlessonsapi.herokuapp.com/api/"

        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())

        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(jsonConverter)
            .build()

        val service: WebServiceInterface = retrofit.create(WebServiceInterface::class.java)

        val callback = object : Callback<MutableList<GameObject>> {
            override fun onFailure(call: Call<MutableList<GameObject>>?, t: Throwable?) {
                // Code here what happens if calling the WebService fails --------------------------
                Log.d("TAG", "WebService call failed")
            }
            override fun onResponse(call: Call<MutableList<GameObject>>?, response: Response<MutableList<GameObject>>?) {
                Log.d("TAG", "Test response")
                // Code here what happens when WebService responds
                if (response != null) {
                    if (response.code() == 200) {
                        Log.d("TAG", "The service join the server")
                        // We got our data !
                        val responseData = response.body()
                        if (responseData != null) {
                            dataGames = responseData
                            print(dataGames)
                        }
                    }
                }
            }
        }

        service.GetListsGames().enqueue(callback)

        val explicitIntent = Intent(this, Game_Details_Activity::class.java)

        view_1.setOnClickListener( object : View.OnClickListener {
            override fun onClick(v: View?) {
                val nameGame = listGamesUsed[0].name
                Log.d("TAG", "$nameGame is selected")
                val Toast = Toast.makeText(this@MainActivity, "$nameGame", Toast.LENGTH_SHORT)
                Toast.show()
                explicitIntent.putExtra("ID", listGamesUsed[0].id)
                startActivity(explicitIntent)
            }
        })

        view_2.setOnClickListener( object : View.OnClickListener {
            override fun onClick(v: View?) {
                val nameGame = listGamesUsed[1].name
                Log.d("TAG", "$nameGame is selected")
                val Toast = Toast.makeText(this@MainActivity, "$nameGame", Toast.LENGTH_SHORT)
                Toast.show()
                explicitIntent.putExtra("ID", listGamesUsed[1].id)
                startActivity(explicitIntent)
            }
        })

        view_3.setOnClickListener( object : View.OnClickListener {
            override fun onClick(v: View?) {
                val nameGame = listGamesUsed[2].name
                Log.d("TAG", "$nameGame is selected")
                val Toast = Toast.makeText(this@MainActivity, "$nameGame", Toast.LENGTH_SHORT)
                Toast.show()
                explicitIntent.putExtra("ID", listGamesUsed[2].id)
                startActivity(explicitIntent)
            }
        })

        view_4.setOnClickListener( object : View.OnClickListener {
            override fun onClick(v: View?) {
                val nameGame = listGamesUsed[3].name
                Log.d("TAG", "$nameGame is selected")
                val Toast = Toast.makeText(this@MainActivity, "$nameGame", Toast.LENGTH_SHORT)
                Toast.show()
                explicitIntent.putExtra("ID", listGamesUsed[3].id)
                startActivity(explicitIntent)
            }
        })
    }
}
