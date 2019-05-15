

#include <opencv2/opencv.hpp>
#include <vector>

__global__ void grayscale( unsigned char * rgb, unsigned char * g, std::size_t cols, std::size_t rows ) {
  auto i = blockIdx.x * blockDim.x + threadIdx.x;
  auto j = blockIdx.y * blockDim.y + threadIdx.y;
  if( i < cols && j < rows ) {
    g[ j * cols + i ] = (
			 307 * rgb[ 3 * ( j * cols + i ) ]
			 + 604 * rgb[ 3 * ( j * cols + i ) + 1 ]
			 + 113 * rgb[  3 * ( j * cols + i ) + 2 ]
			 ) / 1024;
  }
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

  cudaError_t err;
 
  cudaEvent_t start, stop;
  cudaEventCreate( &start );
  cudaEventCreate( &stop );
  cudaEventRecord( start );

  err= cudaMalloc( &rgb_d, 3 * rows * cols );
  if( err != cudaSuccess){
       std::cerr << cudaGetErrorString(err)<<"Erreur lors de l'allocation"<< std::endl;;
   }
  err=cudaMalloc( &g_d, rows * cols );
     if( err != cudaSuccess){
       std::cerr << cudaGetErrorString(err)<<"Erreur lors de l'allocation"<< std::endl;;
   }
  err=cudaMemcpy( rgb_d, rgb, 3 * rows * cols, cudaMemcpyHostToDevice );
if( err != cudaSuccess){
       std::cerr << cudaGetErrorString(err)<<"Erreur lors de la copie host to device"<< std::endl;;
   }
  dim3 t( 32, 32 );
  dim3 b( ( cols - 1) / t.x + 1 , ( rows - 1 ) / t.y + 1 );
  grayscale<<< b, t >>>( rgb_d, g_d, cols, rows );
 // Récupération du code erreur du kernel en cas de plantage.
  cudaDeviceSynchronize(); // Attente de la fin d'exécution du kernel.
  cudaError err = cudaGetLastError();
  if( err != cudaSuccess )
  {
    std::cerr << cudaGetErrorString( err ); // récupération du message associé au code erreur.
  }

  err=cudaMemcpy( g.data(), g_d, rows * cols, cudaMemcpyDeviceToHost );
  if( err != cudaSuccess){
       std::cerr << cudaGetErrorString(err)<<"Erreur lors de la copie devise to host"<< std::endl;;
   }
  cudaEventRecord( stop );

  float duration = 0.0f;
  cudaEventElapsedTime( &duration, start, stop );

  std::cout << "Total Temps mis pour gray kernel est : " << duration << "ms\n";
  cv::imwrite( "out.jpg", m_out );
  cudaFree( rgb_d);
  cudaFree( g_d);
  return 0;
}
