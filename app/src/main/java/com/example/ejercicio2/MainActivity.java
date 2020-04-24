package com.example.ejercicio2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageButton imageButtonPedro;
    ImageButton imageButtonDarlin;
    ImageButton imageButtonYandra;
    ImageButton imageButtonSettings;
    Button buttonIdHome;
    Button getButtonIdHome;

    ConstraintLayout layout;
    List<Estudiantes> studentList = new ArrayList<Estudiantes>();

    static Estudiantes studenSelected;
    static int colorToApply;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpEstudentData();
        setUpBottons();
        setColors();
        setUpImagesButton();


        buttonIdHome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                boolean createSuccessful = deleteStudent(2);

                if (createSuccessful) {
                    Toast.makeText(getApplicationContext(), "student information was saved ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Unable to save student information", Toast.LENGTH_SHORT).show();
                }
            }
        });

        getButtonIdHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int p = counterList();
                boolean createSuccessful = p > 0;

                if (createSuccessful) {
                    Toast.makeText(getApplicationContext(), "Cantidad de EST :" + p , Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "No tiene EST", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void setUpImagesButton() {

        imageButtonPedro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(0);
            }
        });

        imageButtonDarlin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(1);

            }
        });

        imageButtonYandra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(2);

            }
        });

        imageButtonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { sumCounter(); setColors();
            }
        });
    }

    void setUpEstudentData() {

        //studentList = getStudentList();

        studentList.add(new Estudiantes(
                "Pedro Ruiz",
                "drawable/pedro.jpg",
                "Santo Domingo",
                "100391119",
                getString(R.string.expressionPedro),
                R.drawable.pedro));
//
//        studentList.add(new Estudiantes( "Darlin Reyes",
//                "drawable/elreal.jpg",
//                "Santo Domingo",
//                "100339955",
//                getString(R.string.expressionDarlin),
//                R.drawable.darlin1));
////
//        studentList.add(new Estudiantes("Yandra Fermin",
//                "drawable/yandra2.jpg",
//                "Puerto Plata",
//                "100269906",
//                getString(R.string.expressionYandra),
//                R.drawable.yandra));
    }

    void setUpBottons() {
        imageButtonPedro = findViewById(R.id.imageButtonPedro);
        imageButtonDarlin = findViewById(R.id.imageButtonDarlin);
        imageButtonYandra = findViewById(R.id.imageButtonYandra);
        imageButtonSettings = findViewById(R.id.imageButtonSetting);
        buttonIdHome = findViewById(R.id.ButtonIdHome);
        getButtonIdHome = findViewById(R.id.buttonget);
        layout = findViewById(R.id.container);

    }

    void openActivity(int studenIndex) {
        studenSelected = studentList.get(studenIndex);
        Intent intent = new Intent(this, Information.class);
        startActivity(intent);
    }

    void setColors() {

        switch (ColorPlus.counterColor) {

            case 1:
                colorToApply = Color.RED;
                break;
            case 2:
                colorToApply = Color.BLUE;
                break;
            case 3:
                colorToApply = Color.YELLOW;
                break;
            case 4:
                colorToApply = Color.GREEN;
                break;
            case 5:
                colorToApply = Color.CYAN;
                break;
            default:
                colorToApply = Color.WHITE;
                break;
        }

        //colorToApply = Color.CYAN;

        layout.setBackgroundColor(colorToApply);

    }

    void sumCounter() {
        ColorPlus.counterColor++;
        if (ColorPlus.counterColor == 5) {
            ColorPlus.counterColor = 0;
        }
    }

    int counterList(){
        return new BDHelper(getApplicationContext()).count();
    }



    int deletedabase(){
        new BDHelper(getApplicationContext()).deleteTable();
        return 0;
    }



    Estudiantes getSingleStuden(int studentId){
       return new BDHelper(getApplicationContext()).readSingleRecord(studentId);
    }

    boolean updateStudent(Estudiantes estudiante){
        return new BDHelper(getApplicationContext()).update(estudiante);
    }

    boolean deleteStudent(int estudiante){
       return new BDHelper(getApplicationContext()).delete(estudiante);
    }

}




