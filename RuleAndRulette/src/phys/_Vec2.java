package phys;

/*
 * JBox2D - A Java Port of Erin Catto's Box2D
 * 
 * JBox2D homepage: http://jbox2d.sourceforge.net/
 * Box2D homepage: http://www.box2d.org
 * 
 * This software is provided 'as-is', without any express or implied
 * warranty.  In no event will the authors be held liable for any damages
 * arising from the use of this software.
 * 
 * Permission is granted to anyone to use this software for any purpose,
 * including commercial applications, and to alter it and redistribute it
 * freely, subject to the following restrictions:
 * 
 * 1. The origin of this software must not be misrepresented; you must not
 * claim that you wrote the original software. If you use this software
 * in a product, an acknowledgment in the product documentation would be
 * appreciated but is not required.
 * 2. Altered source versions must be plainly marked as such, and must not be
 * misrepresented as being the original software.
 * 3. This notice may not be removed or altered from any source distribution.
 */

// updated to rev 100

/**
 * A 2D column vector
 */
public class _Vec2 {
	/** Should we count _Vec2 creations? */
	static public boolean watchCreations = true;
	/**
	 * Running count of _Vec2 creations.  Must be zeroed out
	 * manually (perhaps at start of time step).  Incremented
	 * in _Vec2 constructor if watchCreations flag is true.
	 * <BR><BR>
	 * Mainly used for optimization purposes, since temporary
	 * _Vec2 creation is often a bottleneck.
	 */
	static public int creationCount = 0;

	public float x, y;

	public _Vec2() {
		this(0, 0);
	}

	public _Vec2(float x, float y) {
		if (_Vec2.watchCreations) {
			++_Vec2.creationCount;
		}
		this.x = x;
		this.y = y;
		// testbed.PTest.debugCount++;
	}
	
	public _Vec2(double x, double y) {
		if (_Vec2.watchCreations) {
			++_Vec2.creationCount;
		}
		this.x = (float) x;
		this.y = (float) y;
		// testbed.PTest.debugCount++;
	}

	public _Vec2( _Vec2 toCopy) {
		this(toCopy.x, toCopy.y);
	}

	/** Zero out this vector. */
	public final void setZero() {
		x = 0.0f;
		y = 0.0f;
	}

