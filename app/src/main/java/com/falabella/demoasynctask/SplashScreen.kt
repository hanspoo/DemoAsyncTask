package com.falabella.demoasynctask

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import com.falabella.demoasynctask.ui.login.LoginActivity
import org.json.JSONObject
import java.net.URL


class PostPrueba(val title: String)

class SplashScreen : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.falabella.demoasynctask.R.layout.activity_main)
        if (hayRed())
            leerConfiguracion()
        else {
            Log.e("SplashScreen", "No hay red")
            levantarMain()
        }
    }

    fun levantarMain() {
        val i = Intent(this@SplashScreen, LoginActivity::class.java)
        startActivity(i)
        this.finish()
    }

    private fun leerConfiguracion() {
        LongOperation().execute("")
    }

    private inner class LongOperation :
        AsyncTask<String?, Void?, String>() {

        override fun doInBackground(vararg params: String?): String? {

            val s = URL("https://jsonplaceholder.typicode.com/posts/1").readText()
            var j = JSONObject(s)
            val post = PostPrueba(j.optString("title"))
            
            return post.title;

        }

        override fun onPostExecute(result: String) {
            levantarMain();
        }

    }

    private fun hayRed(): Boolean {
        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

}