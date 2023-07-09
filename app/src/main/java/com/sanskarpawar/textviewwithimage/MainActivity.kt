package com.sanskarpawar.textviewwithimage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sanskarpawar.textviewwithimage.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).apply {
            binding = this }.root)

        binding.itvSampleText.insertImageBeforeWord(R.drawable.ic_android_logo, "this")

    }

}