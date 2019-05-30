package com.example.game

import android.content.Context
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.RectF
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.R.attr.start



class GameView(context: Context?) : SurfaceView(context), SurfaceHolder.Callback {

    companion object{
        const val STARTING_OBSTACLES = 10
        const val OBSTACLES_ADDITION = 2
    }
    private var obstacle_number = STARTING_OBSTACLES
    private val screen_width = Resources.getSystem().getDisplayMetrics().widthPixels.toFloat()
    private val screen_height = Resources.getSystem().getDisplayMetrics().heightPixels.toFloat()
    private var resetting = false
    private var window_width: Int = 0
    private var window_height: Int = 0

    private val ball = Ball()
    private var ball_thread: GameThread? = null
    private var obstacles = ArrayList<Obstacle>()
    private val goal = Goal()
    private val background_bmp = BitmapFactory.decodeResource(resources, R.drawable.isaac)
    private val goal_bmp = BitmapFactory.decodeResource(resources, R.drawable.adam)
    private val level = Level()

    init {
        holder.addCallback(this)
        ball_thread = GameThread(holder, this)
        window_width = screen_width.toInt()
        window_height = screen_height.toInt()
        initialiseObstacles()
    }


    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        ball_thread!!.setRunning(true)
        ball_thread!!.start()
    }

    override fun draw(canvas: Canvas?) {
        if (canvas != null && !resetting) {
            super.draw(canvas)
            canvas.drawBitmap(background_bmp, null, RectF(0F, 0F, screen_width, screen_height), null)
            ball.draw(canvas)
                if (resetting) return
                for (obstacle in obstacles) {
                    if (resetting) return
                    obstacle.draw(canvas)
                }
            goal.draw(canvas, goal_bmp)
            level.draw(canvas)
        }
    }

    fun update_obstacles(success: Boolean){
        if(success)obstacle_number += OBSTACLES_ADDITION
        else obstacle_number = STARTING_OBSTACLES
    }


    fun updateProximity(){
        ball.reverse()
    }

    fun updateGameView(x: Float, y: Float) {
        if(!resetting) {
            ball.update(x, y, window_width, window_height)
            if (checkCollisions()) reset()
            if (goal.update(ball.getX(), ball.getY())) nextLevel()
            //invalidate()
        }
    }

    fun checkCollisions():Boolean{
        if(!obstacles.isEmpty()) {
            for (obstacle in obstacles) {
                if (obstacle.update(ball.getX(), ball.getY(), ball.getRadius())) {
                    return true
                }
            }
        }
        return false
    }

    fun reset(){
        resetting = true
        ball_thread = GameThread(holder, this)
        surfaceCreated(holder)
        ball.resetBall()
        obstacle_number = STARTING_OBSTACLES
        obstacles.clear()
        initialiseObstacles()
        level.update(false)
        resetting = false
    }

    fun nextLevel(){
        resetting = true
        level.update(true)
        obstacle_number += OBSTACLES_ADDITION
        for (i in (1..OBSTACLES_ADDITION)){
            obstacles.add(Obstacle())
        }
        ball.resetBall()
        resetting = false
    }

    fun initialiseObstacles(){
        obstacles.clear()
        resetting = true
        for(i in (1..obstacle_number)){
            obstacles.add(Obstacle())
        }
        resetting = false
    }

    fun pauseGame() {
        ball_thread!!.setRunning(false)
    }

    fun resumeGame() {
        ball_thread = GameThread(holder, this)
        ball_thread!!.start()
    }

}