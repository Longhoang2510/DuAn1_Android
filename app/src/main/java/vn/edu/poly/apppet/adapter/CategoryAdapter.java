package vn.edu.poly.apppet.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import vn.edu.poly.apppet.R;
import vn.edu.poly.apppet.database.DatabaseHelper;
import vn.edu.poly.apppet.database.TypeDAO;
import vn.edu.poly.apppet.model.Type;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryHoler> {

    private Context context;
    private List<Type> types;
    private TypeDAO typeDAO;

    public CategoryAdapter(Context context, List<Type> types, TypeDAO typeDAO) {
        this.context = context;
        this.types = types;
        this.typeDAO = typeDAO;
    }

    @NonNull
    @Override
    public CategoryHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (context).inflate (R.layout.item_danh_muc, parent, false);
        return new CategoryHoler (view);

    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryHoler holder, final int position) {
        final Type type = types.get (position);
        holder.tvDanhMuc.setText (type.id);

        holder.btnXoaDanhMuc.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                DatabaseHelper databaseHelper = new DatabaseHelper (context);
                TypeDAO typeDAO = new TypeDAO (databaseHelper);

                long re = typeDAO.deleteType (type.id);
                if(re > 0){
                    types.remove (position);
                    notifyDataSetChanged ();
                    Toast.makeText (context, "Xóa thành công", Toast.LENGTH_SHORT).show ();
                }else {
                    Toast.makeText (context, "Lỗi xóa", Toast.LENGTH_SHORT).show ();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return types.size ();
    }
}
