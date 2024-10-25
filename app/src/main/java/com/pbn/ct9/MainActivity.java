package com.pbn.ct9;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pbn.ct9.databinding.ActivityMainBinding;
//import com.pbn.ct9.ui.personalColor.SwipeLeft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ObservableField;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity
{
	private ActivityMainBinding binding;
	float x1,x2,y1,y2;
	String value = "valueX";



	public boolean onTouchEvent(MotionEvent touchEvent){
		switch(touchEvent.getAction()){
			case MotionEvent.ACTION_DOWN:
				x1 = touchEvent.getX();
				y1 = touchEvent.getY();
				break;
			case MotionEvent.ACTION_UP:
				x2 = touchEvent.getX();
				y2 = touchEvent.getY();

				/*if(x1 < x2){
					Intent i = new Intent(MainActivity.this, SwipeLeft.class);
					i.putExtra("my_variable",value);
					startActivity(i);
				}*/
				break;
		}
		return false;
	}



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityMainBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		
		BottomNavigationView navView = findViewById(R.id.nav_view);
		// Passing each menu ID as a set of Ids because each
		// menu should be considered as top level destinations.
		AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
				 R.id.navigation_personal_color, R.id.navigation_member_center)
				.build();
		NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
		NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
		NavigationUI.setupWithNavController(binding.navView, navController);
	}
	
}