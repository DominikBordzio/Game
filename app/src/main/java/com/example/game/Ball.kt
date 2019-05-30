package com.example.game

import android.content.res.Resources
import android.graphics.*

class Ball {
    companion object{
        val PROXIMITY_DEACCELERATION = 0.5F
        val BOUNCE_MULTIPLIER = 0.3F
    }

    val screen_width = Resources.getSystem().getDisplayMetrics().widthPixels.toFloat()
    val screen_height = Resources.getSystem().getDisplayMetrics().heightPixels.toFloat()
    private var last_x = 0F
    private var last_y = 0F
    private val radius = 30.0F

    private var ball_x = radius
    private var ball_y = screen_height/2

    private var collision_x = false
    private var collision_y = false

    private val paint = Paint()

    init{
        paint.color = Color.YELLOW
    }

    fun draw(canvas: Canvas){
        canvas.drawCircle(ball_x, ball_y, radius, paint)
    }

    public fun getX():Float{
        return ball_x
    }

    public fun getY():Float{
        return ball_y
    }

    public fun getRadius():Float{
        return radius
    }

    public fun reverse(){
        last_x = -last_x * PROXIMITY_DEACCELERATION
        last_y = -last_y * PROXIMITY_DEACCELERATION
    }

    fun update(x:Float, y:Float, window_width:Int ,window_height:Int){
        last_x += x
        last_y += y

        ball_x += last_x
        ball_y += last_y

        if (ball_x > (window_width - radius)) {
            ball_x = (window_width - radius)
            last_x = -last_x * BOUNCE_MULTIPLIER
            if (collision_x) {
                collision_x = false
            }
        } else if (ball_x < radius) {
            ball_x = radius
            last_x = -last_x * BOUNCE_MULTIPLIER
            if (collision_x) {
                collision_x = false
            }
        } else {
            collision_x = true
        }

        if (ball_y > (window_height - radius)) {
            ball_y = (window_height - radius)
            last_y = -last_y * BOUNCE_MULTIPLIER
            if (collision_y) {
                collision_y = false
            }
        } else if (ball_y < radius) {
            ball_y = radius
            last_y = -last_y * BOUNCE_MULTIPLIER
            if (collision_y) {
                collision_y = false
            }
        } else {
            collision_y = true
        }

        //invalidate()
    }

    public fun resetBall(){
        ball_x = radius
        ball_y = screen_height/2
        last_x = 0F
        last_y = 0F
    }

}