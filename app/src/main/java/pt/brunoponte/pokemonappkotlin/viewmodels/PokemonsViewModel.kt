package pt.brunoponte.pokemonappkotlin.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pt.brunoponte.pokemonappkotlin.data.entities.Pokemon
import pt.brunoponte.pokemonappkotlin.data.repositories.PokemonRepository
import pt.brunoponte.pokemonappkotlin.utils.Constants
import javax.inject.Inject

@HiltViewModel
class PokemonsViewModel
@Inject constructor(
    private val repository: PokemonRepository
): ViewModel() {

    private val selectedPokemon = MutableLiveData<Pokemon>()

    fun getIsFetching(): LiveData<Boolean> {
        return repository.getIsFetching()
    }

    fun getPokemons(): LiveData<List<Pokemon>> {
        return repository.getPokemons()
    }

    fun getSelectedPokemon(): LiveData<Pokemon> {
        return selectedPokemon
    }

    fun selectPokemon(pokemon: Pokemon) {
        selectedPokemon.value = pokemon
    }

    fun fetchMorePokemons() {
        if (repository.getPokemons().value == null) {
            return
        }

        // Offset is simply the size of the pokemons
        val offset = repository.getPokemons().value!!.size
        repository.fetchPokemons(offset, Constants.pageSize)
    }

}