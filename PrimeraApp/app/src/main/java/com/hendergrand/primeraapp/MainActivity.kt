package com.hendergrand.primeraapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

class MainActivity : AppCompatActivity() {

    private val list = mutableListOf<CarouselItem>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnGetstarted = findViewById<Button>(R.id.btnGetstarted)
        btnGetstarted.setOnClickListener {
            val intent = Intent(this, InicioSesionActivity::class.java)
            startActivity(intent)
        }
        val carousel: ImageCarousel = findViewById(R.id.ic_Carousel)
        list.add(CarouselItem(imageDrawable = R.drawable.connten_image1))
        list.add(CarouselItem(imageDrawable = R.drawable.conntent_image))
        carousel.addData(list)

        }
    }
