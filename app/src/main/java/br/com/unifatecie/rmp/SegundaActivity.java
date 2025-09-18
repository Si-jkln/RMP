package br.com.unifatecie.rmp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SegundaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);

        // Referências dos elementos do layout
        Button btnAdd = findViewById(R.id.btnAdd);
        EditText produto = findViewById(R.id.produto);
        NumberPicker qtd = findViewById(R.id.qtd);

        // Configura NumberPicker
        qtd.setMinValue(1);
        qtd.setMaxValue(100);

        // Inicialmente escondidos
        produto.setVisibility(View.GONE);
        qtd.setVisibility(View.GONE);

        // Ação do botão
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(produto.getVisibility() == View.GONE){
                    produto.setVisibility(View.VISIBLE);
                    qtd.setVisibility(View.VISIBLE);
                } else {
                    produto.setVisibility(View.GONE);
                    qtd.setVisibility(View.GONE);
                }
            }
        });
    }
}
