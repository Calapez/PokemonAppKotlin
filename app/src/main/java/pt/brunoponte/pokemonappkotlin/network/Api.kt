package pt.brunoponte.pokemonappkotlin.network

import pt.brunoponte.pokemonappkotlin.data.entities.Pokemon
import pt.brunoponte.pokemonappkotlin.network.responses.SimplePokemonsResponse
import pt.brunoponte.pokemonappkotlin.utils.Constants
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("pokemon")
    fun listPokemons(
        @Query("offset") offset: Int,
        @Query("limit") pageSize: Int
    ) : Call<SimplePokemonsResponse>

    @GET("pokemon/{name}")
    fun getFullPokemon(
        @Path("name") name: String
    ): Call<Pokemon>

    companion object {

        operator fun invoke() : Api {
            return Retrofit.Builder()
                .baseUrl(Constants.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api::class.java)
        }

    }

}