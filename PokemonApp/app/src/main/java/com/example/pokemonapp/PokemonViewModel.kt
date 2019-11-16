package com.example.pokemonapp

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class PokemonViewModel : ViewModel() {
    val pokemonList = MutableLiveData<ArrayList<Pokemon>>()

    init {
        pokemonList.value = ArrayList() 
    }


    fun refreshPokemon(location: Location){
        pokemonList.value?.clear()
        val rand = Random(Calendar.getInstance().timeInMillis)
        for (i in 0..19){
            val pokemon = Pokemon()
            pokemon.latitude = location.latitude + (rand.nextDouble() - 0.5) / 100
            pokemon.longitude = location.longitude + (rand.nextDouble() - 0.5) / 100

            when (rand.nextInt(3)){
                0 -> {
                    pokemon.name = "Magicarp"
                    pokemon.marker = R.drawable.magicarp
                }
                1 -> {
                    pokemon.name = "Pikachu"
                    pokemon.marker = R.drawable.pikachu
                }
                2 -> {
                    pokemon.name = "Squirtle"
                    pokemon.marker = R.drawable.squirtle
                }
            }

            pokemonList?.value?.add(pokemon)
        }
    }
}