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
import android.widget.Toast;

import java.util.ArrayList;


public class myAdapter extends BaseAdapter {
    private Context context;
    private final ArrayList<String> items;

    public myAdapter(Context context, ArrayList<String> items) {
        this.context = context;
        this.items = items;
    }
    @Override
    public int getCount() {
        return items.size();
    }
    @Override
    public Object getItem(int position) {
        return items.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    @SuppressLint("ViewHolder")
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(context);
        View rowView = inflater.inflate(R.layout.activity_segunda, parent, false);

        ImageButton imageButton = rowView.findViewById(R.id.btnEdit);
        ImageButton imageButton1 = rowView.findViewById(R.id.btnDelete);


        imageButton.setOnClickListener(v -> {
            EditText editText = new EditText(context);
            editText.setText(items.get(position));
            // Aqui você pode implementar um diálogo para editar o item
            // Por simplicidade, vamos apenas atualizar o item diretamente
            new AlertDialog.Builder(context)
                    .setTitle("Editar Item")
                    .setView(editText)
                    .setPositiveButton("Salvar", (dialog, which) -> {
                        items.set(position, editText.getText().toString());
                        notifyDataSetChanged();
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();


        });
        imageButton1.setOnClickListener(v -> {
            // Aqui você pode implementar a lógica para deletar o item
            items.remove(position);
            notifyDataSetChanged();
            Toast.makeText(context, "Item deletado", Toast.LENGTH_SHORT).show();
        });

        return rowView;
    }
}