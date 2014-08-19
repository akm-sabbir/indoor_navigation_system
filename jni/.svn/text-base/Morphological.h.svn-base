#pragma once
#include<opencv2/core/core.hpp>
#include<iostream>
#include<opencv/highgui.h>
#include<opencv/cv.h>

using namespace cv;

class Morphological {
public:
    Morphological(void);
    ~Morphological(void);
    IplImage* opening(IplImage *image, int rows, int cols, int shape, int iter);
    IplImage* closing(IplImage *image, int rows, int cols, int shape, int iter);
};

