package br.com.unifatecie.rmp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> listaCompras;
    private ArrayAdapter<String> adapter;

    private ActivityResultLauncher<Intent> segundaActivityLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listViewCompras = findViewById(R.id.lsfist);
        Button btnAbrirSegunda = findViewById(R.id.btnEdit);

        // Inicializa lista e adapter
        listaCompras = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaCompras);
        listViewCompras.setAdapter(adapter);

        // Configura ActivityResultLauncher para receber lista da SegundaActivity
        segundaActivityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        ArrayList<String> novaLista = result.getData().getStringArrayListExtra("listaItens");

                        if (novaLista != null) {
                            listaCompras.clear();
                            listaCompras.addAll(novaLista);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
        );
        segundaActivityLauncher.launch(new Intent(this, SegundaActivity.class));// QUANDO ABRIR O APLICATIVO PELA PRIMEIRA FEZ JA VAI DIRETO NA ACTIVITY CRIAR UMA LISTA


        // Abre a SegundaActivity
        btnAbrirSegunda.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SegundaActivity.class);
            segundaActivityLauncher.launch(intent);
        });
    }


}
