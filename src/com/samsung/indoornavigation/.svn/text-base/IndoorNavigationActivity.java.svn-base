package com.samsung.indoornavigation;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.samsung.indoornavigation.fragment.ActionDetailFragment;
import com.samsung.indoornavigation.fragment.ActionListFragment;
import com.samsung.indoornavigation.fragment.CameraDetailFragment;
import com.samsung.indoornavigation.fragment.CameraSelectFragment;

public class IndoorNavigationActivity extends Activity implements
		ActionListFragment.DeviceActionListener {

	private static final String TAG = "MAIN_ACTIVITY";

	private BaseLoaderCallback mOpenCVCallBack = new BaseLoaderCallback(this) {
		@Override
		public void onManagerConnected(int status) {
			switch (status) {
			case LoaderCallbackInterface.SUCCESS: {
				Log.i(TAG, "OpenCV loaded successfully");

			}
				break;
			default: {
				super.onManagerConnected(status);
			}
				break;
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);

		Log.i(TAG, "Trying to load OpenCV library");
		if (!OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_2, this,
				mOpenCVCallBack)) {
			Log.e(TAG, "Cannot connect to OpenCV Manager");

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.indoornavigation_activity_menu, menu);
		super.onCreateOptionsMenu(menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		return super.onMenuItemSelected(featureId, item);
	}

	public void showDetails(String device) {
		// TODO Auto-generated method stub
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		if (device == "image") {
			ActionDetailFragment actionDetailFragment = new ActionDetailFragment();
			fragmentTransaction.replace(R.id.mdetails, actionDetailFragment);

		} else if (device == "camera") {
			CameraSelectFragment cameraSelectFragment = new CameraSelectFragment();
			fragmentTransaction.replace(R.id.mdetails, cameraSelectFragment);

		} else {
			CameraDetailFragment cameraDetailFragment = new CameraDetailFragment();
			fragmentTransaction.replace(R.id.mdetails, cameraDetailFragment);
		}

		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();

	}
}