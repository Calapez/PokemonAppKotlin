package pt.brunoponte.pokemonappkotlin.repositories

import androidx.lifecycle.MutableLiveData
import pt.brunoponte.pokemonappkotlin.data.entities.Pokemon
import pt.brunoponte.pokemonappkotlin.network.Api
import pt.brunoponte.pokemonappkotlin.network.responses.SimplePokemonsResponse
import pt.brunoponte.pokemonappkotlin.network.responses.SimplePokemonsResponse.SimplePokemon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class PokemonRepository
@Inject constructor(
    private val api: Api
) {

    private val mSimplePokemons: MutableLiveData<List<SimplePokemon>>
            = MutableLiveData(mutableListOf())

    private val mIsFetching: MutableLiveData<Boolean>
            = MutableLiveData(false)

    fun getSimplePokemons()
            = mSimplePokemons

    fun getIsFetching()
            = mIsFetching

    fun fetchPokemons(offset: Int, pageSize: Int) {
        fetchPokemonsFromApi(offset, pageSize)
    }

    // Fetch pokemons from a given offset and with a given pageSize
    private fun fetchPokemonsFromApi(offset: Int, pageSize: Int) {
        mIsFetching.value = true

        val tempPokemons = mSimplePokemons.value!!.toMutableList()

        // Fetch simple pokemons
        api.listPokemons(offset, pageSize)
            .enqueue(object : Callback<SimplePokemonsResponse> {
                override fun onResponse(
                call: Call<SimplePokemonsResponse>,
                response: Response<SimplePokemonsResponse>
                ) {
                    val wrapper = response.body() ?: return

                    val photosCounter = intArrayOf(0)
                    for (pokemon in wrapper.simplePokemons) {
                        tempPokemons.add(pokemon)

                        api.showPokemon(pokemon.name)
                            .enqueue(object : Callback<Pokemon> {
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