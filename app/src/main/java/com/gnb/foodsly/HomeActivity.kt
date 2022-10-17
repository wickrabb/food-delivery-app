package com.gnb.foodsly

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.gnb.foodsly.adapter.MainCategoryAdapter
import com.gnb.foodsly.adapter.SubCategoryAdapter
import com.gnb.foodsly.database.RecipeDatabase
import com.gnb.foodsly.entities.CategoryItems
import com.gnb.foodsly.entities.MealsItems
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.launch
import java.util.*

class HomeActivity : com.gnb.foodsly.BaseActivity() {
    var arrMainCategory = ArrayList<CategoryItems>()
    var arrSubCategory = ArrayList<MealsItems>()

    var mainCategoryAdapter = MainCategoryAdapter()
    var subCategoryAdapter = SubCategoryAdapter()

    val db = Firebase.firestore



    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        getDataFromDb()

        mainCategoryAdapter.setClickListener(onCLicked)
        subCategoryAdapter.setClickListener(onCLickedSubItem)

        auth = FirebaseAuth.getInstance()
        val docRef = auth.currentUser?.let { db.collection("users").document(it.uid) }
        if (docRef != null) {
            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        txWelcome.setText("Welcome, "+document.data!!.get("first_name"))
                    } else {
                        Log.d("Firestore", "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("Firestore", "get failed with ", exception)
                }
        }

        val mDrawerLayout = this.findViewById(R.id.drawerLayout) as DrawerLayout

        val mNavigationView = findViewById<View>(R.id.navigationView) as NavigationView
        mNavigationView.setNavigationItemSelectedListener { it: MenuItem ->
            when (it.itemId) {
                R.id.menuSignOut -> {
                    auth.signOut()
                    val intent = Intent(this@HomeActivity,SplashActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menuCart -> {
                    val intent = Intent(this@HomeActivity,CartActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> {
                    true
                }
            }
        }

        imageProfile.setOnClickListener{
            drawerLayout.openDrawer(GravityCompat.START)
        }


    }







    private val onCLicked  = object : MainCategoryAdapter.OnItemClickListener{
        override fun onClicked(categoryName: String) {
            getMealDataFromDb(categoryName)
        }
    }

    private val onCLickedSubItem  = object : SubCategoryAdapter.OnItemClickListener{
        override fun onClicked(id: String, price: String) {
            var intent = Intent(this@HomeActivity,DetailActivity::class.java)
            intent.putExtra("id",id)
            intent.putExtra("price",price)
            startActivity(intent)
        }
    }

    private fun getDataFromDb(){
        launch {
            this.let {
                var cat = RecipeDatabase.getDatabase(this@HomeActivity).recipeDao().getAllCategory()
                arrMainCategory = cat as ArrayList<CategoryItems>
                arrMainCategory.reverse()

                getMealDataFromDb(arrMainCategory[0].strcategory)
                mainCategoryAdapter.setData(arrMainCategory)
                rv_main_category.layoutManager = LinearLayoutManager(this@HomeActivity,LinearLayoutManager.HORIZONTAL,false)
                rv_main_category.adapter = mainCategoryAdapter
            }


        }
    }

    private fun getMealDataFromDb(categoryName:String){
        tvCategory.text = "$categoryName Category"
        launch {
            this.let {
                var cat = RecipeDatabase.getDatabase(this@HomeActivity).recipeDao().getSpecificMealList(categoryName)
                arrSubCategory = cat as ArrayList<MealsItems>
                subCategoryAdapter.setData(arrSubCategory)
                rv_sub_category.layoutManager = LinearLayoutManager(this@HomeActivity,LinearLayoutManager.VERTICAL,false)
                rv_sub_category.adapter = subCategoryAdapter
            }


        }
    }
}