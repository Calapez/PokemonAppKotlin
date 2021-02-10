package pt.brunoponte.pokemonappkotlin.repositories

import androidx.lifecycle.MutableLiveData
import pt.brunoponte.pokemonappkotlin.data.entities.pokemon.Pokemon
import pt.brunoponte.pokemonappkotlin.data.entities.pokemon.SimplePokemon
import pt.brunoponte.pokemonappkotlin.data.entities.pokemon.SimplePokemonsWrapper
import pt.brunoponte.pokemonappkotlin.network.Api
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

        val listPokemonsCall: Call<SimplePokemonsWrapper> =
            apiService.listPokemons(offset, pageSize)

        listPokemonsCall.enqueue(object : Callback<SimplePokemonsWrapper> {
            override fun onResponse(
                call: Call<SimplePokemonsWrapper>,
                response: Response<SimplePokemonsWrapper>
            ) {
                val wrapper = response.body() ?: return

                val photosCounter = intArrayOf(0)
                for (pokemon in wrapper.pokemons) {
                    tempPokemons.add(pokemon)

                    val showPokemonCall: Call<Pokemon> =
                        apiService.showPokemon(pokemon.name)
                    showPokemonCall.enqueue(object : Callback<Pokemon> {
                        override fun onResponse(
                            call: Call<Pokemon>,
                            response: Response<Pokemon>
                        ) {
                            photosCounter[0]++
                            pokemon.photoUrl = response.body()!!.sprites!!.frontUrl

                            // Last photo, finish list
                            if (photosCounter[0] == wrapper.pokemons.size) {
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
                call: Call<SimplePokemonsWrapper?>,
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