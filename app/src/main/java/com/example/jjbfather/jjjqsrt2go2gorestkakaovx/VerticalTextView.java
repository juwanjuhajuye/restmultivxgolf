package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.content.Context;

import android.graphics.Canvas;

import android.text.TextPaint;

import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;


public class VerticalTextView extends AppCompatTextView{



    public VerticalTextView(Context context, AttributeSet attrs) {

        super(context, attrs);

    }



    @Override

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(heightMeasureSpec, widthMeasureSpec);

        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());

    }



    // 텍스트뷰 가로 입력

    @Override

    protected void onDraw(Canvas canvas) {

        // Custom View를 생성할때 원하는 폰트 , 색상 , 크기 설정

        TextPaint textPaint = getPaint();

        textPaint.setColor(getCurrentTextColor());

        textPaint.drawableState = getDrawableState();



        // View를 그리위한 객체 Canvas

        canvas.save();



        canvas.translate(0, getHeight());

        canvas.rotate(-90); // 90도로 회전



        canvas.translate(getCompoundPaddingLeft(), getExtendedPaddingTop());



        getLayout().draw(canvas);

        canvas.restore(); // Canvas 상태를 복원합니다.

    }

}