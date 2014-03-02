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
import com.samachar.pddm.jpg.model.Catalogo;
import com.samachar.pddm.jpg.model.Noticia;

//Esta clase se encargar se crear listview con las noticias
// descargadas
// y imagen
public class CatalogosAdapter extends ArrayAdapter<Catalogo> {
	private Context context;
	private List<Catalogo> datos;

	public CatalogosAdapter(Context context, List<Catalogo> objects) {
		super(context, R.layout.fragment_catalogo, objects);
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
			convertView = inflater.inflate(R.layout.layout_catalogos, null);
			holder = new ViewHolder();
			holder.imagen = (ImageView) convertView
					.findViewById(R.id.catalogoImagen);
			holder.nombre = (TextView) convertView.findViewById(R.id.catalogoNombre);
			holder.suscrito= (ImageView) convertView.findViewById(R.id.catalogoSuscrito);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Catalogo catalogo = (Catalogo) datos.get(position);
		holder.nombre.setText(catalogo.getNombre());
		holder.suscrito.setImageResource(R.drawable.ic_catalogo);

		if (holder.imagen != null) {
			if (catalogo.getImagen() != null) {
				Log.i("imagen a descargar posicion " + position,
						catalogo.getImagen());
				// La imagen se descarga en segundo plano
				new DescargarImagen(holder.imagen).execute(catalogo.getImagen());
			}
		}

		return convertView;
	}

	class ViewHolder {
		TextView nombre;	
		ImageView imagen;
		ImageView suscrito;
	}

}
