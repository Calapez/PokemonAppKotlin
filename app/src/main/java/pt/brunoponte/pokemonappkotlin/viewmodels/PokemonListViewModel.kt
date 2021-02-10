package pt.brunoponte.pokemonappkotlin.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import pt.brunoponte.pokemonappkotlin.data.entities.pokemon.SimplePokemon
import pt.brunoponte.pokemonappkotlin.repositories.PokemonRepository
import pt.brunoponte.pokemonappkotlin.utils.Constants

class PokemonListViewModel : ViewModel() {

    val mRepository = PokemonRepository.instance
    val mSimplePokemons = mRepository.getSimplePokemons()
    val mIsFetching = mRepository.getIsFetching()

    fun fetchMorePokemons() {
        if (mRepository.getSimplePokemons().value == null) {
            return
        }

        // Offset is simply the size of the pokemons
        val offset = mRepository.getSimplePokemons().value!!.size
        mRepository.fetchMorePokemons(offset, Constants.pageSize)
    }

    fun getIsFetching(): LiveData<Boolean> {
        return mIsFetching
    }

    fun getSimplePokemons(): LiveData<MutableList<SimplePokemon>> {
        return mSimplePokemons
    }

}