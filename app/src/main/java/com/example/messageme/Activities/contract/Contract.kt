package com.example.messageme.Activities.contract

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts

class Contract : ActivityResultContract<String, String>() {
    override fun createIntent(context: Context, input: String): Intent {
        TODO("Not yet implemented")
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String {
        TODO("Not yet implemented")
    }

}