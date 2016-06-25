/**
 * Write a description of class Quaternion here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Quaternion  
{
    private float x, y, z, w;
    
    public Quaternion(float x, float y, float z, float w)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    
    public Quaternion(Vector4 v4)
    {
        this(v4.getX(), v4.getY(), v4.getZ(), v4.getW());
    }
    
    public Quaternion(Vector3 axis, float angle)
    {
        float sinHalfA = (float)Math.sin(angle / 2);
        float cosHalfA = (float)Math.cos(angle / 2);

        x = axis.getX() * sinHalfA;
        y = axis.getY() * sinHalfA;
        z = axis.getZ() * sinHalfA;
        w = cosHalfA;
    }
    
    public Quaternion(Vector4 axis, float angle)
    {
        float sinHalfA = (float)Math.sin(angle / 2);
        float cosHalfA = (float)Math.cos(angle / 2);

        x = axis.getX() * sinHalfA;
        y = axis.getY() * sinHalfA;
        z = axis.getZ() * sinHalfA;
        w = cosHalfA;
    }
    
    public Quaternion(float eulerX, float eulerY, float eulerZ)
    {
        float sinX = (float)Math.sin(eulerX);
        float cosX = (float)Math.cos(eulerX);
        
        float sinY = (float)Math.sin(eulerY);
        float cosY = (float)Math.cos(eulerY);
        
        float sinZ = (float)Math.sin(eulerZ);
        float cosZ = (float)Math.cos(eulerZ);
        
        float sxsy = sinX * sinY;
        float cxcy = cosX * cosY;
        
        x = cxcy * sinZ + sxsy * cosZ;
        y = sinX * cosY * cosZ + cosX * sinY * sinZ;
        z = cosX * sinY * cosZ - sinX * cosY * sinZ;
        w = cxcy * cosZ - sxsy * sinZ;
    }
    
    public Quaternion(Matrix4x4 rot)
    {
        float trace = rot.get(0, 0) + rot.get(1, 1) + rot.get(2, 2);
        
        if (trace > 0)
        {
            float s = 0.5f / (float)Math.sqrt(trace + 1.0f);
            w = 0.25f / s;
            x = (rot.get(1, 2) - rot.get(2, 1)) * s;
            y = (rot.get(2, 0) - rot.get(0, 2)) * s;
            z = (rot.get(0, 1) - rot.get(1, 0)) * s;
        }
        else
        {
            if (rot.get(0, 0) > rot.get(1, 1) && rot.get(0, 0) > rot.get(2, 2))
            {
                float s = 2.0f * (float)Math.sqrt(1.0f + rot.get(0, 0) - rot.get(1, 1) - rot.get(2, 2));
                w = (rot.get(1, 2) - rot.get(2, 1)) / s;
                x = 0.25f * s;
                y = (rot.get(1, 0) + rot.get(0, 1)) / s;
                z = (rot.get(2, 0) + rot.get(0, 2)) / s;
            }
            else if (rot.get(1, 1) > rot.get(2, 2))
            {
                float s = 2.0f * (float)Math.sqrt(1.0f + rot.get(1, 1) - rot.get(0, 0) - rot.get(2, 2));
                w = (rot.get(2, 0) - rot.get(0, 2)) / s;
                x = (rot.get(1, 0) + rot.get(0, 1)) / s;
                y = 0.25f * s;
                z = (rot.get(2, 1) + rot.get(1, 2)) / s;
            }
            else
            {
                float s = 2.0f * (float)Math.sqrt(1.0f + rot.get(2, 2) - rot.get(0, 0) - rot.get(1, 1));
                w = (rot.get(0, 1) - rot.get(1, 0) ) / s;
                x = (rot.get(2, 0) + rot.get(0, 2) ) / s;
                y = (rot.get(1, 2) + rot.get(2, 1) ) / s;
                z = 0.25f * s;
            }
        }

        float mag = (float)Math.sqrt(x*x + y*y + z*z + w*w);
        
        if (mag != 0)
        {
            x /= mag;
            y /= mag;
            z /= mag;
            w /= mag;
        }
    }
    
    public float magnitude()
    {
        return (float)Math.sqrt(x*x + y*y + z*z + w*w);
    }
    
    public float dot(Quaternion qt)
    {
        return x * qt.getX() + y * qt.getY() + z * qt.getZ() + w * qt.getW();
    }
    
    public Quaternion unit()
    {
        float mag = magnitude();
        
        if (mag == 0)
            return this;
            
        return new Quaternion(x / mag, y / mag, z / mag, w / mag);
    }
    
    public Quaternion conjugate()
    {
        return new Quaternion(-x, -y, -z, w);
    }
    
    public Quaternion add(Quaternion qt)
    {
        return new Quaternion(x + qt.getX(), y + qt.getY(), z + qt.getZ(), w + qt.getW());
    }
    
    public Quaternion sub(Quaternion qt)
    {
        return new Quaternion(x - qt.getX(), y - qt.getY(), z - qt.getZ(), w - qt.getW());
    }
    
    public Quaternion mul(Quaternion qt)
    {
        float nx = x * qt.getW() + w * qt.getX() + y * qt.getZ() - z * qt.getY();
        float ny = y * qt.getW() + w * qt.getY() + z * qt.getX() - x * qt.getZ();
        float nz = z * qt.getW() + w * qt.getZ() + x * qt.getY() - y * qt.getX();
        float nw = w * qt.getW() - x * qt.getX() - y * qt.getY() - z * qt.getZ();
        
        return new Quaternion(nx, ny, nz, nw);
    }
    
    public Quaternion mul(Vector3 v3)
    {
        float nx =  w * v3.getX() + y * v3.getZ() - z * v3.getY();
        float ny =  w * v3.getY() + z * v3.getX() - x * v3.getZ();
        float nz =  w * v3.getZ() + x * v3.getY() - y * v3.getX();
        float nw = -x * v3.getX() - y * v3.getY() - z * v3.getZ();
        
        return new Quaternion(nx, ny, nz, nw);
    }
    
    public Quaternion mul(Vector4 v4)
    {
        return mul(new Vector3(v4.getX(), v4.getY(), v4.getZ()));
    }
    
    public Quaternion mul(float n)
    {
        return new Quaternion(x * n, y * n, z * n, w * n);
    }
    
    public Quaternion nlerp(Quaternion to, float rate, boolean shortest)
    {
        Quaternion correctedTo = to;
        
        if (shortest && dot(to) < 0)
        {
            correctedTo = new Quaternion(-to.getX(), -to.getY(), -to.getZ(), -to.getW());
        }
        
        return correctedTo.sub(this).mul(rate).add(this).unit();
    }
    
    public Quaternion slerp(Quaternion to, float rate, boolean shortest)
    {
        final float EPSILON = 1e3f;
        
        float cos = dot(to);
        Quaternion correctedTo = to;
        
        if (shortest && cos < 0)
        {
            cos = -cos;
            correctedTo = new Quaternion(-to.getX(), -to.getY(), -to.getZ(), -to.getW());
        }
        
        if (Math.abs(cos) >= 1 - EPSILON)
            return nlerp(correctedTo, rate, false);
        
        float sin = (float)Math.sqrt(1.0f - cos * cos);
        float angle = (float)Math.atan2(sin, cos);
        float invSin =  1.0f/sin;

        float srcFactor = (float)Math.sin((1.0f - rate) * angle) * invSin;
        float destFactor = (float)Math.sin(rate * angle) * invSin;
        
        return mul(srcFactor).add(correctedTo.mul(destFactor));
    }
    
    public boolean equals(Quaternion qt)
    {
        return x == qt.getX() && y == qt.getY() && z == qt.getZ() && w == qt.getW();
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
    
    public Vector3 getForward()
    {
        return new Vector3(0, 0, 1).rotate(this);
    }
    
    public Vector3 getBack()
    {
        return new Vector3(0, 0, -1).rotate(this);
    }
    
    public Vector3 getUp()
    {
        return new Vector3(0, 1, 0).rotate(this);
    }
    
    public Vector3 getDown()
    {
        return new Vector3(0, -1, 0).rotate(this);
    }
    
    public Vector3 getLeft()
    {
        return new Vector3(-1, 0, 0).rotate(this);
    }
    
    public Vector3 getRight()
    {
        return new Vector3(1, 0, 0).rotate(this);
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
    
    public String toString()
    {
        return String.format("(%s, %s, %s, %s)", x, y, z, w);
    }
}
