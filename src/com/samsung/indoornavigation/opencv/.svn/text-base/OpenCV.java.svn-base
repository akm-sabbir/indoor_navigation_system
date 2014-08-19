package com.samsung.indoornavigation.opencv;

public class OpenCV {
	static{
		System.loadLibrary("main_object");
	}
	public native boolean setSourceImage(int[] pixels, int width, int height,String str);
	public native boolean setSourceImage3(String filePath, long matAddrRgba);
	public native boolean doProcess(long matAddrRgba);
	public native byte[] getSourceImage();
	public native void extractSURFFeature();
	public native int findEdgesandCorners();
	public native int getCornerpoints();

	
	
}
