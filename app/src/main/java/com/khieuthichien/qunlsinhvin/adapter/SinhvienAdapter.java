package com.khieuthichien.qunlsinhvin.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.khieuthichien.qunlsinhvin.R;
import com.khieuthichien.qunlsinhvin.databse.DatabaseHelper;
import com.khieuthichien.qunlsinhvin.model.Lop;
import com.khieuthichien.qunlsinhvin.model.Sinhvien;

import java.util.List;

public class SinhvienAdapter extends RecyclerView.Adapter<SinhvienAdapter.SinhvienHolder> {

    Context context;
    List<Sinhvien> sinhvienList;
    DatabaseHelper databaseHelper;

    public SinhvienAdapter(Context context, List<Sinhvien> sinhvienList, DatabaseHelper databaseHelper) {
        this.context = context;
        this.sinhvienList = sinhvienList;
        this.databaseHelper = databaseHelper;
    }

    @NonNull
    @Override
    public SinhvienAdapter.SinhvienHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sinhvien, parent, false);
        return new SinhvienHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SinhvienAdapter.SinhvienHolder holder, final int position) {
        final Sinhvien sinhvien = sinhvienList.get(position);
        holder.imganh.setImageResource(R.drawable.alanwalker);
        holder.tvmasinhvien.setText(sinhvien.getMasinhvien());
        holder.tvtensinhvien.setText(sinhvien.getTensinhvien());

        holder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.deleteSinhvien(sinhvienList.get(position));
                sinhvienList.remove(position);
                databaseHelper.updateSinhvien(sinhvien);
                notifyDataSetChanged();
                Toast.makeText(context, "xoa", Toast.LENGTH_SHORT).show();
            }
        });

        holder.imgedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setTitle(sinhvienList.get(position).getTensinhvien());
                dialog.setContentView(R.layout.dialog_edit_sinhvien);

                final EditText edttensinhvien;
                final Spinner splop;
                final Button btnedit;
                final Button btncancel;

                edttensinhvien = dialog.findViewById(R.id.edttensinhvien);
                splop = dialog.findViewById(R.id.splop);
                btnedit = dialog.findViewById(R.id.btnedit);
                btncancel = dialog.findViewById(R.id.btncancel);

                edttensinhvien.setText(sinhvienList.get(position).getTensinhvien());

                btnedit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Sinhvien sinhvien1 = new Sinhvien();
                        sinhvien1.setTensinhvien(sinhvien.getTensinhvien());
                        sinhvien1.setMalop(sinhvien.getMalop());

                        databaseHelper.updateSinhvien(sinhvien1);

                        sinhvienList.get(position).setTensinhvien(edttensinhvien.getText().toString().trim());

                        notifyItemChanged(position);
                        dialog.dismiss();
                    }
                });

                btncancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        if (sinhvienList == null) {
            return 0;
        }
        return sinhvienList.size();
    }

    public class SinhvienHolder extends RecyclerView.ViewHolder {

        ImageView imganh;
        TextView tvmasinhvien;
        TextView tvtensinhvien;
        ImageView imgedit;
        ImageView imgdelete;

        public SinhvienHolder(View itemView) {
            super(itemView);

            imganh = itemView.findViewById(R.id.imganh);
            tvmasinhvien = itemView.findViewById(R.id.tvmasinhvien);
            tvtensinhvien = itemView.findViewById(R.id.tvtensinhvien);
            imgedit = itemView.findViewById(R.id.imgedit);
            imgdelete = itemView.findViewById(R.id.imgdelete);

        }

    }
}
