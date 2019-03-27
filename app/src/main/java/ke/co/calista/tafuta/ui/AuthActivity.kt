package ke.co.calista.tafuta.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import ke.co.calista.tafuta.R
import ke.co.calista.tafuta.ui.auth.AuthFragment

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth_activity)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, AuthFragment.newInstance())
                .commitNow()
        }
    }

    override fun onResume() {
        super.onResume()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
    }
}
