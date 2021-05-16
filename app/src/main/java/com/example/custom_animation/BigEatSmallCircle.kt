package com.example.custom_animation

import android.animation.ValueAnimator
import android.animation.ValueAnimator.INFINITE
import android.animation.ValueAnimator.ofFloat
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.graphics.toRectF

class BigEatSmallCircle:View {
    //小圆的半径
    private var smallArcRadius = 0f
    //大圆的动画因子
    private var sweepAngle = 0f
    private var startAngle = 0f
    //小圆的x,y坐标
    private var smallCircleX = 0f
    private var smallCircleY = 0f
    //小圆x的最大距离
    private var smallCircleBigX = 0f
    //大圆的动画
    private var valueAnimatorBigArc = ValueAnimator()
    //小圆的动画
    private var valueAnimatorSmallCircle = ValueAnimator()
    //画大圆的paint
    private val  paintBigCircle: Paint by lazy {
        Paint().apply {
            style = Paint.Style.FILL
            color = Color.RED
        }
    }
    constructor(context: Context):super(context){}
    constructor(context: Context,attributeSet: AttributeSet):super(context,attributeSet){}

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        Log.v("lfl","在OnSizeChanged")
         ArcRect()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
       //画大圆
        canvas?.drawArc(ArcRect(),startAngle,sweepAngle,true,paintBigCircle)
       //画小圆
        canvas?.drawCircle(smallCircleX,smallCircleY,smallArcRadius,paintBigCircle)
    }
    //确定圆弧的矩形区域 并且确定小圆的x,y坐标
    private fun ArcRect():RectF{
        //距离画布左右边缘的距离为
        var xDistance = 0f
        //距离画布上下边缘的距离
        var yDistance = 0f
        //当画布的高度小于宽度时
        if (measuredHeight<measuredWidth){
            smallArcRadius = measuredHeight/6f
           xDistance = (measuredWidth-8.5f*smallArcRadius)/2
        }else{
            smallArcRadius = measuredWidth/8.5f
            yDistance = (measuredHeight-6*smallArcRadius)/2
        }
        //确定小圆的x，y坐标
        smallCircleBigX = xDistance+7.5f*smallArcRadius
        smallCircleY = yDistance+3*smallArcRadius
        //返回大圆的矩形区域
        return RectF(xDistance,yDistance,
                (xDistance+6*smallArcRadius).toFloat(),
                (yDistance+6*smallArcRadius).toFloat())
    }
    //大圆的动画因子的改变
   private  fun changBigArcAngle(){
      valueAnimatorBigArc =  ValueAnimator.ofFloat(0f,45f).apply {
            duration = 1000L
            repeatCount = INFINITE
            addUpdateListener {
                val value = it.animatedValue as Float
                startAngle = value
                sweepAngle = 360f-value*2
                invalidate()
            }
        }
        //小球移动的动画
        valueAnimatorSmallCircle = ValueAnimator.ofFloat(0f,5.5f*87.5f).apply {
            duration = 1000L
            repeatCount = INFINITE
            addUpdateListener {
                val value = it.animatedValue as Float
                smallCircleX = smallCircleBigX - value
                invalidate()
            }
        }
    }
    //启动动画
    fun startAnima(){
        changBigArcAngle()
        valueAnimatorBigArc.start()
        valueAnimatorSmallCircle.start()
    }
}