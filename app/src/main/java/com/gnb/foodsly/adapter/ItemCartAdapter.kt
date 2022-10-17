package com.gnb.foodsly.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gnb.foodsly.R
import com.gnb.foodsly.entities.CartItems
import com.gnb.foodsly.entities.CategoryItems
import kotlinx.android.synthetic.main.item_rv_cart.view.*
import kotlinx.android.synthetic.main.item_rv_main_category.view.*
import kotlinx.android.synthetic.main.item_rv_main_category.view.tv_dish_name
import java.util.ArrayList

class ItemCartAdapter: RecyclerView.Adapter<ItemCartAdapter.RecipeViewHolder>() {

    var removeListener: OnRemoveClickListener? = null
    var plusListener: OnPlusClickListener? = null
    var minusListener: OnMinusClickListener? = null

    var ctx: Context? = null
    var arrItems = ArrayList<CartItems>()
    class RecipeViewHolder(view: View): RecyclerView.ViewHolder(view){

    }

    fun setData(arrData : List<CartItems>){
        arrItems = arrData as ArrayList<CartItems>
    }

    fun setRemoveClickListener(listener: OnRemoveClickListener){
        removeListener = listener
    }
    fun setPlusClickListener(listener: OnPlusClickListener){
        plusListener = listener
    }
    fun setMinusClickListener(listener: OnMinusClickListener){
        minusListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        ctx = parent.context
        return RecipeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_rv_cart,parent,false))
    }

    override fun getItemCount(): Int {
        return arrItems.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {

        holder.itemView.tv_dish_name.text = arrItems[position].dishName
        holder.itemView.tv_dish_quantity.setText(arrItems[position].quantity.toString())
        holder.itemView.tv_dish_price.text = String.format("%.2f €",arrItems[position].quantity*arrItems[position].price)
        holder.itemView.imageRemove.setOnClickListener{
            removeListener!!.onClicked(position,arrItems[position].id)
        }
        holder.itemView.image_quantity_add.setOnClickListener{
            plusListener!!.onClicked(position,arrItems[position].id,arrItems[position].quantity)

        }
        holder.itemView.image_quantity_remove.setOnClickListener{
            minusListener!!.onClicked(position,arrItems[position].id,arrItems[position].quantity)
        }

    }



    interface OnRemoveClickListener{
        fun onClicked(position:Int,id:Int)
    }
    interface OnPlusClickListener{
        fun onClicked(position:Int,id:Int,quantity:Int)
    }
    interface OnMinusClickListener{
        fun onClicked(position:Int,id:Int,quantity: Int)
    }
}