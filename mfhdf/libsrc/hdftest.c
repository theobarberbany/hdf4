#define CHECK(status, name) {if(status == FAIL) { printf("*** Routine %s FAILED at line %d ***\n", name, __LINE__); num_err++;}}

#include "mfsd.h"

#define FILE1 "test1.hdf"
#define FILE2 "test2.hdf"

#ifdef PROTOTYPE
main(int argc, char *argv[])
#else
main(argc, argv)
int argc;
char *argv[];
#endif 
{
    int32 f1, f2, sdsid, nt, dimsize[10], nattr, rank;
    int32 newsds, newsds2, newsds3, count, dimid, number;
    intn status, i;
    char name[90], text[256];
    int32   start[10], end[10], scale[10], stride[10];
    float32 data[1000], max, min;
    float64 cal, cale, ioff, ioffe;
    char    l[80], u[80], fmt[80], c[80];
    int     num_err = 0;
    int16   sdata[100];

    ncopts = NC_VERBOSE;

    f1 = SDstart(FILE1, DFACC_CREATE);
    CHECK(f1, "SDstart");

    f2 = SDstart(FILE2, DFACC_CREATE);
    CHECK(f2, "SDstart");

    status = SDfileinfo(f1, &number, &nattr);
    if((status != SUCCEED) && (number != 0)) {
        fprintf(stderr, "File1 still has stuff in it\n");
        num_err++;
    }

    dimsize[0] = 4;
    dimsize[1] = 8;
    newsds = SDcreate(f1, "DataSetAlpha", DFNT_FLOAT32, 2, dimsize);
    if(newsds == FAIL) {
        fprintf(stderr, "Failed to create a new data set alpha\n");
        num_err++;
    }

    newsds3 = SDcreate(f1, "DataSetGamma", DFNT_FLOAT64, 1, dimsize);
    if(newsds3 == FAIL) {
        fprintf(stderr, "Failed to create a new data set gamma\n");
        num_err++;
    }

    status = SDfileinfo(f1, &number, &nattr);
    if(number != 2) {
        fprintf(stderr, "Wrong number of datasets in file 1\n");
        num_err++;
    }

/*

    dimid = SDgetdimid(newsds3, 0);
    if(dimid == FAIL) {
        fprintf(stderr, "Failed to get dimension id\n");
        num_err++;
    }

    status = SDsetdimname(dimid, "MyDim");
    CHECK(status, "SDsetdimname");

*/

    dimid = SDgetdimid(newsds, 0);
    if(dimid == FAIL) {
        fprintf(stderr, "Failed to get dimension id\n");
        num_err++;
    }

    status = SDsetdimname(dimid, "MyDim");
    CHECK(status, "SDsetdimname");

    scale[0] = 1;
    scale[1] = 5;
    scale[2] = 7;
    scale[3] = 24;
    status = SDsetdimscale(dimid, 4, DFNT_INT32, (VOIDP) scale);
    CHECK(status, "SDsetdimscale");

    status = SDsetdimstrs(dimid, "DimLabel", NULL, "TheFormat");
    CHECK(status, "SDsetdimstrs");

    status = SDnametoindex(f1, "DataSetAlpha");
    if(status != 0) {
        fprintf(stderr, "Couldn't find data set in file 1\n");
        num_err++;
    }

    status = SDnametoindex(f2, "DataSetAlpha");
    if(status != FAIL) {
        fprintf(stderr, "Found data set in wrong file 2\n");
        num_err++;
    }

    status = SDnametoindex(f1, "BogusDataSet");
    if(status != FAIL) {
        fprintf(stderr, "Found bogus data set in file 1\n");
        num_err++;
    }

    max = -17.5;
    status = SDsetfillvalue(newsds, (VOIDP) &max);
    CHECK(status, "SDsetfillvalue");

    for(i = 0; i < 10; i++)
        data[i] = (float32) i;

    start[0] = start[1] = 1;
    end[0]   = end[1]   = 3;
    status = SDwritedata(newsds, start, NULL, end, (VOIDP) data);
    CHECK(status, "SDwritedata");

    max = 10.0;
    min = 4.6;
    status = SDsetrange(newsds, (VOIDP) &max, (VOIDP) &min);
    CHECK(status, "SDsetrange");

    status = SDsetattr(newsds, "spam", DFNT_CHAR8, 6, "Hi mom");
    CHECK(status, "SDsetattr");

    status = SDsetdatastrs(newsds, "TheLabel", "TheUnits", NULL, "TheCordsys");
    CHECK(status, "SDsetdatastrs");

    status = SDgetdatastrs(newsds, l, u, fmt, c, 80);
    CHECK(status, "SDgetdatastrs");

    if(HDstrcmp(l, "TheLabel")) {
        fprintf(stderr, "Bogus label returned (%s)\n", l);
        num_err++;
    }
    if(HDstrcmp(u, "TheUnits")) {
        fprintf(stderr, "Bogus units returned (%s)\n", u);
        num_err++;
    }
    if(HDstrcmp(fmt, "")) {
        fprintf(stderr, "Bogus format returned\n");
        num_err++;
    }
    if(HDstrcmp(c, "TheCordsys")) {
        fprintf(stderr, "Bogus cordsys returned\n");
        num_err++;
    }

    status = SDsetattr(f1, "F-attr", DFNT_CHAR8, 10, "globulator");
    CHECK(status, "SDsetattr");

    status = SDattrinfo(f1, 0, name, &nt, &count);
    CHECK(status, "SDinqattr");

    status = SDreadattr(f1, 0, text);
    CHECK(status, "SDreadattr");
    
    if(HDstrncmp(text, "globulator", count)) {
        fprintf(stderr, "Invalid global attribute read <%s>\n", text);
        num_err++;
    }

    status = SDfileinfo(f2, &number, &nattr);
    if(number != 0) {
        fprintf(stderr, "File2 still has stuff in it\n");
        num_err++;
    }

    cal   = 1.0;
    cale  = 5.0;
    ioff  = 3.0;
    ioffe = 2.5;
    nt    = DFNT_INT8;
    status = SDsetcal(newsds3, cal, cale, ioff, ioffe, nt);
    CHECK(status, "SDsetcal");

    dimsize[0] = SD_UNLIMITED;
    dimsize[1] = 6;
    newsds2 = SDcreate(f2, "DataSetBeta", DFNT_INT16, 2, dimsize);
    if(newsds2 == FAIL) {
        fprintf(stderr, "Failed to create a new data set\n");
        num_err++;
    }

    status = SDfileinfo(f2, &number, &nattr);
    if(number != 1) {
        fprintf(stderr, "Wrong number of datasets in file 2\n");
        num_err++;
    }

    for(i = 0; i < 50; i++)
        sdata[i] = i;

    start[0] = start[1] = 0;
    end[0]   = 8;
    end[1]   = 6;
    status = SDwritedata(newsds2, start, NULL, end, (VOIDP) sdata);
    CHECK(status, "SDwritedata");


    start[0] = start[1] = 0;
    end[0]   = end[1]   = 3;
    status = SDreaddata(newsds, start, NULL, end, (VOIDP) data);
    CHECK(status, "SDwritedata");

    if(data[0] != -17.5) {
        fprintf(stderr, "Wrong value returned\n");
        num_err++;
    }
    if(data[3] != -17.5) {
        fprintf(stderr, "Wrong value returned\n");
        num_err++;
    }
    if(data[5] != 1.0) {
        fprintf(stderr, "Wrong value returned\n");
        num_err++;
    }
    if(data[6] != -17.5) {
        fprintf(stderr, "Wrong value returned\n");
        num_err++;
    }
    if(data[8] != 4.0) {
        fprintf(stderr, "Wrong value returned\n");
        num_err++;
    }

    for(i = 0; i < 50; i++)
        sdata[i] = 0;

    start[0] = start[1] = 1;
    end[0]   = 3;
    end[1]   = 3;
    stride[0] = 2;
    stride[1] = 2;
    status = SDreaddata(newsds2, start, stride, end, (VOIDP) sdata);
    CHECK(status, "SDwritedata");

    for(i = 0; i < 10; i++)
        printf("%d := %d\n", i, sdata[i]);
    
    cal   = 1.0;
    cale  = 5.0;
    ioff  = 3.0;
    ioffe = 2.5;
    nt    = DFNT_INT8;
    status = SDgetcal(newsds3, &cal, &cale, &ioff, &ioffe, &nt);
    CHECK(status, "SDgetcal");

    if(cal != 1.0) {
        fprintf(stderr, "Wrong calibration info\n");
        num_err++;
    }

    if(cale != 5.0) {
        fprintf(stderr, "Wrong calibration info\n");
        num_err++;
    }

    if(ioff != 3.0) {
        fprintf(stderr, "Wrong calibration info\n");
        num_err++;
    }

    if(ioffe != 2.5) {
        fprintf(stderr, "Wrong calibration info\n");
        num_err++;
    }

    if(nt != DFNT_INT8) {
        fprintf(stderr, "Wrong calibration info\n");
        num_err++;
    }

    status = SDendaccess(newsds);
    CHECK(status, "SDendaccess");

    status = SDendaccess(newsds2);
    CHECK(status, "SDendaccess");

    status = SDendaccess(newsds3);
    CHECK(status, "SDendaccess");

    status = SDend(f1);
    CHECK(status, "SDend");

    status = SDend(f2);
    CHECK(status, "SDend");

    printf("num_err == %d\n", num_err);

    exit(0);
}
