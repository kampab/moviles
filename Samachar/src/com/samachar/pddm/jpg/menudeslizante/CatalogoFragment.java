package com.samachar.pddm.jpg.menudeslizante;

import java.util.ArrayList;

import com.example.dom.R;
import com.samachar.pddm.jpg.internet.DescargarCatalogos;
import com.samachar.pddm.jpg.model.Catalogo;
import com.samachar.pddm.jpg.parser.RssParserDom;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

@SuppressLint("NewApi")
public class CatalogoFragment extends Fragment {
	private static final String[] TITULARES = {
			"http://estaticos.elmundo.es/elmundo/rss/portada.xml", 
			"http://www.oas.org/documents/spa/rssNews.asp" };
	private ArrayList<Catalogo> canales;

	public CatalogoFragment() {
		canales = new ArrayList<Catalogo>();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_catalogo, container,
				false);

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		
		DescargarCatalogos descargarCatalogos = new DescargarCatalogos(getActivity(), getActivity().getApplicationContext());
		descargarCatalogos.execute(TITULARES);
	}
}



	

