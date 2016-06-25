/**
 * Write a description of class Vector4 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Vector4  
{
    public static final Vector4 UP = new Vector4(0, 1, 0);
    public static final Vector4 DOWN = new Vector4(0, -1, 0);
    public static final Vector4 LEFT = new Vector4(-1, 0, 0);
    public static final Vector4 RIGHT = new Vector4(1, 0, 0);
    public static final Vector4 FORWARD = new Vector4(0, 0, 1);
    public static final Vector4 BACK = new Vector4(0, 0, -1);
    
    private float x, y, z, w;
    
    public Vector4()
    {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.w = 1;
    }
    
    public Vector4(float x, float y, float z)
    {
        this(x, y, z, 1);
    }
    
    public Vector4(float x, float y, float z, float w)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    
    public Vector4(Vector3 v3)
    {
        this(v3.getX(), v3.getY(), v3.getZ());
    }
    
    public Vector4(Vector3 v3, float w)
    {
        this(v3.getX(), v3.getY(), v3.getZ(), w);
    }
    
    public Vector4(Vector4 v4)
    {
        this(v4.getX(), v4.getY(), v4.getZ(), v4.getW());
    }
    
    public Vector4(Quaternion qt)
    {
        this(qt.getX(), qt.getY(), qt.getZ(), qt.getW());
    }
    
    public float magnitude()
    {
        return (float)Math.sqrt(x*x + y*y + z*z + w*w);
    }
    
    public float dot(Vector4 v4)
    {
        return x * v4.getX() + y * v4.getY() + z * v4.getZ() + w * v4.getW();
    }
    
    public Vector4 cross(Vector4 v4)
    {
        float nx = y * v4.getZ() - z * v4.getY();
        float ny = z * v4.getX() - x * v4.getZ();
        float nz = x * v4.getY() - y * v4.getX();
        
        return new Vector4(nx, ny, nz, 0);
    }
    
    public Vector4 unit()
    {
        float mag = magnitude();
        
        if (mag == 0)
            return this;
            
        return new Vector4(x / mag, y / mag, z / mag, w / mag);
    }
    
    public Vector4 dirTo(Vector4 pt)
    {
        return pt.sub(this).unit();
    }
    
    public float max()
    {
        return Math.max(Math.max(x, y), Math.max(z, w));
    }
    
    public float min()
    {
        return Math.min(Math.min(x, y), Math.min(z, w));
    }
    
    public Vector4 abs()
    {
        return new Vector4(Math.abs(x), Math.abs(y), Math.abs(z), Math.abs(w));
    }
    
    public Vector4 lerp(Vector4 to, float inc)
    {
        return to.sub(this).mul(inc).add(this);
    }
    
    public Vector4 rotate(Vector4 axis, float angle)
    {
        float sinA = (float)Math.sin(-angle);
        float cosA = (float)Math.cos(-angle);
        
        return cross(axis.mul(sinA)).add(mul(cosA)).add(axis.mul(dot(axis.mul(1 - cosA))));
    }
    
    public Vector4 rotate(Quaternion qt)
    {
        Quaternion conj = qt.conjugate();
        Quaternion w = qt.mul(this).mul(conj);
        
        return new Vector4(w.getX(), w.getY(), w.getZ());
    }
    
    public Vector4 add(Vector4 v4)
    {
        return new Vector4(x + v4.getX(), y + v4.getY(), z + v4.getZ(), w + v4.getW());
    }
    
    public Vector4 add(float n)
    {
        return new Vector4(x + n, y + n, z + n, w + n);
    }
    
    public Vector4 sub(Vector4 v4)
    {
        return new Vector4(x - v4.getX(), y - v4.getY(), z - v4.getZ(), w - v4.getW());
    }
    
    public Vector4 sub(float n)
    {
        return new Vector4(x - n, y - n, z - n, w - n);
    }
    
    public Vector4 mul(Vector4 v4)
    {
        return new Vector4(x * v4.getX(), y * v4.getY(), z * v4.getZ(), w * v4.getW());
    }
    
    public Vector4 mul(float n)
    {
        return new Vector4(x * n, y * n, z * n, w * n);
    }
    
    public Vector4 div(Vector4 v4)
    {
        return new Vector4(x / v4.getX(), y / v4.getY(), z / v4.getZ(), w / v4.getW());
    }
    
    public Vector4 div(float n)
    {
        return new Vector4(x / n, y / n, z / n, w / n);
    }
    
    public boolean equals(Vector4 v4)
    {
        return x == v4.getX() && y == v4.getY() && z == v4.getZ() && w == v4.getW();
    }
    
    public float getX()
    {
        return x;
    }
    
    public float getY()
    {
        return y;
    }
    
    public float getZ()
    {
        return z;
    }
    
    public float getW()
    {
        return w;
    }
    
    public void setX(float x)
    {
        this.x = x;
    }
    
    public void setY(float y)
    {
        this.y = y;
    }
    
    public void setZ(float z)
    {
        this.z = z;
    }
    
    public void setW(float w)
    {
        this.w = w;
    }

	public Vector3 getXYZ() 
	{
		return new Vector3(this.x, this.y, this.z);
	}
    
    public String toString() {
		return String.format("(%s, %s, %s, %s)", x, y, z, w);
	}
}
