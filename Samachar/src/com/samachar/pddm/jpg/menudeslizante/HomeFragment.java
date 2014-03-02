package com.samachar.pddm.jpg.menudeslizante;

import com.example.dom.R;
import com.samachar.pddm.jpg.internet.DescargarRss;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

@SuppressLint({ "ValidFragment", "NewApi" })
public class HomeFragment extends Fragment {
	Context contexto;

	public HomeFragment(Context contexto) {
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_home, container,
				false);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// Crear arraylist
		if (conexion()) {
			// Se crea la instancia de cargarXML task
			DescargarRss cargar = new DescargarRss(getActivity(), getActivity()
					.getApplicationContext());
			Log.i("MainActivity", "Descargar en segundo plano");
			// Descargar la contenido de URL
			cargar.execute("http://estaticos.elmundo.es/elmundo/rss/portada.xml","http://xml.newsisfree.com/feeds/52/5152.xml");
			//cargar.execute("http://xml.newsisfree.com/feeds/52/5152.xml");
		} else {
			// ImageView imagen = (ImageView)
			// findViewById(R.id.imagen_noConexion);
			// imagen.setImageResource(R.id.imagen_noConexion);
			Toast.makeText(getActivity(), "No hay conexion internet",
					Toast.LENGTH_LONG).show();
		}

	}

	public boolean conexion() {
		ConnectivityManager cm = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnected()) {
			return true;
		}
		return false;
	}
}
