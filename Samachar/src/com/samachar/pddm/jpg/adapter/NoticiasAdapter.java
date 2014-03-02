package com.samachar.pddm.jpg.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dom.R;
import com.samachar.pddm.jpg.internet.DescargarImagen;
import com.samachar.pddm.jpg.model.Noticia;

//Esta clase se encargar se crear listview con las noticias
// descargadas
// y imagen
public class NoticiasAdapter extends ArrayAdapter<Noticia> {
	private Context context;
	private List<Noticia> datos;

	public NoticiasAdapter(Context context, List<Noticia> objects) {
		super(context, R.layout.layout_titulos, objects);
		this.context = context;
		this.datos = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Esta variable ayuda no sobrecargar el sistema porque
		// guarda
		// los datos que hayan sido cargado en listview y después
		// recupera con el tag
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.layout_titulos, null);
			holder = new ViewHolder();
			holder.fecha = (TextView) convertView
					.findViewById(R.id.titulo_titulo);
			holder.titulo = (TextView) convertView
					.findViewById(R.id.titulo_fecha);
			holder.imagen = (ImageView) convertView
					.findViewById(R.id.titulo_imagen);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Noticia noticia = (Noticia) datos.get(position);
		holder.titulo.setText(noticia.getTitulo());
		holder.fecha.setText(noticia.getFecha());

		if (holder.imagen != null) {
			if (noticia.getImagen() != null) {
				Log.i("imagen a descargar posicion " + position,
						noticia.getImagen());
				// La imagen se descarga en segundo plano
				new DescargarImagen(holder.imagen).execute(noticia.getImagen());
			}
		}

		return convertView;
	}

	class ViewHolder {
		TextView titulo;
		TextView fecha;
		ImageView imagen;
	}

}
