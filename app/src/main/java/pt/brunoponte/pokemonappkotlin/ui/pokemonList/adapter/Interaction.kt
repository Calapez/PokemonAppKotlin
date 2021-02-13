package pt.brunoponte.pokemonappkotlin.ui.pokemonList.adapter

import pt.brunoponte.pokemonappkotlin.data.entities.Pokemon


interface Interaction {
    fun onItemSelected(position: Int, item: Pokemon)
}