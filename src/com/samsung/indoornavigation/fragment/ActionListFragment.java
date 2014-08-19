package com.samsung.indoornavigation.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.samsung.indoornavigation.R;

public class ActionListFragment extends ListFragment{
	List<String> actions = new ArrayList<String>();
	View mContentView=null;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		this.setListAdapter(new ActionListAdapter(getActivity(),  R.layout.list_row, actions));
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mContentView = inflater.inflate(R.layout.action_fragment, null);
		return mContentView;
				
	}
	
	class ActionListAdapter extends ArrayAdapter<String>{

		private List<String> items;
		//String[] presidents={"image","video","camera"};
		int []image_id={R.drawable.content_picture,R.drawable.device_access_camera,R.drawable.ic_action_video};
		public ActionListAdapter(Context context, 
				int textViewResourceId, List<String> objects) {
			super(context,  textViewResourceId, objects);
			items=objects;
			items.add("image");
			items.add("camera");
			items.add("video");
			// TODO Auto-generated constructor stub
		}
		

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getActivity()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.list_row, null);
			}
			String device = items.get(position);
			if (device != null) {
		        TextView title = (TextView)v.findViewById(R.id.title_song); // title
		        ImageView imageView = (ImageView)v.findViewById(R.id.list_image); // title
		        imageView.setImageResource(image_id[position]);
		        // Setting all values in listview
		        title.setText(device);
		        
			}
			return v;
		}

	}
    /**
     * Initiate a connection with the peer.
     */

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		String device = (String) getListAdapter().getItem(position);
        ((DeviceActionListener) getActivity()).showDetails(device);
	}
    public interface DeviceActionListener {
        void showDetails(String device);
    }

}
