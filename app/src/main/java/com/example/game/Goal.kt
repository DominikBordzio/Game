package com.example.game

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas

class Goal {

    companion object {
        const val END_MARGIN = 0.9
        const val GOAL_SIZE = 32
        const val HITBOX_MULTIPLIER = 6
    }

    val randomInteger = (1..12).shuffled().first()
    private val screen_width = Resources.getSystem().getDisplayMetrics().widthPixels.toFloat()
    private val screen_height = Resources.getSystem().getDisplayMetrics().heightPixels.toFloat()

    private var gx = (screen_width * END_MARGIN).toFloat()
    private var gy = (screen_height/2 - 2*GOAL_SIZE)


    fun draw(canvas: Canvas, bmp: Bitmap) {
        canvas.drawBitmap(bmp, gx, gy, null)
    }

    fun update(ball_x: Float, ball_y: Float):Boolean {
        if(ball_x >= gx && ball_y >= gy  && ball_y <= gy + HITBOX_MULTIPLIER*GOAL_SIZE)return true
        //if(Math.pow((ball_x - gx).toDouble(), 2.0) + Math.pow((ball_y - gy).toDouble(),2.0) <= (GOAL_SIZE+bradius)*(GOAL_SIZE+bradius))return true
        else return false
    }
}