package com.gnb.foodsly.entities.converter

import androidx.room.TypeConverter
import com.gnb.foodsly.entities.CartItems
import com.gnb.foodsly.entities.CategoryItems
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CartItemListConverter {
    @TypeConverter
    fun fromCartItemList(cartItem: List<CartItems>):String?{
        if (cartItem == null){
            return (null)
        }else{
            val gson = Gson()
            val type = object : TypeToken<CartItems>(){

            }.type
            return gson.toJson(cartItem,type)
        }
    }

    @TypeConverter
    fun toCartItemList ( cartItemString: String):List<CartItems>?{
        if (cartItemString == null){
            return (null)
        }else{
            val gson = Gson()
            val type = object : TypeToken<CartItems>(){

            }.type
            return  gson.fromJson(cartItemString,type)
        }
    }
}