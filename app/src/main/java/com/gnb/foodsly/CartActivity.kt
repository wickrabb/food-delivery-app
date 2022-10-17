package com.gnb.foodsly

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.gnb.foodsly.adapter.ItemCartAdapter
import com.gnb.foodsly.database.RecipeDatabase
import com.gnb.foodsly.entities.CartItems
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.item_rv_cart.*
import java.util.ArrayList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CartActivity : BaseActivity() {

    var arrItems = ArrayList<CartItems>()
    var itemCartAdapter = ItemCartAdapter()

    var totalValue = 0f;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        itemCartAdapter.setRemoveClickListener(this.onClickedRemove)
        itemCartAdapter.setPlusClickListener(this.onClickedPlus)
        itemCartAdapter.setMinusClickListener(this.onClickedMinus)

        getDataFromDb()



    }

    private fun getDataFromDb() = runBlocking{
        val job = launch {
            this.let {
                var cat = RecipeDatabase.getDatabase(this@CartActivity).recipeDao().getCartItemsList()
                arrItems = cat as ArrayList<CartItems>

                itemCartAdapter.setData(arrItems)
                cartItems.layoutManager = LinearLayoutManager(this@CartActivity,
                    LinearLayoutManager.VERTICAL,false)
                cartItems.adapter = itemCartAdapter
            }
        }
        job.join()
        updateTotalValue()


    }

    private fun updateTotalValue(){
        totalValue = 0f
        for(item in arrItems){
            totalValue+=(item.quantity*item.price)
        }
        txTotalValue.text = String.format("%.2f €",totalValue)
    }

    private val onClickedRemove  = object : ItemCartAdapter.OnRemoveClickListener{
        override fun onClicked(position:Int,id: Int) {
            launch{
                RecipeDatabase.getDatabase(this@CartActivity).recipeDao().deleteCartItem(id)
                arrItems.removeAt(position)
                updateTotalValue()
                itemCartAdapter.setData(arrItems)
                itemCartAdapter.notifyDataSetChanged()

            }

        }
    }
    private val onClickedPlus  = object : ItemCartAdapter.OnPlusClickListener{
        override fun onClicked(position:Int,id: Int,quantity:Int) {
            launch{
                updateQuantity(position,id,quantity+1)

            }

        }
    }
    private val onClickedMinus  = object : ItemCartAdapter.OnMinusClickListener{
        override fun onClicked(position:Int,id: Int,quantity:Int) {
           updateQuantity(position,id,quantity-1)
        }
    }

    private fun updateQuantity(position:Int,id:Int,quantity:Int){
        launch{
            RecipeDatabase.getDatabase(this@CartActivity).recipeDao().updateCartItemQuantity(id,quantity)
            arrItems.get(position).quantity = quantity
            tv_dish_quantity.setText((quantity).toString())
            tv_dish_price.setText(String.format("%.2f €",arrItems[position].quantity*arrItems[position].price))
            updateTotalValue()
            itemCartAdapter.setData(arrItems)
            itemCartAdapter.notifyDataSetChanged()

        }
    }

}