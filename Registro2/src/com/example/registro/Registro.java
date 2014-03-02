package com.example.registro;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Actividad que muestra al usuario un formulario que debe ser rellanado por el usuario para ser registrado en una base de datos y acceder a la aplicación.
 * Si se cumplimenta correctamente y no existe un usuario con ese email los datos se introducen en base de datos; en caso contrario se muestra el mensaje de error correspondiente.
 * @author pablo
 *
 */
public class Registro extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	/**
	 * Comprueba todos los datos del formulario al pulsarse un botón.
	 * Si todo es correcto se inserta la información en la base de datos; en caso contrario se muestra un mensaje de error
	 * @param v Vista que contiene el botón al que está asociada la ejecución del método
	 */
	public void comprobarFormulario(View v)
	{
		EditText FechaNac,Nombre,Apellidos,Email,Pass,RePass;
		CheckBox acepto;
		TextView MensajeError;
		boolean errores=false;
		Nombre=(EditText)findViewById(R.id.editNombre);
		Apellidos=(EditText)findViewById(R.id.editApellidos);
		FechaNac=(EditText)findViewById(R.id.editFechaNac);
		Email=(EditText)findViewById(R.id.editEmail);
		Pass=(EditText)findViewById(R.id.editPass);
		RePass=(EditText)findViewById(R.id.editRepass);
		acepto=(CheckBox)findViewById(R.id.checkBox1);
		MensajeError=(TextView)findViewById(R.id.MensajeError);
		MensajeError.setVisibility(View.VISIBLE);
		MensajeError.setText("");
		//Comprobar todos los posibles errores al rellenar el formulario
		if(ComprobarFecha()==true)
		{
			MensajeError.setText(getResources().getString(R.string.ErrorFechaNac));
			errores=true;
		}
		if (FechaNac.getText().toString().compareTo("")==0)
		{
			MensajeError.setText(getResources().getString(R.string.FaltaFechaNac));
			errores=true;
		}
		if (Nombre.getText().toString().compareTo("")==0)
		{
			MensajeError.setText(getResources().getString(R.string.FaltaNombre));
			errores=true;
		}
		if (Apellidos.getText().toString().compareTo("")==0)
		{
			MensajeError.setText(getResources().getString(R.string.FaltaApellido));
			errores=true;
		}
		if (Email.getText().toString().compareTo("")==0)
		{
			MensajeError.setText(getResources().getString(R.string.FaltaEmail));
			errores=true;
		}
		if (Pass.getText().toString().compareTo("")==0||Pass.getText().toString().compareTo(RePass.getText().toString())!=0)
		{
			MensajeError.setText(getResources().getString(R.string.Contrasenyaincorrecta));
			errores=true;
		}
		if (acepto.isChecked()==false)
		{
			MensajeError.setText(getResources().getString(R.string.Noacepta));
			errores=true;
		}
		//Si no hay errores
		if(errores==false)
		{
			//Abrimos la base de datos 'DBUsuarios' en modo escritura
	        UsuarioBDHelper base =new UsuarioBDHelper(this, "DBUsuarios", null, 1);	 
	        SQLiteDatabase db = base.getWritableDatabase();
	        //Comprobamos que no haya ningún usuario con ese email
			Cursor cursor = db.rawQuery(" SELECT nombre FROM Usuarios WHERE email='"+Email.getText().toString()+"' ", null);
			//Si podemos colocarnos al principio del cursor quiere decir que existe un usuario con ese email
			if (cursor.moveToFirst()) 
			{
				MensajeError.setVisibility(View.VISIBLE);
				MensajeError.setText(getResources().getString(R.string.UserExiste));
			}
			else
			{
				if(db != null)
		        {
	                //Insertamos los datos en la tabla Usuarios
	                db.execSQL("INSERT INTO Usuarios (nombre , email , password, fecha_nac) "
	                +"VALUES ('" + Nombre.getText().toString()+" "+Apellidos.getText().toString() +"', '" + 
	                Email.getText().toString() +"', '" + Pass.getText().toString() +"', '" + FechaNac.getText().toString() +"')");
	                Toast prueba=Toast.makeText(getApplicationContext(),getResources().getString(R.string.UserCreado),Toast.LENGTH_SHORT); 
	    			prueba.show();
		            //Cerramos la base de datos
		            db.close();
		            finish();
		        }
			}  
        }
	}
	/**
	 * Comprueba que la fecha introducida tenga el formato adecuado y que sea correcta.	
	 * @return true si la fecha es correcta y false en caso contrario
	 */
	public boolean ComprobarFecha()
	{
		EditText FechaNac;
		String auxFecha;
		int auxdia = 0,auxmes = 0,auxanyo = 0;
		FechaNac=(EditText)findViewById(R.id.editFechaNac);
		boolean errorFecha=false;
		if(FechaNac.getText().toString().compareTo("")==0||FechaNac.getText().toString().length()<10)
		{
			errorFecha=true;
		}
		else
		{	
			auxFecha=""+FechaNac.getText().toString();
			try
			{
				auxdia=Integer.parseInt(auxFecha.substring(0,2));
				auxmes=Integer.parseInt(auxFecha.substring(3,5));
				auxanyo=Integer.parseInt(auxFecha.substring(6,auxFecha.length()));
			}
			catch(NumberFormatException ex) 
			{
				errorFecha=true;
			}
			
			if(auxFecha.charAt(2)!='/'&&auxFecha.charAt(5)!='/')
			{
				errorFecha=true;
			}
			if(auxmes<1||auxmes>12)
			{
				errorFecha=true;
			}
			//Comprobacion para todos los meses
			if (auxdia<=0||auxdia>31)
			{
				errorFecha=true;
			}
			//Meses con 30 dias
			if((auxmes==4||auxmes==6||auxmes==9||auxmes==11)&&auxdia>30)
			{
				errorFecha=true;
			}
			//Febrero:Se comprueba si el anyo es Bisiesto
			if((auxanyo % 4 == 0) && ((auxanyo % 100 != 0) || (auxanyo % 400 == 0)))
			{
				if(auxmes==2&&auxdia>29)
				{
					errorFecha=true;
				}	
			}
			else
			{
				if(auxmes==2&&auxdia>28)
				{
					errorFecha=true;
				}
			}	
		}
		return errorFecha;
	}

}
