package br.com.unifatecie.rmp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private ListView listViewCompras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        /*Intent intent = new Intent(this, SegundaActivity.class );// cria a intent para iniciar a segunda activity
        startActivity(intent);// inicia a segunda activity*/

        listViewCompras = findViewById(R.id.lsfist);
        String[] compras = {"Arroz", "Feijão", "Macarrão", "Carne", "Frango", "Leite", "Pão", "Queijo", "Manteiga", "Ovos"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.item_lista, R.id.textoItem, compras);
        listViewCompras.setAdapter(adapter);
        listViewCompras.setOnItemClickListener(((parent, view, position, id) -> {
            String item = (String) parent.getItemAtPosition(position);


            Toast.makeText(this, "Item selecionado: " + item, Toast.LENGTH_SHORT).show();
        }));
    }
}