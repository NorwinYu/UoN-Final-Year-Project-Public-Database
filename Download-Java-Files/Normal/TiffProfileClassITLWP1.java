/**********************************************************************
 * Jhove - JSTOR/Harvard Object Validation Environment
 * Copyright 2003 by JSTOR and the President and Fellows of Harvard College
 **********************************************************************/

package edu.harvard.hul.ois.jhove.module.tiff;

import edu.harvard.hul.ois.jhove.*;

/**
 *  Profile checker for TIFF Class IT-LW/P1.
 *
 *  The TIFF/IT spec states that "TIFF/IT-LW/P1 is a simplified
 *  image file format profile for line art (LW) image data and
 *  can be considered as a constrained subset of TIFF/IT-LW specified
 *  for simpler implementation."
 *
 *  @author Gary McGath
 */
public final class TiffProfileClassITLWP1 extends TiffProfileClassIT
{
    public TiffProfileClassITLWP1 ()
    {
        super ();
        _profileText =  "TIFF/IT-LW/P1 (ISO 12639:1998)";
    }

    /**
     *  Returns true if the IFD satisfies the requirements
     *  of the profile.  See the documentation for
     *  details.
     */
    @Override
	public boolean satisfiesThisProfile (IFD ifd) 
    {
        if (!super.satisfiesThisProfile (ifd)) {
            return false;
        }

	if (!(ifd instanceof TiffIFD)) {
	    return false;
	}
	TiffIFD tifd = (TiffIFD) ifd;

        /* Check required tags. */
        if (tifd.getColorTable () == null) {
            return false;
        }

	NisoImageMetadata niso = tifd.getNisoImageMetadata ();
        int [] bps = niso.getBitsPerSample ();
        if (bps[0] != 8) {
            return false;
        }

        /* Check required values. */
        if (!satisfiesSamplesPerPixel (tifd, 1)) {
            return false;
        }

        if (!satisfiesResolutionUnit (tifd, new int [] {2, 3} )) {
            return false;
        }

        if (!satisfiesNewSubfileType (tifd, 0)) {
            return false;
        }

        if (!satisfiesPhotometricInterpretation (tifd, 5)) {
            return false;
        }

        if (!satisfiesCompression (tifd, 32896)) {
            return false;
        }

        int inkSet = tifd.getInkSet ();
        if (inkSet != 1) {
            return false;
        }

        int bprl = tifd.getBitsPerRunLength ();
        if (bprl != 8) {
            return false; 
        }

        int bperl = tifd.getBitsPerExtendedRunLength ();
        if (bperl != 16) {
            return false; 
        }

        int numInks = tifd.getNumberOfInks ();
        if (numInks != 4 || numInks != niso.getSamplesPerPixel ()) {
            return false;
        }
        return true;
    }
}
