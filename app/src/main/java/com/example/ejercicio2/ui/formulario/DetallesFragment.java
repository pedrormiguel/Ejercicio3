package com.example.ejercicio2.ui.formulario;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ejercicio2.BDHelper;
import com.example.ejercicio2.Estudiantes;
import com.example.ejercicio2.R;
import com.google.android.material.textfield.TextInputLayout;

public class DetallesFragment extends Fragment {

    ImageView imageViewInformationPhoto;
    EditText  textNameInformationName;
    EditText  textNameInformationNumberId;
    EditText  textNameInformationCity;
    EditText  textNameInformationExpression;
    Button    buttonBuscar;
    Button    buttonActualizar;
    Button    buttonEliminar;
    EditText  editTextBuscador;

    Estudiantes currentStudent;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_informacion,container,false);

        setupBottoms(root);

        buttonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numeroId = Integer.parseInt( editTextBuscador.getText().toString() );
                buscarEstudiante(numeroId);
            }
        });

        buttonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStudent();
            }
        });

        buttonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteStudent(currentStudent.id);
            }
        });


        return root;

    }

    void buscarEstudiante(int studenId){

         currentStudent = getSingleStuden(studenId);

        if(currentStudent == null){

            imageViewInformationPhoto.setImageResource(R.drawable.tarjeta_de_estudiante);
            textNameInformationName.setText("No existe");
            textNameInformationNumberId.setText("No existe");
            textNameInformationCity.setText("No existe");
            textNameInformationExpression.setText("No existe");

            buttonEliminar.setEnabled(false);
            buttonActualizar.setEnabled(false);

            Toast.makeText(getContext(),"Estudiante no existe",Toast.LENGTH_LONG);
            return;
        }



        imageViewInformationPhoto.setImageResource(R.drawable.darlin1);
        textNameInformationName.setText(currentStudent.name);
        textNameInformationNumberId.setText(currentStudent.numberId);
        textNameInformationCity.setText(currentStudent.city);
        textNameInformationExpression.setText(currentStudent.expression);


        buttonEliminar.setEnabled(true);
        buttonActualizar.setEnabled(true);

    }


    Estudiantes getSingleStuden(int studentId){
        return new BDHelper(getContext()).readSingleRecord(studentId);
    }

    boolean updateStudent(){

        currentStudent.name         = textNameInformationName.getText().toString();
        currentStudent.numberId     = textNameInformationNumberId.getText().toString();
        currentStudent.city         = textNameInformationCity.getText().toString();
        currentStudent.expression   = textNameInformationExpression.getText().toString();

        boolean a = validarCampo(textNameInformationName, currentStudent.name, 30);
        boolean b = validarCampo(textNameInformationNumberId, currentStudent.numberId, 30);
        boolean c = validarCampo(textNameInformationCity, currentStudent.city, 30);
        boolean d = validarCampo(textNameInformationExpression, currentStudent.expression, 600);

        if (a && b && c && d) {
            limpiarCampos();
            return new BDHelper(getContext()).update(currentStudent);
        }

        return false;

    }



   void setupBottoms(View root){

        imageViewInformationPhoto = root.findViewById(R.id.imageViewInformation);

        textNameInformationName = root.findViewById(R.id.TextNameInformation);

        textNameInformationNumberId = root.findViewById(R.id.textNameInformationNumberId);

        textNameInformationCity = root.findViewById(R.id.textNameInformationCity);

        textNameInformationExpression = root.findViewById(R.id.textNameInformationExpression);

        editTextBuscador = root.findViewById(R.id.editTextBuscador);

        buttonBuscar = root.findViewById(R.id.buttonBuscar);

        buttonEliminar = root.findViewById(R.id.buttonEliminar);

        buttonActualizar = root.findViewById(R.id.buttonActualizar);

        buttonEliminar.setEnabled(false);
        buttonActualizar.setEnabled(false);

    }

    private boolean validarCampo(EditText campo, String nombre, int lengt) {
        //Pattern patron = Pattern.compile("^[a-zA-Z ]+$]");

        if (nombre.isEmpty() || nombre == null || nombre.length() > lengt) {
            campo.setError("Empty");
            return false;
        } else {
            campo.setError(null);
        }

        return nombre.length() < lengt;
    }

    void limpiarCampos(){
        imageViewInformationPhoto.setImageResource(R.drawable.ic_launcher_background);
        textNameInformationName.setText("");
        textNameInformationNumberId.setText("");
        textNameInformationCity.setText("");
        textNameInformationExpression.setText("");
    }

    boolean deleteStudent(int estudiante){

        limpiarCampos();
        buttonEliminar.setEnabled(false);
        buttonActualizar.setEnabled(false);
        return new BDHelper(getContext()).delete(estudiante);
    }

}
