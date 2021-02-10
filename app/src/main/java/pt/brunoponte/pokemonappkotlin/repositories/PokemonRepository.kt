package pt.brunoponte.pokemonappkotlin.repositories

import androidx.lifecycle.MutableLiveData
import pt.brunoponte.pokemonappkotlin.data.entities.Pokemon
import pt.brunoponte.pokemonappkotlin.network.Api
import pt.brunoponte.pokemonappkotlin.network.responses.SimplePokemonsResponse
import pt.brunoponte.pokemonappkotlin.network.responses.SimplePokemonsResponse.SimplePokemon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonRepository {

    private var instance: PokemonRepository? = null

    val mSimplePokemons: MutableLiveData<MutableList<SimplePokemon>> = MutableLiveData(mutableListOf())
    val mIsFetching: MutableLiveData<Boolean> = MutableLiveData(false)

    companion object {
        val instance = PokemonRepository()
    }

    fun getSimplePokemons()
            = mSimplePokemons

    fun getIsFetching()
            = mIsFetching

    // Fetch pokemons from a given offset and with a given pageSize
    fun fetchMorePokemons(offset: Int, pageSize: Int) {
        mIsFetching.value = true

        val tempPokemons = mSimplePokemons.value!!.toMutableList()

        // Fetch pokemons
        val apiService = Api()

        val listPokemonsCall: Call<SimplePokemonsResponse> =
            apiService.listPokemons(offset, pageSize)

        listPokemonsCall.enqueue(object : Callback<SimplePokemonsResponse> {
            override fun onResponse(
                call: Call<SimplePokemonsResponse>,
                response: Response<SimplePokemonsResponse>
            ) {
                val wrapper = response.body() ?: return

                val photosCounter = intArrayOf(0)
                for (pokemon in wrapper.simplePokemons) {
                    tempPokemons.add(pokemon)

                    val showPokemonCall: Call<Pokemon> =
                        apiService.showPokemon(pokemon.name)
                    showPokemonCall.enqueue(object : Callback<Pokemon> {
                        override fun onResponse(
                            call: Call<Pokemon>,
                            response: Response<Pokemon>
                        ) {
                            photosCounter[0]++

                            response.body()?.sprites?.frontUrl?.let { frontUrl ->
                                pokemon.photoUrl = frontUrl
                            }

                            // Last photo, finish list
                            if (photosCounter[0] == wrapper.simplePokemons.size) {
                                mIsFetching.value = false
                                mSimplePokemons.postValue(tempPokemons)
                            }
                        }

                        override fun onFailure(
                            call: Call<Pokemon>,
                            t: Throwable
                        ) {
                            t.printStackTrace()
                        }
                    })
                }
            }

            override fun onFailure(
                call: Call<SimplePokemonsResponse>,
                t: Throwable
            ) {
                t.printStackTrace()
                val noPokemons = mSimplePokemons.value!!.toMutableList()
                noPokemons.clear()
                mSimplePokemons.postValue(noPokemons)
            }
        })
    }

}