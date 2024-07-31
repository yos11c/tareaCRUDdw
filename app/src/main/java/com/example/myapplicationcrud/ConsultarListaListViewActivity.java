package com.example.myapplicationcrud;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;

import com.example.myapplicationcrud.Entidades.Usuario;
import com.example.myapplicationcrud.utilidades.Utilidades;

public class ConsultarListaListViewActivity extends AppCompatActivity {

    ListView listViewPersonas;
    ArrayList<String> listaInformacion;
    ArrayList<Usuario> listaUsuarios;

    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_lista_list_view);

        conn = new ConexionSQLiteHelper(getApplicationContext(), "bd_usuarios", null, 1);

        listViewPersonas = findViewById(R.id.listViewPersonas);

        consultarListaPersonas();

        if (listaInformacion != null) {
            ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaInformacion);
            listViewPersonas.setAdapter(adaptador);

            listViewPersonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                    String informacion = "id: " + listaUsuarios.get(pos).getId() + "\n";
                    informacion += "Nombre: " + listaUsuarios.get(pos).getNombre() + "\n";
                    informacion += "Telefono: " + listaUsuarios.get(pos).getTelefono() + "\n";



                    Usuario user = listaUsuarios.get(pos);

                    Intent intent = new Intent(ConsultarListaListViewActivity.this, DetalleUsuarioActivity.class);

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("usuario", (Serializable) user);

                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        } else {
            Log.e("ListView", "La lista de información está vacía o no se inicializó correctamente.");
        }
    }

    private void consultarListaPersonas() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = conn.getReadableDatabase();

            listaUsuarios = new ArrayList<>();
            cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_USUARIO, null);

            while (cursor.moveToNext()) {
                Usuario usuario = new Usuario();
                usuario.setId(cursor.getInt(0));
                usuario.setNombre(cursor.getString(1));
                usuario.setTelefono(cursor.getString(2));

                listaUsuarios.add(usuario);
            }

            obtenerLista();
        } catch (Exception e) {
            Log.e("DBError", "Error al consultar la base de datos", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
    }

    private void obtenerLista() {
        listaInformacion = new ArrayList<>();

        for (Usuario usuario : listaUsuarios) {
            listaInformacion.add(usuario.getId() + " - " + usuario.getNombre());
        }
    }
}