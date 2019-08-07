package vn.edu.poly.apppet.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import vn.edu.poly.apppet.R;

public class CategoryHoler extends RecyclerView.ViewHolder {

    public TextView tvDanhMuc;
    public Button btnXoaDanhMuc;


    public CategoryHoler(View itemView) {
        super (itemView);


        tvDanhMuc = itemView.findViewById(R.id.tvDanhMuc);
        btnXoaDanhMuc = itemView.findViewById(R.id.btnXoaDanhMuc);



    }
}
