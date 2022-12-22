package com.example.kalkulatoruas;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ExampleViewHolder> {
    private final ArrayList<ListItem> listItems;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView Number1;
        public TextView Operasi;
        public TextView Number2;
        public TextView Hasil;
        public CardView itemLayout;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            Number1 = itemView.findViewById(R.id.textview_line1);
            Operasi = itemView.findViewById(R.id.textview_operasi);
            Number2 = itemView.findViewById(R.id.textview_line2);
            Hasil = itemView.findViewById(R.id.textview_hasil);
            itemLayout = itemView.findViewById(R.id.item_layout);
        }

    }

    public Adapter(ArrayList<ListItem> exampleList) {
        listItems = exampleList;
    }

    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ListItem currentItem = listItems.get(position);
        holder.Number1.setText(currentItem.getNumber1());
        holder.Operasi.setText(currentItem.getOperasi());
        holder.Number2.setText(currentItem.getNumber2());
        holder.Hasil.setText(currentItem.getHasil());
        holder.itemLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Hapus Operasi");
                builder.setMessage("Apakah kamu ingin menghapus operasi perhitungan ini?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listItems.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, listItems.size());
                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }
}
