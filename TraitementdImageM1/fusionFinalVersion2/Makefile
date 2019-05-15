
CFLAGS=-O3 -march=native

LDLIBS=`pkg-config --libs opencv`


fusion: fusionFinal.cu
	nvcc -o $@ $< $(LDLIBS)


.PHONY: clean

clean:
	rm sobel
