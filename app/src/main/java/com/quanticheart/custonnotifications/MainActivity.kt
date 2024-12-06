package com.quanticheart.custonnotifications

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.quanticheart.custonnotifications.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val biding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(biding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                11
            )
        }

        biding.simple.setOnClickListener {
            debugNotification("Simple notification")
        }

        biding.custom.setOnClickListener {
            sendCustomNotificationLoading(true, 1)
        }

        biding.customSent.setOnClickListener {
            sendCustomNotificationLoading(true, 1)
        }

        biding.customReview.setOnClickListener {
            sendCustomNotificationLoading(true, 2)
        }

        biding.customCancel.setOnClickListener {
            sendCustomNotificationLoading(true, 4)
        }

        biding.customOK.setOnClickListener {
            sendCustomNotificationLoading(true, 3)
        }

        biding.hide.setOnClickListener {
            Notify(this).cancel(100)
        }

        biding.hideAll.setOnClickListener {
            Notify(this).cancelAll()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun sendCustomNotificationLoading(status: Boolean, step: Int) {

        var title = "Title"
        var progress = 0
        var color = 0
        var ic = R.drawable.await

        when (step) {
            1 -> {
                title = "Register in Progress"
                progress = 0
                color = getColor(R.color.gray)
                ic = R.drawable.await
            }

            2 -> {
                title = "Data in analysis"
                progress = 50
                color = getColor(R.color.gray)
                ic = R.drawable.await
            }

            3 -> {
                title = "Data approved"
                progress = 100
                color = getColor(R.color.green)
                ic = R.drawable.ok
            }

            4 -> {
                title = "Data rejected"
                progress = 100
                color = getColor(R.color.red)
                ic = R.drawable.cancel
            }
        }

        Notify(this)
            .setCustomLayout(R.layout.notification_custon_simple) { customLayout ->
                customLayout.setTextViewText(R.id.stepTextView, title)
                customLayout.setImageViewResource(R.id.ic, ic)
                customLayout.setInt(R.id.ic, "setColorFilter", color)

            }
            .setCustomBigLayout(R.layout.notification_custon_big) { customLayout ->
                customLayout.setTextViewText(R.id.stepTextView, title)
                customLayout.setImageViewResource(R.id.icStatus, ic)
                customLayout.setInt(R.id.icStatus, "setColorFilter", color)

                customLayout.setInt(R.id.progressBar, "setProgress", progress)
            }
            .setId(100)
            .show()
    }

    private fun remote() {
        Notify(this)
    }
}