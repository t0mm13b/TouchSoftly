package t0mm13b.touchSoftly;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class TouchSoftlyStartup extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		boolean blnAutoStart = prefs.getBoolean(context.getString(R.string.keyAutoStart), false);
		if (blnAutoStart){
			Editor e = prefs.edit();
			e.putBoolean(context.getString(R.string.keyActivatedViaBoot), true);
			e.commit();
			Intent serviceIntent = new Intent(context, t0mm13b.touchSoftly.OnScreenPageHandler.class);
			context.startService(serviceIntent);
		}else{
			Editor e = prefs.edit();
			e.putBoolean(context.getString(R.string.keyActivatedViaBoot), false);
			e.commit();
		}
		
	}
}