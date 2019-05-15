#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
extern"C"{
#include <pgm.h>
#include <ppm.h>
}

__global__ void gray_oneD(pixel *in, pixel *out, int cols, int rows){
	//1D
	int i = threadIdx.x + blockIdx.x * blockDim.x;
	out[i].b = out[i].g = out[i].r = (in[i].b + in[i].g + in[i].r) / 3;
}


pixel * readppm (const char* fname, int* cols, int* rows, pixval* maxval, int* format){
  FILE* img_in;
  img_in = fopen(fname, "r");
  
  ppm_readppminit(img_in, cols, rows, maxval, format);
  
  pixel* out = (pixel*)malloc ((*cols)*(*rows)*sizeof(pixel));
  int i;
  for (i =0; i < *rows; i++){
    ppm_readppmrow(img_in, out+(i*(*cols)), *cols, *maxval, *format);
  }
  return out;
}

void writeppm(const char* fname, pixel* out, int cols, int rows, pixval maxval, int format){
  FILE* img_out;
  img_out = fopen(fname, "w+");
  int i;
  
  ppm_writeppminit(img_out, cols, rows, maxval, format);


  for (i =0; i < rows; i++){
    ppm_writeppmrow(img_out, out+(i*(cols)), cols, maxval, 1);
  }
}




int main(){
  
  const char* fname = "peppers.ppm";
  const char* fname_out = "peppers_out.ppm";
  
  pixel *ppm_in, *ppm_out; // en mémoire CPU
  pixel *d_ppm_in, *d_ppm_out; // en mémoire GPU
  
  int cols, rows, format;
  pixval maxval;
 
  //Lire image dans ppm_in
	ppm_in = readppm(fname, &cols, &rows, &maxval, &format);

  //Allocation mémoire 
	ppm_out = (pixel*)malloc(cols*rows*sizeof(pixel));
	cudaMalloc(&d_ppm_in, cols*rows*sizeof(pixel));
	cudaMalloc(&d_ppm_out, cols*rows*sizeof(pixel));
  
  //Copie de ppm_in dans d_ppm_in (en mémoire GPU)
  cudaMemcpy(d_ppm_in,ppm_in, cols*rows*sizeof(pixel), cudaMemcpyHostToDevice);
	
  
  //Déclaration tailles de grilles et blocs + lancement kernel
  int blockSize = rows;
  int gridSize = cols;

  gray_oneD<<<blockSize, gridSize>>>(d_ppm_in, d_ppm_out, cols, rows);
  
  
  //Copie du résultat dans ppm_out
    cudaMemcpy(ppm_out,d_ppm_out, cols*rows*sizeof(pixel), cudaMemcpyDeviceToHost);
 
  //Ecriture du fichier ppm
	writeppm(fname_out, ppm_out, cols, rows, maxval, format);
  
  //Libération mémoire
  cudaFree(d_ppm_in);
  cudaFree(d_ppm_out);
  
  free(ppm_out);
  free(ppm_in);
  return 0;
}


