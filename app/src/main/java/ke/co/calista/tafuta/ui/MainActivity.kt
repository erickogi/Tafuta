package ke.co.calista.tafuta.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ke.co.calista.tafuta.R
import ke.co.calista.tafuta.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        /// window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        // actionBar?.hide()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

    }
}
