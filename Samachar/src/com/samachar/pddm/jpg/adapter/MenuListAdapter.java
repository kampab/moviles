package com.samachar.pddm.jpg.adapter;


import java.util.ArrayList;

import com.example.dom.R;
import com.samachar.pddm.jpg.model.DMenuItem;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuListAdapter extends BaseAdapter {
	
	private Context context;
	private ArrayList<DMenuItem> menuItems;
	
	public MenuListAdapter(Context context, ArrayList<DMenuItem> menuItems){
		this.context = context;
		this.menuItems = menuItems;
	}

	@Override
	public int getCount() {
		return menuItems.size();
	}

	@Override
	public Object getItem(int position) {		
		return menuItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.menu_list_item, null);
        }
         
        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitulo = (TextView) convertView.findViewById(R.id.title);
        TextView txtCont = (TextView) convertView.findViewById(R.id.counter);
         
        imgIcon.setImageResource(menuItems.get(position).geticono());        
        txtTitulo.setText(menuItems.get(position).gettitulo());
        
        //muestra la cuenta de si visibile o no 
        
        if(menuItems.get(position).getCounterVisibility()){
        	txtCont.setText(menuItems.get(position).getCont());
        }else{
        	// oculta cont
        	txtCont.setVisibility(View.GONE);
        }
        
        return convertView;
	}

}
