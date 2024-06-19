package com.jimbonlemu.clefer.source.local

import com.jimbonlemu.clefer.R

data class Slider(
    val id: String?,
    val name: String?,
    val image: Int
)

object DummySlider {
    val listSlider = listOf(
        Slider(id = "1", name = "Slider1", image = R.drawable.slider1),
        Slider(id = "2", name = "Slider2", image = R.drawable.slider2),
        Slider(id = "3", name = "Slider3", image = R.drawable.slider3),
        Slider(id = "4", name = "Slider4", image = R.drawable.slider4),
    )
}


