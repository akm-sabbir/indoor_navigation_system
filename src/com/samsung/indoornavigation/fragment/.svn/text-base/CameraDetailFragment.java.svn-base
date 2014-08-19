package com.samsung.indoornavigation.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.samsung.indoornavigation.R;
import com.samsung.indoornavigation.surfaceview.Sample3View;

public class CameraDetailFragment extends Fragment{
	View mContexView=null;
    private static final String TAG = "Sample::Activity";
    private Sample3View mView;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContexView=inflater.inflate(R.layout.camera_fragment, null);
		FrameLayout frameLayout=(FrameLayout)mContexView.findViewById(R.id.camera_preview);
		
		mView = new Sample3View(getActivity());
		frameLayout.addView(mView);
		


		return mContexView ;
	}
	@Override
	public void onPause() {
        Log.i(TAG, "onPause");
		super.onPause();
		if (null != mView)
			mView.releaseCamera();
	}
	
	@Override
	public void onResume() {
        Log.i(TAG, "onResume");
		super.onResume();
		if((null != mView) && !mView.openCamera() ) {
/*			AlertDialog ad = new AlertDialog.Builder(this).create();  
			ad.setCancelable(false); // This blocks the 'BACK' button  
			ad.setMessage("Fatal error: can't open camera!");  
			ad.setButton("OK", new DialogInterface.OnClickListener() {  
			    public void onClick(DialogInterface dialog, int which) {  
				dialog.dismiss();
				finish();
			    }  
			});  
			ad.show();*/
		}
	}

}
