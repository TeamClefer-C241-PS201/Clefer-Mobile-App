package com.jimbonlemu.clefer.utils

import android.content.Context
import com.jimbonlemu.clefer.R
import com.shashank.sony.fancytoastlib.FancyToast

object CleferToast {
    fun successToast(msg:String,context:Context){
        FancyToast.makeText(context,msg,FancyToast.LENGTH_LONG,FancyToast.SUCCESS, R.drawable.icon_clefer,false).show()
    }

    fun errorToast(msg:String,context:Context){
        FancyToast.makeText(context,msg,FancyToast.LENGTH_LONG,FancyToast.ERROR, R.drawable.icon_clefer,false).show()
    }

    fun informToast(msg: String,context:Context){
        FancyToast.makeText(context,msg,FancyToast.LENGTH_LONG,FancyToast.INFO, R.drawable.icon_clefer,false).show()
    }
}