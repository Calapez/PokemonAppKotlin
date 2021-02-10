package pt.brunoponte.pokemonappkotlin.ui.pokemonList.adapter

import pt.brunoponte.pokemonappkotlin.network.responses.SimplePokemonsResponse.SimplePokemon


interface Interaction {
    fun onItemSelected(position: Int, item: SimplePokemon)
}