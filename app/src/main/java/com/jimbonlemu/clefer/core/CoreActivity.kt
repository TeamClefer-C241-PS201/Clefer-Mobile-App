package com.jimbonlemu.clefer.core

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class CoreActivity<viewBinding:ViewBinding> : AppCompatActivity() {
    private var _binding: viewBinding? = null
    protected val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewBinding(layoutInflater)
        setContentView(binding.root)

        initIntent()
        initUI()
        initAction()
        initProcess()
        initObservers()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    abstract fun getViewBinding(layoutInflater: LayoutInflater): viewBinding

    abstract fun initIntent()

    abstract fun initUI()

    abstract fun initAction()

    abstract fun initProcess()

    abstract fun initObservers()
}