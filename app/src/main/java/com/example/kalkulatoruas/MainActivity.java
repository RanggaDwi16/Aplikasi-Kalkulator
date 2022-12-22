package com.example.kalkulatoruas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<ListItem> listItems;
    private RecyclerView RecyclerView;
    private Adapter Adapter;
    private RecyclerView.LayoutManager LayoutManager;
    RadioGroup group;
    RadioButton tambah, kurang, kali, bagi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();
        buildRecyclerView();
        setInsertButton();

        group = findViewById(R.id.group);
        tambah = findViewById(R.id.tambah);
        kurang = findViewById(R.id.kurang);
        kali = findViewById(R.id.kali);
        bagi = findViewById(R.id.bagi);

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (tambah.isChecked()) {
                    Toast.makeText(MainActivity.this, "Tambah", Toast.LENGTH_SHORT).show();
                } else if (kurang.isChecked()) {
                    Toast.makeText(MainActivity.this, "Kurang", Toast.LENGTH_SHORT).show();
                } else if (kali.isChecked()) {
                    Toast.makeText(MainActivity.this, "Kali", Toast.LENGTH_SHORT).show();
                } else if (bagi.isChecked()) {
                    Toast.makeText(MainActivity.this, "Bagi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setInsertButton() {
        Button button = findViewById(R.id.insert);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText number1 = findViewById(R.id.number1);
                EditText number2 = findViewById(R.id.number2);
                insertItem(""+number1.getText().toString(), ""+number2.getText().toString());
                saveData();
            }
        });
    }

    private void insertItem(String number1, String number2) {
        int hasil =0;
        if (tambah.isChecked()) {
            hasil = Integer.parseInt(number1) + Integer.parseInt(number2);
        } else if (kurang.isChecked()) {
            hasil = Integer.parseInt(number1) - Integer.parseInt(number2);
        } else if (kali.isChecked()) {
            hasil = Integer.parseInt(number1) * Integer.parseInt(number2);
        } else if (bagi.isChecked()) {
            hasil = Integer.parseInt(number1) / Integer.parseInt(number2);
        }

        String operasi = "";
        if(tambah.isChecked()){
            operasi = "+";
        } else if(kurang.isChecked()){
            operasi = "-";
        } else if(kali.isChecked()){
            operasi = "*";
        } else if(bagi.isChecked()){
            operasi = "/";
        }

        if(number1.isEmpty()){
            Toast.makeText(this, "Masukkan angka pertama", Toast.LENGTH_SHORT).show();
        }else if(!tambah.isChecked() && !kurang.isChecked() && !kali.isChecked() && !bagi.isChecked()){
            Toast.makeText(this, "Pilih operasi", Toast.LENGTH_SHORT).show();
        }else if(number2.isEmpty()){
            Toast.makeText(this, "Masukkan angka kedua", Toast.LENGTH_SHORT).show();
        }else {
            listItems.add(new ListItem(number1, operasi, number2, String.valueOf(hasil)));
            Adapter.notifyItemInserted(listItems.size());
        }
    }

    private void saveData() {
        SharedPreferences Adapter = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = Adapter.edit();
        Gson gson = new Gson();
        String json = gson.toJson(listItems);
        editor.putString("task list", json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences Adapter = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = Adapter.getString("task list", null);
        Type type = new TypeToken<ArrayList<ListItem>>() {}.getType();
        listItems = gson.fromJson(json, type);

        if (listItems == null) {
            listItems = new ArrayList<>();
        }
    }

    private void buildRecyclerView() {
        RecyclerView = findViewById(R.id.recyclerView);
        RecyclerView.setHasFixedSize(true);
        LayoutManager = new LinearLayoutManager(this);
        Adapter = new Adapter(listItems);
        RecyclerView.setLayoutManager(LayoutManager);
        RecyclerView.setAdapter(Adapter);
    }

}