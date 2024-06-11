package com.jimbonlemu.clefer.views.community

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivityDetailCommentBinding

class DetailCommentActivity : CoreActivity<ActivityDetailCommentBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_comment)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun setupBinding(layoutInflater: LayoutInflater): ActivityDetailCommentBinding = ActivityDetailCommentBinding.inflate(layoutInflater)
}