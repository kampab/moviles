package com.samachar.pddm.jpg.internet;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import com.example.dom.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class DescargarImagen extends AsyncTask<String, Void, Bitmap> {
	private final WeakReference<ImageView> imagenReferencia;

	public DescargarImagen(ImageView imageView) {
		imagenReferencia = new WeakReference<ImageView>(imageView);
	}

	// Descarga la imagen en segundo plano
	@Override
	protected Bitmap doInBackground(String... params) {
		return  descargarBitmap(params[0]);
		
	}

	//Cuando la imagen se ha terminado se descargar, se asigna a imageview
	@Override
	protected void onPostExecute(Bitmap bitmap) {
		if (isCancelled()) {
			bitmap = null;
		}

		if (imagenReferencia != null) {
			ImageView imageView = imagenReferencia.get();
			if (imageView != null) {
				
				if (bitmap != null) {
					imageView.setImageBitmap(bitmap);
				} else {
					imageView
							.setImageDrawable(imageView.getContext()
							.getResources()
							.getDrawable(R.drawable.ic_launcher));
				}
			}
		}
	}

	
	
	
	
	
	
	static Bitmap descargarBitmap(String url) {
		final AndroidHttpClient cliente = AndroidHttpClient.newInstance("Android");
		final HttpGet getRequest = new HttpGet(url);

		try {
			HttpResponse respuesta = cliente.execute(getRequest);
			final int statusCodigo = respuesta.getStatusLine().getStatusCode();
			if (statusCodigo != HttpStatus.SC_OK) {
				Log.i("DescargarImagen", "Error " + statusCodigo
						+ "Error mientras se descargaba la imagen " + url);	
				return null;
			}

			final HttpEntity entity = respuesta.getEntity();
			if (entity != null) {
				InputStream inputStream = null;
				try {
					inputStream = entity.getContent();
					final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
					return bitmap;
				}finally{
					if(inputStream!=null){
						cliente.close();
						inputStream.close();
					}
					entity.consumeContent();
				}
				
			}
		} catch (IOException e) {
			getRequest.abort();
			e.printStackTrace();
		} finally {
			if (cliente != null) {
				cliente.close();
			}
		}
		return null;
	}

}
