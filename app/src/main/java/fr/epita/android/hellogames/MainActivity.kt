package fr.epita.android.hellogames

import CustomRecyclerAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Recycler.setHasFixedSize(true)

        Recycler.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)

        val myItemClickListener = View.OnClickListener { rowView ->
            val explicitIntent = Intent(this, Game_Details_Activity::class.java)
            val nameGame = rowView.name_game.text
            Log.d("TAG", "$nameGame is selected")
            val Toast = Toast.makeText(this@MainActivity, "$nameGame", Toast.LENGTH_SHORT)
            Toast.show()
            explicitIntent.putExtra("ID",rowView.tag as Int)
            startActivity(explicitIntent)
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
                            Recycler.adapter = CustomRecyclerAdapter(this@MainActivity, responseData, myItemClickListener)
                        }
                    }
                }
            }
        }

        service.GetListsGames().enqueue(callback)
    }
}
