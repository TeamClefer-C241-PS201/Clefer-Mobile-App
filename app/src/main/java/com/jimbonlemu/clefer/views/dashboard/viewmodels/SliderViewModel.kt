package com.jimbonlemu.clefer.views.dashboard.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jimbonlemu.clefer.source.local.DummySlider
import com.jimbonlemu.clefer.source.local.Slider

class SliderViewModel  : ViewModel(){
    val listSlider: LiveData<List<Slider>> = MutableLiveData<List<Slider>>().apply {
        value = DummySlider.listSlider
    }
}