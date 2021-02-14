package pt.brunoponte.pokemonappkotlin.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class HelpersTest {

    @Test
    fun `empty string returns empty string`() {
        val result = Helpers.capitalizeFirstLetter("")
        assertThat(result).isEqualTo("")
    }

    @Test
    fun `valid string returns correct string 1`() {
        val result = Helpers.capitalizeFirstLetter("abcdef")
        assertThat(result).isEqualTo("Abcdef")
    }

    @Test
    fun `valid string returns correct string 2`() {
        val result = Helpers.capitalizeFirstLetter("ABCDEF")
        assertThat(result).isEqualTo("ABCDEF")
    }

}