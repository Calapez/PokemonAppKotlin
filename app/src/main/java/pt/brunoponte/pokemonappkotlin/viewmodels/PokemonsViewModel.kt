package pt.brunoponte.pokemonappkotlin.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pt.brunoponte.pokemonappkotlin.data.entities.Pokemon
import pt.brunoponte.pokemonappkotlin.network.Api
import pt.brunoponte.pokemonappkotlin.network.responses.SimplePokemonsResponse.SimplePokemon
import pt.brunoponte.pokemonappkotlin.repositories.PokemonRepository
import pt.brunoponte.pokemonappkotlin.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class PokemonsViewModel
@Inject constructor(
    private val repository: PokemonRepository
): ViewModel() {

    private val selectedPokemon = MutableLiveData<Pokemon>()

    fun getIsFetching(): LiveData<Boolean> {
        return repository.getIsFetching()
    }

    fun getSimplePokemons(): LiveData<List<SimplePokemon>> {
        return repository.getSimplePokemons()
    }

    fun getSelectedPokemon(): LiveData<Pokemon> {
        return selectedPokemon
    }

    fun selectPokemon(pokemon: SimplePokemon) {
        fetchPokemonDetails(pokemon.name)
    }

    fun fetchMorePokemons() {
        if (repository.getSimplePokemons().value == null) {
            return
        }

        // Offset is simply the size of the pokemons
        val offset = repository.getSimplePokemons().value!!.size
        repository.fetchPokemons(offset, Constants.pageSize)
    }

    // FIXME: This is just temporarily in ModelView
    private fun fetchPokemonDetails(name: String) {
        val apiService = Api()

        val showPokemonsCall: Call<Pokemon> = apiService.showPokemon(name)
        showPokemonsCall.enqueue(object : Callback<Pokemon> {
            override fun onResponse(
                call: Call<Pokemon>,
                response: Response<Pokemon>
            ) {
                selectedPokemon.postValue(response.body())
            }

            override fun onFailure(
                call: Call<Pokemon>,
                t: Throwable
            ) {
                t.printStackTrace()
                selectedPokemon.postValue(null)
            }
        })
    }


}