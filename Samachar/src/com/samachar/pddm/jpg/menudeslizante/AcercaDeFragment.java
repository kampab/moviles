package com.samachar.pddm.jpg.menudeslizante;

import com.example.dom.R;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("NewApi")
public class AcercaDeFragment extends Fragment {
	
	public AcercaDeFragment(){}
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_acerde, container, false);
         
        return rootView;
    }
}