/*
 * jutils.c
 *
 * Copyright (C) 1991, 1992, Thomas G. Lane.
 * This file is part of the Independent JPEG Group's software.
 * For conditions of distribution and use, see the accompanying README file.
 *
 * This file contains miscellaneous utility routines needed for both
 * compression and decompression.
 * Note we prefix all global names with "j" to minimize conflicts with
 * a surrounding application.
 */

#include "jinclude.h"


GLOBAL long
#ifdef PROTOTYPE
jround_up (long a, long b)
#else
jround_up (a, b)
long a;
long b;
#endif
/* Compute a rounded up to next multiple of b; a >= 0, b > 0 */
{
  a += b-1;
  return a - (a % b);
}


GLOBAL VOID
#ifdef PROTOTYPE
jcopy_sample_rows (JSAMPARRAY input_array, int source_row,
		   JSAMPARRAY output_array, int dest_row,
		   int num_rows, long num_cols)
#else
jcopy_sample_rows (input_array, source_row, output_array, dest_row, num_rows,
        num_cols)
JSAMPARRAY input_array;
int source_row;
JSAMPARRAY output_array;
int dest_row;
int num_rows;
long num_cols;
#endif
/* Copy some rows of samples from one place to another.
 * num_rows rows are copied from input_array[source_row++]
 * to output_array[dest_row++]; these areas should not overlap.
 * The source and destination arrays must be at least as wide as num_cols.
 */
{
  /* On normal machines we can use memcpy().  This won't work on 80x86 because
   * the sample arrays are FAR and we're assuming a small-pointer memory model.
   */
  register JSAMPROW inptr, outptr;
#ifdef NEED_FAR_POINTERS
  register long count;
#else
  register size_t count = (size_t) (num_cols * SIZEOF(JSAMPLE));
#endif
  register int row;

  input_array += source_row;
  output_array += dest_row;

  for (row = num_rows; row > 0; row--) {
    inptr = *input_array++;
    outptr = *output_array++;
#ifdef NEED_FAR_POINTERS
    for (count = num_cols; count > 0; count--)
      *outptr++ = *inptr++;	/* needn't bother with GETJSAMPLE() here */
#else
    HDmemcpy((VOIDP) outptr, (VOIDP) inptr, count);
#endif
  }
}


GLOBAL VOID
#ifdef PROTOTYPE
jcopy_block_row (JBLOCKROW input_row, JBLOCKROW output_row, long num_blocks)
#else
jcopy_block_row (input_row, output_row, num_blocks)
JBLOCKROW input_row;
JBLOCKROW output_row;
long num_blocks;
#endif
/* Copy a row of coefficient blocks from one place to another. */
{
  /* On normal machines we can use memcpy().  This won't work on 80x86 because
   * the block arrays are FAR and we're assuming a small-pointer memory model.
   */
#ifdef NEED_FAR_POINTERS
  register JCOEFPTR inptr, outptr;
  register long count;

  inptr = (JCOEFPTR) input_row;
  outptr = (JCOEFPTR) output_row;
  for (count = num_blocks * DCTSIZE2; count > 0; count--) {
    *outptr++ = *inptr++;
  }
#else
    HDmemcpy((VOIDP) output_row, (VOIDP) input_row,
	   (size_t) (num_blocks * (DCTSIZE2 * SIZEOF(JCOEF))));
#endif
}


GLOBAL VOID
#ifdef PROTOTYPE
jzero_far (VOIDP target, size_t bytestozero)
#else
jzero_far (target, bytestozero)
VOIDP target;
size_t bytestozero;
#endif
/* Zero out a chunk of FAR memory. */
/* This might be sample-array data, block-array data, or alloc_medium data. */
{
  /* On normal machines we can use MEMZERO().  This won't work on 80x86
   * because we're assuming a small-pointer memory model.
   */
#ifdef NEED_FAR_POINTERS
  register char FAR * ptr = (char FAR *) target;
  register size_t count;

  for (count = bytestozero; count > 0; count--) {
    *ptr++ = 0;
  }
#else
  MEMZERO((VOIDP) target, bytestozero);
#endif
}
