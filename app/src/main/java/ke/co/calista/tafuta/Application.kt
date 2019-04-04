package ke.co.calista.tafuta


import android.os.Handler
import androidx.multidex.MultiDexApplication


class Application : MultiDexApplication() {

    companion object {


        val TAG = Application::class.java.simpleName

        @Volatile
        lateinit var applicationHandler: Handler

        @Volatile
        var application: Application? = null


    }

}
