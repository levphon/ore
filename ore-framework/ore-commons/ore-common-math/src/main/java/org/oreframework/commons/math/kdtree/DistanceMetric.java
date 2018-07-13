// Abstract distance metric class

package org.oreframework.commons.math.kdtree;

/** 
 * @author  huangzz
 * @version  [1.0.0, 2015-9-22]
 */
public abstract class DistanceMetric {
    
    protected abstract double distance(double [] a, double [] b);
}
