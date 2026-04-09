package com.example.tp3.UI

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tp3.databinding.ActivityHomeBinding
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.security.ProviderInstaller

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private var providerReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        installSecurityProvider()

        binding.btnVoirMeteo.setOnClickListener {
            if (providerReady) {
                startActivity(Intent(this, WeatherActivity::class.java))
            } else {
                Toast.makeText(
                    this,
                    "Le module de sécurité n'est pas encore prêt. Réessaie dans un instant.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun installSecurityProvider() {
        try {
            ProviderInstaller.installIfNeeded(this)
            providerReady = true
        } catch (e: GooglePlayServicesRepairableException) {
            GoogleApiAvailability.getInstance()
                .showErrorNotification(this, e.connectionStatusCode)

            Toast.makeText(
                this,
                "Google Play Services doit être mis à jour pour sécuriser la connexion.",
                Toast.LENGTH_LONG
            ).show()
        } catch (e: GooglePlayServicesNotAvailableException) {
            Toast.makeText(
                this,
                "Impossible d'installer le provider de sécurité sur cet appareil.",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}