package com.samachar.pddm.jpg.fragmentoContenido;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.example.dom.R;
import com.example.dom.R.id;
import com.example.dom.R.layout;
import com.example.dom.R.menu;

import android.os.Bundle;
import android.provider.MediaStore.Images.ImageColumns;
import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Layout;
import android.text.method.LinkMovementMethod;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class NoticiasDescripcion extends Activity {
	TextView desc;
	Application contexto;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_descripcion);
		
		contexto = getApplication();

		
		
		String url =getIntent().getStringExtra("descripcion");
		
		desc = (TextView) findViewById(R.id.noticiaDescripcion);
		desc.setMovementMethod(LinkMovementMethod.getInstance());
		desc.setText(Html.fromHtml(url.trim()));
		
		ImageView img = (ImageView) findViewById(R.id.noticiaImagen);
		try {
			img.setImageDrawable(descargarImagen(url.trim()));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}

	private Drawable descargarImagen(String url) throws MalformedURLException, IOException{
		return Drawable.createFromStream((InputStream)new URL(url).getContent(), "src");
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.noticias_descripcion, menu);
	
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Dialog dialogo = new Dialog(this);
		dialogo.setTitle("Ajustes");
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.layout_dialogo,
				(ViewGroup) findViewById(R.layout.activity_main));
		dialogo.setContentView(layout);
		dialogo.show();

		SeekBar brilloControl = (SeekBar) layout
				.findViewById(R.id.guardado_brillo);

		brilloControl
				.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
					}

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						Log.i("brillo", progress + " ");
						if (progress < 5)
							progress = 5;
						float valor = (float) progress / 100;
						WindowManager.LayoutParams layoutparametros = getWindow()
								.getAttributes();
						layoutparametros.screenBrightness = valor;
						getWindow().setAttributes(layoutparametros);
					}
				});

		RadioGroup rg = (RadioGroup) layout.findViewById(R.id.radio_grupo);
		rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				View layout = getWindow().getDecorView();
				
				
				switch (checkedId) {
				case R.id.radio_blanco:				
					layout.setBackgroundColor(Color.WHITE);
					desc.setTextColor(Color.BLACK);
					Toast.makeText(getApplicationContext(), "blanco", Toast.LENGTH_LONG).show();
					break;
				case R.id.radio_negro:
					layout.setBackgroundColor(Color.BLACK);
					desc.setTextColor(Color.WHITE);
					Toast.makeText(getApplicationContext(), "negro", Toast.LENGTH_LONG).show();
					break;
				default:

				}

			}
			
		});
		

		
		
		
		return super.onOptionsItemSelected(item);
	}
	


}
