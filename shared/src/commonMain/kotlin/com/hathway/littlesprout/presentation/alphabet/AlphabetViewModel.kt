package com.hathway.littlesprout.presentation.alphabet

import androidx.lifecycle.ViewModel
import com.hathway.littlesprout.domain.model.AlphabetItem
import com.hathway.littlesprout.presentation.music.getAudioPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import littlesprout.shared.generated.resources.*

class AlphabetViewModel : ViewModel() {
    private val audioPlayer = getAudioPlayer()

    private val _alphabetList = MutableStateFlow(
        listOf(
            AlphabetItem(
                letter = "Aa",
                letterImage = Res.drawable.aa,
                objectImage = Res.drawable.img_apple,
                description = "A - Apple",
                audio = "audio_a_apple.mp3"
            ),
            AlphabetItem(
                letter = "Bb",
                letterImage = Res.drawable.bb,
                objectImage = Res.drawable.img_ball,
                description = "B - Ball",
                audio = "audio_b_ball.mp3"
            ),
            AlphabetItem(
                letter = "Cc",
                letterImage = Res.drawable.cc,
                objectImage = Res.drawable.img_cat,
                description = "C - Cat",
                audio = "audio_c_cat.mp3"
            ),
            AlphabetItem(
                letter = "Dd",
                letterImage = Res.drawable.img_dd,
                objectImage = Res.drawable.img_duck,
                description = "D - Duck",
                audio = "audio_d_duck.mp3"
            ),
            AlphabetItem(
                letter = "Ee",
                letterImage = Res.drawable.ee,
                objectImage = Res.drawable.img_elephant,
                description = "E - Elephant",
                audio = "audio_e_elephant.mp3"
            ),
            AlphabetItem(
                letter = "Ff",
                letterImage = Res.drawable.ff,
                objectImage = Res.drawable.img_fish,
                description = "F - Fish",
                audio = "audio_f_fish.mp3"
            ),
            AlphabetItem(
                letter = "Gg",
                letterImage = Res.drawable.gg,
                objectImage = Res.drawable.img_giraffe,
                description = "G - Giraffe",
                audio = "audio_g_giraffe.mp3"
            ),
            AlphabetItem(
                letter = "Hh",
                letterImage = Res.drawable.hh,
                objectImage = Res.drawable.img_hat,
                description = "H - Hat",
                audio = "audio_h_hat.mp3"
            ),
            AlphabetItem(
                letter = "Ii",
                letterImage = Res.drawable.ii,
                objectImage = Res.drawable.img_igloo,
                description = "I - Igloo",
                audio = "audio_i_igloo.mp3"
            ),
            AlphabetItem(
                letter = "Jj",
                letterImage = Res.drawable.jj,
                objectImage = Res.drawable.img_jam,
                description = "J - Jam",
                audio = "audio_j_jam.mp3"
            ),
            AlphabetItem(
                letter = "Kk",
                letterImage = Res.drawable.kk,
                objectImage = Res.drawable.img_kite,
                description = "K - Kite",
                audio = "audio_k_kite.mp3"
            ),
            AlphabetItem(
                letter = "Ll",
                letterImage = Res.drawable.ll,
                objectImage = Res.drawable.img_lion,
                description = "L - Lion",
                audio = "audio_l_lion.mp3"
            ),
            AlphabetItem(
                letter = "Mm",
                letterImage = Res.drawable.mm,
                objectImage = Res.drawable.img_monkey,
                description = "M - Monkey",
                audio = "audio_m_monkey.mp3"
            ),
            AlphabetItem(
                letter = "Nn",
                letterImage = Res.drawable.nn,
                objectImage = Res.drawable.img_nurse,
                description = "N - Nurse",
                audio = "audio_n_nurse.mp3"
            ),
            AlphabetItem(
                letter = "Oo",
                letterImage = Res.drawable.oo,
                objectImage = Res.drawable.img_octopus,
                description = "O - Octopus",
                audio = "audio_o_octopus.mp3"
            ),
            AlphabetItem(
                letter = "Pp",
                letterImage = Res.drawable.pp,
                objectImage = Res.drawable.img_panda,
                description = "P - Panda",
                audio = "audio_p_panda.mp3"
            ),
            AlphabetItem(
                letter = "Qq",
                letterImage = Res.drawable.qq,
                objectImage = Res.drawable.img_queen,
                description = "Q - Queen",
                audio = "audio_q_queen.mp3"
            ),
            AlphabetItem(
                letter = "Rr",
                letterImage = Res.drawable.rr,
                objectImage = Res.drawable.img_rainbow,
                description = "R - Rainbow",
                audio = "audio_r_rainbow.mp3"
            ),
            AlphabetItem(
                letter = "Ss",
                letterImage = Res.drawable.ss,
                objectImage = Res.drawable.img_sun,
                description = "S - Sun",
                audio = "audio_s_sun.mp3"
            ),
            AlphabetItem(
                letter = "Tt",
                letterImage = Res.drawable.tt,
                objectImage = Res.drawable.img_tiger,
                description = "T - Tiger",
                audio = "audio_t_tiger.mp3"
            ),
            AlphabetItem(
                letter = "Uu",
                letterImage = Res.drawable.uu,
                objectImage = Res.drawable.img_umbrella,
                description = "U - Umbrella",
                audio = "audio_u_umbrella.mp3"
            ),
            AlphabetItem(
                letter = "Vv",
                letterImage = Res.drawable.vv,
                objectImage = Res.drawable.img_van,
                description = "V - Van",
                audio = "audio_v_van.mp3"
            ),
            AlphabetItem(
                letter = "Ww",
                letterImage = Res.drawable.ww,
                objectImage = Res.drawable.img_whale,
                description = "W - Whale",
                audio = "audio_w_whale.mp3"
            ),
            AlphabetItem(
                letter = "Xx",
                letterImage = Res.drawable.xx,
                objectImage = Res.drawable.img_xylophone,
                description = "X - Xylophone",
                audio = "audio_x_xylophone.mp3"
            ),
            AlphabetItem(
                letter = "Yy",
                letterImage = Res.drawable.yy,
                objectImage = Res.drawable.img_yak,
                description = "Y - Yak",
                audio = "audio_y_yak.mp3"
            ),
            AlphabetItem(
                letter = "Zz",
                letterImage = Res.drawable.zz,
                objectImage = Res.drawable.img_zebra,
                description = "Z - Zebra",
                audio = "audio_z_zebra.mp3"
            )
        )
    )
    val alphabetList = _alphabetList.asStateFlow()

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex = _currentIndex.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    init {
        audioPlayer.preload(_alphabetList.value.mapNotNull { it.audio })
        audioPlayer.onPlaybackComplete {
            _isPlaying.value = false
        }
    }

    fun playCurrentAudio() {
        val current = _alphabetList.value.getOrNull(_currentIndex.value)
        current?.audio?.let {
            _isPlaying.value = true
            audioPlayer.play(it, interruptCurrent = true)
        }
    }

    fun nextItem() {
        stopAudio()
        if (_currentIndex.value < _alphabetList.value.size - 1) {
            _currentIndex.value++
            playCurrentAudio()
        }
    }

    fun previousItem() {
        stopAudio()
        if (_currentIndex.value > 0) {
            _currentIndex.value--
            playCurrentAudio()
        }
    }

    private fun stopAudio() {
        audioPlayer.stop()
        _isPlaying.value = false
    }

    override fun onCleared() {
        super.onCleared()
        stopAudio()
    }
}
