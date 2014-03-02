package com.samachar.pddm.jpg;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.dom.R;
import com.samachar.pddm.jpg.adapter.MenuListAdapter;
import com.samachar.pddm.jpg.mapas.Mapa;
import com.samachar.pddm.jpg.menudeslizante.AcercaDeFragment;
import com.samachar.pddm.jpg.menudeslizante.CatalogoFragment;
import com.samachar.pddm.jpg.menudeslizante.DatosFragment;
import com.samachar.pddm.jpg.menudeslizante.GuardadoFragment;
import com.samachar.pddm.jpg.menudeslizante.HomeFragment;
import com.samachar.pddm.jpg.model.DMenuItem;

@SuppressLint("NewApi")
public class MainActivity extends Activity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	// titulo de menu
	private CharSequence mDrawerTitle;

	// para guardar titulo de app
	private CharSequence mTitle;

	// items de menu
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<DMenuItem> navDrawerItems;
	private MenuListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTitle = mDrawerTitle = getTitle();

		// cargar items de menu
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// cargar icono de menu
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.menu_lista);

		navDrawerItems = new ArrayList<DMenuItem>();

		// añadir items a array
		// Principal
		navDrawerItems.add(new DMenuItem(navMenuTitles[0], navMenuIcons
				.getResourceId(0, -1)));
		// Find People
		navDrawerItems.add(new DMenuItem(navMenuTitles[1], navMenuIcons
				.getResourceId(1, -1)));
		// Photos
		navDrawerItems.add(new DMenuItem(navMenuTitles[2], navMenuIcons
				.getResourceId(2, -1)));
		// Communities, Will add a counter here
		navDrawerItems.add(new DMenuItem(navMenuTitles[3], navMenuIcons
				.getResourceId(3, -1), true, "22"));
		// Pages
		navDrawerItems.add(new DMenuItem(navMenuTitles[4], navMenuIcons
				.getResourceId(4, -1)));
		// What's hot, We will add a counter here
		navDrawerItems.add(new DMenuItem(navMenuTitles[5], navMenuIcons
				.getResourceId(5, -1), true, "50+"));

		// reciclar typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// asignar adapter
		adapter = new MenuListAdapter(getApplicationContext(), navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// permitir a accionar a actionbar como un boton
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, // icono de menu
				R.string.app_name, // al abrir la lista
				R.string.app_name // al cerrar la lista
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// llamar a onPrepareOptionsMenu() para mostrar iconos de action
				// bars
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// llamar a onPrepareOptionsMenu() para ocultar iconos de action
				// bars
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// mostrar primera vez el primer item
			displayView(0);
		}
	}

	/**
	 * menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// mostrar el item seleccionado
			displayView(position);
		}
	}

	public boolean conexion() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnected()) {
			return true;
		}
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// muestra icono y titulo cuando se activa action bar
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// manejar evento de click en action bar
		switch (item.getItemId()) {
		case R.id.settings:
			
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * es llamada cuando ejecuta invalidateOptionsMenu()
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Muestra los fragmentos correspondiente al item seleccionado
	 * */
	private void displayView(int position) {
		// remplaza el contenido de main por otro
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new HomeFragment(getApplication());
			break;
		case 1:
			Intent i = new Intent(this, Mapa.class);
			startActivity(i);
			break;
		case 2:
			fragment = new CatalogoFragment();
			break;
		case 3:
			fragment = new DatosFragment();
			break;
		case 4:
			fragment = new GuardadoFragment();
			break;
		case 5:
			fragment = new AcercaDeFragment();
			break;

		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();

			// actualiza item y titulo cuando menu deslizante se cierra
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// Error cuando se esta creando fragmento
			Log.e("MainActivity", "Error fragmento");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// sincronia el estado despues de se ha guardado en
		// onRestoreInstancesstate
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

}
