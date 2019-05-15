CC=gcc
CFLAGS=-O3 -march=native

LDLIBS=`pkg-config --libs opencv`


sobel: sobel.cu
	nvcc -o $@ $< $(LDLIBS)


.PHONY: clean

clean:
	rm sobel
