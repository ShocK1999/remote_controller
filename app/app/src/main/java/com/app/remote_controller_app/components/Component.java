package com.app.remote_controller_app.components;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.app.remote_controller_app.BluetoothService;
import com.app.remote_controller_app.MainActivity;
import com.app.remote_controller_app.components.view_components.MyConstrainLayout;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type( value = Button.class),
        @JsonSubTypes.Type( value = LED.class)
})
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC)
public abstract class Component{
    protected String name;
    protected String id;
    protected int sizeX;
    protected int sizeY;
    protected float posX;
    protected float posY;
    protected float layer;

    @JsonIgnore
    protected BluetoothService bluetoothService;

    @JsonIgnore
    private MyConstrainLayout layout;



    public Component(String name, String id, int sizeX, int sizeY, float posX, float posY, float layer) {
        this.name = name;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.posX = posX;
        this.posY = posY;
        this.id = id;
        this.layer = layer;
    }

    public abstract View getEditView(Context context, Fragment fragment);
    public abstract View getUsageView(Context context);

    public void setLayout(MyConstrainLayout layout) {
        this.layout = layout;
    }

    protected void setAndroidView(View view, Context context){
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(dpToPx(sizeX), dpToPx(sizeY));
        view.setLayoutParams(lp);
        view.setTranslationZ(layer);

        view.setX((dpToPx(posX) - view.getWidth()/2.f));
        view.setY((dpToPx(posY) - view.getHeight()/2.f));
    }

    protected void setMove(View view, Context context){
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                view.setOnTouchListener(new View.OnTouchListener() {
                    float memX, memY = -1;
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        float ex = event.getRawX();
                        float ey = event.getRawY();

                        if(event.getAction()==MotionEvent.ACTION_MOVE) {
                            if(memX!=-1 && Math.abs(ex - memX) < MainActivity.dead_zone) ex = memX;
                            else memX= -1;

                            if(memY!=-1 && Math.abs(ey - memY) < MainActivity.dead_zone) ey = memY;
                            else memY= -1;

                            if(ex + v.getWidth()/2.f <= MainActivity.width && ex - v.getWidth()/2.f >= 0)
                                v.setX(ex - v.getWidth() / 2.f);
                            else if(ex + v.getWidth()/2.f > MainActivity.width)
                                v.setX(MainActivity.width - v.getWidth());
                            else if(ex - v.getWidth()/2.f < 0)
                                v.setX(0);

                            if(ey + v.getHeight()/2.f <= MainActivity.height && ey - v.getHeight()/2.f >= 0)
                                v.setY(ey - v.getHeight() / 2.f);
                            else if(ey + v.getHeight()/2.f > MainActivity.height)
                                v.setY(MainActivity.height - v.getHeight());
                            else if(ey - v.getHeight()/2.f < 0)
                                v.setY(0);

                            layout.removeLine();
                            if(layout.drawLineX(v))
                                memX = ex;
                            if(layout.drawLineY(v))
                                memY = ey;

                        }
                        if(event.getAction()==MotionEvent.ACTION_UP){
                            layout.removeLine();
                            move(Math.round(v.getX()), Math.round(v.getY()));
                            ((MainActivity) context).updateCurrentSelectedController();
                            view.setOnTouchListener(null);
                        }


                        return false;
                    }
                });
                return true;
            }
        });
    }

    public void setBluetoothService(BluetoothService bluetoothService) {
        this.bluetoothService = bluetoothService;
    }

    public void resize(int x, int y){//przyjmuje w pikselach a zapisuje w dp
        if(x>MainActivity.width)
            x=MainActivity.width;
        if(x<MainActivity.scale)
            x = (int) MainActivity.scale * 32;

        if(y>MainActivity.height)
            y=MainActivity.height;
        if(y<MainActivity.scale)
            y = (int) MainActivity.scale * 32;

        this.sizeX=pxToDp(x);
        this.sizeY=pxToDp(y);
    }

    public void move(int x, int y){//przyjuje w pikselach ale ustaiwia w dp
        if(x>MainActivity.width)
            x = MainActivity.width;
        if(y>MainActivity.height)
            y=MainActivity.height;


        this.posX=pxToDp(x);
        this.posY=pxToDp(y);
    }

    public static int dpToPx(float dp)
    {
        return Math.round(dp * MainActivity.scale);
    }

    public static int pxToDp(int px)
    {
        return Math.round(px / MainActivity.scale);
    }


    public String getName() {
        return name;
    }
    public String getId() {
        return id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setId(String id) {
        this.id = id;
    }

    public int getSizeX() {
        return sizeX;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public float getLayer() {
        return layer;
    }

    public void setLayer(float layer) {
        this.layer = layer;
    }
}
