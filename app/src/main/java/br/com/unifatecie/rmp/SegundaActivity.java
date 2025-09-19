package br.com.unifatecie.rmp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;

public class SegundaActivity extends AppCompatActivity {

    private EditText edtProduto, edtData;
    private NumberPicker npQtd;
    private Button btnAdd, btnFinalizar;
    private ListView listView;
    private myAdapter adapter;
    private ArrayList<ItemLista> listaItems;

    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);

        edtProduto = findViewById(R.id.produto);
        edtData = findViewById(R.id.edtData);
        npQtd = findViewById(R.id.qtd);
        btnAdd = findViewById(R.id.btnAdd);
        btnFinalizar = findViewById(R.id.btnFinalizar);
        listView = findViewById(R.id.lssecond);

        npQtd.setMinValue(1);
        npQtd.setMaxValue(100);

        // Deixa o texto do NumberPicker preto
        try {
            @SuppressLint("DiscouragedPrivateApi") Field f = npQtd.getClass().getDeclaredField("mInputText");
            f.setAccessible(true);
            EditText numberPickerEditText = (EditText) f.get(npQtd);
            numberPickerEditText.setTextColor(getResources().getColor(android.R.color.black));
        } catch (Exception e) {
            e.printStackTrace();
        }



        edtProduto.setVisibility(View.GONE);
        npQtd.setVisibility(View.GONE);
        edtData.setVisibility(View.GONE);

        listaItems = new ArrayList<>();
        adapter = new myAdapter(this, listaItems);
        listView.setAdapter(adapter);

        btnAdd.setOnClickListener(v -> {
            if (edtProduto.getVisibility() == View.GONE) {
                edtProduto.setVisibility(View.VISIBLE);
                npQtd.setVisibility(View.VISIBLE);
                edtData.setVisibility(View.VISIBLE);
                btnAdd.setText("Salvar");
            } else {
                String nome = edtProduto.getText().toString().trim();
                String data = edtData.getText().toString().trim();
                int qtd = npQtd.getValue();

                if (!nome.isEmpty() && !data.isEmpty()) {
                    ItemLista novoItem = new ItemLista(nome, data, qtd);
                    listaItems.add(novoItem);
                    adapter.notifyDataSetChanged();

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

        btnFinalizar.setOnClickListener(v -> enviarListaParaMain());

        edtData.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int dia = calendar.get(Calendar.DAY_OF_MONTH);
            int mes = calendar.get(Calendar.MONTH);
            int ano = calendar.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    (view, year, monthOfYear, dayOfMonth) -> {
                        monthOfYear += 1;
                        String dataSelecionada = String.format("%02d/%02d/%04d", dayOfMonth, monthOfYear, year);
                        edtData.setText(dataSelecionada);
                    },
                    ano, mes, dia
            );
            datePickerDialog.show();
        });
    }

    private void enviarListaParaMain() {
        ArrayList<String> listaStrings = new ArrayList<>();
        for (ItemLista item : listaItems) {
            listaStrings.add(item.getNome() + " - " + item.getQtd() + " - " + item.getData());
        }
        Intent resultIntent = new Intent();
        resultIntent.putStringArrayListExtra("listaItens", listaStrings);
        setResult(RESULT_OK, resultIntent);
        finish();
    }


}
