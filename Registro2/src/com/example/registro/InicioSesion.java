package com.example.registro;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Actividad que permite al usuario introducir los datos de su cuenta previamente creada para acceder a la aplicación
 * @author pablo
 *
 */
public class InicioSesion extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inicio_sesion);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inicio_sesion, menu);
		return true;
	}
	/**
	 * Recoge los datos introducidos por el usuario y realiza una consulta en base de datos para ver si son correctos.
	 * Si no se encuentra ninguna coincidencia se muestra un error; en caso contrario se lanza una nueva actividad
	 * @param v La vista que contiene el botón al que se asocia este método
	 */
	public void PulsadoBoton(View v)
	{
		//Si se pulsa enviar:
		if(v.getId()==R.id.EnviarLog)
		{
			EditText Email,Pass;
			TextView MensajeError;
			Email=(EditText)findViewById(R.id.IntroducirMail);
			Pass=(EditText)findViewById(R.id.IntroducirPass);
			MensajeError=(TextView)findViewById(R.id.ErrorLog);	
			//Abrimos la base de datos 'DBUsuarios' en modo escritura
	        UsuarioBDHelper base =new UsuarioBDHelper(this, "DBUsuarios", null, 1);	 
	        SQLiteDatabase db = base.getWritableDatabase();
	        //Comprobamos si existe un usuario con ese email y esa contrasenya
			Cursor cursor = db.rawQuery(" SELECT nombre,password,email FROM Usuarios WHERE email='"+Email.getText().toString()+"' AND password='"+Pass.getText().toString()+"'", null);
			//Si podemos colocarnos al principio del cursor quiere decir que existe un usuario con ese email y esa password
			if (cursor.moveToFirst()) 
			{
				MensajeError.setVisibility(View.VISIBLE);
				MensajeError.setText("Usuario Correcto");
			}
			//Si no hay ningún Usuario con ese Email
			else
			{
				MensajeError.setVisibility(View.VISIBLE);
				MensajeError.setText(getResources().getString(R.string.ErrorLog));
			}
			db.close();
		}
		else
		{
			Intent regis=new Intent(this,Registro.class);
			startActivity(regis);
		}
	}

}
