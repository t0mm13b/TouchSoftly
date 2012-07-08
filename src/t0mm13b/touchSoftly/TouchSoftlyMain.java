package t0mm13b.touchSoftly;


import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class TouchSoftlyMain extends Activity {

	private Context _context;
	private Intent _serviceIntent;
	private SharedPreferences _mainPrefs;
	private boolean _blnActivatedViaStartup = false;
	private boolean _blnNonAutoStart = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _context = this.getBaseContext();
        _serviceIntent = new Intent(_context, t0mm13b.touchSoftly.OnScreenPageHandler.class);
        _mainPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        _blnActivatedViaStartup = _mainPrefs.getBoolean(_context.getString(R.string.keyActivatedViaBoot), false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.menu_settings:
            // Launch the DeviceListActivity to see devices and do scan
            Intent intentLaunchPrefs = new Intent(_context, t0mm13b.touchSoftly.PrefTouchSoftly.class);
            startActivity(intentLaunchPrefs);
            return true;
        }
        return false;
    }

    @Override
    public void onDestroy(){
    	super.onDestroy();
    	boolean blnAutoStartStillOn = _mainPrefs.getBoolean(_context.getString(R.string.keyAutoStart), false);
		if (!blnAutoStartStillOn || !_blnActivatedViaStartup){
			_context.stopService(_serviceIntent);
		}
    }
    
    public void btnStart_OnClick(View view){
    	if (_blnActivatedViaStartup){
    		Toast.makeText(_context, getString(R.string.alreadyStarted), Toast.LENGTH_SHORT).show();
    	}else{
    		_context.startService(_serviceIntent);
    		_blnNonAutoStart = true;
    	}
    }
    
    public void btnStop_OnClick(View view){
    	if (_blnActivatedViaStartup){
    		boolean blnAutoStartStillOn = _mainPrefs.getBoolean(_context.getString(R.string.keyAutoStart), false);
    		if (blnAutoStartStillOn){
    			_context.stopService(_serviceIntent);
    			Toast.makeText(_context, getString(R.string.stoppedButStartUpActivated), Toast.LENGTH_SHORT).show();
    		}
    	}else{
        	if (_blnNonAutoStart){
        		_context.stopService(_serviceIntent);
        		_blnNonAutoStart = false;
        	}
    	}
    }
}
