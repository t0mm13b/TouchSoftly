package t0mm13b.touchSoftly;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class PrefTouchSoftly extends PreferenceActivity implements OnSharedPreferenceChangeListener{

	private SharedPreferences _prefs = null;
	private CheckBoxPreference _chkBoxAutoStart;
	
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefs_touchsoftly);
		PreferenceManager.setDefaultValues(this,R.xml.prefs_touchsoftly, false);
		_chkBoxAutoStart = (CheckBoxPreference)findPreference(getString(R.string.keyAutoStart));
		if (_chkBoxAutoStart != null){
			_chkBoxAutoStart.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){

				@Override
				public boolean onPreferenceChange(Preference preference,
						Object newValue) {
					boolean blnAutoStart = Boolean.getBoolean(newValue.toString());
					Editor e = _prefs.edit();
					e.putBoolean(getString(R.string.keyAutoStart), blnAutoStart);
					e.putBoolean(getString(R.string.keyActivatedViaBoot), false);
					e.commit();
					return true;
				}
				
			});
		}
		this.getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}
	@Override
	public void onStart(){
		super.onStart();
		_prefs = PreferenceManager.getDefaultSharedPreferences(this);
	}
	@Override
	public void onPause(){
		super.onPause();
		this.getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}
	@Override
	public void onResume(){
		super.onResume();
		this.getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		// TODO Auto-generated method stub
		
	}
}
