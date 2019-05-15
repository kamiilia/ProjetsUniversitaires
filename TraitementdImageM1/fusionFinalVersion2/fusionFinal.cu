#include <opencv2/opencv.hpp>
#include <vector>

__global__ void fusion(unsigned char * rgb, unsigned char *out, unsigned char *out2, std::size_t cols , std::size_t rows ) {
  auto i = blockIdx.x * blockDim.x + threadIdx.x;
  auto j = blockIdx.y * blockDim.y + threadIdx.y;
  
  i-= blockIdx.x*2;
  j-= blockIdx.y*2; 
  
	if( i < cols && j < rows ) {
   out[ j * cols + i ] = (
			 307 * rgb[ 3 * ( j * cols + i ) ]
			 + 604 * rgb[ 3 * ( j * cols + i ) + 1 ]
			 + 113 * rgb[  3 * ( j * cols + i ) + 2 ]
			 ) / 1024;
  }
    
  __syncthreads();
    
  int h, v, res;
  if( i < cols-1 && j < rows-1 && i>0 && j>0) {
    // Horizontal
			h =    out[((j - 1) * cols + i - 1)] -    out[((j - 1) * cols + i + 1)]
				+ 2 *out[( j      * cols + i - 1)] - 2 *out[( j      * cols + i + 1)]
				+    out[((j + 1) * cols + i - 1)] -    out[((j + 1) * cols + i + 1)];
			// Vertical
			v =    out[((j - 1) * cols + i - 1)] -    out[((j + 1) * cols + i - 1)]
				+ 2 *out[((j - 1) * cols + i    )] - 2 *out[((j + 1) * cols + i    )]
				+    out[((j - 1) * cols + i + 1)] -    out[((j + 1) * cols + i + 1)];
			res = h*h + v*v;
			res = res > 255*255 ? res = 255*255 : res;
			out2[(j * cols + i)] = sqrt((float)res);
  }

}


int main()
{
  cv::Mat m_in = cv::imread("in.jpg", cv::IMREAD_UNCHANGED );
  auto rgb = m_in.data;
  auto rows = m_in.rows;
  auto cols = m_in.cols;
  std::vector< unsigned char >out( rows * cols );
  cv::Mat m_out( rows, cols, CV_8UC1,out.data() );
  cudaError_t err = cudaSuccess;
  
  unsigned char * rgb_d;
  unsigned char *out_d;
  unsigned char *out2_d;
  
  cudaEvent_t start, stop;
  cudaEventCreate(&start);
  cudaEventCreate(&stop);
  cudaEventRecord(start);
  
  err = cudaMalloc( &rgb_d, 3 * rows * cols );
  if(err!=cudaSuccess){
    std::cerr << cudaGetErrorString(err)<< std :: endl;
    return 1;
  }
  err = cudaMalloc( &out_d, rows * cols );
  if(err!=cudaSuccess){
    std::cerr << cudaGetErrorString(err)<< std :: endl;
    return 1;
  }
  err = cudaMalloc( &out2_d, rows * cols );
  if(err!=cudaSuccess){
    std::cerr << cudaGetErrorString(err)<< std :: endl;
    return 1;
  }
  
  err = cudaMemcpy( rgb_d, rgb, 3 * rows * cols, cudaMemcpyHostToDevice );
  if(err!=cudaSuccess){
    std::cerr << cudaGetErrorString(err)<< std :: endl;
    return 1;
  }
  
  dim3 t( 32, 32 );
  dim3 b( ( cols - 1) / (t.x - 2) + 1 , ( rows - 1 ) / (t.y - 2) + 1 );
  
  fusion<<<b,t>>>(rgb_d,out_d,out2_d, cols,rows);

  err=cudaGetLastError();
  if(err!=cudaSuccess){
    std::cerr << "error grayscaleSobel : " << cudaGetErrorString(err)<< std :: endl;
    return 1;
  }
  
  err = cudaMemcpy(outdata(),out2_d, rows * cols, cudaMemcpyDeviceToHost);
  if(err!=cudaSuccess){
    std::cerr << "error cudaMemcpy : "<< cudaGetErrorString(err)<< std :: endl;
    return 1;
  }
  
  cudaEventRecord(stop);
	cudaEventSynchronize(stop);
	float elapseTime;
	cudaEventElapsedTime(&elapseTime,start,stop);
	std::cout<< elapseTime << " ms" << std::endl;
  
  cv::imwrite( "out.jpg", m_out );
  cudaFree(rgb_d);
  cudaFree(out_d);
  cudaFree(out2_d );
  return 0;
}
