package com.hgtech.smartio.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hgtech.smartio.databinding.ActivityAuthenticationBinding
import com.hgtech.smartio.network.manager.UserWrapper.USER_ID
import com.hgtech.smartio.network.manager.UserWrapper.getID

class AuthenticationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(getID()) {
            if (this != "0") {
                startActivity(Intent(this@AuthenticationActivity, MainActivity::class.java).apply {
                    putExtra(USER_ID, this@with)
                })
                finish()
            }

            else {
                val binding = ActivityAuthenticationBinding.inflate(layoutInflater)
                setContentView(binding.root)
            }
        }

    }
}