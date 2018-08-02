from distutils.core import setup
from distutils.extension import Extension
from Cython.Build import cythonize

SDnametoindices_extension = Extension(
    name="SDnametoindices",
    sources=["pymfsd.pyx"],
    libraries=["hdf4"],
)
setup(
    name="SDnametoindices",
    ext_modules=cythonize([SDnametoindices_extension ])
)
