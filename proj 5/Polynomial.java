	package il.ac.tau.cs.sw1.hw6;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Polynomial {
		
		private double[] p ;
		
		
		/*
		 * Creates the zero-polynomial with p(x) = 0 for all x.
		 */
		public Polynomial()
		{
			 p=new double[1];
			p[0]=0;
		} 
		/*
		 * Creates a new polynomial with the given coefficients.
		 */
		public Polynomial(double[] coefficients) 
		{
			p=new double[coefficients.length];
			for (int i=0;i<p.length;i++) {
				p[i]=coefficients[i];
			}
		}
		/*
		 * Addes this polynomial to the given one
		 *  and retruns the sum as a new polynomial.
		 */
		public Polynomial adds(Polynomial polynomial)
		{
			if(p!=null&&polynomial.p!=null)
			{
			double[] newP=new double[Math.max(p.length, polynomial.p.length)];
			for (int i=0;i<Math.min(p.length, polynomial.p.length);i++) {
				newP[i]=p[i]+polynomial.p[i];
			}
			if((p.length> polynomial.p.length)) {
				for (int i=polynomial.p.length;i<p.length;i++) {
					newP[i]=p[i];
				}
			}
			else
				if((p.length<polynomial.p.length)) {
					for (int i=p.length;i<polynomial.p.length;i++) {
						newP[i]=polynomial.p[i];
					}
				}
			Polynomial pol=new Polynomial(newP);
			return pol;
			}
			if(p==null&&polynomial.p!=null) {
				Polynomial pol=new Polynomial(polynomial.p);
			return pol;
			}
			if(p!=null&&polynomial.p==null) {
		        Polynomial pol=new Polynomial(p);
			return pol;
			}
	
				return new Polynomial(null);
			}
			
			
		/*
		 * Multiplies a to this polynomial and returns 
		 * the result as a new polynomial.
		 */
		public Polynomial multiply(double a)
		{
			if(p!=null) {
			double[] newP=new double[p.length];
			for (int i=0;i<newP.length;i++) {
				newP[i]=p[i]*a;
			}
			Polynomial pol=new Polynomial(newP);
			return pol;
			}
			return new Polynomial(null);
			
		}
		/*
		 * Returns the degree (the largest exponent) of this polynomial.
		 */
		public int getDegree()
		{
			if(this.p!=null) {
			for(int i=p.length-1;i>=0;i--) {
			if(p[i]!=0.0) {
				return i;
			}
		}
			}
			return 0;
		}
		/*
		 * Returns the coefficient of the variable x 
		 * with degree n in this polynomial.
		 */
		public double getCoefficient(int n)
		{
			if(p!=null) {
			if(n>p.length-1) {
			return 0.0;
		}
			else return p[n];
		}
			return 0.0;
		}
		
		/*
		 * set the coefficient of the variable x 
		 * with degree n to c in this polynomial.
		 * If the degree of this polynomial < n, it means that that the coefficient of the variable x 
		 * with degree n was 0, and now it will change to c. 
		 */
		public void setCoefficient(int n, double c)
		{
			if(p!=null) {
			if (n>p.length-1) {
				double[] newP=new double[n+1];
				for (int i=0;i<p.length;i++) {
					newP[i]=p[i];
				}
				newP[n]=c;
				p=newP;
			}
			else {
				p[n]=c;
			}
			}
			
		}
		
		/*
		 * Returns the first derivation of this polynomial.
		 *  The first derivation of a polynomal a0x0 + ...  + anxn is defined as 1 * a1x0 + ... + n anxn-1.
		
		 */
		public Polynomial getFirstDerivation()
		{
			if(this.p!=null) {
				if (p.length<=1) {
					Polynomial pol=new Polynomial();
					return pol;
				}
				if(p.length>1) {
			 double[] newP=new double[p.length-1];
			for (int i=1;i<p.length;i++) {
				newP[i-1]=i*p[i];
			}
				Polynomial pol=new Polynomial(newP);
				return pol;
				}

			}
				return this;
		}
		
		/*
		 * given an assignment for the variable x,
		 * compute the polynomial value
		 */
		public double computePolynomial(double x)
		{ 
			double ans=0.0;
			if(this.p==null) {
				return ans;
			}
			for (int i=0;i<this.p.length;i++) {
				ans=ans+this.p[i]*Math.pow(x,i);
			}
			return ans;
		}
		
		/*
		 * given an assignment for the variable x,
		 * return true iff x is an extrema point (local minimum or local maximum of this polynomial)
		 * x is an extrema point if and only if The value of first derivation of a polynomal at x is 0
		 * and the second derivation of a polynomal value at x is not 0.
		 */
		public boolean isExtrema(double x)
		{
			boolean firstDerZero=getFirstDerivation().computePolynomial(x)==0.0;
			boolean secDerNotZero=(getFirstDerivation().getFirstDerivation()).computePolynomial(x)!=0.0;
			return firstDerZero&&secDerNotZero;
		}

	
	}
