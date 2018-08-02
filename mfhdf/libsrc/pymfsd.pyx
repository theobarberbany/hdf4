# Try to wrap SDnametoindices using cython.
# found on line 1075 of mfsd.c
# I don't understand how var_list works / what I need to do
# Is it optional ?

# cdef extern from "mfhdf.h":
    # intn SDnametoindicies(int32 fid, const char *name, hdf_varlist_t* var_list)

cdef extern from "mfhdf.h":
   int SDnametoindicies(int fid, const char *name)

def pySDnametoindicies(fid, name: bytes) -> int:
    return SDnametoindicies(fid, name)
