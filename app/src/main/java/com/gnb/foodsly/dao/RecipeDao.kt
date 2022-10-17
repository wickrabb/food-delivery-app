package com.gnb.foodsly.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gnb.foodsly.entities.CartItems
import com.gnb.foodsly.entities.CategoryItems
import com.gnb.foodsly.entities.MealsItems

@Dao
interface RecipeDao {

    @Query("SELECT * FROM CategoryItems ORDER BY id DESC")
    suspend fun getAllCategory() : List<CategoryItems>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(categoryItems: CategoryItems?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(mealsItems: MealsItems?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItems: CartItems?)

    @Query("DELETE FROM CartItems WHERE id = :id")
    suspend fun deleteCartItem(id: Int)

    @Query("UPDATE CartItems SET quantity = :quantity WHERE id = :id")
    suspend fun updateCartItemQuantity(id: Int, quantity: Int)



    @Query("DELETE FROM CategoryItems")
    suspend fun clearCategories()

    @Query("DELETE FROM MealItems")
    suspend fun clearMeals()

    @Query("SELECT * FROM MealItems WHERE categoryName = :categoryName ORDER BY id DESC")
    suspend fun getSpecificMealList(categoryName:String) : List<MealsItems>

    @Query("SELECT * FROM CartItems ORDER BY id DESC")
    suspend fun getCartItemsList() : List<CartItems>

    @Query("SELECT * FROM MealItems WHERE idMeal = :idMeal ORDER BY id ASC")
    suspend fun getMeal(idMeal:String?) : MealsItems
}