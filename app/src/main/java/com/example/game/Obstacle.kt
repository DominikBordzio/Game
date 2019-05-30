package com.example.game

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import java.util.*

class Obstacle {

    companion object{
        const val start_margin = 0.15
        const val end_margin = 0.85
        const val size_increaase = 10F
    }

    val randomInteger = (1..12).shuffled().first()
    private val screen_width = Resources.getSystem().getDisplayMetrics().widthPixels.toFloat()
    private val screen_height = Resources.getSystem().getDisplayMetrics().heightPixels.toFloat()

    private var ox = ((screen_width*start_margin).toInt()..(screen_width*end_margin).toInt()).shuffled().first().toFloat()
    private var oy = ((0..screen_height.toInt()).shuffled().first().toFloat())


    private val radius = (30..60).shuffled().first().toFloat()
    private val paint = Paint()

    init{
        paint.color = Color.RED
    }

    fun draw(canvas: Canvas){
        canvas.drawCircle(ox, oy, radius, paint)
    }

    fun update(ball_x: Float, ball_y:Float, bradius:Float): Boolean{
        if(Math.pow((ball_x - ox).toDouble(), 2.0) + Math.pow((ball_y - oy).toDouble(),2.0) <= (radius+bradius)*(radius+bradius))return true
        else return false
    }

}