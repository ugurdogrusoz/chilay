package org.ivis.layout.cose;

import org.ivis.layout.fd.FDLayoutEdge;
import org.ivis.util.IGeometry;
import org.ivis.util.IMath;
import org.ivis.util.PointD;
import org.ivis.util.RectangleD;

/**
 * This class implements CoSE specific data and functionality for edges.
 *
 * @author Erhan Giral
 * @author Ugur Dogrusoz
 * @author Cihan Kucukkececi
 *
 * Copyright: i-Vis Research Group, Bilkent University, 2007 - present
 */
public class CoSEEdge extends FDLayoutEdge
{
// -----------------------------------------------------------------------------
// Section: Instance variables
// -----------------------------------------------------------------------------

// -----------------------------------------------------------------------------
// Section: Constructors and initializations
// -----------------------------------------------------------------------------
	/*
	 * Constructor
	 */
	public CoSEEdge(CoSENode source, CoSENode target, Object vEdge)
	{
		super(source, target, vEdge);
	}

// -----------------------------------------------------------------------------
// Section: Remaining methods
// -----------------------------------------------------------------------------

    /**
     * This method updates the length of this edge as well as whether or not the
     * circle representing the geometry of its end nodes overlap.
     */
    public void updateLengthCircular()
    {
        double[] clipPointCoordinates = new double[4];
        RectangleD rectA = this.target.getRect();
        RectangleD rectB = this.source.getRect();
        
        double radiusA = ((CoSENode)this.target).getRadiusX();
        double radiusB = ((CoSENode)this.source).getRadiusX();

        // calculate circular separation amount in X and Y directions
        PointD centerA = new PointD(rectA.getCenterX(), rectA.getCenterY());
        PointD centerB = new PointD(rectB.getCenterX(), rectB.getCenterY());

        this.isOverlapingSourceAndTarget =
                IGeometry.intersectsCircle(centerA, centerB, radiusA, radiusB);

        if (!this.isOverlapingSourceAndTarget)
        {
        	IGeometry.getCircularIntersection(centerA, centerB, radiusA, radiusB, clipPointCoordinates);
        	
            // target clip point minus source clip point gives us length
            this.lengthX = clipPointCoordinates[0] - clipPointCoordinates[2];
            this.lengthY = clipPointCoordinates[1] - clipPointCoordinates[3];

            if (Math.abs(this.lengthX) < 1.0)
            {
                this.lengthX = IMath.sign(this.lengthX);
            }

            if (Math.abs(this.lengthY) < 1.0)
            {
                this.lengthY = IMath.sign(this.lengthY);
            }

            this.length = Math.sqrt(
                    this.lengthX * this.lengthX + this.lengthY * this.lengthY);
        }
    }
}