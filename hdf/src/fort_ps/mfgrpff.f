C****************************************************************************
C* NCSA HDF                                                                 *
C* Software Development Group                                               *
C* National Center for Supercomputing Applications                          *
C* University of Illinois at Urbana-Champaign                               *
C* 605 E. Springfield, Champaign IL 61820                                   *
C*                                                                          *
C* For conditions of distribution and use, see the accompanying             *
C* hdf/COPYING file.                                                        *
C*                                                                          *
C****************************************************************************
C $Id$
C
C------------------------------------------------------------------------------
C File:     mfgrpff.f
C Purpose:  Fortran stubs for Fortran PowerStation GR routines
C Invokes:  mfgrpf.c
C Contents: 
C Remarks: none
C------------------------------------------------------------------
C----------------------------------------------------------------
C Name: mgcreat
C Purpose:  Create a new raster image
C Inputs:   
C       grid: GR ID of interface to create image in
C       name: name of raster image
C       ncomp: number of components per pixel
C       nt: number-type of each component
C       il: interlace scheme to use
C       dimsizes[2]: dimensions of the image to create
C Returns: RI ID on success, -1 on failure
C Users:    HDF Fortran programmers
C Invokes: mgicreat
C------------------------------------------------------------------------------

      integer function mgcreat(grid, name, ncomp, nt, il, dimsizes)
      character*(*) name
      integer grid, ncomp, nt, il, dimsizes
C     integer  mgicreat
      INTERFACE 
         INTEGER FUNCTION mgicreat(grid,name,ncomp,nt,il,dimsizes,
     +                             nmlen)
            !MS$ATTRIBUTES C,reference,alias:'_MGICREAT' :: mgicreat
            integer grid, ncomp,nt,il,dimsizes, nmlen
            character*(*) name
         END FUNCTION mgicreat
      END INTERFACE
 
      mgcreat = mgicreat(grid, name, ncomp, nt, il, dimsizes,
     +                                              len(name))
      return
      end

C------------------------------------------------------------------------------
C Name: mgn2ndx
C Purpose:  Map the name of a raster image to an index in the file
C Inputs:   
C       grid: GR ID of interface to create image in
C       name: name of raster image
C Returns: index of image on success, -1 on failure
C Users:    HDF Fortran programmers
C Invokes: mgin2ndx
C------------------------------------------------------------------------------

      integer function mgn2ndx(grid, name)
      character*(*) name
      integer grid
C     integer mgin2ndx
      INTERFACE
        INTEGER FUNCTION mgin2ndx(grid,name,nmlen)
          !MS$ATTRIBUTES C,reference,alias:'_MGIN2NDX' :: mgin2ndx
          integer grid, nmlen
          character*(*) name
        END FUNCTION mgin2ndx
      END INTERFACE

      mgn2ndx = mgin2ndx(grid, name, len(name))
      return
      end

C------------------------------------------------------------------------------
C Name: mgsxfil
C Purpose:  Convert a standard image into an external image
C Inputs:   
C       riid: RI ID of image to move
C       filename: filename of file to move image into
C       offset: offset in file to move image to
C Returns: SUCCEED/FAIL
C Users:    HDF Fortran programmers
C Invokes: mgisxfil
C-------------------------------------------------------------

      integer function mgsxfil(riid, filename, offset)
      character*(*) filename
      integer riid, offset
C      integer mgisxfil
      INTERFACE
        INTEGER FUNCTION mgisxfil(riid,filename,offset, nmlen)
          !MS$ATTRIBUTES C,reference,alias:'_MGISXFIL' :: mgisxfil
          integer riid, offset, nmlen
          character*(*) filename
        END FUNCTION mgisxfil
      END INTERFACE

      mgsxfil = mgisxfil(riid, filename, offset, len(filename))
      return
      end

C-------------------------------------------------------------
C Name: mgscatt
C Purpose:  Add a char type attribute to a raster image
C Inputs:   
C       riid: RI ID of image
C       name: the name of the attribute
C       nt: the number-type of the attribute
C       count: the number of values in the attribute
C       data: the data for the attribute
C Returns: SUCCEED/FAIL
C Users:    HDF Fortran programmers
C Invokes: mgiscatt
C-------------------------------------------------------------

      integer function mgscatt(riid, name, nt, count, data)
      character*(*) name
      character*(*) data
      integer riid, nt, count
