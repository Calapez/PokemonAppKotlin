package pt.brunoponte.pokemonappkotlin.repositories

import androidx.lifecycle.MutableLiveData
import pt.brunoponte.pokemonappkotlin.data.entities.Pokemon
import pt.brunoponte.pokemonappkotlin.network.Api
import pt.brunoponte.pokemonappkotlin.network.responses.SimplePokemonsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class PokemonRepository
@Inject constructor(
    val api: Api
) {

    private val mPokemons: MutableLiveData<List<Pokemon>>
            = MutableLiveData(mutableListOf())

    private val mIsFetching: MutableLiveData<Boolean>
            = MutableLiveData(false)

    fun getPokemons()
            = mPokemons

    fun getIsFetching()
            = mIsFetching

    fun fetchPokemons(offset: Int, pageSize: Int) {
        fetchPokemonsFromApi(offset, pageSize)
    }

    private fun savePokemon(pokemon: Pokemon) {
        mPokemons.value?.let { oldPokemons ->
            val newPokemons = oldPokemons.toMutableList()
                    .also { it.add(pokemon) }

            mPokemons.value = newPokemons
        }
    }

    // Fetch pokemons from a given offset and with a given pageSize
    private fun fetchPokemonsFromApi(offset: Int, pageSize: Int) {
        mIsFetching.value = true

        // Fetch simple pokemons
        api.listPokemons(offset, pageSize)
            .enqueue(object: Callback<SimplePokemonsResponse> {
                override fun onResponse(
                call: Call<SimplePokemonsResponse>,
                response: Response<SimplePokemonsResponse>
                ) {

                    if (response.isSuccessful) {
                        response.body()?.simplePokemons?.forEach { simplePokemon ->

                            api.getPokemonDetails(simplePokemon.name).enqueue(object: Callback<Pokemon> {
                                override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                                    response.body()?.let { pokemon ->
                                        savePokemon(pokemon)
                                    }
                                }

                                override fun onFailure(call: Call<Pokemon>, t: Throwable) { }

                            })
                        }
                    }
                }

                override fun onFailure(call: Call<SimplePokemonsResponse>, t: Throwable) { }
            })
    }

}