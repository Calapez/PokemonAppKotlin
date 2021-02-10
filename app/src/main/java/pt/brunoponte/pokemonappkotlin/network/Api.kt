package pt.brunoponte.pokemonappkotlin.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pt.brunoponte.pokemonappkotlin.data.entities.pokemon.Pokemon
import pt.brunoponte.pokemonappkotlin.data.entities.pokemon.SimplePokemon
import pt.brunoponte.pokemonappkotlin.data.entities.pokemon.SimplePokemonsWrapper
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
    ) : Call<SimplePokemonsWrapper>

    @GET("pokemon/{name}")
    fun showPokemon(
        @Path("name") name: String
    ): Call<Pokemon>

    companion object {
        operator fun invoke() : Api {

            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            return Retrofit.Builder()
                .baseUrl(Constants.baseUrl)
//                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api::class.java)
        }
    }

}