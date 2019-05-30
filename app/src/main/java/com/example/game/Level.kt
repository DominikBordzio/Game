package com.example.game

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface

class Level {
    companion object{
        const val NORMAL_SIZE = 40F
        const val UP_SIZE = 70F
        const val EPIC_SIZE = 100F
        const val UP_LEVEL = 5
        const val EPIC_LEVEL = 10
        const val NORMAL_FONT  ="Arial"
        const val UP_FONT = "Bahnschrift"
        const val EPIC_FONT = "Comic Sans"
        const val MARGIN_MULTIPLIER = 2
    }
    val screen_width = Resources.getSystem().getDisplayMetrics().widthPixels.toFloat()
    val screen_height = Resources.getSystem().getDisplayMetrics().heightPixels.toFloat()
    private var lvl = 1
    private var highest = 1
    private val lvl_paint = Paint()
    private var lvl_placement = NORMAL_SIZE
    private var high_top = screen_height
    private var high_right = screen_width
    private val high_paint = Paint()
    init{
        high_paint.color = Color.GRAY
        high_paint.typeface = Typeface.create(UP_FONT,Typeface.ITALIC)
        high_paint.textSize = UP_SIZE
        high_paint.textAlign = Paint.Align.RIGHT
    }
    private fun setText():String{
        if(lvl<EPIC_LEVEL)return "Level $lvl"
        else return "Level " + "Adam Małysz"
    }

    private fun setHigh():String{
        return "Highest $highest"
    }

    private fun setLevelLooks(){
        if(lvl >= EPIC_LEVEL) {
            lvl_paint.color = Color.GREEN
            lvl_paint.textSize = EPIC_SIZE
            lvl_paint.typeface = Typeface.create(EPIC_FONT,Typeface.BOLD_ITALIC)
            lvl_placement = EPIC_SIZE
        }else if(lvl >= UP_LEVEL) {
            lvl_paint.color = Color.BLUE
            lvl_paint.textSize = UP_SIZE
            lvl_paint.typeface = Typeface.create(UP_FONT,Typeface.ITALIC)
            lvl_placement = UP_SIZE
        }
        else{
            lvl_paint.color = Color.WHITE
            lvl_paint.textSize = NORMAL_SIZE
            lvl_paint.typeface = Typeface.create(NORMAL_FONT, Typeface.ITALIC)
            lvl_placement = NORMAL_SIZE
        }
    }

    private fun setHighLevelLooks(){

    }

    public fun draw(canvas: Canvas){
        setLevelLooks()
        canvas.drawText(setText(), 0F, lvl_placement, lvl_paint)
        canvas.drawText(setHigh(), high_right - MARGIN_MULTIPLIER*UP_SIZE, UP_SIZE, high_paint)
    }

    public fun update(lvl_up: Boolean){
        if(lvl_up)lvl++
        else lvl = 1
        updateHighest()
    }

    public fun updateHighest(){
        if(lvl > highest)highest = lvl
    }
}