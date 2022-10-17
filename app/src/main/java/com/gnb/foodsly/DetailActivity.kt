package com.gnb.foodsly


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.gnb.foodsly.database.RecipeDatabase
import com.gnb.foodsly.entities.CartItems
import com.gnb.foodsly.entities.MealResponse
import com.gnb.foodsly.entities.MealsItems
import com.gnb.foodsly.interfaces.GetDataService
import com.gnb.foodsly.retofitclient.RetrofitClientInstance
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Float.parseFloat


class DetailActivity : com.gnb.foodsly.BaseActivity() {

    var id = ""
    var price = ""
    var dishName = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        id = intent.getStringExtra("id").toString()
        price = intent.getStringExtra("price").toString()
        getSpecificItem(id!!)

        imgToolbarBtnBack.setOnClickListener {
            finish()
        }

        btnOrder.setOnClickListener {
            launch {
                var cartItemModel = CartItems(
                    0,
                    id,
                    dishName,
                    1,
                    parseFloat(price)
                )
                RecipeDatabase.getDatabase(this@DetailActivity)
                    .recipeDao().insertCartItem(cartItemModel)
            }
        }

    }

    fun getSpecificItem(id:String) {
        val service = RetrofitClientInstance.retrofitInstance!!.create(GetDataService::class.java)
        val call = service.getSpecificItem(id)
        call.enqueue(object : Callback<MealResponse> {
            override fun onFailure(call: Call<MealResponse>, t: Throwable) {

                Toast.makeText(this@DetailActivity, "Something went wrong", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onResponse(
                call: Call<MealResponse>,
                response: Response<MealResponse>
            ) {

                Glide.with(this@DetailActivity).load(response.body()!!.mealsEntity[0].strmealthumb).into(imgItem)

                dishName = response.body()!!.mealsEntity[0].strmeal
                tvCategory.text = dishName

                var ingredient = "${response.body()!!.mealsEntity[0].stringredient1}, " +
                        "${response.body()!!.mealsEntity[0].stringredient2} " +
                        "${response.body()!!.mealsEntity[0].stringredient3} " +
                        "${response.body()!!.mealsEntity[0].stringredient4} " +
                        "${response.body()!!.mealsEntity[0].stringredient5} " +
                        "${response.body()!!.mealsEntity[0].stringredient6} " +
                        "${response.body()!!.mealsEntity[0].stringredient7} " +
                        "${response.body()!!.mealsEntity[0].stringredient8} " +
                        "${response.body()!!.mealsEntity[0].stringredient9} " +
                        "${response.body()!!.mealsEntity[0].stringredient10} " +
                        "${response.body()!!.mealsEntity[0].stringredient11} " +
                        "${response.body()!!.mealsEntity[0].stringredient12} " +
                        "${response.body()!!.mealsEntity[0].stringredient13} " +
                        "${response.body()!!.mealsEntity[0].stringredient14} " +
                        "${response.body()!!.mealsEntity[0].stringredient15} " +
                        "${response.body()!!.mealsEntity[0].stringredient16} " +
                        "${response.body()!!.mealsEntity[0].stringredient17} " +
                        "${response.body()!!.mealsEntity[0].stringredient18} " +
                        "${response.body()!!.mealsEntity[0].stringredient19} " +
                        "${response.body()!!.mealsEntity[0].stringredient20}  "

                tvIngredients.text = ingredient.trim().replace(" ",", ")

                launch{
                    this.let{
                        btnOrder.text = "ORDER FOR "+price
                    }
                }
            }

        })

    }


}