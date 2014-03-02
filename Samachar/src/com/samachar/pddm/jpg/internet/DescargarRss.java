

package com.samachar.pddm.jpg.internet;

import java.util.List;

import com.example.dom.R;
import com.samachar.pddm.jpg.model.Noticia;
import com.samachar.pddm.jpg.parser.RssParserDom;
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

// Clase que sirve para descargar el contenido de URL en segundo plano
public class DescargarRss extends AsyncTask<String, Integer, Boolean> {
	List<Noticia> noticias;
	ListView lista;
	private Context contexto;
	Activity actividad;

	/*
	 * Descarga el contenido de noticia y despues da formato
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */

	public DescargarRss(Activity actividad, Context contexto) {
		this.contexto = contexto;
		this.actividad = actividad;
		lista = (ListView) actividad.findViewById(R.id.listaNoticias);
		Log.i("Constructor", "");

	}

	@Override
	protected Boolean doInBackground(String... params) {
		Log.i("InBackground", params[0]);
		RssParserDom parser = new RssParserDom(params[0]);
		RssParserDom parser1 = new RssParserDom(params[1]);
		Log.i("Hacer parse", "");
		noticias = parser.parse();
		noticias.addAll(parser1.parse());
		Log.i("Parseado", noticias.toString());
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

		// listview de noticias
		NoticiasAdapter adapter = new NoticiasAdapter(contexto, noticias);

		lista.setAdapter(adapter);

		lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				
				Intent i = new Intent(contexto.getApplicationContext(),NoticiasDescripcion.class);
				i.putExtra("descripcion", noticias.get(position).getDescripcion());
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				contexto.getApplicationContext().startActivity(i);
				
				/*
				Toast.makeText(contexto,
						noticias.get(position).getDescripcion(),
						Toast.LENGTH_LONG).show();

				LayoutInflater li = (LayoutInflater) contexto
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View v = li.inflate(R.layout.toast, null);

				WebView txtMessage = (WebView) v.findViewById(R.id.toast_web);

				txtMessage.loadUrl(noticias.get(position).getLink());

				Toast toast = new Toast(contexto);
				toast.setView(v);
				toast.setDuration(20000);
				toast.show();
				*/
			}
		});

	}

}
