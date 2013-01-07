package com.samsung.indoornavigation;

import java.io.File;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.samsung.indoornavigation.fragment.ActionDetailFragment;
import com.samsung.indoornavigation.fragment.ActionListFragment;
import com.samsung.indoornavigation.fragment.CameraDetailFragment;
import com.samsung.indoornavigation.fragment.CameraSelectFragment;
import com.samsung.indoornavigation.opencv.OpenCV;
import com.samsung.indoornavigation.opencv.Utility;


public class IndoorNavigationActivity extends Activity implements ActionListFragment.DeviceActionListener{
	private static final String IMAGE_DIRECTORY = "/sdcard/DCIM/Camera";
	private static final int ACTIVITY_SELECT_CAMERA = 0;
	private static final int ACTIVITY_SELECT_IMAGE = 1;
	private static final String TAG = "MAIN_ACTIVITY";
	private String mCurrentImagePath = null;
	private OpenCV opencv = new OpenCV();
	private ProgressDialog progressDialog;
	MyAsyncTask aTask = new MyAsyncTask();
	Bitmap mBitmap;

	private int indicator = 0;

	private static int doContine;

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
		inflater.inflate(R.menu.activity_test_open_cv, menu);
		super.onCreateOptionsMenu(menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_camera:
			Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			long timeTaken = System.currentTimeMillis();
			mCurrentImagePath = IMAGE_DIRECTORY + "/"
					+ Utility.createName(timeTaken) + ".jpg";
			Log.i(TAG, mCurrentImagePath);
			// fileUri=Uri.fromFile(new File(mCurrentImagePath));
			cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(new File(mCurrentImagePath)));
			startActivityForResult(cameraIntent, ACTIVITY_SELECT_CAMERA);

