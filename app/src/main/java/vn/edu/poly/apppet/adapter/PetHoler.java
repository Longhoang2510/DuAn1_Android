package vn.edu.poly.apppet.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import vn.edu.poly.apppet.R;

public class PetHoler extends RecyclerView.ViewHolder {

    public TextView tvTypePet;
    public TextView tvLoai;
    public TextView tvTime;



    public PetHoler(View itemView) {
        super (itemView);

        tvTypePet = itemView.findViewById (R.id.tvTypePet);
        tvLoai = itemView.findViewById (R.id.tvLoai);
        tvTime = itemView.findViewById (R.id.tvTime);
    }
}
