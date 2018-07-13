// Hamming distance metric class

package org.oreframework.commons.math.kdtree;

/** 
 * @author  huangzz
 * @version  [1.0.0, 2015-9-22]
 */
public class HammingDistance extends DistanceMetric {

	protected double distance(double[] a, double[] b) {
		double dist = 0;
		for (int i = 0; i < a.length; ++i) {
			double diff = (a[i] - b[i]);
			dist += Math.abs(diff);
		}
		return dist;
	}
}
