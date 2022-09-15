package com.example.memeshare

import android.app.DownloadManager
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource

import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.memeshare.singleston.MySingleton.Companion.getInstance
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var currentImageUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadmeme()


    }

    private fun loadmeme(){
        pbar.visibility=View.VISIBLE


        val url = "https://meme-api.herokuapp.com/gimme"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener{ response ->
                currentImageUrl = response.getString("url")
                Glide.with(this).load(currentImageUrl).listener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        pbar.visibility=View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        pbar.visibility=View.GONE
                        return false
                    }
                }).into(image)



//                Toast.makeText(this,"worksssssssssssssss",Toast.LENGTH_LONG).show()

            },
            Response.ErrorListener {
//                Toast.makeText(this,"errorrrrrrrrr",Toast.LENGTH_LONG).show()

            })

// Add the request to the RequestQueue.
        singleston.MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)


    }
    fun sharemene(view:View){
        val intent=Intent(Intent.ACTION_SEND)
        intent.type="text/plain/image"
        intent.putExtra(Intent.EXTRA_TEXT,"hey checkout the app i made $currentImageUrl")
        val chooser=Intent.createChooser(intent,"sharee this meme")
        startActivity(chooser)




//
//        Toast.makeText(this,"tada",Toast.LENGTH_LONG).show()



    }
    fun nextmeme(view:View){
        loadmeme()
//        Toast.makeText(this,"next meme",Toast.LENGTH_LONG).show()

    }
}