C      integer mgiscatt
      INTERFACE
        INTEGER FUNCTION mgiscatt(riid,name,nt,count,data,
     +                             nmlen)
          !MS$ATTRIBUTES C,reference,alias:'_MGISCATT' :: mgiscatt
          integer riid,nt,count, nmlen
          character*(*) name, data
         END FUNCTION mgiscatt
      END INTERFACE

      mgscatt = mgiscatt(riid, name, nt, count, data, len(name))
      return
      end

C-------------------------------------------------------------
C Name: mgsnatt
C Purpose:  Add a numeric attribute to a raster image
C Inputs:   
C       riid: RI ID of image
C       name: the name of the attribute
C       nt: the number-type of the attribute
C       count: the number of values in the attribute
C       data: the data for the attribute
C Returns: SUCCEED/FAIL
C Users:    HDF Fortran programmers
C Invokes: mgisattr
C-------------------------------------------------------------

      integer function mgsnatt(riid, name, nt, count, data)
      character*(*) name
      integer data
      integer riid, nt, count
C      integer mgisattr
      INTERFACE
        INTEGER FUNCTION mgisattr(riid,name,nt,count,data,
     +                             nmlen)
          !MS$ATTRIBUTES C,reference,alias:'_MGISATTR' :: mgisattr
          integer riid, nt,count,data, nmlen
          character*(*) name
        END FUNCTION mgisattr
      END INTERFACE

      mgsnatt = mgisattr(riid, name, nt, count, data, len(name))
      return
      end

C-------------------------------------------------------------
C Name: mgsattr
C Purpose:  Add an attribute to a raster image
C Inputs:   
C       riid: RI ID of image
C       name: the name of the attribute
C       nt: the number-type of the attribute
C       count: the number of values in the attribute
C       data: the data for the attribute
C Returns: SUCCEED/FAIL
C Users:    HDF Fortran programmers
C Invokes: mgisattr
C-------------------------------------------------------------

C      integer function mgsattr(riid, name, nt, count, data)
C      character*(*) name
C      character*(*) data
C      integer riid, nt, count
C      integer mgisattr
C      INTERFACE
C        INTEGER FUNCTION mgisattr(riid,name,nt,count,data,
C     +                             nmlen)
C          !MS$ATTRIBUTES C,reference,alias:'_MGISATTR' :: mgisattr
C          integer riid, nt,count, nmlen
C          character*(*) name, data
C        END FUNCTION mgisattr
C      END INTERFACE

C      mgsattr = mgisattr(riid, name, nt, count, data, len(name))
C      return
C      end
C---------------------------------------------------------------
C Name: mgfndat
C Purpose:  Locate an attribute for a raster image
C Inputs:   
C       riid: RI ID of image
C       name: the name of the attribute
C Returns: SUCCEED/FAIL
C Users:    HDF Fortran programmers
C Invokes: mgifndat
C----------------------------------------------------------------

      integer function mgfndat(riid, name)
      character*(*) name
      integer riid
C      integer mgifndat
      INTERFACE
        INTEGER FUNCTION mgifndat(riid,name,nmlen)
          !MS$ATTRIBUTES C,reference,alias:'_MGIFNDAT' :: mgifndat
          integer riid, nmlen
          character*(*) name
        END FUNCTION mgifndat
      END INTERFACE

      mgfndat = mgifndat(riid, name, len(name))
      return
      end
C---------------------------------------------------------------
      integer function mgstart(fid)
        integer fid
C        integer mgistrt
      INTERFACE
        INTEGER FUNCTION mgistrt(fid)
          !MS$ATTRIBUTES C,reference,alias:'_MGISTRT' :: mgistrt
          integer fid
        END FUNCTION mgistrt
      END INTERFACE
     
      mgstart = mgistrt(fid)
      return
      end
C------------------------------------------------------------------
     
      integer function mgfinfo(grid,datasets,attrs)
        integer grid, datasets,attrs
C        integer mgifinf
      INTERFACE
        INTEGER FUNCTION mgifinf(grid,datasets,attrs)
          !MS$ATTRIBUTES C,reference,alias:'_MGIFINF' :: mgifinf
          integer grid,datasets,attrs
        END FUNCTION mgifinf
      END INTERFACE
     
      mgfinfo = mgifinf(grid, datasets, attrs)
      return
      end
