package com.history.historyofkyrgyzstan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.GravityCompat
import androidx.navigation.ui.onNavDestinationSelected
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.history.historyofkyrgyzstan.databinding.ActivityMainBinding
import com.history.historyofkyrgyzstan.databinding.ContentMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerProfileFullname:TextView
    private lateinit var drawerProfileEmail:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        // Now you can use menuItem for further actions
        // For example, setting an onClickListener

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_test,R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.alertDialog
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)

        navView.setupWithNavController(navController)


        firebaseAuth = FirebaseAuth.getInstance()//nmmnbnmn
        MyDb.g_firestore= FirebaseFirestore.getInstance()

        MyDb.getUserData(object : MyCompleteListener {
            override fun OnSucces() {
                // Access my_account properties and update UI
                drawerProfileFullname = navView.getHeaderView(0).findViewById(R.id.header_fullname)
                drawerProfileEmail = navView.getHeaderView(0).findViewById(R.id.header_email)
                drawerProfileFullname.text = MyDb.my_account.surname + " " + MyDb.my_account.name
                drawerProfileEmail.text = MyDb.my_account.email
            }

            override fun OnFailure() {
                // Handle failure if necessary
                Log.e("MainActivity", "Failed to load user data")
            }
        })



    }

    private fun logoutUser() {
        firebaseAuth.signOut()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)

        // Get the switch menu item
        val switchItem = menu.findItem(R.id.action_dark_theme)
        // Get the switch
        val switch = switchItem.actionView as SwitchCompat

        // Set the switch state based on the current night mode
        switch.isChecked = when (AppCompatDelegate.getDefaultNightMode()) {
            AppCompatDelegate.MODE_NIGHT_YES -> true
            else -> false
        }

        // Set the switch listener
        switch.setOnCheckedChangeListener { _, isChecked ->
            // Implement logic to switch theme based on isChecked
            val newNightMode = if (isChecked) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }

            // Set the new night mode
            AppCompatDelegate.setDefaultNightMode(newNightMode)

            // Recreate the activity to apply the new theme
            recreate()
        }

        return true
    }



    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}