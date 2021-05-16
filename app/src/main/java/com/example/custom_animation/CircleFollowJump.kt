package com.example.custom_animation

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.CollapsibleActionView
import android.view.ContextMenu
import android.view.View

class CircleFollowJump:View {
    //圆的半径
    private var raduis = 0f
    //圆在y轴上的位置
    private var cy = 0f
    //圆的x的位置
    private val cxArray = mutableListOf<Float>()
    //存圆的半径
    private val raduisArray = arrayOf(0f,0f,0f)
    //存入动画
    private val valueAnimatorArray = mutableListOf<ValueAnimator>()
    //准备画笔
    private val paint_circle:Paint by lazy {
        Paint().apply {
            style = Paint.Style.FILL
            color = Color.RED
        }
    }
    constructor(context: Context):super(context){}
    constructor(context: Context,attributeSet: AttributeSet):super(context,attributeSet){}

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        sure_centerAndradius()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //画三个圆
       draw3circle(canvas)
    }
    //确定中心点，以及圆的最大半径
    private fun sure_centerAndradius(){
        //临时判断值，
        val temp = measuredHeight/2f
        if (7*temp>measuredWidth){
            //圆的半径大了，
            raduis = measuredWidth/7f
        }else{
            //圆的半径刚刚好
            raduis = temp
        }
        //在x轴上圆离两边的距离
        val xDistance = (measuredWidth-7*raduis)/2
        //在y轴上圆离两边的距离
        val yDistance = (measuredHeight-2*raduis)/2
        //圆中心点y
        cy = yDistance+raduis
        //将半径存入数组中
        //各圆中心点x
        for (i in 0..2){
            raduisArray[i] = raduis
            val cx = (xDistance+raduis+i*2.5*raduis).toFloat()
            cxArray.add(cx)
        }
    }
    //画三个圆
    private fun draw3circle(canvas: Canvas?){
        for (i in 0..2){
            canvas?.drawCircle(cxArray[i],cy,raduisArray[i],paint_circle)
        }
    }
    //动画因子的改变
   private  fun changeRadius(){
        Log.v("lfl","在改变因子方法中")
        for (i in 0..2){
            ValueAnimator.ofFloat(1f,0.1f,1f).apply {
                duration = 1000
                repeatCount = ValueAnimator.INFINITE
                startDelay = i*155L
                addUpdateListener {
                    val value = it.animatedValue as Float
                    //各圆半径为
                    raduisArray[i] = raduis*value
                    invalidate()
                }
                valueAnimatorArray.add(this)
            }
        }
    }
    //启动动画
    fun startAnima(){
        changeRadius()
        for (i in 0..2){
            valueAnimatorArray[i].start()
        }
    }

}


