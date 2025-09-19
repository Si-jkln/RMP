package br.com.unifatecie.rmp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class myAdapter extends BaseAdapter {
    private Context context;
    private final ArrayList<ItemLista> items;

    public myAdapter(Context context, ArrayList<ItemLista> items) {
        this.context = context;
        this.items = items;
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public ItemLista getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lista, parent, false);
        }

        TextView txtNome = convertView.findViewById(R.id.txtNome);
        TextView txtData = convertView.findViewById(R.id.txtData);
        TextView txtQtd = convertView.findViewById(R.id.txtQtd);
        TextView btnEdit = convertView.findViewById(R.id.btnEdit);
        TextView btnDelete = convertView.findViewById(R.id.btnDelete);

        ItemLista item = getItem(position);

        txtNome.setText(item.getNome());
        txtData.setText("Data da próxima compra: " + item.getData());
        txtQtd.setText("Quantidade: " + item.getQtd());

        //editar
        btnEdit.setOnClickListener(v -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle("Editar Produto");
            View view = LayoutInflater.from(context).inflate(R.layout.item_lista, null);
            TextView edtNome = view.findViewById(R.id.txtNome);
            TextView edtData = view.findViewById(R.id.txtData);
            TextView edtQtd = view.findViewById(R.id.txtQtd);

            edtNome.setText(item.getNome());
            edtData.setText(item.getData());
            edtQtd.setText(String.valueOf(item.getQtd()));

            dialog.setView(view);
            dialog.setPositiveButton("Salvar", (d, w) ->{
                item.setNome(edtNome.getText().toString());
                item.setData(edtData.getText().toString());
                item.setQtd(Integer.parseInt(edtQtd.getText().toString()));
                notifyDataSetChanged();
                });
                    dialog.setNegativeButton("Cancelar", null);
                     dialog.show();

            });

        //deletar
        btnDelete.setOnClickListener(v -> {
           items.remove(position);
           notifyDataSetChanged();
           Toast.makeText(context, "Item removido", Toast.LENGTH_SHORT).show();
        });

        return convertView;
    }
}