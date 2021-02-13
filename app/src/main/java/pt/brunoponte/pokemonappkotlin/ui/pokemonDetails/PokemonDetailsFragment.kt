package pt.brunoponte.pokemonappkotlin.ui.pokemonDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import pt.brunoponte.pokemonappkotlin.data.entities.Pokemon
import pt.brunoponte.pokemonappkotlin.databinding.FragmentPokemonDetailsBinding
import pt.brunoponte.pokemonappkotlin.utils.Constants
import pt.brunoponte.pokemonappkotlin.utils.Constants.Companion.capitalizeFirstLetter
import pt.brunoponte.pokemonappkotlin.utils.Constants.Companion.fillImageFromUrl
import pt.brunoponte.pokemonappkotlin.viewmodels.PokemonsViewModel

@AndroidEntryPoint
class PokemonDetailsFragment : Fragment() {

    private val viewModel: PokemonsViewModel by activityViewModels()
    private lateinit var binding: FragmentPokemonDetailsBinding  // Using view binding
    private lateinit var abilitiesAdapter: ArrayAdapter<String>
    private lateinit var movesAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movesAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_expandable_list_item_1,
            mutableListOf()
        )

        abilitiesAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_expandable_list_item_1,
                mutableListOf()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokemonDetailsBinding.inflate(inflater, container, false)
        setObservers()
        return binding.root
    }

    private fun setObservers() {
        viewModel.getSelectedPokemon().observe(viewLifecycleOwner) { pokemon ->
            fillPokemonUi(pokemon)
        }
    }

    private fun fillPokemonUi(pokemon: Pokemon) {
        movesAdapter.apply {
            clear()
            addAll(
                pokemon.moves.map { move ->
                    capitalizeFirstLetter(move.details.name)
                }
            )
        }

        abilitiesAdapter.apply {
            clear()
            addAll(
                pokemon.abilities.map { ability ->
                    capitalizeFirstLetter(ability.details.name)
                }
            )
        }

        with(binding) {
            textName.text = Constants.capitalizeFirstLetter(pokemon.name)
            listAbilities.adapter = movesAdapter
            listMoves.adapter = abilitiesAdapter

            pokemon.sprites.let { sprites ->
                fillImageFromUrl(binding.imgFront, sprites.frontUrl)
                fillImageFromUrl(binding.imgBack, sprites.backUrl)
            }
        }
    }
}