package com.samachar.pddm.jpg.mapas;


import com.example.dom.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;
/**
 * Actividad que extiende de fragment activity para poder utilizar los mapas de android y muestra un mapa con localización por gps
 * @author pablo
 *
 */
public class Mapa extends android.support.v4.app.FragmentActivity{
	private GoogleMap mapa;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_mapas);
		configGPS();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	/**
	 *Configura el gps recogiendo el servicio de localizacion del proveedor de gps y configura la forma de recepción de actualizaciones de ubicación.
	 *En este caso cada 5 segundos de espera desde la última recepción y 1000m de distancia a la última posición se ejecuta el metodo onlocationchanged del listener asignado
	 */
	public void configGPS()
	{
		LocationManager mLocationManager;
		LocationListener mLocationListener;
		mLocationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
		mLocationListener=new MiLocationListener();
		mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1000, mLocationListener);
	}
	/**
	 * Pone un marcador en las coordenadas del mapa que se le pasen por parámetros con una imagen de la carpeta drawable
	 * @param location localización de la ubicación donde se añadirá el marcador
	 */
	public void marcador(Location location)
	{
		mapa = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		mapa.addMarker(new MarkerOptions()
        .position(new LatLng(location.getLatitude(),location.getLongitude()))
        .title("Tu ubicación")
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.loc))
        .anchor(0.5f, 0.5f));	
	}
	/*public void marcador(LatLng location)
	{
		mapa = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		mapa.addMarker(new MarkerOptions()
        .position(new LatLng(location.latitude,location.longitude))
        .title("Tu ubicación")
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.loc))
        .anchor(0.5f, 0.5f));	
	}*/
	/**
	 * Hereda de LocationListener y recoge automaticamente todos los eventos relacionados con el cambio de estado de la informacion gps
	 * @author pablo
	 *
	 */
	public class MiLocationListener implements LocationListener
	{	
		/*Para cambiar la ubicación del gps:
		telnet localhost 5554
		geo fix -3.55397 39.41253
		*/
		@Override
		public void onLocationChanged(Location location) 
		{
			Log.d("HelloGPSActivity","Latitud: "+ String.valueOf(location.getLatitude()));
			Log.d("HelloGPSActivity","Longtud: "+ String.valueOf(location.getLongitude()));
			marcador(location);
		}
		//Se ejecuta cuando el gps se desactiva
		@Override
		public void onProviderDisabled(String provider) 
		{
			Vibrator vibracion = (Vibrator) getSystemService(getApplicationContext().VIBRATOR_SERVICE);
			vibracion.vibrate(1000);
			Toast desactivado=Toast.makeText(getApplicationContext(),"GPS desactivado",Toast.LENGTH_SHORT);
			desactivado.show();
		}
		//Se ejecuta cuando el gps se activa
		@Override
		public void onProviderEnabled(String provider) 
		{
			Vibrator vibracion = (Vibrator) getSystemService(getApplicationContext().VIBRATOR_SERVICE);
			vibracion.vibrate(1000);
			Toast activado=Toast.makeText(getApplicationContext(),"GPS activado",Toast.LENGTH_SHORT);
			activado.show();
		}
		//Detecta el cambio de estado del provider
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) 
		{
			// TODO Auto-generated method stub
			
		}

	}

		

}
