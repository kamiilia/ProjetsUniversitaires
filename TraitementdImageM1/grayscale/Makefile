
CXXFLAGS=-O3 -march=native
LDLIBS=`pkg-config --libs opencv`


grayscale_cuda: gray.cu
	nvcc -o $@ $< $(LDLIBS)
.PHONY: clean

clean:
	rm grayscale
