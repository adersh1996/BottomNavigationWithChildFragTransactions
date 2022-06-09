package com.adersh.srishti.bottomnavigationtutorial;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class HomeFragmentThree extends Fragment {

    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home_three, container, false);

        button=view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (savedInstanceState==null){

                    ((MainActivity)getActivity()).pushFragments(MainActivity.TAB_HOME, new HomeFragmentFour(),true);


                }

            }
        });


        return view;
    }
}