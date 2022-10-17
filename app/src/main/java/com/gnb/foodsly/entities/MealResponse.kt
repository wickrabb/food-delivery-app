package com.gnb.foodsly.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MealResponse(
    @Expose
    @SerializedName("meals")
    var mealsEntity: List<MealsEntity>
)

data class MealsEntity(
    @Expose
    @SerializedName("idMeal")
    val idmeal: String,
    @Expose
    @SerializedName("strMeal")
    val strmeal: String,
    @Expose
    @SerializedName("strCategory")
    val strcategory: String,
    @Expose
    @SerializedName("strArea")
    val strarea: String,
    @Expose
    @SerializedName("strInstructions")
    val strinstructions: String,
    @Expose
    @SerializedName("strMealThumb")
    val strmealthumb: String,
    @Expose
    @SerializedName("strTags")
    val strtags: String,
    @Expose
    @SerializedName("strYoutube")
    val stryoutube: String,
    @Expose
    @SerializedName("strIngredient1")
    val stringredient1: String,
    @Expose
    @SerializedName("strIngredient2")
    val stringredient2: String,
    @Expose
    @SerializedName("strIngredient3")
    val stringredient3: String,
    @Expose
    @SerializedName("strIngredient4")
    val stringredient4: String,
    @Expose
    @SerializedName("strIngredient5")
    val stringredient5: String,
    @Expose
    @SerializedName("strIngredient6")
    val stringredient6: String,
    @Expose
    @SerializedName("strIngredient7")
    val stringredient7: String,
    @Expose
    @SerializedName("strIngredient8")
    val stringredient8: String,
    @Expose
    @SerializedName("strIngredient9")
    val stringredient9: String,
    @Expose
    @SerializedName("strIngredient10")
    val stringredient10: String,
    @Expose
    @SerializedName("strIngredient11")
    val stringredient11: String,
    @Expose
    @SerializedName("strIngredient12")
    val stringredient12: String,
    @Expose
    @SerializedName("strIngredient13")
    val stringredient13: String,
    @Expose
    @SerializedName("strIngredient14")
    val stringredient14: String,
    @Expose
    @SerializedName("strIngredient15")
    val stringredient15: String,
    @Expose
    @SerializedName("strIngredient16")
    val stringredient16: String,
    @Expose
    @SerializedName("strIngredient17")
    val stringredient17: String,
    @Expose
    @SerializedName("strIngredient18")
    val stringredient18: String,
    @Expose
    @SerializedName("strIngredient19")
    val stringredient19: String,
    @Expose
    @SerializedName("strIngredient20")
    val stringredient20: String,
    @Expose
    @SerializedName("strSource")
    val strsource: String
)