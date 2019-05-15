#include <opencv2/opencv.hpp>
#include <vector>
#include <iostream>

__global__ void grayscale( unsigned char * rgb, unsigned char * g, std::size_t cols, std::size_t rows ) {
  auto i = blockIdx.x*blockDim.x+ threadIdx.x;
  auto j = blockIdx.y*blockDim.y+ threadIdx.y;
  if( i < cols && j < rows ) {
    g[ j * cols + i ] = (
			 307 * rgb[ 3 * ( j * cols + i ) ]
		       + 604 * rgb[ 3 * ( j * cols + i ) + 1 ]
		       + 113 * rgb[  3 * ( j * cols + i ) + 2 ]
		       ) / 1024;
  }
}

__global__ void sobel(unsigned char * g_in, unsigned char * g, std::size_t cols, std::size_t rows  ){
  auto i = blockIdx.x*blockDim.x+ threadIdx.x;
  auto j = blockIdx.y*blockDim.y+ threadIdx.y;
  int h, v, res;


  if(i>0 && j>0 && i < cols-1 && j < rows-1){
    // Horizontal
  	h =     g_in[((j - 1) * cols + i - 1) * 1 ] -     g_in[((j - 1) * cols + i + 1) * 1 ]
  	  + 2 * g_in[( j      * cols + i - 1) * 1 ] - 2 * g_in[( j      * cols + i + 1) * 1 ]
  	  +     g_in[((j + 1) * cols + i - 1) * 1 ] -     g_in[((j + 1) * cols + i + 1) * 1 ];

  	// Vertical

  	v =     g_in[((j - 1) * cols + i - 1) * 1 ] -     g_in[((j + 1) * cols + i - 1) * 1 ]
  	  + 2 * g_in[((j - 1) * cols + i    ) * 1 ] - 2 * g_in[((j + 1) * cols + i    ) * 1 ]
  	  +     g_in[((j - 1) * cols + i + 1) * 1 ] -     g_in[((j + 1) * cols + i + 1) * 1 ];

  	//h = h > 255 ? 255 : h;
  	//v = v > 255 ? 255 : v;
  	res = h*h + v*v;
  	res = res > 255*255 ? res = 255*255 : res;
  	g[(j * cols + i) ] = sqrt( (float) res);
  }
}

int main()
{
  cv::Mat m_in = cv::imread("in.jpg", cv::IMREAD_UNCHANGED );

  auto rgb = m_in.data;

  auto rows = m_in.rows;
  auto cols = m_in.cols;

  std::vector< unsigned char > g( rows * cols ); // image de sortie grayscale.
  std::vector< unsigned char > gsobel( rows * cols ); // image de sortie sobel.

  cv::Mat m_out( rows, cols, CV_8UC1, gsobel.data() );

  unsigned char * rgb_d;
  unsigned char * g_d;
  unsigned char * gsobel_d;
  cudaEvent_t start,stop;
  cudaEventCreate(&start);
  cudaEventCreate(&stop);
  cudaEventRecord(start);

  cudaMalloc(&rgb_d,3*rows*cols); // allocation pour l'image d'entrée sur le device.
  cudaMalloc(&g_d,rows*cols); // allocation pour l'image de sortie du grayscale sur le device.

  cudaMemcpy(rgb_d,rgb,3*rows*cols,cudaMemcpyHostToDevice); // copie de l'image d'entrée vers le device.

  dim3 t( 32, 32 );
  dim3 b( ( cols - 1) / t.x + 1 , ( rows - 1 ) / t.y + 1 );
  grayscale<<< b, t >>>( rgb_d,g_d,cols,rows );

  //cudaMemcpy(gsobel.data(),g_d,rows*cols, cudaMemcpyDeviceToHost); // récupération de l'image en niveaux de gris sur l'hôte.

  cudaDeviceSynchronize();
  auto err = cudaGetLastError();
  if( err != cudaSuccess )
  {
    std::cerr << cudaGetErrorString( err ) << std::endl;
  }

  cudaMalloc(&gsobel_d, rows*cols);
  sobel<<< b, t >>>(g_d,gsobel_d,cols,rows);

  cudaDeviceSynchronize();
  err = cudaGetLastError();
  if( err != cudaSuccess )
  {
    std::cerr << " " << cudaGetErrorString( err ) << std::endl;
  }

  cudaMemcpy(gsobel.data(),gsobel_d,rows*cols, cudaMemcpyDeviceToHost);


  cudaEventRecord(stop);
  cudaEventSynchronize(stop);
  float elapseTime;
  cudaEventElapsedTime(&elapseTime,start,stop);
  std::cout<<elapseTime<<"ms"<<std::endl;

  cv::imwrite( "out.jpg", m_out ); // sauvegarde de l'image.

  cudaFree( rgb_d );
  cudaFree( g_d);
  cudaFree( gsobel_d);

  return 0;
}
