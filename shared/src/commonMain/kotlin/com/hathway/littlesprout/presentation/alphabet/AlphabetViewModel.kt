package com.hathway.littlesprout.presentation.alphabet

import androidx.lifecycle.ViewModel
import com.hathway.littlesprout.domain.model.AlphabetItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import littlesprout.shared.generated.resources.*

class AlphabetViewModel : ViewModel() {
    private val _alphabetList = MutableStateFlow(
        listOf(
            AlphabetItem(
                letter = "Aa",
                letterImage = Res.drawable.aa,
                objectImage = Res.drawable.img_apple,
                description = "A for Apple",
                audio = "a_apple"
            ),
            AlphabetItem(
                letter = "Bb",
                letterImage = Res.drawable.bb,
                objectImage = Res.drawable.img_ball,
                description = "B for Ball",
                audio = "b_ball"
            ),
            AlphabetItem(
                letter = "Cc",
                letterImage = Res.drawable.cc,
                objectImage = Res.drawable.img_cat,
                description = "C for Cat",
                audio = "c_cat"
            ),
            AlphabetItem(
                letter = "Dd",
                letterImage = Res.drawable.img_duck,
                objectImage = Res.drawable.img_duck,
                description = "D for Duck",
                audio = "d_duck"
            ),
            AlphabetItem(
                letter = "Ee",
                letterImage = Res.drawable.img_elephant,
                objectImage = Res.drawable.img_elephant,
                description = "E for Elephant",
                audio = "e_elephant"
            ),
            AlphabetItem(
                letter = "Ff",
                letterImage = Res.drawable.img_duck,
                objectImage = Res.drawable.img_duck,
                description = "F for Fish",
                audio = "f_fish"
            ),
            AlphabetItem(
                letter = "Gg",
                letterImage = Res.drawable.img_giraffe,
                objectImage = Res.drawable.img_giraffe,
                description = "G for Giraffe",
                audio = "g_giraffe"
            ),
            AlphabetItem(
                letter = "Hh",
                letterImage = Res.drawable.img_hat,
                objectImage = Res.drawable.img_hat,
                description = "H for Hat",
                audio = "h_hat"
            ),
            AlphabetItem(
                letter = "Ii",
                letterImage = Res.drawable.img_igloo,
                objectImage = Res.drawable.img_igloo,
                description = "I for Igloo",
                audio = "i_igloo"
            ),
            AlphabetItem(
                letter = "Jj",
                letterImage = Res.drawable.img_jam,
                objectImage = Res.drawable.img_jam,
                description = "J for Jam",
                audio = "j_jam"
            ),
            AlphabetItem(
                letter = "Kk",
                letterImage = Res.drawable.img_kite,
                objectImage = Res.drawable.img_kite,
                description = "K for Kite",
                audio = "k_kite"
            ),
            AlphabetItem(
                letter = "Ll",
                letterImage = Res.drawable.img_lion,
                objectImage = Res.drawable.img_lion,
                description = "L for Lion",
                audio = "l_lion"
            ),
            AlphabetItem(
                letter = "Mm",
                letterImage = Res.drawable.img_monkey,
                objectImage = Res.drawable.img_monkey,
                description = "M for Monkey",
                audio = "m_monkey"
            ),
            AlphabetItem(
                letter = "Nn",
                letterImage = Res.drawable.img_nurse,
                objectImage = Res.drawable.img_nurse,
                description = "N for Nurse",
                audio = "n_nurse"
            ),
            AlphabetItem(
                letter = "Oo",
                letterImage = Res.drawable.img_octopus,
                objectImage = Res.drawable.img_octopus,
                description = "O for Octopus",
                audio = "o_octopus"
            ),
            AlphabetItem(
                letter = "Pp",
                letterImage = Res.drawable.img_panda,
                objectImage = Res.drawable.img_panda,
                description = "P for Panda",
                audio = "p_panda"
            ),
            AlphabetItem(
                letter = "Qq",
                letterImage = Res.drawable.img_queen,
                objectImage = Res.drawable.img_queen,
                description = "Q for Queen",
                audio = "q_queen"
            ),
            AlphabetItem(
                letter = "Rr",
                letterImage = Res.drawable.img_rainbow,
                objectImage = Res.drawable.img_rainbow,
                description = "R for Rainbow",
                audio = "r_rainbow"
            ),
            AlphabetItem(
                letter = "Ss",
                letterImage = Res.drawable.img_sun,
                objectImage = Res.drawable.img_sun,
                description = "S for Sun",
                audio = "s_sun"
            ),
            AlphabetItem(
                letter = "Tt",
                letterImage = Res.drawable.img_tiger,
                objectImage = Res.drawable.img_tiger,
                description = "T for Tiger",
                audio = "t_tiger"
            ),
            AlphabetItem(
                letter = "Uu",
                letterImage = Res.drawable.img_umbrella,
                objectImage = Res.drawable.img_umbrella,
                description = "U for Umbrella",
                audio = "u_umbrella"
            ),
            AlphabetItem(
                letter = "Vv",
                letterImage = Res.drawable.img_van,
                objectImage = Res.drawable.img_van,
                description = "V for Van",
                audio = "v_van"
            ),
            AlphabetItem(
                letter = "Ww",
                letterImage = Res.drawable.img_whale,
                objectImage = Res.drawable.img_whale,
                description = "W for Whale",
                audio = "w_whale"
            ),
            AlphabetItem(
                letter = "Xx",
                letterImage = Res.drawable.img_xylophone,
                objectImage = Res.drawable.img_xylophone,
                description = "X for Xylophone",
                audio = "x_xylophone"
            ),
            AlphabetItem(
                letter = "Yy",
                letterImage = Res.drawable.img_yak,
                objectImage = Res.drawable.img_yak,
                description = "Y for Yak",
                audio = "y_yak"
            ),
            AlphabetItem(
                letter = "Zz",
                letterImage = Res.drawable.img_zebra,
                objectImage = Res.drawable.img_zebra,
                description = "Z for Zebra",
                audio = "z_zebra"
            )
        )
    )
    val alphabetList = _alphabetList.asStateFlow()

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex = _currentIndex.asStateFlow()

    fun nextItem() {
        if (_currentIndex.value < _alphabetList.value.size - 1) {
            _currentIndex.value++
        }
    }

    fun previousItem() {
        if (_currentIndex.value > 0) {
            _currentIndex.value--
        }
    }
}