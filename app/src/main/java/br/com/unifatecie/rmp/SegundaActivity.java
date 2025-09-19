package br.com.unifatecie.rmp;

import android.app.DatePickerDialog;
import java.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SegundaActivity extends AppCompatActivity {
    private EditText edtProduto, edtData;
    private NumberPicker npQtd;
    private Button btnAdd;
    private ListView listView;
    private myAdapter adapter;
    private ArrayList<ItemLista> listaItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);

        // Referências dos elementos do layout
        btnAdd = findViewById(R.id.btnAdd);
        edtProduto = findViewById(R.id.produto);
        edtData = findViewById(R.id.edtData);
        npQtd = findViewById(R.id.qtd);
        listView = findViewById(R.id.lssecond);

        // Configura NumberPicker
        npQtd.setMinValue(1);
        npQtd.setMaxValue(100);

        // Inicialmente escondidos
        edtProduto.setVisibility(View.GONE);
        npQtd.setVisibility(View.GONE);
        edtData.setVisibility(View.GONE);

        //inicializando lista e adapter
        listaItems = new ArrayList<>();
        adapter = new myAdapter(this, listaItems);
        listView.setAdapter(adapter);


        btnAdd.setOnClickListener(v ->{
            if(edtProduto.getVisibility() == View.GONE){
            edtProduto.setVisibility(View.VISIBLE);
            npQtd.setVisibility(View.VISIBLE);
            edtData.setVisibility(View.VISIBLE);
            btnAdd.setText("Salvar");
            } else {
                //pega valores e adicona na lista
                String nome = edtProduto.getText().toString().trim();
                String data = edtData.getText().toString().trim();
                int qtd = npQtd.getValue();

                if (!nome.isEmpty() && !data.isEmpty()) {
                    ItemLista novoItem = new ItemLista(nome, data, qtd);
                    listaItems.add(novoItem);
                    adapter.notifyDataSetChanged();

                    // limpa e esconde campos
                    edtProduto.setText("");
                    edtData.setText("");
                    edtProduto.setVisibility(View.GONE);
                    edtData.setVisibility(View.GONE);
                    npQtd.setVisibility(View.GONE);
                    btnAdd.setText("+");
                } else {
                    Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        edtData = findViewById(R.id.edtData);

        // Quando o usuário clicar no campo, abre o calendário
        edtData.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int dia = calendar.get(Calendar.DAY_OF_MONTH);
            int mes = calendar.get(Calendar.MONTH);
            int ano = calendar.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this, // Contexto
                    (view, year, monthOfYear, dayOfMonth) -> {
                        // Ajusta mês (começa do 0)
                        monthOfYear += 1;
                        String dataSelecionada = String.format("%02d/%02d/%04d", dayOfMonth, monthOfYear, year);
                        edtData.setText(dataSelecionada);
                    },
                    ano,
                    mes,
                    dia
            );

            datePickerDialog.show();
        });

    }
}
