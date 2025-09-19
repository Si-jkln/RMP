package br.com.unifatecie.rmp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class myAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ItemLista> items;

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
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lista, parent, false);

        TextView txtNome = convertView.findViewById(R.id.edtNome);
        TextView txtData = convertView.findViewById(R.id.edtData);
        TextView txtQtd = convertView.findViewById(R.id.edtQtd);
        ImageButton btnEdit = convertView.findViewById(R.id.btnEdit);
        ImageButton btnDelete = convertView.findViewById(R.id.btnDelete);


        ItemLista item = getItem(position);

        txtNome.setText(item.getNome());
        txtData.setText("Data: " + item.getData());
        txtQtd.setText("Qtd: " + item.getQtd());

       /* btnDelete.setOnClickListener(v -> {
            items.remove(position);
            notifyDataSetChanged();
        });*/

        btnEdit.setOnClickListener(v -> {
            // Aqui você pode implementar uma caixa de diálogo para editar os dados
        });

        return convertView;
    }
}
