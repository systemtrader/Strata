/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.math.impl.differentiation;

import com.opengamma.strata.collect.ArgChecker;
import com.opengamma.strata.math.impl.MathException;
import com.opengamma.strata.math.impl.function.Function1D;
import com.opengamma.strata.math.impl.matrix.DoubleMatrix1D;
import com.opengamma.strata.math.impl.matrix.DoubleMatrix2D;

/**
 * Differentiates a vector field (i.e. there is a vector value for every point
 * in some vector space) with respect to the vector space using finite difference.
 * <p>
 * For a function $\mathbf{y} = f(\mathbf{x})$ where $\mathbf{x}$ is a
 * n-dimensional vector and $\mathbf{y}$ is a m-dimensional vector, this class
 * produces the Jacobian function $\mathbf{J}(\mathbf{x})$, i.e. a function
 * that returns the Jacobian for each point $\mathbf{x}$, where
 * $\mathbf{J}$ is the $m \times n$ matrix $\frac{dy_i}{dx_j}$
 */
public class VectorFieldFirstOrderDifferentiator
    implements Differentiator<DoubleMatrix1D, DoubleMatrix1D, DoubleMatrix2D> {

  private static final double DEFAULT_EPS = 1e-5;

  private final double eps;
  private final double twoEps;
  private final FiniteDifferenceType differenceType;

  /**
   * Creates an instance using the default value of eps (10<sup>-5</sup>) and central differencing type.
   */
  public VectorFieldFirstOrderDifferentiator() {
    this(FiniteDifferenceType.CENTRAL, DEFAULT_EPS);
  }

  /**
   * Creates an instance using the default value of eps (10<sup>-5</sup>).
   * 
   * @param differenceType  the differencing type to be used in calculating the gradient function
   */
  public VectorFieldFirstOrderDifferentiator(FiniteDifferenceType differenceType) {
    this(differenceType, DEFAULT_EPS);
  }

  /**
   * Creates an instance using the central differencing type.
   * <p>
   * If the size of the domain is very small or very large, consider re-scaling first.
   * If this value is too small, the result will most likely be dominated by noise.
   * Use around 10<sup>-5</sup> times the domain size.
   * 
   * @param eps  the step size used to approximate the derivative
   */
  public VectorFieldFirstOrderDifferentiator(double eps) {
    this(FiniteDifferenceType.CENTRAL, eps);
  }

  /**
   * Creates an instance.
   * <p>
   * If the size of the domain is very small or very large, consider re-scaling first.
   * If this value is too small, the result will most likely be dominated by noise.
   * Use around 10<sup>-5</sup> times the domain size.
   * 
   * @param differenceType  the differencing type to be used in calculating the gradient function
   * @param eps  the step size used to approximate the derivative
   */
  public VectorFieldFirstOrderDifferentiator(FiniteDifferenceType differenceType, double eps) {
    ArgChecker.notNull(differenceType, "differenceType");
    this.differenceType = differenceType;
    this.eps = eps;
    this.twoEps = 2 * eps;
  }

  //-------------------------------------------------------------------------
  @Override
  public Function1D<DoubleMatrix1D, DoubleMatrix2D> differentiate(Function1D<DoubleMatrix1D, DoubleMatrix1D> function) {
    ArgChecker.notNull(function, "function");
    switch (differenceType) {
      case FORWARD:
        return new Function1D<DoubleMatrix1D, DoubleMatrix2D>() {
          @SuppressWarnings("synthetic-access")
          @Override
          public DoubleMatrix2D evaluate(DoubleMatrix1D x) {
            ArgChecker.notNull(x, "x");
            DoubleMatrix1D y = function.evaluate(x);
            int n = x.size();
            int m = y.size();
            double[][] res = new double[m][n];
            for (int j = 0; j < n; j++) {
              double xj = x.get(j);
              DoubleMatrix1D up = function.evaluate(x.with(j, xj + eps));
              for (int i = 0; i < m; i++) {
                res[i][j] = (up.get(i) - y.get(i)) / eps;
              }
            }
            return new DoubleMatrix2D(res);
          }
        };
      case CENTRAL:
        return new Function1D<DoubleMatrix1D, DoubleMatrix2D>() {
          @SuppressWarnings("synthetic-access")
          @Override
          public DoubleMatrix2D evaluate(DoubleMatrix1D x) {
            ArgChecker.notNull(x, "x");
            DoubleMatrix1D y = function.evaluate(x); // need this unused evaluation to get size of y
            int n = x.size();
            int m = y.size();
            double[][] res = new double[m][n];
            for (int j = 0; j < n; j++) {
              double xj = x.get(j);
              DoubleMatrix1D up = function.evaluate(x.with(j, xj + eps));
              DoubleMatrix1D down = function.evaluate(x.with(j, xj - eps));
              for (int i = 0; i < m; i++) {
                res[i][j] = (up.get(i) - down.get(i)) / twoEps;
              }
            }
            return new DoubleMatrix2D(res);
          }
        };
      case BACKWARD:
        return new Function1D<DoubleMatrix1D, DoubleMatrix2D>() {
          @SuppressWarnings("synthetic-access")
          @Override
          public DoubleMatrix2D evaluate(DoubleMatrix1D x) {
            ArgChecker.notNull(x, "x");
            DoubleMatrix1D y = function.evaluate(x);
            int n = x.size();
            int m = y.size();
            double[][] res = new double[m][n];
            for (int j = 0; j < n; j++) {
              double xj = x.get(j);
              DoubleMatrix1D down = function.evaluate(x.with(j, xj - eps));
              for (int i = 0; i < m; i++) {
                res[i][j] = (y.get(i) - down.get(i)) / eps;
              }
            }
            return new DoubleMatrix2D(res);
          }
        };
      default:
        throw new IllegalArgumentException("Can only handle forward, backward and central differencing");
    }
  }

  //-------------------------------------------------------------------------
  @Override
  public Function1D<DoubleMatrix1D, DoubleMatrix2D> differentiate(
      Function1D<DoubleMatrix1D, DoubleMatrix1D> function,
      Function1D<DoubleMatrix1D, Boolean> domain) {

    ArgChecker.notNull(function, "function");
    ArgChecker.notNull(domain, "domain");
    double[] wFwd = new double[] {-3., 4., -1.};
    double[] wCent = new double[] {-1., 0., 1.};
    double[] wBack = new double[] {1., -4., 3.};

    return new Function1D<DoubleMatrix1D, DoubleMatrix2D>() {
      @SuppressWarnings("synthetic-access")
      @Override
      public DoubleMatrix2D evaluate(DoubleMatrix1D x) {
        ArgChecker.notNull(x, "x");
        ArgChecker.isTrue(domain.evaluate(x), "point {} is not in the function domain", x.toString());

        DoubleMatrix1D mid = function.evaluate(x); // need this unused evaluation to get size of y
        int n = x.size();
        int m = mid.size();
        double[][] res = new double[m][n];
        DoubleMatrix1D[] y = new DoubleMatrix1D[3];
        double[] w;

        for (int j = 0; j < n; j++) {
          double xj = x.get(j);
          DoubleMatrix1D xPlusOneEps = x.with(j, xj + eps);
          DoubleMatrix1D xMinusOneEps = x.with(j, xj - eps);
          if (!domain.evaluate(xPlusOneEps)) {
            DoubleMatrix1D xMinusTwoEps = x.with(j, xj - twoEps);
            if (!domain.evaluate(xMinusTwoEps)) {
              throw new MathException("cannot get derivative at point " + x.toString() + " in direction " + j);
            }
            y[2] = mid;
            y[0] = function.evaluate(xMinusTwoEps);
            y[1] = function.evaluate(xMinusOneEps);
            w = wBack;
          } else {
            if (!domain.evaluate(xMinusOneEps)) {
              y[0] = mid;
              y[1] = function.evaluate(xPlusOneEps);
              y[2] = function.evaluate(x.with(j, xj + twoEps));
              w = wFwd;
            } else {
              y[2] = function.evaluate(xPlusOneEps);
              y[0] = function.evaluate(xMinusOneEps);
              y[1] = mid;
              w = wCent;
            }
          }

          for (int i = 0; i < m; i++) {
            double sum = 0;
            for (int k = 0; k < 3; k++) {
              if (w[k] != 0.0) {
                sum += w[k] * y[k].get(i);
              }
            }
            res[i][j] = sum / twoEps;
          }
        }
        return new DoubleMatrix2D(res);
      }
    };
  }

}