package com.gnb.foodsly.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "CartItems")
data class CartItems(
    @PrimaryKey(autoGenerate = true)
    var id:Int,

    @ColumnInfo(name = "dishid")
    @Expose
    @SerializedName("dishId")
    val dishId: String,

    @ColumnInfo(name = "dishname")
    @Expose
    @SerializedName("dishName")
    val dishName: String,

    @ColumnInfo(name = "quantity")
    @Expose
    @SerializedName("Quantity")
    var quantity: Int,

    @ColumnInfo(name = "price")
    @Expose
    @SerializedName("price")
    val price: Float
)