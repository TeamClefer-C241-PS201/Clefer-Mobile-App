package com.jimbonlemu.clefer.views.community

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.view.isGone
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivityQuestionBinding
import com.jimbonlemu.clefer.source.remote.request.DiscussionRequest
import com.jimbonlemu.clefer.utils.ResponseState
import com.jimbonlemu.clefer.views.community.viewmodel.CommunityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class QuestionActivity : CoreActivity<ActivityQuestionBinding>() {
    private val questionViewModel: CommunityViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            setupButton()
            initObservers()
            setupToolbar()
        }
    }
    @Suppress("DEPRECATION")
    private fun setupToolbar(){
        binding.toolbar.setupToolbar(
            title = getString(R.string.question),
            showBackButton = true,
            backAction = { onBackPressed() }
        )
    }

    private fun ActivityQuestionBinding.setupButton() {
        btnPost.setOnClickListener {
            val titleField = edtTitle.inputText.trim()
            val descriptionField = edtDescription.inputText.trim()

            if (titleField.isEmpty() || descriptionField.isEmpty()) {
                getToast(
                    when {
                        titleField.isEmpty() -> "Please fill in the title"
                        else -> "Please fill in the description"
                    }
                )
            } else {
                val discussionRequest = DiscussionRequest(
                    postTitle = titleField,
                    postDesc = descriptionField
                )
                questionViewModel.createDiscussion(discussionRequest)
            }
        }
    }

    private fun ActivityQuestionBinding.initObservers() {
        questionViewModel.createDiscussion.observe(this@QuestionActivity) { response ->
            when (response) {
                is ResponseState.Loading -> {
                    enabledComponent(false)
                }

                is ResponseState.Success -> {
                    enabledComponent(true)
                    Toast.makeText(
                        this@QuestionActivity,
                        "Question posted successfully", Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }

                is ResponseState.Error -> {
                    enabledComponent(true)
                    Toast.makeText(this@QuestionActivity, response.errorMessage, Toast.LENGTH_SHORT).show()
                }

                else -> main.isGone = true
            }
        }
    }

    private fun ActivityQuestionBinding.enabledComponent(isComponentEnabled: Boolean) {
        if (isComponentEnabled) {
            edtTitle.isEnabled = true
            edtDescription.isEnabled = true
            btnPost.isEnabled = true
        } else {
            edtTitle.isEnabled = false
            edtDescription.isEnabled = false
            btnPost.isEnabled = false
        }
    }

    override fun setupBinding(layoutInflater: LayoutInflater): ActivityQuestionBinding =
        ActivityQuestionBinding.inflate(layoutInflater)

    private fun getToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