	/** Set the vector component-wise. */
	public final void set(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/** Set this vector to another vector. */
	public final _Vec2 set(_Vec2 v) {
		this.x = v.x;
		this.y = v.y;
		return this;
	}

	/** Return the sum of this vector and another; does not alter either one. */
	public final _Vec2 add(_Vec2 v) {
		return new _Vec2(x + v.x, y + v.y);
	}
	
	

	/** Return the difference of this vector and another; does not alter either one. */
	public final _Vec2 sub(_Vec2 v) {
		return new _Vec2(x - v.x, y - v.y);
	}

	/** Return this vector multiplied by a scalar; does not alter this vector. */
	public final _Vec2 mul(float a) {
		return new _Vec2(x * a, y * a);
	}

	/** Return the negation of this vector; does not alter this vector. */
	public final _Vec2 negate() {
		return new _Vec2(-x, -y);
	}

	/** Flip the vector and return it - alters this vector. */
	public final _Vec2 negateLocal() {
		x = -x;
		y = -y;
		return this;
	}

	/** Add another vector to this one and returns result - alters this vector. */
	public final _Vec2 addLocal(_Vec2 v) {
		x += v.x;
		y += v.y;
		return this;
	}
	
	/** Adds values to this vector and returns result - alters this vector. */
	public final _Vec2 addLocal( float x, float y) {
		this.x+=x;
		this.y+=y;
		return this;
	}

	/** Subtract another vector from this one and return result - alters this vector. */
	public final _Vec2 subLocal(_Vec2 v) {
		x -= v.x;
		y -= v.y;
		return this;
	}

	/** Multiply this vector by a number and return result - alters this vector. */
	public final _Vec2 mulLocal(float a) {
		x *= a;
		y *= a;
		return this;
	}

	/** Return the length of this vector. */
	public final float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	/** Return the squared length of this vector. */
	public final float lengthSquared() {
		return (x*x + y*y);
	}

	/** Normalize this vector and return the length before normalization.  Alters this vector. */
	public final float normalize() {
		float length = length();
		if (length < Math.E) {
			return 0f;
		}

		float invLength = 1.0f / length;
		x *= invLength;
		y *= invLength;
		return length;
	}

	/** True if the vector represents a pair of valid, non-infinite floating point numbers. */
	public final boolean isValid() {
		return x != Float.NaN && x != Float.NEGATIVE_INFINITY
		&& x != Float.POSITIVE_INFINITY && y != Float.NaN
		&& y != Float.NEGATIVE_INFINITY && y != Float.POSITIVE_INFINITY;
	}

	/** Return a new vector that has positive components. */
	public final _Vec2 abs() {
		return new _Vec2((float)(Math.abs(x)), (float)(Math.abs(y)));
	}

	/* djm created */
	public final void absLocal(){
		x = (float)(Math.abs(x));
		y = (float)(Math.abs(y));
	}

	@Override
	/** Return a copy of this vector. */
	public final _Vec2 clone() {
		return new _Vec2(x, y);
	}

	@Override
	public final String toString() {
		return "(" + x + "," + y + ")";
	}

	/*
	 * Static
	 */

	public final static _Vec2 abs(_Vec2 a) {
		return new _Vec2((float)(Math.abs(a.x)), (float)(Math.abs(a.y)));
	}

	/* djm created */
	public final static void absToOut(_Vec2 a, _Vec2 out){
		out.x = (float)(Math.abs(a.x));
		out.y = (float)(Math.abs(a.y));
	}

	public final static float dot(_Vec2 a, _Vec2 b) {
		return a.x * b.x + a.y * b.y;
	}

	public final static float cross(_Vec2 a, _Vec2 b) {
		return a.x * b.y - a.y * b.x;
	}

	public final static _Vec2 cross(_Vec2 a, float s) {
		return new _Vec2(s * a.y, -s * a.x);
	}

	/* djm created */
	public final static void crossToOut(_Vec2 a, float s, _Vec2 out){
		float tempy = -s * a.x;
		out.x = s * a.y;
		out.y = tempy;
	}

	public final static _Vec2 cross(float s, _Vec2 a) {
		return new _Vec2(-s * a.y, s * a.x);
	}

	/* djm created */
	public final static void crossToOut(float s, _Vec2 a, _Vec2 out){
		float tempY = s * a.x;
		out.x = -s * a.y;
		out.y = tempY;
	}
	
	public final static void negateToOut(_Vec2 a, _Vec2 out){
		out.x = -a.x;
		out.y = -a.y;
	}

	public final static _Vec2 min(_Vec2 a, _Vec2 b) {
		return new _Vec2(a.x < b.x ? a.x : b.x, a.y < b.y ? a.y : b.y);
	}

	public final static _Vec2 max(_Vec2 a, _Vec2 b) {
		return new _Vec2(a.x > b.x ? a.x : b.x, a.y > b.y ? a.y : b.y);
	}

	/* djm created */
	public final static void minToOut(_Vec2 a, _Vec2 b, _Vec2 out) {
		out.x = a.x < b.x ? a.x : b.x;
		out.y = a.y < b.y ? a.y : b.y;
	}

	/* djm created */
	public final static void maxToOut(_Vec2 a, _Vec2 b, _Vec2 out) {
		out.x = a.x > b.x ? a.x : b.x;
		out.y = a.y > b.y ? a.y : b.y;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() { //automatically generated by Eclipse
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) { //automatically generated by Eclipse
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		_Vec2 other = (_Vec2) obj;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
			return false;
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
			return false;
		return true;
	}
}