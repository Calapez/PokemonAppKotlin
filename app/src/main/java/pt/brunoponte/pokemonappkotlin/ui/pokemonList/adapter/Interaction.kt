package pt.brunoponte.pokemonappkotlin.ui.pokemonList.adapter

import pt.brunoponte.pokemonappkotlin.data.entities.pokemon.SimplePokemon


interface Interaction {
    fun onItemSelected(position: Int, item: SimplePokemon)
}