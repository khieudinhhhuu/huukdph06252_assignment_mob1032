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
import android.widget.TextView;
import android.widget.Toast;

import com.khieuthichien.qunlsinhvin.R;
import com.khieuthichien.qunlsinhvin.databse.DatabaseHelper;
import com.khieuthichien.qunlsinhvin.model.Lop;

import java.util.List;

public class LopAdapter extends RecyclerView.Adapter<LopAdapter.LopHolder> {

    Context context;
    List<Lop> lopList;
    DatabaseHelper databaseHelper;

    public LopAdapter(Context context, List<Lop> lopList, DatabaseHelper databaseHelper) {
        this.context = context;
        this.lopList = lopList;
        this.databaseHelper = databaseHelper;
    }

    @NonNull
    @Override
    public LopAdapter.LopHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lop, parent, false);
        return new LopHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LopAdapter.LopHolder holder, final int position) {
        final Lop lop = lopList.get(position);
        holder.imganh.setImageResource(R.drawable.polytechnic);
        holder.tvmalop.setText(lop.getMalop());
        holder.tvtenlop.setText(lop.getTenlop());

        holder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.deleteLop(lopList.get(position));
                lopList.remove(position);
                databaseHelper.updateLop(lop);
                notifyDataSetChanged();
                Toast.makeText(context, "xoa", Toast.LENGTH_SHORT).show();
            }
        });

        holder.imgedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setTitle(lopList.get(position).getMalop());
                dialog.setContentView(R.layout.dialog_edit_lop);

                final EditText edtmalop;
                final EditText edttenlop;
                final Button btnedit;
                final Button btncancel;

                edtmalop = dialog.findViewById(R.id.edtmalop);
                edttenlop = dialog.findViewById(R.id.edttenlop);
                btnedit = dialog.findViewById(R.id.btnedit);
                btncancel = dialog.findViewById(R.id.btncancel);

                edtmalop.setText(lopList.get(position).getMalop());
                edttenlop.setText(lopList.get(position).getTenlop());

                btnedit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Lop lop1 = new Lop();
                        lop1.setMalop(lopList.get(position).getMalop());
                        lop1.setTenlop(lopList.get(position).getTenlop());

                        databaseHelper.updateLop(lop1);

                        // cap nhat thay doi len giao dien
                        lopList.get(position).setMalop(edtmalop.getText().toString().trim());
                        lopList.get(position).setTenlop(edttenlop.getText().toString().trim());

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
        if (lopList == null) {
            return 0;
        }
        return lopList.size();
    }

    public class LopHolder extends RecyclerView.ViewHolder {

        ImageView imganh;
        TextView tvmalop;
        TextView tvtenlop;
        ImageView imgedit;
        ImageView imgdelete;

        public LopHolder(View itemView) {
            super(itemView);

            imganh = itemView.findViewById(R.id.imganh);
            tvmalop = itemView.findViewById(R.id.tvmalop);
            tvtenlop = itemView.findViewById(R.id.tvtenlop);
            imgedit = itemView.findViewById(R.id.imgedit);
            imgdelete = itemView.findViewById(R.id.imgdelete);

        }

    }
}
