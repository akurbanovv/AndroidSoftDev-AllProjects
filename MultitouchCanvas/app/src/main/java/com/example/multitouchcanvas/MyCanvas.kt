package com.example.multitouchcanvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MyCanvas @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    var currentPaths = ArrayList<Path>()
    var allPaths = HashMap<Path, Paint>()
    var pathPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    var detector: GestureDetectorCompat




    init {
        // pathPaint.color = Color.argb(255, rand.nextInt(255), rand.nextInt(255), rand.nextInt(255))
        randomColor()
        pathPaint.style = Paint.Style.STROKE
        pathPaint.strokeWidth = 25f
        detector = GestureDetectorCompat(context,SimpleOnGestureListener())
    }

    fun randomColor() {
        val rand = Random()
        pathPaint.color = Color.argb(255, rand.nextInt(255), rand.nextInt(255), rand.nextInt(255))
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        for (path in allPaths) {
            canvas?.drawPath(path.key, pathPaint)
        }
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        detector.onTouchEvent(event)
        when (event?.actionMasked) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                val x = event.getX(event.actionIndex)
                val y = event.getY(event.actionIndex)
                val path = Path()
                path.moveTo(x, y)
                currentPaths.add(path)
                allPaths.put(path, pathPaint)
            }

            MotionEvent.ACTION_MOVE -> {
                for (i in 0 until currentPaths.size){
                    currentPaths.get(i).lineTo(event.getX(i), event.getY(i))
                }
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP, MotionEvent.ACTION_CANCEL -> {
                // randomColor()
                currentPaths.clear()
            }
        }
        invalidate()
        return true
    }


    inner class SimpleOnGestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onLongPress(e: MotionEvent?) {
            super.onLongPress(e)
            allPaths.clear()
            invalidate()
        }

        override fun onDoubleTap(e: MotionEvent?): Boolean {
            pathPaint = Paint(Paint.ANTI_ALIAS_FLAG)
            pathPaint.color = Color.argb(255, kotlin.random.Random.nextInt(255),
                kotlin.random.Random.nextInt(255),
                kotlin.random.Random.nextInt(255))
            pathPaint.style = Paint.Style.STROKE
            pathPaint.strokeWidth = 25f
            return super.onDoubleTap(e)
        }
    }





}
