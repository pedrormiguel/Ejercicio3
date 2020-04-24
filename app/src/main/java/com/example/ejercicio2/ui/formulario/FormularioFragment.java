package com.example.ejercicio2.ui.formulario;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ejercicio2.BDHelper;
import com.example.ejercicio2.Estudiantes;
import com.example.ejercicio2.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class FormularioFragment extends Fragment {

    private static final int GALLERY_REQUEST_CODE = 0;
    ImageView imageViewUser;
    //Botones
    Button buttonImage, buttonFormSaveStudent;

    //Text
    TextInputLayout textInputEditTextName;
    TextInputLayout textInputLayoutNumberId;
    TextInputLayout textInputLayoutCity;
    EditText editTextExpression;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(
                R.layout.activity_formulario,
                container, false);

        buttonImage = root.findViewById(R.id.buttonPhoto);
        buttonFormSaveStudent = root.findViewById(R.id.buttonFormSave);
        textInputEditTextName = root.findViewById(R.id.textInputLayoutName);
        textInputLayoutNumberId = root.findViewById(R.id.textInputLayoutNumerId);
        textInputLayoutCity = root.findViewById(R.id.textInputLayoutCity);
        editTextExpression = root.findViewById(R.id.editTextExpression);

        buttonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFromGallery();
            }
        });

        buttonFormSaveStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarDatos();
            }
        });

        return root;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Result code is RESULT_OK only if the user selects an Image
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case GALLERY_REQUEST_CODE:
                    //data.getData return the content URI for the selected Image
                    Uri selectedImage = data.getData();

                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    // Get the cursor
                    Cursor cursor = getContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();
                    //Get the column index of MediaStore.Images.Media.DATA
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    //Gets the String value in the column
                    String imgDecodableString = cursor.getString(columnIndex);

                    cursor.close();
                    // Set the Image in ImageView after decoding the String
                    //imageViewUser.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
                    //imageViewUser.setImageURI(selectedImage.getPath());
                    textInputEditTextName.getEditText().setText(selectedImage.getPath());
                    Toast.makeText(getContext(), selectedImage.getPath(), Toast.LENGTH_SHORT).show();


                    break;
            }

    }

    private void pickFromGallery() {
        //Create an Intent with action as ACTION_PICK
        Intent intent = new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        // Launching the Intent
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    private boolean validarCampo(TextInputLayout campo, String nombre, int lengt) {
        //Pattern patron = Pattern.compile("^[a-zA-Z ]+$]");

        if (nombre.isEmpty() || nombre == null || nombre.length() > lengt) {
            campo.getEditText().setError("Nombre inválido");
            return false;
        } else {
            campo.setError(null);
        }

        return nombre.length() < lengt;
    }

    private boolean validarCampo(EditText campo, String nombre, int lengt) {
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$");

        if (!patron.matcher(nombre).matches() || nombre.length() > lengt) {
            campo.setError("Nombre inválido");
            return false;
        } else {
            campo.setError(null);
        }

        return patron.matcher(nombre).matches() || nombre.length() < lengt;
    }

    private void validarDatos() {
        String name = textInputEditTextName.getEditText().getText().toString();
        //String pathPhoto
        String numberId = textInputLayoutNumberId.getEditText().getText().toString();
        String city = textInputLayoutCity.getEditText().getText().toString();
        String expression = editTextExpression.getText().toString();

        boolean a = validarCampo(textInputEditTextName, name, 30);
        boolean b = validarCampo(textInputLayoutNumberId, numberId, 30);
        boolean c = validarCampo(textInputLayoutCity, city, 30);
        boolean d = validarCampo(editTextExpression, expression, 600);

        if (a && b && c && d) {

            Toast.makeText(getContext(), "Se guarda el registro", Toast.LENGTH_LONG).show();

            saveStudent(
                    new Estudiantes(
                    name,
                    "path",
                    city,
                    numberId,
                    expression,
                    0));

            limpiarCampos();
        }
    }

    private void limpiarCampos() {
            textInputEditTextName.getEditText().setText("");
            textInputLayoutCity.getEditText().setText("");
            textInputLayoutNumberId.getEditText().setText("");
            editTextExpression.setText("");
        }

    boolean saveStudent(Estudiantes estudiante){
        return new BDHelper(getContext()).create(estudiante);
    }


}



