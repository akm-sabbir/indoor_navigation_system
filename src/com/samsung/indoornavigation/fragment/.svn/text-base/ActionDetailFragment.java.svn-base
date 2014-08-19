package com.samsung.indoornavigation.fragment;

import org.opencv.android.Utils;
import org.opencv.core.Mat;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.samsung.indoornavigation.R;
import com.samsung.indoornavigation.opencv.ImageUtility;
import com.samsung.indoornavigation.opencv.OpenCV;

public class ActionDetailFragment extends Fragment {
	protected static final int ACTIVITY_SELECT_IMAGE = 10;
	View mContentView = null;
	ProgressBar bar;
	MyAsyncTask mAsyncTask;
	String mCurrentImagePath;
	public OpenCV mOpencv = new OpenCV();
	Bitmap mBitmap;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mContentView = inflater.inflate(R.layout.action_detail, null);
		bar = (ProgressBar) mContentView.findViewById(R.id.progressBar1);

		mContentView.findViewById(R.id.select_btn).setOnClickListener(
				new View.OnClickListener() {

					public void onClick(View v) {
						Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
						intent.setType("image/*");
						startActivityForResult(intent, ACTIVITY_SELECT_IMAGE);

					}
				});
		return mContentView;
	}

	/**
	 * Gets the corresponding path to a file from the given content:// URI
	 * 
	 * @param selectedUri
	 *            The content:// URI to find the file path from
	 * @param contentResolver
	 *            The content resolver to use to perform the query.
	 * @return the file path as a string
	 */
	private String getFilePathFromContentUri(Uri selectedUri,
			ContentResolver contentResolver) {
		String filePath;
		String[] filePathColumn = { Images.Media.DATA };

		Cursor cursor = contentResolver.query(selectedUri, filePathColumn,
				null, null, null);
		cursor.moveToFirst();

		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		filePath = cursor.getString(columnIndex);
		cursor.close();
		return filePath;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ACTIVITY_SELECT_IMAGE
				&& resultCode == Activity.RESULT_OK) {

			Uri currImageURI = data.getData();
			mCurrentImagePath = getFilePathFromContentUri(currImageURI,
					getActivity().getContentResolver());
			mAsyncTask = new MyAsyncTask();
			mAsyncTask.execute(0);
			/*
			 * Toast.makeText(getActivity(), " " + currImageURI,
			 * Toast.LENGTH_LONG) .show();
			 */

			/*
			 * try { mBitmap = BitmapFactory.decodeStream(getActivity()
			 * .getContentResolver().openInputStream(currImageURI)); ImageView
			 * targetImage = (ImageView) mContentView
			 * .findViewById(R.id.imageView1); if (mBitmap != null &&
			 * targetImage != null) {
			 * 
			 * String[] proj = { Images.Media.DATA, Images.Media.ORIENTATION };
			 * Cursor cursor = getActivity().managedQuery(currImageURI, proj,
			 * null, null, null); int columnIndex =
			 * cursor.getColumnIndex(proj[0]); cursor.moveToFirst();
			 * mCurrentImagePath =
			 * getFilePathFromContentUri(currImageURI,getActivity
			 * ().getContentResolver());
			 * 
			 * Toast.makeText(getActivity(), " " + mCurrentImagePath,
			 * Toast.LENGTH_LONG).show();
			 * 
			 * Log.i("TagMe", mCurrentImagePath.toString() +"\n"); //
			 * targetImage.setImageBitmap(mBitmap);
			 * 
			 * } } catch (FileNotFoundException e) { // TODO Auto-generated
			 * catch block e.printStackTrace(); }
			 */
		}
	}

	public void showDetails(String device) {

		Toast.makeText(getActivity(), " " + device, Toast.LENGTH_LONG).show();
		/*
		 * this.device = device; this.getView().setVisibility(View.VISIBLE);
		 * TextView view = (TextView)
		 * mContentView.findViewById(R.id.device_address);
		 * view.setText(device.deviceAddress); view = (TextView)
		 * mContentView.findViewById(R.id.device_info);
		 * view.setText(device.toString());
		 */

	}


	class MyAsyncTask extends AsyncTask<Integer, Integer, Long> {

		public MyAsyncTask(Activity context) {

		}

		public MyAsyncTask() {
			// TODO Auto-generated constructor stub
		}

		@Override
		protected Long doInBackground(Integer... params) {

			long start = System.currentTimeMillis();
			// int width = mBitmap.getWidth();
			// int height = mBitmap.getHeight();
			// int[] pixels = new int[width * height];
			// mBitmap.getPixels(pixels, 0, width, 0, 0, width, height);
		
			Mat mat=new Mat();
			mBitmap=ImageUtility.getBitmapFromLocalPath(mCurrentImagePath,1);
			Utils.bitmapToMat(mBitmap, mat);
			publishProgress(30);
			mOpencv.doProcess(mat.getNativeObjAddr());

			//mOpencv.setSourceImage(null,0,0,mCurrentImagePath);
			publishProgress(30);
			Utils.matToBitmap(mat,mBitmap);

/*			mOpencv.findEdgesandCorners();
			publishProgress(10);
			mOpencv.getCornerpoints();
			publishProgress(10);*/
			
			
			/*byte[] imageData = mOpencv.getSourceImage();
			mBitmap = BitmapFactory.decodeByteArray(imageData, 0,
					imageData.length);*/


			publishProgress(10);

			long end = System.currentTimeMillis();
			long elapse = end - start;
			return elapse;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			bar.incrementProgressBy(values[0]);
		}

		@Override
		protected void onPostExecute(Long time) {
			bar.setProgress(0);
			ImageView targetImage = (ImageView) mContentView
					.findViewById(R.id.imageView1);
			targetImage.setImageBitmap(mBitmap);
		}

		@Override
		protected void onPreExecute() {

			bar.setProgress(0);

		}

	}

}
