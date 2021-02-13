package pt.brunoponte.pokemonappkotlin.repositories

import androidx.lifecycle.MutableLiveData
import pt.brunoponte.pokemonappkotlin.data.entities.Pokemon
import pt.brunoponte.pokemonappkotlin.network.Api
import pt.brunoponte.pokemonappkotlin.network.responses.SimplePokemonsResponse
import pt.brunoponte.pokemonappkotlin.network.responses.SimplePokemonsResponse.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class PokemonRepository
@Inject constructor(
    private val api: Api
) {

    private val mPokemons: MutableLiveData<List<Pokemon>> = MutableLiveData(mutableListOf())

    private val mIsFetching: MutableLiveData<Boolean> = MutableLiveData(false)

    fun getPokemons()
            = mPokemons

    fun getIsFetching()
            = mIsFetching

    fun fetchPokemons(offset: Int, pageSize: Int) {
        fetchSimplePokemonsFromApi(offset, pageSize)
    }

    private fun savePokemon(pokemon: Pokemon) {
        mPokemons.value?.let { oldPokemons ->
            val newPokemons = oldPokemons.toMutableList()
                    .also { it.add(pokemon) }

            mPokemons.value = newPokemons
        }
    }

    // Fetch pokemons from a given offset and with a given pageSize
    private fun fetchSimplePokemonsFromApi(offset: Int, pageSize: Int) {
        mIsFetching.postValue(true)

        // Fetch simple pokemons
        api.listPokemons(offset, pageSize)
            .enqueue(object: Callback<SimplePokemonsResponse> {
                override fun onResponse(
                call: Call<SimplePokemonsResponse>,
                response: Response<SimplePokemonsResponse>
                ) {

                    if (response.isSuccessful) {
                        response.body()?.simplePokemons?.let { simplePokemons ->
                            fetchFullPokemonsFromApi(simplePokemons)
                        }
                    }
                }

                override fun onFailure(call: Call<SimplePokemonsResponse>, t: Throwable) {
                    mIsFetching.postValue(false)
                }
            })
    }

    // Fetch full pokemons from a list of simple pokemons
    private fun fetchFullPokemonsFromApi(simplePokemons: List<SimplePokemon>) {
        mIsFetching.value = true

        var fetchedPokemons = 0

        simplePokemons.forEach { simplePokemon ->

            api.getFullPokemon(simplePokemon.name).enqueue(object: Callback<Pokemon> {
                override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                    response.body()?.let { pokemon ->
                        savePokemon(pokemon)
                        fetchedPokemons++
                    }

                    if (fetchedPokemons == simplePokemons.size) {
                        mIsFetching.postValue(false)
                        return
                    }
                }

                override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                    mIsFetching.postValue(false)
                }

            })
        }
    }

}