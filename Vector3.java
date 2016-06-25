
public class Vector3
{
    public static final Vector3 UP = new Vector3(0, 1, 0);
    public static final Vector3 DOWN = new Vector3(0, -1, 0);
    public static final Vector3 LEFT = new Vector3(-1, 0, 0);
    public static final Vector3 RIGHT = new Vector3(1, 0, 0);
    public static final Vector3 FORWARD = new Vector3(0, 0, 1);
    public static final Vector3 BACK = new Vector3(0, 0, -1);
    
    private final float x, y, z;
    
    public Vector3()
    {
        x = 0;
        y = 0;
        z = 0;
    }
    
    public Vector3(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Vector3(Vector3 v3)
    {
        x = v3.getX();
        y = v3.getY();
        z = v3.getZ();
    }
    
    public float magnitude()
    {
        return (float)Math.sqrt(x*x + y*y + z*z);
    }
    
    public float dot(Vector3 v3)
    {
        return x * v3.getX() + y * v3.getY() + z * v3.getZ();
    }
    
    public Vector3 cross(Vector3 v3)
    {
        float nx = y * v3.getZ() - z * v3.getY();
        float ny = z * v3.getX() - x * v3.getZ();
        float nz = x * v3.getY() - y * v3.getX();
        
        return new Vector3(nx, ny, nz);
    }
    
    public Vector3 unit()
    {
        float mag = magnitude();
        
        if (mag == 0)
        {
            return this;
        }
        
        return new Vector3(x / mag, y / mag, z / mag);
    }
    
    public Vector3 rotate(Vector3 axis, float angle)
    {
        float sinA = (float)Math.sin(-angle);
        float cosA = (float)Math.cos(-angle);
        
        return  cross(axis.mul(sinA)).add(mul(cosA)).add(axis.mul(dot(axis.mul(1 - cosA))));
    }
    
    public Vector3 rotate(Quaternion rot)
    {
        Quaternion conj = rot.conjugate();
        Quaternion w = rot.mul(this).mul(conj);
        
        return new Vector3(w.getX(), w.getY(), w.getZ());
    }
    
    public Vector3 dirTo(Vector3 pt)
    {
        return pt.sub(this).unit();
    }
    
    public Vector3 lerp(Vector3 to, float rate)
    {
        return to.sub(this).mul(rate).add(this);
    }
    
    public float max()
    {
        return Math.max(x, Math.max(y, z));
    }
    
    public float min()
    {
        return Math.min(x, Math.min(y, z));
    }
    
    public Vector3 abs()
    {
        return new Vector3(Math.abs(x), Math.abs(y), Math.abs(z));
    }
    
    public Vector3 add(Vector3 v3)
    {
        return new Vector3(x + v3.getX(), y + v3.getY(), z + v3.getZ());
    }
    
    public Vector3 add(float n)
    {
        return new Vector3(x + n, y + n, z + n);
    }
    
    public Vector3 sub(Vector3 v3)
    {
        return new Vector3(x - v3.getX(), y - v3.getY(), z - v3.getZ());
    }
    
    public Vector3 sub(float n)
    {
        return new Vector3(x - n, y - n, z - n);
    }
    
    public Vector3 mul(Vector3 v3)
    {
        return new Vector3(x * v3.getX(), y * v3.getY(), z * v3.getZ());
    }
    
    public Vector3 mul(float n)
    {
        return new Vector3(x * n, y * n, z * n);
    }
    
    public Vector3 div(Vector3 v3)
    {
        return new Vector3(x / v3.getX(), y / v3.getY(), z / v3.getZ());
    }
    
    public Vector3 div(float n)
    {
        return new Vector3(x / n, y / n, z / n);
    }
    
    public boolean equals(Vector3 v3)
    {
        return x == v3.getX() && y == v3.getY() && z == v3.getZ();
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
    
    public float get(int index)
    {
        switch (index)
        {
            case 0:
                return x;
            case 1:
                return y;
            case 2:
                return z;
            default:
                throw new IndexOutOfBoundsException();
        }
    }
    
    public String toString()
    {
        return String.format("(%s, %s, %s)", x, y, z);
    }
}
