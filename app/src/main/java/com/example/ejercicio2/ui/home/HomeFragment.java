package com.example.ejercicio2.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ejercicio2.BDHelper;
import com.example.ejercicio2.Estudiantes;
import com.example.ejercicio2.Information;
import com.example.ejercicio2.R;

import java.util.List;

public class HomeFragment extends Fragment {

    List<Estudiantes> list;
    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = root.findViewById(R.id.recyclerView);

//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
//                LinearLayoutManager.VERTICAL,false));

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        list = getStudentList();

        AdapeterData adapter =  new AdapeterData(list);
        recyclerView.setAdapter(adapter);

        return root;
    }

    List<Estudiantes> getStudentList() {
        return  new BDHelper(getContext()).read();
    }

    void inte(){
        Intent intent = new Intent(getContext(), Information.class);
    }
}