C------------------------------------------------------------------
     
      integer function mgend(grid)
        integer grid
C        integer mgiend
      INTERFACE
        INTEGER FUNCTION mgiend(grid)
          !MS$ATTRIBUTES C,reference,alias:'_MGIEND' :: mgiend
          integer grid
        END FUNCTION mgiend
      END INTERFACE
     
      mgend = mgiend(grid)
      return
      end
C------------------------------------------------------------------
     
      integer function mgselct(grid,index)
        integer grid, index
C        integer mgislct
      INTERFACE
        INTEGER FUNCTION mgislct(grid,index)
          !MS$ATTRIBUTES C,reference,alias:'_MGISLCT' :: mgislct
          integer grid,index
        END FUNCTION mgislct
      END INTERFACE
     
      mgselct = mgislct(grid, index)
      return
      end
C------------------------------------------------------------------
     
      integer function mggiinf(riid,name,ncomp,nt,il,dimsizes,attrs)
        integer riid, ncomp,nt,il,dimsizes,attrs
        character*(*) name
C        integer mgigiinf
      INTERFACE
        INTEGER FUNCTION mgigiinf(riid,name,ncomp,nt,il,
     +                            dimsizes,attrs)
          !MS$ATTRIBUTES C,reference,alias:'_MGIGIINF' :: mgigiinf
          integer riid,ncomp,nt,il,dimsizes,attrs
          character*(*) name
        END FUNCTION mgigiinf
      END INTERFACE
     
      mggiinf = mgigiinf(riid,name,ncomp,nt,il,dimsizes,attrs)
      return
      end
C------------------------------------------------------------------
    
      integer function mgwcimg(riid,start,stride,count,cdata)
        integer riid, start,stride,count
        character*(*) cdata
C        integer mgiwcim
      INTERFACE
        INTEGER FUNCTION mgiwcim(riid,start,stride,count,
     +                            cdata)
          !MS$ATTRIBUTES C,reference,alias:'_MGIWCIM' :: mgiwcim
          integer riid,start,stride,count
          character*(*) cdata
        END FUNCTION mgiwcim
      END INTERFACE

      mgwcimg = mgiwcim(riid,start,stride,count,cdata)
      return
      end
C------------------------------------------------------------------
    
      integer function mgwrimg(riid,start,stride,count,data)
        integer riid, start,stride,count,data
C        integer mgiwimg
      INTERFACE
        INTEGER FUNCTION mgiwimg(riid,start,stride,count,
     +                            data)
          !MS$ATTRIBUTES C,reference,alias:'_MGIWIMG' :: mgiwimg
          integer riid,start,stride,count,data
        END FUNCTION mgiwimg
      END INTERFACE

      mgwrimg = mgiwimg(riid,start,stride,count,data)
      return
      end

C------------------------------------------------------------------
    
      integer function mgrcimg(riid,start,stride,count,cdata)
        integer riid, start,stride,count
        character*(*) cdata
C        integer mgircim
      INTERFACE
        INTEGER FUNCTION mgircim(riid,start,stride,count,
     +                            cdata)
          !MS$ATTRIBUTES C,reference,alias:'_MGIRCIM' :: mgircim
          integer riid,start,stride,count
          character*(*) cdata
        END FUNCTION mgircim
      END INTERFACE

      mgrcimg = mgircim(riid,start,stride,count,cdata)
      return
      end
C------------------------------------------------------------------
    
      integer function mgrdimg(riid,start,stride,count,data)
        integer riid, start,stride,count,data
C        integer mgirimg
      INTERFACE
        INTEGER FUNCTION mgirimg(riid,start,stride,count,
     +                            data)
          !MS$ATTRIBUTES C,reference,alias:'_MGIRIMG' :: mgirimg
          integer riid,start,stride,count,data
        END FUNCTION mgirimg
      END INTERFACE

      mgrdimg = mgirimg(riid,start,stride,count,data)
      return
      end

C------------------------------------------------------------------
    
      integer function mgendac(riid)
        integer riid
C        integer mgiendac
      INTERFACE
        INTEGER FUNCTION mgiendac(riid)
          !MS$ATTRIBUTES C,reference,alias:'_MGIENDAC' :: mgiendac
          integer riid
        END FUNCTION mgiendac
      END INTERFACE

      mgendac = mgiendac(riid)
      return
      end
C------------------------------------------------------------------
   
      integer function mgid2rf(riid)
        integer riid
