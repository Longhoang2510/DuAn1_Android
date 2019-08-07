package vn.edu.poly.apppet.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import vn.edu.poly.apppet.R;

public class CareFragment extends Fragment {

    private View c;
    private LinearLayout imvDog;
    private LinearLayout imvCat;








    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        c = inflater.inflate (R.layout.fragment_care, container, false);

        intyviti();
        checkNetworkStatus();


        imvDog.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                boolean check = checkNetworkStatus ();
                if (check){

                }else {
                    Toast.makeText (getActivity (), "Yêu cầu kết nối mạng! ", Toast.LENGTH_SHORT).show ();
                    return;
                }

                DetailCareFragment detailCareFragment = new DetailCareFragment ();

                Bundle bundle = new Bundle ();
                bundle.putString ("pet_name", "CarePet" );
                detailCareFragment.setArguments (bundle);


                Fragment fragment = detailCareFragment;
                FragmentManager fragmentManager = getFragmentManager ();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
                fragmentTransaction.replace (R.id.frameLayout, fragment);
                fragmentTransaction.addToBackStack (null);
                fragmentTransaction.commit ();

            }
        });

        imvCat.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {


                boolean check = checkNetworkStatus ();
                if (check){

                }else {
                    Toast.makeText (getActivity (), "Yêu cầu kết nối mạng! ", Toast.LENGTH_SHORT).show ();
                    return;
                }



                DetailCareFragment detailCareFragment = new DetailCareFragment ();

                Bundle bundle = new Bundle ();
                bundle.putString ("pet_name", "CareCat" );
                detailCareFragment.setArguments (bundle);


                Fragment fragment = detailCareFragment;
                FragmentManager fragmentManager = getFragmentManager ();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
                fragmentTransaction.replace (R.id.frameLayout, fragment);
                fragmentTransaction.addToBackStack (null);
                fragmentTransaction.commit ();


            }
        });





        return c;
    }

    private void intyviti() {
        imvDog = c.findViewById(R.id.imvDog);
        imvCat = c.findViewById(R.id.imvCat);

    }


    private boolean checkNetworkStatus() {
        ConnectivityManager cm = (ConnectivityManager) getActivity ().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

}
