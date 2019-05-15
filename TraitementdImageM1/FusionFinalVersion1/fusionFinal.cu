
__global__ void fusion(unsigned char * rgb, unsigned char * g, unsigned char * g2, std::size_t cols, std::size_t rows ) { // image d'entrée en couleur et image de sortie en couleur

  auto i=blockIdx.x*blockDim.x + threadIdx.x;
  auto j= blockIdx.y*blockDim.y + threadIdx.y;
  int h, v, res;
  if( i > 0 && i < (cols-1) && j > 0 && j < (rows-1) ) {
     
               //debut grayscale
              
               g[((j - 1) * cols + i - 1) ] = (307 * rgb [ 3 * ((j - 1) * cols + i - 1)] + 604 * rgb[ 3 * ((j - 1) * cols + i - 1) + 1 ] + 113 * rgb[  3 * ((j - 1) * cols + i - 1) + 2 ]) / 1024;

                g[((j - 1) * cols + i + 1) ] = (307 * rgb [ 3 * ((j - 1) * cols + i + 1)] + 604 * rgb[ 3 * ((j - 1) * cols + i + 1) + 1 ] + 113 * rgb[  3 * ((j - 1) * cols + i + 1) + 2 ]) / 1024;
               
                g[( j      * cols + i - 1) ] = (307 * rgb [ 3 * ( j      * cols + i - 1)] + 604 * rgb[ 3 * ( j      * cols + i - 1) + 1 ] + 113 * rgb[ 3 * ( j      * cols + i - 1) + 2 ]) / 1024;


                g[( j      * cols + i + 1)] = (307 * rgb [ 3 * ( j      * cols + i + 1)] + 604 * rgb[ 3 * ( j      * cols + i + 1) + 1 ] + 113 * rgb[ 3 * ( j      * cols + i + 1) + 2 ]) / 1024;

                g[((j + 1) * cols + i - 1) ] = (307 * rgb [ 3 * ((j + 1) * cols + i - 1)] + 604 * rgb[ 3 * ((j + 1) * cols + i - 1) + 1 ] + 113 * rgb[ 3 * ((j + 1) * cols + i - 1) + 2 ]) / 1024;

                g[((j + 1) * cols + i + 1)] = (307 * rgb [ 3 * ((j + 1) * cols + i + 1)] + 604 * rgb[ 3 * ((j + 1) * cols + i + 1) + 1 ] + 113 * rgb[ 3 * ((j + 1) * cols + i + 1) + 2 ]) / 1024;
               
                //fin grayscale
               
                // debut sobel

                // Horizontal
                h =     g[((j - 1) * cols + i - 1)] -     g[((j - 1) * cols + i + 1) ]
                  + 2 * g[( j      * cols + i - 1)] - 2 * g[( j      * cols + i + 1) ]
                  +     g[((j + 1) * cols + i - 1) ] -     g[((j + 1) * cols + i + 1) ];

                // Vertical

                v =     g[((j - 1) * cols + i - 1) ] -     g[((j + 1) * cols + i - 1) ]
                  + 2 * g[((j - 1) * cols + i    ) ] - 2 * g[((j + 1) * cols + i    ) ]
                  +     g[((j - 1) * cols + i + 1) ] -     g[((j + 1) * cols + i + 1) ];

                res = h*h + v*v;
                res = res > 255*255 ? res = 255*255 : res;

                g2[ (j * cols + i)] = sqrtf(res);
                //fin sobel
               
      }
    
  
  
 
}






int main()
{
 
  //Debut sobel et grayscale
 
  cv::Mat m_in = cv::imread("in.jpg", cv::IMREAD_UNCHANGED );

  auto rgb = m_in.data;  //l'image d'entrée en couleur

  auto rows = m_in.rows;
  auto cols = m_in.cols;

  std::vector< unsigned char > g( rows * cols ); // image de sortie en niveau de gris

  cv::Mat m_out( rows, cols, CV_8UC1, g.data() );

  unsigned char * rgb_d;
  unsigned char * g_d;
  unsigned char * g_d3;
 
  cudaError_t err;
 
  cudaEvent_t start, stop;
  cudaEventCreate( &start );
  cudaEventCreate( &stop );

  cudaEventRecord( start );

  err=cudaMalloc( &rgb_d,3*rows*cols ); // allocation pour l'image d'entrée sur le device. 3 = rgb
   if( err != cudaSuccess){
       std::cerr << cudaGetErrorString(err)<<"Erreur lors de l'allocation"<< std::endl;;
   }
  err=cudaMalloc( &g_d,rows*cols  ); // allocation pour l'image de sortie sur le device.
   if( err != cudaSuccess){
       std::cerr << cudaGetErrorString(err)<<"Erreur lors de l'allocation"<< std::endl;;
   }
  err=cudaMalloc( &g_d3,rows*cols ); // allocation pour l'image de sortie sur le device.
   if( err != cudaSuccess){
       std::cerr << cudaGetErrorString(err)<<"Erreur lors de l'allocation"<< std::endl;;
   }

  err=cudaMemcpy(rgb_d,rgb, 3*rows*cols ,cudaMemcpyHostToDevice ); // copie de l'image d'entrée vers le device. la destination en premier
   if( err != cudaSuccess){
       std::cerr << cudaGetErrorString(err)<<"Erreur lors de la copie host to device"<< std::endl;;
   }

  dim3 t( 32, 32 );
  dim3 b( ( cols - 1) / t.x + 1 , ( rows - 1 ) / t.y + 1 );
 
 
 
 
  fusion<<< b, t >>>( rgb_d, g_d,g_d3, cols, rows );
 
  // Récupération du code erreur du kernel en cas de plantage
  cudaDeviceSynchronize();
  err= cudaGetLastError();
  if( err!= cudaSuccess){
      std::cerr << cudaGetErrorString(err) <<"Erreur lors de l'appel du kernel fusion"<<std::endl;;
  }
 
  //Récupération du code erreur pour les fonctions CUDA synchrones

   err = cudaMemcpy( g.data(), g_d3, rows*cols, cudaMemcpyDeviceToHost); // récupération de l'image en niveaux de gris sur l'hôte.
   if( err != cudaSuccess){
       std::cerr << cudaGetErrorString(err)<<"Erreur lors de la copie devise to host"<< std::endl;;
   }


  cudaDeviceSynchronize();
  cudaEventRecord( stop );
  cudaEventSynchronize( stop );

  float duration = 0.0f;
  cudaEventElapsedTime( &duration, start, stop );

  std::cout << "Total Temps mis pour la fusion des deux kernels : " << duration << "ms\n";
  cv::imwrite( "out_fusion.jpg", m_out ); // sauvegarde de l'image.

  cudaFree( rgb_d );
  cudaFree( g_d);
  cudaFree( g_d3);
  //Fin grayscale
 
 

  return 0;
}
