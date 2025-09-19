package br.com.unifatecie.rmp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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
    private static final int NOTIFICATION_PERMISSION_CODE = 1;

    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);

        pedirPermissaoNotificacao();// pedi a permissão logo na entrada do APP


        edtProduto = findViewById(R.id.produto);
        edtData = findViewById(R.id.edtData);
        npQtd = findViewById(R.id.qtd);
        btnAdd = findViewById(R.id.btnAdd);
        btnFinalizar = findViewById(R.id.btnFinalizar);
        listView = findViewById(R.id.lssecond);

        npQtd.setMinValue(1);
        npQtd.setMaxValue(100);

        /*ESSE BLOCO DEIXA O NUMBERPICKE INVISIVEL*/
        edtProduto.setVisibility(View.GONE);
        npQtd.setVisibility(View.GONE);
        edtData.setVisibility(View.GONE);

         /*ESSE BLOCO CIRA A LISTA E O ADAPTER*/
        listaItems = new ArrayList<>();
        adapter = new myAdapter(this, listaItems);
        listView.setAdapter(adapter);

        /*ESSE BLOCO FAZ TODA A FUNÇÃO DO BO~TAO ADICIONAR*/
        btnAdd.setOnClickListener(v -> {
            if (edtProduto.getVisibility() == View.GONE) {  //AQUI TORNA VISIVEL A NUMBERPICKE
                edtProduto.setVisibility(View.VISIBLE);
                npQtd.setVisibility(View.VISIBLE);
                edtData.setVisibility(View.VISIBLE);
                btnAdd.setText("Salvar"); // DEPOIS DE PREENCHER PRODUTO, QUANTIDADE E A DATA, VAI APARECER O BOTÃO PARA SALVAR
            } else {
                /*Essas três linhas pegam os dados digitados ou selecionados pelo usuário:*/
                String nome = edtProduto.getText().toString().trim();
                String data = edtData.getText().toString().trim();
                int qtd = npQtd.getValue();

                /*ESSE BLOCO VERIFICA SE OS DADOS FORAM PREENCHIDOS, CRIA UM ITEM, ADICIONA À LISTA E ATUALIZA A INTERFACE E LIMPA E OCULTA OS CAMPOS*/
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
                    Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();// AQUI AVISA QUE PRECISA PREENCHER OS CAMPOS
                }
            }
        });

        btnFinalizar.setOnClickListener(v -> enviarListaParaMain());// AQUI ELE RETORNA PARA A MAIN ACTIVITY

        /*ESSE BLOCO FAZ A FUNÇÃO DA DATA PEGANDO AS INFORMAÇÕES PARA PODER NOTIFICAR O DIA DA PROXIMA COMPRA*/
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

    /*ESSE BLOCO É A PERMISSÃO DAS NOTIFICAÇÃO, PARA QUANDO ESTIVER PROXIMO AO DIA ELE AVISARA*/
    private void pedirPermissaoNotificacao() {
        // Android 13 ou superior precisa pedir permissão
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                // Solicita permissão
                requestPermissions(
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        NOTIFICATION_PERMISSION_CODE
                );
            } else {
                // Permissão já concedida
                Toast.makeText(this, "Permissão de notificações já concedida", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    /*ESSE BLOCO AQUI VERIFICA O RESULTADO DA PERMISSÃO SE FOI ACEITO OU NÃO */
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == NOTIFICATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissão de notificações concedida!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permissão de notificações negada!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*ENVIA A LISTA FEITA PARA A MAIN ACTIVITY*/
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


