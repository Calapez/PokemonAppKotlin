package pt.brunoponte.pokemonappkotlin.ui.pokemonDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import pt.brunoponte.pokemonappkotlin.data.entities.Pokemon
import pt.brunoponte.pokemonappkotlin.databinding.FragmentPokemonDetailsBinding
import pt.brunoponte.pokemonappkotlin.ui.pokemonDetails.adapter.CharacteristicsAdapter
import pt.brunoponte.pokemonappkotlin.utils.Helpers.Companion.capitalizeFirstLetter
import pt.brunoponte.pokemonappkotlin.utils.Helpers.Companion.fillImageFromUrl
import pt.brunoponte.pokemonappkotlin.viewmodels.PokemonsViewModel

@AndroidEntryPoint
class PokemonDetailsFragment : Fragment() {

    private val viewModel: PokemonsViewModel by activityViewModels()
    private val abilitiesAdapter = CharacteristicsAdapter()
    private val movesAdapter = CharacteristicsAdapter()
    private lateinit var binding: FragmentPokemonDetailsBinding  // Using view binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokemonDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerViews()
        setObservers()
    }

    private fun initRecyclerViews() {
        // Init abilities recycler view
        with(binding.recylerAbilities) {
            val layoutManager = LinearLayoutManager(context)
            layoutManager.orientation = LinearLayoutManager.VERTICAL

            this.layoutManager = layoutManager
            this.adapter = abilitiesAdapter
        }

        // Init moves recycler view
        with(binding.recyclerMoves) {
            val layoutManager = LinearLayoutManager(context)
            layoutManager.orientation = LinearLayoutManager.VERTICAL

            this.layoutManager = layoutManager
            this.adapter = movesAdapter
        }
    }

    private fun setObservers() {
        viewModel.getSelectedPokemon().observe(viewLifecycleOwner) { pokemon ->
            updateUi(pokemon)
        }
    }

    private fun updateUi(pokemon: Pokemon) {
        binding.textName.text = capitalizeFirstLetter(pokemon.name)

        pokemon.sprites.let { sprites ->
            fillImageFromUrl(binding.imgBack, sprites.backUrl)
            fillImageFromUrl(binding.imgFront, sprites.frontUrl)
        }

        updateAdapters(pokemon)
    }

    private fun updateAdapters(pokemon: Pokemon) {
        abilitiesAdapter.setStrings(
                pokemon.abilities.map { abilities ->
                    capitalizeFirstLetter(abilities.details.name)
                }
        )

        movesAdapter.setStrings(
                pokemon.moves.map { move ->
                    capitalizeFirstLetter(move.details.name)
                }
        )
    }


}