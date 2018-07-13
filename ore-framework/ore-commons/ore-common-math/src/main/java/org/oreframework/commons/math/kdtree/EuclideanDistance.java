// Hamming distance metric class

package org.oreframework.commons.math.kdtree;

import org.oreframework.commons.math.GoogleMap;

/** 
 * @author  huangzz
 * @version  [1.0.0, 2015-9-22]
 */
public class EuclideanDistance extends DistanceMetric {

	protected double distance(double[] a, double[] b) {
		double lng1  = a[0];
		double lat1  = a[1];
		double rad1  = a[2];
		
		double lng2  = b[0];
		double lat2  = b[1];
		//public static double D_jw(double wd1, double jd1, double wd2, double jd2) {
		//return Math.sqrt(sqrdist(a, b));
		//System.out.println(rad1 + "/" + b[2]);
		return GoogleMap.D_jw(lat1, lng1, lat2, lng2) - rad1;
	}

	protected static double sqrdist(double[] a, double[] b) {
		double dist = 0;
		for (int i = 0; i < a.length; ++i) {
			double diff = (a[i] - b[i]);
			dist += diff * diff;
		}
		return dist;
	}
}
