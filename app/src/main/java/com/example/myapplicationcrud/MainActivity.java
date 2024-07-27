package com.example.myapplicationcrud;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        com.example.myapplicationcrud.ConexionSQLiteHelper conn = new com.example.myapplicationcrud.ConexionSQLiteHelper(this, "bd_usuarios", null, 1);


    }

    public void onClick(View view) {
        Intent intent = null;

        intent = new Intent(MainActivity.this, RegistroUsuariosActivity.class);

        if (intent != null) {
            startActivity(intent);
        }
    }
}
