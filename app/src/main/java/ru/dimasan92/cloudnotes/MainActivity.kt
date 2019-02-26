package ru.dimasan92.cloudnotes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_action.setOnClickListener {
            tv_response_to_action.text = resources.getString(R.string.click)
        }
    }
}
