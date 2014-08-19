/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

#include <string.h>
#include<vector>
#include <jni.h>
#include <android/log.h>



#include <opencv2/highgui/highgui.hpp>
#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/features2d/features2d.hpp>
#include "Log.hpp"
#include<stdio.h>
#include<iostream>
#include<cmath>
#include<vector>
#include<stdlib.h>
#include<time.h>
#include<string.h>
#include "EdgeFinder.h"
#include "CornerFinder.h"
#include "ROIExtractor.h"

using namespace std;
using namespace cv;


#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jboolean JNICALL Java_com_samsung_indoornavigation_opencv_OpenCV_doProcess(
		JNIEnv * env, jobject thiz, jlong addrRgba){

		sbrc::Log::info("set Image Start");
	    Mat* pMatRgb=(Mat*)addrRgba;
		//Do something with the Image
	   // rectangle(*pMatRgb, cvPoint(20, 15), cvPoint(100, 70), cvScalar(255, 0, 0, 0),1, 8, 0);

	    Mat src=pMatRgb->clone();
	    IplImage* img = &(IplImage)src;

	    //imshow("EdgeFinder",(Mat) ef.doProcess());
		if (img == NULL) {
			sbrc::Log::info("Image load Failed");
			return 0;
		}

	    sbrc::Log::info("clone done");
	    EdgeFinder ef(img);
	    sbrc::Log::info("EdgeFinder  init done");
	    ef.doProcess();

		sbrc::Log::info("Load Image Done.");

		CornerFinder cf;
		cf.setVeticalLineHolder(ef.getVeticalLineHolder());
		sbrc::Log::info("setVeticalLineHolder Done.");
		cf.setOtherLineHolder(ef.getOtherLineHolder());
		sbrc::Log::info("cf.setOtherLineHolder Done.");
		cf.getCornerpoints(img);

		sbrc::Log::info(".getCornerpoints Done.");

	/*	vector<CvPoint> superContainer=cf.getVoxelGrid()->listofPoints;
		for(int i = 0; i < superContainer.size(); i++){
		circle(*pMatRgb,cvPoint(superContainer.at(i).x,superContainer.at(i).y),1,cvScalar(25,i*200,255),2);
		}*/

		ROIExtractor roiExtractor;

		/*vector<vector<pair<CvPoint, int> > > imageROIExtractor(
				vector<CvPoint*>&lineContainer, vector<CvPoint> cornerPoints,
				IplImage *img, Mat *imgMat);*/

		vector<CvPoint*> lineContainer=cf.getVeticalLineHolder();
		vector<CvPoint> cornerPoints=cf.getVoxelGrid()->listofPoints;
		vector < vector < pair<CvPoint, int > > > container = roiExtractor.imageROIExtractor(lineContainer,cornerPoints,( IplImage* ) img,(Mat *)pMatRgb);

		return 1;
}


#ifdef __cplusplus
}
#endif