C        integer mgiid2r
      INTERFACE
        INTEGER FUNCTION mgiid2r(riid)
          !MS$ATTRIBUTES C,reference,alias:'_MGIID2R' :: mgiid2r
          integer riid
        END FUNCTION mgiid2r
      END INTERFACE

      mgid2rf = mgiid2r(riid)
      return
      end
C------------------------------------------------------------------
   
      integer function mgr2idx(grid, ref)
        integer grid, ref
C        integer mgir2dx
      INTERFACE
        INTEGER FUNCTION mgir2dx(grid,ref)
          !MS$ATTRIBUTES C,reference,alias:'_MGIR2DX' :: mgir2dx
          integer grid, ref
        END FUNCTION mgir2dx
      END INTERFACE

      mgr2idx = mgir2dx(grid, ref)
      return
      end
C------------------------------------------------------------------
   
      integer function mgrltil(riid, il)
        integer riid, il
C        integer mgiltil
      INTERFACE
        INTEGER FUNCTION mgiltil(riid, il)
          !MS$ATTRIBUTES C,reference,alias:'_MGILTIL' :: mgiltil
          integer riid, il
        END FUNCTION mgiltil
      END INTERFACE

      mgrltil = mgiltil(riid, il)
      return
      end
C------------------------------------------------------------------
   
      integer function mgrimil(riid, il)
        integer riid, il
C        integer mgiimil
      INTERFACE
        INTEGER FUNCTION mgiimil(riid, il)
          !MS$ATTRIBUTES C,reference,alias:'_MGIIMIL' :: mgiimil
          integer riid, il
        END FUNCTION mgiimil
      END INTERFACE

      mgrimil = mgiimil(riid, il)
      return
      end
C------------------------------------------------------------------
   
      integer function mggltid(riid, lut_index)
        integer riid, lut_index
C        integer mgiglid
      INTERFACE
        INTEGER FUNCTION mgiglid(riid, lut_index)
          !MS$ATTRIBUTES C,reference,alias:'_MGIGLID' :: mgiglid
          integer riid, lut_index
        END FUNCTION mgiglid
      END INTERFACE

      mggltid = mgiglid(riid, lut_index)
      return
      end
C------------------------------------------------------------------
   
      integer function mgglinf(lutid,ncomp,nt,il,nentries)
        integer lutid,ncomp,nt,il,nentries
C        integer mgiglinf
      INTERFACE
        INTEGER FUNCTION mgiglinf(lutid,ncomp,nt,il,nentries)
          !MS$ATTRIBUTES C,reference,alias:'_MGIGLINF' :: mgiglinf
          integer lutid, ncomp, nt, il, nentries
        END FUNCTION mgiglinf
      END INTERFACE

      mgglinf = mgiglinf(lutid,ncomp,nt,il,nentries)
      return
      end
C------------------------------------------------------------------
   
      integer function mgwclut(lutid,ncomp,nt,il,nentries,cdata)
        integer lutid,ncomp,nt,il,nentries
        character*(*) cdata
C        integer mgiwclt
      INTERFACE
        INTEGER FUNCTION mgiwclt(lutid,ncomp,nt,il,nentries,cdata)
          !MS$ATTRIBUTES C,reference,alias:'_MGIWCLT' :: mgiwclt
          integer lutid, ncomp, nt, il, nentries
          character*(*) cdata
        END FUNCTION mgiwclt
      END INTERFACE

      mgwclut = mgiwclt(lutid,ncomp,nt,il,nentries,cdata)
      return
      end
C------------------------------------------------------------------
   
      integer function mgwrlut(lutid,ncomp,nt,il,nentries,data)
        integer lutid,ncomp,nt,il,nentries, data
C        integer mgiwrlt
      INTERFACE
        INTEGER FUNCTION mgiwrlt(lutid,ncomp,nt,il,nentries,data)
          !MS$ATTRIBUTES C,reference,alias:'_MGIWRLT' :: mgiwrlt
          integer lutid, ncomp, nt, il, nentries, data
        END FUNCTION mgiwrlt
      END INTERFACE

      mgwrlut = mgiwrlt(lutid,ncomp,nt,il,nentries,data)
      return
      end
C------------------------------------------------------------------
   
      integer function mgrclut(lutid,cdata)
        integer lutid
        character*(*) cdata
