package vn.edu.poly.apppet.adapter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vn.edu.poly.apppet.R;
import vn.edu.poly.apppet.database.DatabaseHelper;
import vn.edu.poly.apppet.database.PetDAO;
import vn.edu.poly.apppet.database.TypeDAO;
import vn.edu.poly.apppet.model.Pet;

public class PetAdapter extends RecyclerView.Adapter<PetHoler> {

    private Context context;
    private List<Pet> pets;
    private PetDAO petDAO;


    public PetAdapter(Context context, List<Pet> pets, PetDAO petDAO) {
        this.context = context;
        this.pets = pets;
        this.petDAO = petDAO;
    }

    @NonNull
    @Override
    public PetHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (context).inflate (R.layout.item_pet, parent, false);
        return new PetHoler (view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetHoler holder, final int position) {

        final Pet pet = pets.get (position);
        holder.tvTypePet.setText ("Danh mục: " + pet.idType);
        holder.tvLoai.setText ("Loại: " + pet.ten);
        holder.tvTime.setText ("Thời gian: " + pet.gio + "      " + pet.ngay);


        holder.itemView.setOnLongClickListener (new View.OnLongClickListener () {
            @Override
            public boolean onLongClick(View v) {

                DialogCapNhat (position);

                return false;
            }
        });


    }


    @Override
    public int getItemCount() {
        return pets.size ();
    }

    private void DialogCapNhat(final int position) {
        final Pet pet = pets.get (position);
        final Dialog dialog = new Dialog (context);
        dialog.requestWindowFeature (Window.FEATURE_NO_TITLE);
        dialog.setContentView (R.layout.diglog_capnhat);


        final Spinner spTypecn;
        final EditText edtNamecn;
        final TextView tvGiocn;
        final Button btGiocn;
        final TextView tvNgaycn;
        final Button btNgaycn;
        final EditText edtNotecn;
        final Button btHuycn;
        final Button btXoa;
        final Button btCapNhat;
        final String[] stTenTheLoai = new String[1];

        spTypecn = dialog.findViewById (R.id.spTypecn);
        edtNamecn = dialog.findViewById (R.id.edtNamecn);
        tvGiocn = dialog.findViewById (R.id.tvGiocn);
        btGiocn = dialog.findViewById (R.id.btGiocn);
        tvNgaycn = dialog.findViewById (R.id.tvNgaycn);
        btNgaycn = dialog.findViewById (R.id.btNgaycn);
        edtNotecn = dialog.findViewById (R.id.edtNotecn);
        btHuycn = dialog.findViewById (R.id.btHuycn);
        btXoa = dialog.findViewById (R.id.btXoa);
        btCapNhat = dialog.findViewById (R.id.btCapNhat);

        //xet
        edtNamecn.setText (pet.ten);
        tvGiocn.setText (pet.gio);
        tvNgaycn.setText (pet.ngay);
        edtNotecn.setText (pet.note);

        //Xu ly spinner
        final TypeDAO manager;
        final DatabaseHelper databaseHelper;
        databaseHelper = new DatabaseHelper (context);
        manager = new TypeDAO (databaseHelper);

        List<String> list;


        list = manager.getAllType ();

//        list.add("Ăn");
//        list.add("Uống");


        final ArrayAdapter<String> adapter = new ArrayAdapter (context, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource (android.R.layout.simple_list_item_single_choice);
        spTypecn.setAdapter (adapter);

        if (list != null) {

            for (int i = 0; i < list.size (); i++) {

                if (list.get (i).equals (pet.idType)) {
                    int index = i;
                    spTypecn.setSelection (index);
                    break;
                }

            }

        }


        spTypecn.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener () {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stTenTheLoai[0] = spTypecn.getSelectedItem () + "";


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //xu ly ngay gio

        btGiocn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                showTimePickerDialog (tvGiocn);
            }
        });

        btNgaycn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                showDatePickerDialog (tvNgaycn);
            }
        });


        btCapNhat.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                Pet pet1 = new Pet ();
                String type = stTenTheLoai[0].toString ();
                String name = edtNamecn.getText ().toString ().trim ();
                String gio = tvGiocn.getText ().toString ();
                String ngay = tvNgaycn.getText ().toString ().trim ();
                String note = edtNotecn.getText ().toString ().trim ();

                if(name.equals ("")){
                    Toast.makeText (context, "Không được để trống !", Toast.LENGTH_SHORT).show ();
                    return;
                }

                pet1.idPet = pet.idPet;
                pet1.idType = type;
                pet1.ten = name;
                pet1.gio = gio;
                pet1.ngay = ngay;
                pet1.note = note;

                long re = petDAO.updatePet (pet1);

                if (re > 0) {

                    Toast.makeText (context, "Cập nhật thành công", Toast.LENGTH_SHORT).show ();
                    pets.get (position).idPet = pet.idPet;

                    pets.get (position).idType = type;
                    pets.get (position).ten = name;
                    pets.get (position).gio = gio;
                    pets.get (position).ngay = ngay;
                    pets.get (position).note = note;

                    notifyDataSetChanged ();
                    dialog.cancel ();

                } else {
                    // update ko thanh cong
                    Toast.makeText (context, "Cập nhật không thành công!", Toast.LENGTH_SHORT).show ();
                }

            }
        });

        btXoa.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                long re = petDAO.deletePet (pet.idPet);

                if (re > 0) {

                    pets.remove (position);
                    notifyDataSetChanged ();
                    dialog.cancel ();
                    Toast.makeText (context, "Đã xóa", Toast.LENGTH_SHORT).show ();

                } else {
                    // ko thanh cong
                    Toast.makeText (context, "Không thể xóa!", Toast.LENGTH_SHORT).show ();
                }
            }
        });


        btHuycn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                dialog.cancel ();
            }
        });


        //custom dialog
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams ();
        lp.copyFrom (dialog.getWindow ().getAttributes ());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show ();
        dialog.getWindow ().setAttributes (lp);


    }


    public void showTimePickerDialog(final TextView gio) {

        //lấy thời gian và hiện tại
        Calendar cal = Calendar.getInstance ();
        final int hour = cal.get (Calendar.HOUR_OF_DAY);
        int minute = cal.get (Calendar.MINUTE);

        //b2: khoi tạo time picker dialog
        TimePickerDialog time = new TimePickerDialog (context, new TimePickerDialog.OnTimeSetListener () {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

//                Toast.makeText (getActivity (), hourOfDay+ ":" + minute, Toast.LENGTH_SHORT).show ();
                gio.setText (hourOfDay + ":" + minute);

            }
        }, hour, minute, false);

        //b3: hien thi
        time.show ();


    }

    public void showDatePickerDialog(final TextView ngay) {

        //b1: lấy thông tin ngày tháng
        Calendar cal = Calendar.getInstance ();
        int year = cal.get (Calendar.YEAR);
        int month = cal.get (Calendar.MONTH);
        int day = cal.get (Calendar.DAY_OF_MONTH);

        //b2: khởi tạo date picker dialog
        DatePickerDialog date = new DatePickerDialog (context, new DatePickerDialog.OnDateSetListener () {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                Toast.makeText (getActivity (), year+"/"+month+"/"+dayOfMonth, Toast.LENGTH_SHORT).show ();
                ngay.setText (dayOfMonth + "/" + month + "/" + year);
            }
        }, year, month, day);


        date.show ();


    }


}
