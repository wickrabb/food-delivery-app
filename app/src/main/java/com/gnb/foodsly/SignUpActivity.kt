package com.gnb.foodsly

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.*


class SignUpActivity : AppCompatActivity() {
    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            result.data
            result.data?.let {
                val place = Autocomplete.getPlaceFromIntent(result.data!!)
                Log.d("Address",place.address.toString()+" "+place.name)
                etAddress.setText(place.name.toString())
            }
        }
    }
    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        Places.initialize(applicationContext, BuildConfig.MAPS_API_KEY)
        // Create a new PlacesClient instance
        val placesClient = Places.createClient(this)

        auth = FirebaseAuth.getInstance()

        val countries = ArrayList<String>();
        for(loc in Locale.getAvailableLocales()){
            val country = loc.getDisplayCountry()
            if(country.length > 0 && !countries.contains(country)){
                countries.add(country)
            }
        }

        Collections.sort(countries,String.CASE_INSENSITIVE_ORDER)
        val adapter = ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,countries)
        etCountry.setAdapter(adapter)
        val map: MutableMap<String, Locale> = HashMap()
        for (locale in Locale.getAvailableLocales()) {
            map[locale.displayCountry] = locale
        }

        etAddress.setOnClickListener{


            // Set the fields to specify which types of place data to
            // return after the user has made a selection.
            val fields = listOf(Place.Field.ID, Place.Field.NAME)

            // Start the autocomplete intent.
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).setTypeFilter(TypeFilter.ADDRESS).setCountry(
                map.get(etCountry.text.toString())?.country
            )
                .build(this)
            startForResult.launch(intent)
        }

        btnSignUp.setOnClickListener{
            auth.createUserWithEmailAndPassword(etEmail.text.toString(), etPassword.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Auth", "createUserWithEmail:success")
                        val user = auth.currentUser
                        Toast.makeText(baseContext, "Sign Up Successful.",
                            Toast.LENGTH_SHORT).show()

                        val userData = hashMapOf(
                            "first_name" to etFirstName.text.toString(),
                            "last_name" to etLastName.text.toString(),
                            "country" to etCountry.text.toString(),
                            "address" to etAddress.text.toString()
                        )

// Add a new document with a generated ID
                        if (user != null) {
                            db.collection("users").document(user.uid)
                                .set(userData)
                                .addOnSuccessListener { documentReference ->
                                    Log.d("Firestore", "DocumentSnapshot added with ID:" + user.uid)
                                }
                                .addOnFailureListener { e ->
                                    Log.w("Firestore", "Error adding document", e)
                                }
                        }

                        var intent = Intent(this@SignUpActivity, SplashActivity::class.java)
                        startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Auth", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Sign Up failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun onSignUp(){
        if(etEmail.text.toString()== ""){
        }
    }



    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            var intent = Intent(this@SignUpActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}