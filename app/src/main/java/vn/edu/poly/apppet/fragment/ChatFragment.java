package vn.edu.poly.apppet.fragment;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import vn.edu.poly.apppet.R;
import vn.edu.poly.apppet.database.DatabaseHelper;
import vn.edu.poly.apppet.database.UserDAO;
import vn.edu.poly.apppet.model.User;


public class ChatFragment extends Fragment {

    private View v;

    private LinearLayout chatDog;
    private LinearLayout chatCat;

    private Spinner spnThanhPho;
    private DatabaseHelper databaseHelper;
    private UserDAO userDAO;

    private String name;
    public byte[] imvByte;

    private DatabaseReference mData = FirebaseDatabase.getInstance ().getReference ().getRoot ();

    private String thanhpho;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate (R.layout.fragment_chat, container, false);

        intivity ();
        checkNetworkStatus ();


        //lay ten
        databaseHelper = new DatabaseHelper (getActivity ());
        userDAO = new UserDAO (databaseHelper);
        name = userDAO.getName ("1");
        imvByte = userDAO.getImage ("1");


        //themdulieuThanhpho
//        mData.child ("ThanhPho").push ().setValue ("Hà Nội");
//        mData.child ("ThanhPho").push ().setValue ("Hồ Chí Minh");


        final ArrayList<String> arrayTp = new ArrayList<String> ();
        arrayTp.add ("Chọn thành phố");
        mData.child ("ThanhPho").addChildEventListener (new ChildEventListener () {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                arrayTp.add (dataSnapshot.getValue ().toString ());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //xetspiner
        ArrayAdapter arrayAdapter = new ArrayAdapter (getActivity (), android.R.layout.simple_spinner_item, arrayTp);
        arrayAdapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
        spnThanhPho.setAdapter (arrayAdapter);

        spnThanhPho.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener () {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                thanhpho = arrayTp.get (position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        chatDog.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                //kiemtra Internet
                boolean check = checkNetworkStatus ();
                if (check) {

                } else {
                    Toast.makeText (getActivity (), "Yêu cầu kết nối mạng! ", Toast.LENGTH_SHORT).show ();
                    return;
                }


                if (name.equals ("null")) {
                    Toast.makeText (getActivity (), "Bạn cần cập nhật thông tin để thực hiện chức năng này!", Toast.LENGTH_SHORT).show ();
                } else {
                    ChatDogFunction ();
                }


            }
        });


        chatCat.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                //kiemtra Internet
                boolean check = checkNetworkStatus ();
                if (check) {

                } else {
                    Toast.makeText (getActivity (), "Yêu cầu kết nối mạng! ", Toast.LENGTH_SHORT).show ();
                    return;
                }


                User usercheck = new User ();
                usercheck = userDAO.getUserById ("1");
                if (usercheck.name == null) {
                    Toast.makeText (getActivity (), "Bạn cần cập nhật thông tin để thực hiện chức năng này!", Toast.LENGTH_SHORT).show ();
                } else {
                    ChatCatFunction ();
                }


            }
        });


        return v;
    }

    private void ChatCatFunction() {
        String tp;


        if (thanhpho.equalsIgnoreCase ("Chọn thành phố")) {
            Toast.makeText (getActivity (), "Yêu cầu bạn chọn thành phố!", Toast.LENGTH_SHORT).show ();
            return;

        } else if (thanhpho.equalsIgnoreCase ("Hà Nội")) {
            tp = "HN";

        } else if (thanhpho.equalsIgnoreCase ("Hồ Chí Minh")) {
            tp = "HCM";

        } else {
            Toast.makeText (getActivity (), "Lỗi!", Toast.LENGTH_SHORT).show ();
            return;
        }

        MessengerFragment messengerFragment = new MessengerFragment ();

        Bundle bundle = new Bundle ();
        bundle.putString ("room_name", "ChatCat" + tp);
        bundle.putString ("user_name", name);
        bundle.putByteArray ("imvByte", imvByte);
        messengerFragment.setArguments (bundle);

        Fragment fragment = messengerFragment;
        FragmentManager fragmentManager = getFragmentManager ();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
        fragmentTransaction.replace (R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack (null);

        fragmentTransaction.commit ();
    }


    private void ChatDogFunction() {
        String tp;

        if (thanhpho.equalsIgnoreCase ("Chọn thành phố")) {
            Toast.makeText (getActivity (), "Yêu cầu bạn chọn thành phố!", Toast.LENGTH_SHORT).show ();
            return;

        } else if (thanhpho.equalsIgnoreCase ("Hà Nội")) {
            tp = "HN";

        } else if (thanhpho.equalsIgnoreCase ("Hồ Chí Minh")) {
            tp = "HCM";

        } else {
            Toast.makeText (getActivity (), "Lỗi!", Toast.LENGTH_SHORT).show ();
            return;
        }

        MessengerFragment messengerFragment = new MessengerFragment ();

        Bundle bundle = new Bundle ();
        bundle.putString ("room_name", "ChatDog" + tp);
        bundle.putString ("user_name", name);
        bundle.putByteArray ("imvByte", imvByte);
        messengerFragment.setArguments (bundle);


        Fragment fragment = messengerFragment;
        FragmentManager fragmentManager = getFragmentManager ();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
        fragmentTransaction.replace (R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack (null);


        fragmentTransaction.commit ();        //kiemtra Internet
        boolean check = checkNetworkStatus ();
        if (check){

        }else {
            Toast.makeText (getActivity (), "Yêu cầu kết nối mạng! ", Toast.LENGTH_SHORT).show ();
            return;
        }



    }

    private void intivity() {
        spnThanhPho = v.findViewById (R.id.spnThanhPho);
        chatDog = v.findViewById (R.id.chatDog);
        chatCat = v.findViewById (R.id.chatCat);
    }

    private boolean checkNetworkStatus() {
        ConnectivityManager cm = (ConnectivityManager) getActivity ().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }


}