			// Intent cameraIntent = new
			// Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			// startActivityForResult(cameraIntent, 1888);
			return true;
		case R.id.menu_image:
			Intent galleryIntent = new Intent(Intent.ACTION_PICK,
					Images.Media.INTERNAL_CONTENT_URI);
			startActivityForResult(galleryIntent, ACTIVITY_SELECT_IMAGE);
			// runDialog(100);
			return true;
		}

		return super.onMenuItemSelected(featureId, item);
	}

	public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
		int width = bm.getWidth();
		int height = bm.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// CREATE A MATRIX FOR THE MANIPULATION
		Matrix matrix = new Matrix();
		// RESIZE THE BIT MAP
		matrix.postScale(scaleWidth, scaleHeight);

		// RECREATE THE NEW BITMAP
		Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
				matrix, false);
		return resizedBitmap;
	}

	public void extractFeature(Bitmap mBitmap) {

		mBitmap = getResizedBitmap(mBitmap, 480, 640);

		Log.i("BitmapSize", "Size" + mBitmap.getRowBytes());

		int width = mBitmap.getWidth();
		int height = mBitmap.getHeight();

		int[] pixels = new int[width * height];
		mBitmap.getPixels(pixels, 0, width, 0, 0, width, height);
		// testCV.setSourceImage(pixels, width, height);
		opencv.setSourceImage(pixels, width, height, null);
		long start = System.currentTimeMillis();
		// opencv.extractSURFFeature();
		// opencv.findEdgesandCorners();
		opencv.getCornerpoints();
		long end = System.currentTimeMillis();
		// byte[] imageData = testCV.getSourceImage();
		byte[] imageData = opencv.getSourceImage();
		long elapse = end - start;
		Toast.makeText(this, "" + elapse + " ms is used to extract features.",
				Toast.LENGTH_LONG).show();
		mBitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);

	}

	void showDialog() {

		// progressDialog = ProgressDialog.show(this, "Please wait....",
		// "Image Processing");
		progressDialog = new ProgressDialog(this);
		progressDialog.setTitle("Please wait....");
		progressDialog.setMessage("Image Processing");
		progressDialog.setIndeterminate(false);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.show();
	}

	void dismissDialog() {
		progressDialog.dismiss();
	}

	@SuppressLint("ValidFragment")
	class dialogFragment extends DialogFragment {

		public Dialog onCreateDialog(Bundle saveInstances) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setMessage("Fire in the Hole")
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub

								}
							})
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									doContine = 1;
								}
							});
			return builder.create();
		}

		public void show(FragmentManager fragmentManager, String string) {
			// TODO Auto-generated method stub
			// super.show(fragmentManager, string);
		}

	}

	class ThreadPooling extends Thread {

		private volatile int lockers;
		private volatile int lines;
		private volatile int corners;

		public ThreadPooling() {
			lockers = 1;
			lines = 1;
			corners = 0;
		}

		public void setLines(int val) {
			lines = val;
		}

		public int getLines() {
			return lines;
		}

		public void setCorners(int val) {
			corners = val;
		}

		public int getCorners() {
			return corners;
		}

		public int getLocker() {
			return lockers;
		}

		public void setLocker(int val) {
			lockers = val;
		}

		public synchronized void run() {
			Log.i("thread start again", "again");
			try {
				if (lines == 1) {
					Log.i("Lockers value", "get Lines");
					try {
						opencv.findEdgesandCorners();
						setLocker(0);
					} catch (RuntimeException ex) {
						setLocker(0);
						Toast.makeText(getApplicationContext(),
								ex.getMessage(), Toast.LENGTH_LONG).show();
					}
				}
				if (corners == 1) {
					try {
						Log.i("Lockers value", "get Corners");
						opencv.getCornerpoints();
						setLocker(0);
					} catch (RuntimeException ex) {
						setLocker(0);
						Toast.makeText(getApplicationContext(),
								ex.getMessage(), Toast.LENGTH_LONG).show();
					}
				}
			} catch (RuntimeException ex) {
				Log.i("Runtime Error", ex.getMessage());
			}
		}
	}

	class MyAsyncTask extends AsyncTask<Integer, Integer, Long> {

		@SuppressLint("UseValueOf")
		@SuppressWarnings("deprecation")
		private Activity ctx;

		public MyAsyncTask(Activity context) {
			// TODO Auto-generated constructor stub
			ctx = context;
		}

		public MyAsyncTask() {
			// TODO Auto-generated constructor stub
		}

		@Override
		protected Long doInBackground(Integer... params) {
			progressDialog.setProgress(0);
			doContine = 0;
			long start = System.currentTimeMillis();
			// mBitmap = BitmapFactory.decodeFile(mCurrentImagePath);
			// mBitmap= getResizedBitmap(mBitmap, 480, 640);

			// Log.("BitmapSize", "Size"+mBitmap.getRowBytes());
			int width = mBitmap.getWidth();
			int height = mBitmap.getHeight();
			int[] pixels = new int[width * height];
			mBitmap.getPixels(pixels, 0, width, 0, 0, width, height);
			publishProgress(10);
			// testCV.setSourceImage(pixels, width, height);

			try {
				if (opencv.setSourceImage(pixels, width, height,
						mCurrentImagePath)) {
					try {

					} catch (Exception ex) {

					}
					if (doContine == 1)
						return (long) 1;
				}
			} catch (RuntimeException ex) {
				return (long) 1;
			}
			publishProgress(10);
			// opencv.extractSURFFeature();
			ThreadPooling th = new ThreadPooling();
			try {

				th.setLines(1);
				th.setCorners(0);
				th.setLocker(1);
				th.start();
				long starts = System.currentTimeMillis();
				while (th.getLocker() == 1) {
					if (System.currentTimeMillis() - starts > 3000) {
						try {
							Long it = new Long(
									(System.currentTimeMillis() - starts));
							Log.i(it.toString(), "Tag");
							mBitmap = BitmapFactory
									.decodeFile("/storage/sdcard0/DCIM/blank.jpeg");
							indicator = 1;
							Toast.makeText(
									getApplicationContext(),
									"Operation is taking too long select another image",
									Toast.LENGTH_LONG).show();
						} catch (RuntimeException ex) {
							Toast.makeText(getApplicationContext(),
									ex.getMessage(), Toast.LENGTH_LONG).show();
						}
						return System.currentTimeMillis() - starts;
					}
				}
				Log.i("After line detection", "after Lines");
				th = new ThreadPooling();
				th.setLocker(1);
				th.setCorners(1);
				th.setLines(0);
				th.start();
				starts = System.currentTimeMillis();
				while (th.getLocker() == 1) {
					if (System.currentTimeMillis() - starts > 3000) {
						try {
							mBitmap = BitmapFactory
									.decodeFile("/storage/sdcard0/DCIM/blank.jpeg");
							indicator = 1;
							Toast.makeText(
									getApplicationContext(),
									"Operation is taking too long select another image",
									Toast.LENGTH_LONG).show();
						} catch (RuntimeException ex) {
							Toast.makeText(getApplicationContext(),
									ex.getMessage(), Toast.LENGTH_LONG).show();
						}
						return (long) 1;
					}
				}
				publishProgress(25);

			} catch (RuntimeException ex) {
				// df.show(getFragmentManager(), "Tag");
				return (long) 1;
			}

			// opencv.getCornerpoints();
			publishProgress(35);
			// if(doContine == 1){return start - System.currentTimeMillis();}
			long end = System.currentTimeMillis();
			// byte[] imageData = testCV.getSourceImage();

			byte[] imageData = opencv.getSourceImage();
			publishProgress(10);
			long elapse = end - start;
			/*
			 * Toast.makeText(this, "" + elapse +
			 * " ms is used to extract features.", Toast.LENGTH_LONG).show();
			 */
			mBitmap = BitmapFactory.decodeByteArray(imageData, 0,
					imageData.length);
			publishProgress(10);
			return start - System.currentTimeMillis();
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			progressDialog.incrementProgressBy((int) (values[0]));
		}

		@Override
		protected void onPostExecute(Long time) {
			// updateUI("Done with all the operations, it took:" +
			// time + " millisecondes");

			dismissDialog();
			if (indicator == 1) {
				indicator = 0;
				AlertDialog.Builder df = new AlertDialog.Builder(ctx);
				df.setTitle("Alert Box");
				df.setMessage("Computation is taking too much time please try another Image");
				df.setPositiveButton("Exit",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								// mImageView.setImageBitmap(null);

								ctx.startActivity(ctx.getIntent());
							}
						});
				df.setNegativeButton("OK",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								indicator = 0;
								Intent galleryIntent = new Intent(
										Intent.ACTION_PICK,
										Images.Media.INTERNAL_CONTENT_URI);
								startActivityForResult(galleryIntent,
										ACTIVITY_SELECT_IMAGE);
								// mImageView.setImageBitmap(mBitmap);
							}
						});
				AlertDialog dialog = df.create();
				dialog.show();

				// df.show();
				Toast.makeText(getApplicationContext(),
						"Operation is taking too long select another image",
						Toast.LENGTH_LONG).show();
			} else {
				// mImageView.setImageBitmap(mBitmap);

			}
			// dialogFragment df = new dialogFragment();
			// df.show(getFragmentManager(), "Tags");
			aTask = new MyAsyncTask(ctx);
		}

		@Override
		protected void onPreExecute() {
			mBitmap = BitmapFactory.decodeFile(mCurrentImagePath);
			mBitmap = getResizedBitmap(mBitmap, 480, 640);
			// mImageView.setImageBitmap(mBitmap);

			showDialog();
			// progressDialog = ProgressDialog.show(getApplicationContext(),
			// "Please wait....", "Here your message");
			// updateUI("Starting process");
		}

		public void doLongOperation() {

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == ACTIVITY_SELECT_CAMERA
				&& resultCode == Activity.RESULT_OK) {
			aTask.execute(1);


		}
		if (requestCode == ACTIVITY_SELECT_IMAGE && resultCode == RESULT_OK) {
			// progressDialog = ProgressDialog.show(getApplicationContext(),
			// "Please wait....", "Here your message");
			Uri currImageURI = data.getData();
			String[] proj = { Images.Media.DATA, Images.Media.ORIENTATION };
			Cursor cursor = managedQuery(currImageURI, proj, null, null, null);
			int columnIndex = cursor.getColumnIndex(proj[0]);
			cursor.moveToFirst();
			mCurrentImagePath = cursor.getString(columnIndex);
			Log.i("TagMe", mCurrentImagePath.toString() + "\n");
			aTask.execute(1);
			// progressDialog.dismiss();

		}
	}



	public void showDetails(String device) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		if (device == "image") {
			ActionDetailFragment actionDetailFragment=new ActionDetailFragment();
			fragmentTransaction.replace(R.id.mdetails, actionDetailFragment);

		}else if(device == "camera"){
			CameraSelectFragment cameraSelectFragment=new CameraSelectFragment();
			fragmentTransaction.replace(R.id.mdetails, cameraSelectFragment);

			
		} else {
			CameraDetailFragment cameraDetailFragment = new CameraDetailFragment();
			fragmentTransaction.replace(R.id.mdetails, cameraDetailFragment);
		}
		
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();


		
	}
}