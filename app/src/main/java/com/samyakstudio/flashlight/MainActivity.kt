package com.samyakstudio.flashlight


import android.content.Context

import android.content.pm.PackageManager

import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Build
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.samyakstudio.flashlight.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var toggleButton: ImageButton
    private lateinit var binding: ActivityMainBinding
    private var flashOn: Boolean = false

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toggleButton = binding.toggleButton

        // Check if the device has a camera flash feature
        val hasCameraFlash = packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)

        toggleButton.setOnClickListener {
            if (hasCameraFlash) {
                // If flashOn is true, turn off the flashlight; otherwise, turn it on
                if (flashOn) {
                    flashOn = false
                    toggleButton.setImageResource(R.drawable.ic_off)
                    try {
                        flashLightOff() // Turn off flashlight function
                    } catch (e: CameraAccessException) {
                        e.printStackTrace()
                    }
                } else {
                    flashOn = true
                    toggleButton.setImageResource(R.drawable.ic_on)
                    try {
                        flashLightOn() // Turn on flashlight function
                    } catch (e: CameraAccessException) {
                        e.printStackTrace()
                    }
                }
            } else {
                // Inform the user that there is no flash available on their device
                Toast.makeText(this@MainActivity, "No flash available on your device", Toast.LENGTH_LONG).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun flashLightOn() {
        // Access the CameraManager to control the camera flashlight
        val cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val cameraId = cameraManager.cameraIdList[0] // Get the first camera (assuming it has a flash)
        cameraManager.setTorchMode(cameraId, true) // Turn on the flashlight
        Toast.makeText(this@MainActivity, "FlashLight is ON", Toast.LENGTH_SHORT).show()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun flashLightOff() {
        // Access the CameraManager to control the camera flashlight
        val cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val cameraId = cameraManager.cameraIdList[0] // Get the first camera (assuming it has a flash)
        cameraManager.setTorchMode(cameraId, false) // Turn off the flashlight
        Toast.makeText(this@MainActivity, "FlashLight is OFF", Toast.LENGTH_SHORT).show()
    }
}
