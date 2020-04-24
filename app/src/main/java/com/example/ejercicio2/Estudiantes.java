package com.example.ejercicio2;

import android.graphics.drawable.Drawable;

public class Estudiantes {
    public int id;
    public String name;
    public String pathImage;
    public String city;
    public String numberId;
    public String expression;
    public int photoInt;

    public Estudiantes(String name, String pathImage, String city, String numberId, String expression, int photo){
//        this.id = id;
        this.name = name;
        this.pathImage = pathImage;
        this.city = city;
        this.numberId = numberId;
        this.expression = expression;
        this.photoInt = photo;
    }

    Estudiantes(int id, String name, String pathImage, String city, String numberId, String expression, int photo){
        this.id = id;
        this.name = name;
        this.pathImage = pathImage;
        this.city = city;
        this.numberId = numberId;
        this.expression = expression;
        this.photoInt = photo;
    }






}