C        integer mgirclt
      INTERFACE
        INTEGER FUNCTION mgirclt(lutid,cdata)
          !MS$ATTRIBUTES C,reference,alias:'_MGIRCLT' :: mgirclt
          integer lutid
          character*(*) cdata
        END FUNCTION mgirclt
      END INTERFACE

      mgrclut = mgirclt(lutid,cdata)
      return
      end
C------------------------------------------------------------------
   
      integer function mgrdlut(lutid,data)
        integer lutid, data
C        integer mgirdlt
      INTERFACE
        INTEGER FUNCTION mgirdlt(lutid,data)
          !MS$ATTRIBUTES C,reference,alias:'_MGIRDLT' :: mgirdlt
          integer lutid, data
        END FUNCTION mgirdlt
      END INTERFACE

      mgrdlut = mgirdlt(lutid,data)
      return
      end
C------------------------------------------------------------------
   
      integer function mgsactp(riid,acctype)
        integer riid, acctype
C        integer mgiactp
      INTERFACE
        INTEGER FUNCTION mgiactp(riid,acctype)
          !MS$ATTRIBUTES C,reference,alias:'_MGIACTP' :: mgiactp
          integer riid,acctype
        END FUNCTION mgiactp
      END INTERFACE

      mgsactp = mgiactp(riid,acctype)
      return
      end
C------------------------------------------------------------------

      integer function mgatinf(riid,index,name,nt,count)
        integer riid, index,nt,count
        character*(*) name
C        integer mgiainf
      INTERFACE
        INTEGER FUNCTION mgiainf(riid,index,name,nt,count)
          !MS$ATTRIBUTES C,reference,alias:'_MGIAINF' :: mgiainf
          integer riid,index,nt,count
          character*(*) name
        END FUNCTION mgiainf
      END INTERFACE

      mgatinf = mgiainf(riid,index,name,nt,count)
      return
      end

C-------------------------------------------------------------
C Name: mggcatt
C Purpose:  Get a char type attribute of a raster image
C Inputs:   
C       riid: RI ID of image
C       index: the index of the attribute
C       cdata: buffer to hold the data of the attribute
C Returns: SUCCEED/FAIL
C Users:    HDF Fortran programmers
C Invokes: mgigcat
C-------------------------------------------------------------

      integer function mggcatt(riid, index, cdata)
      integer riid, index
      character*(*) cdata
C      integer mgigcat
      INTERFACE
        INTEGER FUNCTION mgigcat(riid,index,cdata)
          !MS$ATTRIBUTES C,reference,alias:'_MGIGCAT' :: mgigcat
          integer riid,index
          character*(*)  cdata
         END FUNCTION mgigcat
      END INTERFACE

      mggcatt = mgigcat(riid, index, cdata)
      return
      end

C-------------------------------------------------------------
C Name: mggnatt
C Purpose:  Get a numeric attribute of a raster image
C Inputs:   
C       riid: RI ID of image
C       index: the index of the attribute
C       data: the data of the attribute
C Returns: SUCCEED/FAIL
C Users:    HDF Fortran programmers
C Invokes: mgignat
C-------------------------------------------------------------

      integer function mggnatt(riid, index, data)
      integer data
      integer riid, index
C      integer mgignat
      INTERFACE
        INTEGER FUNCTION mgignat(riid,index,data)
          !MS$ATTRIBUTES C,reference,alias:'_MGIGNAT' :: mgignat
          integer riid, index,data
        END FUNCTION mgignat
      END INTERFACE

      mggnatt = mgignat(riid, index, data)
      return
      end

C-------------------------------------------------------------
C Name: mggatt
C Purpose:  Get an attribute of a raster image
C Inputs:   
C       riid: RI ID of image
C       index: the index of the attribute
C       data: the data for the attribute
C Returns: SUCCEED/FAIL
C Users:    HDF Fortran programmers
C Invokes: mgisattr
C-------------------------------------------------------------
      integer function mggattr(riid, index, data)
      integer data
      integer riid, index
C      integer mgigatt
      INTERFACE
        INTEGER FUNCTION mgigatt(riid,index,data)
          !MS$ATTRIBUTES C,reference,alias:'_MGIGATT' :: mgigatt
          integer riid, index,data
        END FUNCTION mgigatt
      END INTERFACE

      mggattr = mgigatt(riid, index, data)
      return
      end

