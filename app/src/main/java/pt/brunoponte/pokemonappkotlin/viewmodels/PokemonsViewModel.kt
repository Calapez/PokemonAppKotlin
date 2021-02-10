package pt.brunoponte.pokemonappkotlin.viewmodels

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

class PokemonsViewModel : ViewModel() {

    private val mRepository = PokemonRepository.instance
    private val mSimplePokemons = mRepository.getSimplePokemons()
    private val mIsFetching = mRepository.getIsFetching()
    private val selectedPokemon = MutableLiveData<Pokemon>()

    fun getIsFetching()
            = mIsFetching

    fun getSimplePokemons()
            = mSimplePokemons

    fun getSelectedPokemon()
            = selectedPokemon

    fun setSelectedPokemon(pokemon: SimplePokemon) {
        fetchPokemonDetails(pokemon.name)
    }

    fun fetchMorePokemons() {
        if (mRepository.getSimplePokemons().value == null) {
            return
        }

        // Offset is simply the size of the pokemons
        val offset = mRepository.getSimplePokemons().value!!.size
        mRepository.fetchMorePokemons(offset, Constants.pageSize)
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