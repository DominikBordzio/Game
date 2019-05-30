package com.example.game

import android.graphics.Canvas
import android.view.SurfaceHolder
import android.R.attr.bottom
import android.R.attr.right
import android.R.attr.top
import android.R.attr.left
import android.R
import android.graphics.drawable.Drawable



class GameThread (surfaceHolder: SurfaceHolder, panel : GameView) : Thread() {
    private var surface_holder : SurfaceHolder?= null
    private var game_view : GameView ?= null
    private var running = false

    init {
        this.surface_holder = surfaceHolder
        this.game_view = panel
    }

    fun setRunning(run : Boolean){
        this.running = run
    }

    override fun run() {
        var canvas: Canvas?
        //setRunning(true)
        while (running){
            canvas = null
            try {
                canvas = surface_holder!!.lockCanvas(null)
                game_view!!.draw(canvas)
            }finally {
                if (canvas!= null){
                    surface_holder!!.unlockCanvasAndPost(canvas)
                }
            }
        }
    }

}