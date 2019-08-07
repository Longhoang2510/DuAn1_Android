package vn.edu.poly.apppet.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import vn.edu.poly.apppet.R;
import vn.edu.poly.apppet.model.Care;

public class DetailAdapter extends ArrayAdapter<Care> {

    private List<Care> cares;
    private LayoutInflater inflater;

    public DetailAdapter(@NonNull Context context, int resource, @NonNull List<Care> objects) {
        super(context, resource, objects);
        this.cares = objects;
        this.inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        viewHolder holder;
        if (convertView == null) {//lan dau tao view item
            holder = new viewHolder();
            convertView = inflater.inflate(R.layout.item_detail, parent, false);
            holder.tvTille = convertView.findViewById(R.id.tvTille);
            holder.imvCare = convertView.findViewById (R.id.imvCare);
            convertView.setTag(holder);

        } else {// da tao view cho item roi
            holder = (viewHolder) convertView.getTag();

        }

        //thiet lap gia tri cho item
        //lay phan tu mang dang lam viec voi item

        Care care = cares.get (position);
        holder.tvTille.setText (care.tieude);
        Picasso.get ().load(care.linkimage).into(holder.imvCare);


        return convertView;
    }

    /**
     * dungf đẻ tối ưu hóa khi cuốn lên cuấn xuống
     */
    public static class viewHolder {
        public TextView tvTille;
        public ImageView imvCare;

    }


}
