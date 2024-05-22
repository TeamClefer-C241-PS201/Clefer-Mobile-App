package com.jimbonlemu.clefer.views

import android.view.LayoutInflater
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivityWelcomeBinding

class WelcomeActivity :  CoreActivity<ActivityWelcomeBinding>() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }

    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityWelcomeBinding {
        return ActivityWelcomeBinding.inflate(layoutInflater)
    }

    override fun initIntent() {

    }

    override fun initUI() {

    }

    override fun initAction() {

    }

    override fun initProcess() {

    }

    override fun initObservers() {

    }
}