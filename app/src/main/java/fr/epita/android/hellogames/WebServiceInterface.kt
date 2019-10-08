package fr.epita.android.hellogames

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServiceInterface {
    @GET("game/list")
    fun GetListsGames() : Call<MutableList<GameObject>>
    @GET("game/details")
    fun GetDetails(@Query("game_id") userId: Int) : Call<GameDetails>
}