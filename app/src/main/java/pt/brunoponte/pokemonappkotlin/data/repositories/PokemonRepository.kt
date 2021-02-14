package pt.brunoponte.pokemonappkotlin.data.repositories

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

    private val _pokemons: MutableLiveData<List<Pokemon>> = MutableLiveData(mutableListOf())

    private val _isFetching: MutableLiveData<Boolean> = MutableLiveData(false)

    fun getPokemons()
            = _pokemons

    fun getIsFetching()
            = _isFetching

    fun fetchPokemons(offset: Int, pageSize: Int) {
        fetchSimplePokemonsFromApi(offset, pageSize)
    }

    private fun savePokemons(pokemonsToSave: List<Pokemon?>) {
        _pokemons.value?.let { oldPokemons ->

            val newPokemons = oldPokemons.toMutableList()
            pokemonsToSave.forEach { pokemonToSave ->
                if (pokemonToSave != null) {
                    newPokemons.add(pokemonToSave)
                }
            }

            _pokemons.value = newPokemons
        }
    }

    // Fetch pokemons from a given offset and with a given pageSize
    private fun fetchSimplePokemonsFromApi(offset: Int, pageSize: Int) {
        _isFetching.postValue(true)

        // Fetch simple pokemons
        api.listPokemons(offset, pageSize)
            .enqueue(object: Callback<SimplePokemonsResponse> {
                override fun onResponse(
                call: Call<SimplePokemonsResponse>,
                response: Response<SimplePokemonsResponse>
                ) {

                    if (response.isSuccessful) {
                        response.body()?.simplePokemons?.let { simplePokemons ->
                            val simplePokemonsMap = simplePokemons.map {
                                it.name to it
                            }.toMap()

                            fetchFullPokemonsFromApi(simplePokemonsMap)
                        }
                    }
                }

                override fun onFailure(call: Call<SimplePokemonsResponse>, t: Throwable) {
                    _isFetching.postValue(false)
                }
            })
    }

    // Fetch full pokemons from a list of simple pokemons
    private fun fetchFullPokemonsFromApi(simplePokemons: Map<String, SimplePokemon>) {
        _isFetching.postValue(true)

        // Keep pokemons sorted by ID
        val fullPokemonsMap = sortedMapOf<Long, Pokemon>()

        simplePokemons.keys.forEach { pokemonName ->
            api.getFullPokemon(pokemonName).enqueue(object: Callback<Pokemon> {
                override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                    response.body()?.let { fullPokemon ->
                        fullPokemonsMap[fullPokemon.id] = fullPokemon
                    }

                    if (fullPokemonsMap.size == simplePokemons.size) {
                        savePokemons(fullPokemonsMap.values.toList())
                        _isFetching.postValue(false)
                        return
                    }
                }

                override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                    _isFetching.postValue(false)
                }

            })
        }
    }

}