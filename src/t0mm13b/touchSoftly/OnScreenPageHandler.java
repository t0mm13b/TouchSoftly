package t0mm13b.touchSoftly;

import java.io.IOException;

import t0mm13b.touchSoftly.R;
import android.app.ActivityManager;
import android.app.Instrumentation;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;

public class OnScreenPageHandler extends Service{
	private static final String TAG = "OnScreenPageHandler";
	//private HUDPageView _hudPageView;
	private WindowManager.LayoutParams _layOutParams;
	private LayoutInflater _layOutInflater;
	//private ActivityManager _activityMgr;
	private WindowManager _winMgr;
	private View _hudPageView;
	private Button _btnPageUp;
	private Button _btnPageDown;
	private Instrumentation _instr;
	
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onStart(Intent intent, int startId){
		Log.d(TAG, "onStart *** ENTER ***");
		super.onStart(intent, startId);
		Log.d(TAG, "onStart *** LEAVE ***");
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		Log.d(TAG, "onStartCommand *** ENTER ***");
		Log.d(TAG, "onStartCommand *** LEAVE ***");
		return Service.START_NOT_STICKY;
	}
	
	@Override
	public void onCreate(){
		Log.d(TAG, "onCreate *** ENTER ***");
		super.onCreate();
		//_activityMgr = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
		//
		_layOutInflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
		if (_layOutInflater != null) Log.d(TAG, "onCreate() - Got _layOutInflater!");
		else Log.d(TAG, "onCreate() - _layOutInflater is null! :(");
		_winMgr = (WindowManager)getSystemService(WINDOW_SERVICE);
		if (_winMgr != null) Log.d(TAG, "onCreate() - Got _winMgr!");
		else Log.d(TAG, "onCreate() - _winMgr is null! :(");
		//
		_instr = new Instrumentation();
		if (_instr != null) Log.d(TAG, "onCreate() - Got _instr!");
		else Log.d(TAG, "onCreate() - _instr is null! :(");
		//
		_layOutParams = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
				WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
				PixelFormat.TRANSLUCENT);
		if (_layOutParams != null){
			Log.d(TAG, "onCreate() - Got _layOutParams!");
			_layOutParams.gravity = Gravity.RIGHT | Gravity.TOP;
		}else Log.d(TAG, "onCreate() - _layOutParams is null! :(");
		_hudPageView = _layOutInflater.inflate(R.layout.service_hudpageview, null);
		if (_hudPageView != null){
			Log.d(TAG, "onCreate() - Got _hudPageView!");
			
			_btnPageUp = (Button)_hudPageView.findViewById(R.id.btnPageUp);
			_btnPageDown = (Button)_hudPageView.findViewById(R.id.btnPageDown);
			if (_btnPageUp == null) Log.d(TAG, "onCreate() - _btnPageUp is null! :(");
			if (_btnPageDown == null) Log.d(TAG, "onCreate() - _btnPageDown is null! :(");

			_hudPageView.setOnTouchListener(new OnTouchListener(){
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					Log.d(TAG, "_hudPageView:onTouch() - Got touch event");
					Rect outRectButtonPageUp = new Rect();
					Rect outRectButtonPageDown = new Rect();
					_btnPageUp.getHitRect(outRectButtonPageUp);
					_btnPageDown.getHitRect(outRectButtonPageDown);
					float x = event.getX();
					float y = event.getY();
					if (x > outRectButtonPageUp.left && x < outRectButtonPageUp.right &&
							y > outRectButtonPageUp.top && y < outRectButtonPageUp.bottom){
						Log.d(TAG, "_hudPageView:onTouch() - touch positioned within PageUp!");
						Runnable runSync = new Runnable(){

							@Override
							public void run() {
								// Fail!
								//_instr.sendKeyDownUpSync(KeyEvent.KEYCODE_PAGE_UP);
								// Fail!
								Process p = null;
								try {
									p = Runtime.getRuntime().exec("/system/bin/input keyevent 92");
									Log.d(TAG, "_hudPageView:onTouch() - sending keyevent PageUp!");
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								if (p != null)
									try {
										p.waitFor();
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
							}
							
						};
						Thread tRunSync = new Thread(null, runSync, "RunSync");
						tRunSync.start();
					}
					if (x > outRectButtonPageDown.left && x < outRectButtonPageDown.right &&
							y > outRectButtonPageDown.top && y < outRectButtonPageDown.bottom){
						Log.d(TAG, "_hudPageView:onTouch() - touch positioned within PageDown!");
						Runnable runSync = new Runnable(){

							@Override
							public void run() {
								// TODO Auto-generated method stub
								//_instr.sendKeyDownUpSync(KeyEvent.KEYCODE_PAGE_DOWN);
								Process p = null;
								try {
									p = Runtime.getRuntime().exec("/system/bin/input keyevent 93");
									Log.d(TAG, "_hudPageView:onTouch() - sending keyevent PageDown!");
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								if (p != null)
									try {
										p.waitFor();
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
							}
							
						};
						Thread tRunSync = new Thread(null, runSync, "RunSync");
						tRunSync.start();

					}
					return false;
				}
				
			});
			
			_winMgr.addView(_hudPageView, _layOutParams);
		}else Log.d(TAG, "onCreate() - _hudPageView is null! :(");
		Log.d(TAG, "onCreate *** LEAVE ***");
		
	}
	@Override
	public void onDestroy(){
		Log.d(TAG, "onDestroy *** ENTER ***");
		super.onDestroy();
		if (_hudPageView != null){
			if (_winMgr != null){
				_winMgr.removeView(_hudPageView);
				_hudPageView = null;
			}
		}
		Log.d(TAG, "onDestroy *** LEAVE ***");
	}
}
