package com.example.colordemon.GameStruct.Units;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.util.Log;

import com.example.colordemon.GameStruct.Collider;
import com.example.colordemon.GameStruct.GameObjectType;
import com.example.colordemon.R;

import java.util.ArrayList;

public class Hero extends Unit{
    public static final int Name = 0;
    private final float stopTime = 5f;
    private float radius;
    private float angle;
    private float startAngle;
    private float radiusX;
    private float radiusY;
    private float angleSpeed = 2*(float)Math.PI/20f;
    private float addX;
    private float addY;
    private float tickTime = 1f;
    public int[] cooldowns = new int[4]; // max 0 - 2, max 1 - 5, max 2 - 8, max 3 - 15
    public int damageType = 0; // 0 - dash, 1 - enemyPort, 2 - circleDash, 3 - ult
    public Hero(float x, float y, float velocityX, float velocityY, Collider collider,float scaleX,float scaleY) {
        super(x, y, velocityX,velocityY,collider,scaleX,scaleY);
    }
    public void run(){
        switch (damageType){
            case 0:
                dashUpdate();
                break;
            case 1:
                enemyPortUpdate();
                break;
            case 2:
                circleUpdate();
                break;
            case 3:
                ultUpdate();
                break;
            default:
                Log.wtf("WTF",damageType +"");
        }

    }
    private void dashUpdate(){
        if(tickTime<=stopTime){
            x+=velocityX;
            y+=velocityY;
            tickTime+=1f;
        }
        else{
            tickTime=1f;
            velocityX=0f;
            velocityY=0f;
        }
    }
    private void ultUpdate(){

    }
    private void circleUpdate(){
        if(angle<=startAngle+2*Math.PI){
            angle+=angleSpeed;
            x=radiusX+radius*(float)Math.cos(angle);
            y=radiusY+radius*(float)Math.sin(angle);
        }
        Log.i("III",x+" "+y);
    }
    private void enemyPortUpdate(){
        x=addX;
        y=addY;
        addY=0;
        addX=0;
    }
    public int nowSprite(){
        return 0;
    }
    public void dash(float addVelocityX,float addVelocityY){
        float koef = Math.min(Math.abs(400f/addVelocityX),Math.abs(400f/addVelocityY));
        if(Math.abs(addVelocityX)>400f) addVelocityX=addVelocityX*koef;
        if(Math.abs(addVelocityY)>400f) addVelocityY=addVelocityY*koef;
        velocityX=addVelocityX/stopTime;
        velocityY=addVelocityY/stopTime;
    }
    public void enemyPort(float newX,float newY){
        addX=newX;
        addY=newY;
    }
    public void circleDash(float radiusX,float radiusY,float width,float height){
        float addX=x-width/2;
        float addY=y-height/2;
        this.radiusX=radiusX+addX;
        this.radiusY=radiusY+addY;
        radiusX+=addX;
        radiusY+=addY;
        radius = (float) Math.sqrt((radiusX-x)*(radiusX-x)+ (radiusY-y)*(radiusY-y));
        startAngle = radiusY-y>=0 ? (float) Math.acos(Math.max(-1,Math.min(1,(x-radiusX)/radius)))
                : (float)-Math.acos(Math.max(-1,Math.min(1,(x-radiusX)/radius)));
        //startAngle=0;
        angle=startAngle;
    }
    public void ult(Float[][] Coord){

    }
}
