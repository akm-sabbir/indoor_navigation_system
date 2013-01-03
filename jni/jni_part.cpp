#include <jni.h>
#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/features2d/features2d.hpp>
#include <vector>
#include "Log.hpp"
#include <iostream>

using namespace std;
using namespace cv;

extern "C" {
extern IplImage* pImage;
JNIEXPORT void JNICALL Java_com_example_opencvtest_OpenCV_FindFeatures(JNIEnv*, jobject, jlong addrGray, jlong addrRgba)
{
	char logMsg[100];
	Mat* pMatGr=(Mat*)addrGray;
	Mat* pMatRgb=(Mat*)addrRgba;
	sbrc::Log::info("Log print");
	pImage=&((IplImage)(*pMatRgb));
	IplImage *img=pImage;
	sprintf(logMsg,"width=%d height=%d ",img->width,img->height);
	sbrc::Log::info(logMsg);
}
JNIEXPORT void JNICALL Java_net_learn2develop_Fragments_Sample3View_FindFeatures
(JNIEnv* env, jobject obj, jint width, jint height, jbyteArray yuv, jintArray bgra)
{
	jbyte* _yuv = env->GetByteArrayElements(yuv, 0);
	jint* _bgra = env->GetIntArrayElements(bgra, 0);

	Mat myuv(height + height/2, width, CV_8UC1, (unsigned char *)_yuv);
	Mat mbgra(height, width, CV_8UC4, (unsigned char *)_bgra);
	Mat mgray(height, width, CV_8UC1, (unsigned char *)_yuv);

	//Please make attention about BGRA byte order
	//ARGB stored in java as int array becomes BGRA at native level
	cvtColor(myuv, mbgra, CV_YUV420sp2BGR, 4);

	vector<KeyPoint> v;

	FastFeatureDetector detector(50);
	detector.detect(mgray, v);
	for( size_t i = 0; i < v.size(); i++ )
	circle(mbgra, Point(v[i].pt.x, v[i].pt.y), 10, Scalar(0,0,255,255));

	env->ReleaseIntArrayElements(bgra, _bgra, 0);
	env->ReleaseByteArrayElements(yuv, _yuv, 0);
}
}
