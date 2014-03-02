package com.samachar.pddm.jpg.internet;

import java.util.ArrayList;
import java.util.List;

import com.example.dom.R;
import com.samachar.pddm.jpg.model.Catalogo;
import com.samachar.pddm.jpg.model.Noticia;
import com.samachar.pddm.jpg.parser.RssParserDom;
import com.samachar.pddm.jpg.adapter.CatalogosAdapter;
import com.samachar.pddm.jpg.adapter.NoticiasAdapter;
import com.samachar.pddm.jpg.fragmentoContenido.NoticiasDescripcion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

// Clase que sirve para descargar el contenido de URL en segundo plano
public class DescargarCatalogos extends AsyncTask<String, Integer, Boolean> {
	List<Catalogo> catalogos;
	ListView lista;
	private Context contexto;
	Activity actividad;

	/*
	 * Descarga el contenido de noticia y despues da formato
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */

	public DescargarCatalogos(Activity actividad, Context contexto) {
		this.contexto = contexto;
		this.actividad = actividad;
		lista = (ListView) actividad.findViewById(R.id.listaCatalogo);
		Log.i("Constructor", "");

	}

	@Override
	protected Boolean doInBackground(String... params) {
		Log.i("InBackground", params[0]);

		catalogos = new ArrayList<Catalogo>();
		
		for (int i = 0; i < params.length; i++) {
			RssParserDom parser = new RssParserDom(params[i]);
			catalogos.add(parser.parseTitulares());
			Log.i("Parseado catalogos", catalogos.get(i).getNombre());
		}

		return true;
	}

	/*
	 * La noticia descargada es mostrado en listview
	 * 
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		Log.i("OnpostExecute", result.toString());

		//TextView txt = (TextView) actividad.findViewById(R.id.txtCatalogo);
		//for(int i=0;i<catalogos.size();i++)
			//txt.setText(txt.getText() + " " + catalogos.get(i).getNombre());
		
		
		// listview de noticias
		CatalogosAdapter adapter = new CatalogosAdapter(contexto, catalogos);

		lista.setAdapter(adapter);
		
		Log.i("Adapter Catalogo", "Asignado adapter");

		
		lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

		
			}
		});
		

	}

}
