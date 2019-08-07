package vn.edu.poly.apppet.fragment;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import vn.edu.poly.apppet.R;
import vn.edu.poly.apppet.adapter.DetailAdapter;
import vn.edu.poly.apppet.model.Care;


public class DetailCareFragment extends Fragment {

    private View d;
    String pet_name;
    DatabaseReference mData;
    ListView lvDetail;
    List<Care> cares;
    DetailAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         d =inflater.inflate (R.layout.fragment_detail_care, container, false);


         //khai bao
        lvDetail = d.findViewById (R.id.lvDetail);

        //lay su lieu
        Bundle bundle = getArguments ();
        pet_name = bundle.getString ("pet_name");
//        Toast.makeText (getActivity (), pet_name, Toast.LENGTH_SHORT).show ();

        //khai bao data base
        mData = FirebaseDatabase.getInstance ().getReference ();


        //xet adapter
        cares = new ArrayList<> ();
        adapter = new DetailAdapter (getActivity (), R.layout.item_detail, cares );
        lvDetail.setAdapter (adapter);


        lvDetail.setOnItemClickListener (new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DetailPetFragment detailPetFragment = new DetailPetFragment ();

                Bundle bundle = new Bundle ();
                bundle.putString ("tille",cares.get (position).tieude);
                bundle.putString ("noidung",cares.get (position).noidung);
                bundle.putString ("linkimage", cares.get (position).linkimage);
                detailPetFragment.setArguments (bundle);


                Fragment fragment = detailPetFragment;
                FragmentManager fragmentManager = getFragmentManager ();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
                fragmentTransaction.replace (R.id.frameLayout, fragment);
                fragmentTransaction.addToBackStack (null);

                fragmentTransaction.commit ();


            }
        });




//        Care care = new Care ("tieude", "noidung ", "aa");
//        mData.child ("CareCat").push ().setValue (care);

        mData.child (pet_name).addChildEventListener (new ChildEventListener () {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Care c = dataSnapshot.getValue (Care.class);
                cares.add (c);
                adapter.notifyDataSetChanged ();

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




        return d;
    }
}
