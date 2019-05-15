
#include <opencv2/opencv.hpp>
#include <vector>
#include <math.h>

#include <stdio.h>
#include <stdlib.h>
#include <sys/time.h>

#include<chrono>
#include <IL/il.h>

__global__ void fusion(unsigned char * rgb,unsigned char * g, std::size_t cols, std::size_t rows ) {
  auto i = blockIdx.x * blockDim.x + threadIdx.x;
  auto j = blockIdx.y * blockDim.y + threadIdx.y;
int h, v, res;

	// Debut grayscale
if(i < cols && j < rows ) {
    g[ j * cols + i ] = (
			 307 * rgb[ 3 * ( j * cols + i ) ]
			 + 604 * rgb[ 3 * ( j * cols + i ) + 1 ]
			 + 113 * rgb[  3 * ( j * cols + i ) + 2 ]
			 ) / 1024;
  }

	// Fin grayscale

	// Debut sobel
 if( i>0 && j>0 && i<(cols-1) && j<(rows-1)) {

	// Horizontal
	h =     g[((j - 1) * cols + i - 1)] -   g[((j - 1) * cols + i + 1)]
	  + 2 * g[( j      * cols + i - 1)] - 2 * g[( j      * cols + i + 1)]
	  +     g[((j + 1) * cols + i - 1)] -     g[((j + 1) * cols + i + 1)];

	// Vertical

	v =     g[((j - 1) * cols + i - 1)] -     g[((j + 1) * cols + i - 1)]
	  + 2 * g[((j - 1) * cols + i    )] - 2 * g[((j + 1) * cols + i    )]
	  +     g[((j - 1) * cols + i + 1)] -     g[((j + 1) * cols + i + 1)];


	res = h*h + v*v;
	res = res > 255*255 ? res = 255*255 : res;

	g[(j * cols + i)] = sqrtf(res);
	// Fin sobel

      }



int main()
{
  cv::Mat m_in = cv::imread("in.jpg", cv::IMREAD_UNCHANGED );
	
  auto rgb = m_in.data;
  auto rows = m_in.rows;
  auto cols = m_in.cols;
  std::vector< unsigned char > g( rows * cols );
  cv::Mat m_out( rows, cols, CV_8UC1, g.data() );
  unsigned char * rgb_d;
  unsigned char * g_d;

  cudaMalloc( &rgb_d, 3 * rows * cols );
  cudaMalloc( &g_d, rows * cols );
  cudaMemcpy( rgb_d, rgb, 3 * rows * cols, cudaMemcpyHostToDevice );
  dim3 t( 32, 32 );
  dim3 b( ( cols - 1) / t.x + 1 , ( rows - 1 ) / t.y + 1 );
  
  fusion<<< b, t >>>( rgb_d,g_d, cols, rows );


  cudaMemcpy( g.data(), g_d, rows * cols, cudaMemcpyDeviceToHost );

  cv::imwrite( "out.jpg", m_out );
  cudaFree( rgb_d);
  cudaFree( g_d);
  return 0;
}
