package com.fuadhev.mytayqatask.common.utils

import android.app.Activity
import android.view.View
import com.shashank.sony.fancytoastlib.FancyToast

object Extensions {
    fun Activity.showMessage(
        message: String,
        style: Int,
    ) {
        FancyToast.makeText(this,message, FancyToast.LENGTH_SHORT,style,false).show()
    }

}