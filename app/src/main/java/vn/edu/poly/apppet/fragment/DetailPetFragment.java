package vn.edu.poly.apppet.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import vn.edu.poly.apppet.R;


public class DetailPetFragment extends Fragment {
    private View dp;

    private TextView tvTilleCare;
    private TextView tvNoiDung;
    private ImageView imvDetail;
    String tieude, noidung, linkimage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        dp =inflater.inflate (R.layout.fragment_detail_pet, container, false);

        tvTilleCare = dp.findViewById(R.id.tvTilleCare);
        tvNoiDung = dp.findViewById(R.id.tvNoiDung);
        imvDetail = dp.findViewById (R.id.imvDetail);

        Bundle bundle = getArguments ();
        tieude = bundle.getString ("tille");
        noidung = bundle.getString ("noidung");
        linkimage = bundle.getString ("linkimage");


        tvTilleCare.setText (tieude);
        tvNoiDung.setText (noidung);

        Glide.with (getActivity ()).load (linkimage).into (imvDetail);
        return dp;
    }








}
