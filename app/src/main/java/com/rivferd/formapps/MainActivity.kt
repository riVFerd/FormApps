package com.rivferd.formapps

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.rivferd.formapps.databinding.ActivityMainBinding
import com.rivferd.formapps.util.ParticipantModel
import java.io.ByteArrayOutputStream


class MainActivity : AppCompatActivity() {

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 111
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var participantModel: ParticipantModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var geocoder: Geocoder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpActivity()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.im_list_participant) {
            startActivity(Intent(this, ListParticipantActivity::class.java))
        } else if (item.itemId == R.id.im_reset) {
            participantModel.reset()
        }
        return true
    }

    private fun setUpActivity() {
        geocoder = Geocoder(this)
        participantModel = ParticipantModel(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Get location related setting
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)

                val longitude = locationResult.lastLocation!!.longitude
                val latitude = locationResult.lastLocation!!.latitude
                binding.tvLocationResult.text =
                    geocoder.getFromLocation(latitude, longitude, 1)[0].getAddressLine(0)
                Log.d("get location", "location updated")
            }
        }

        binding.btnGetLocation.setOnClickListener {
            // Set location request
            val locationRequest = LocationRequest.create().apply {
                interval = 5000
                fastestInterval = 2000
                priority = Priority.PRIORITY_HIGH_ACCURACY
            }

            // Handle location permission
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
                return@setOnClickListener
            }

            // Get latest location
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                null
            )
        }

        // On image selected listener
        val getImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
            if (it == null) return@registerForActivityResult
            binding.imgImage.apply {
                if (this.visibility == View.GONE) this.visibility = View.VISIBLE
                this.setImageURI(it)
            }
        }

        // Start to select Image
        binding.btnUploadPhoto.setOnClickListener{
            getImage.launch("image/*")
        }

        binding.btnSubmit.setOnClickListener {
            validateThenSubmit()
        }
    }

    private fun validateThenSubmit() {
        val name = binding.edtName.text.toString()
        val phoneNumber = binding.edtPhoneNumber.text.toString()
        val gender = if (binding.radioMan.isChecked) "Laki-laki" else "Perempuan"
        val location = binding.tvLocationResult.text.toString()
        var image: ByteArray

        // simple validation
        if (name.isBlank() || phoneNumber.isBlank() || gender.isBlank() || location.isBlank() || binding.imgImage.visibility == View.GONE) {
            Toast.makeText(this, "Masih ada data yang kosong", Toast.LENGTH_SHORT).show()
            return
        }

        with((binding.imgImage.drawable as BitmapDrawable).bitmap) {
            val outputStream = ByteArrayOutputStream()
            this.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            image = outputStream.toByteArray()
        }

        // Submitting
        participantModel.insert(name, phoneNumber, gender, location, image)

        // Clear form
        binding.edtName.setText("")
        binding.edtPhoneNumber.setText("")
        binding.radioMan.isChecked = true
        binding.tvLocationResult.text = ""
        fusedLocationClient.removeLocationUpdates(locationCallback)
        binding.imgImage.visibility = View.GONE

        Toast.makeText(this, "Pendaftaraan Berhasil", Toast.LENGTH_SHORT).show()
    }
}