/**
 * Write a description of class Matrix4x4 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Matrix4x4  
{
    private float[][] matrix;
    
    public Matrix4x4()
    {
        matrix = new float[4][4];
        
        for (int y = 0; y < 4; y++)
        {
            for (int x = 0; x < 4; x++)
            {
                matrix[y][x] = x == y ? 1 : 0;
            }
        }
    }
    
    public static Matrix4x4 fromPosition(float x, float y, float z)
    {
        Matrix4x4 out = new Matrix4x4();
        
        out.set(0, 3, x);
        out.set(1, 3, y);
        out.set(2, 3, z);
        
        return out;
    }
    
    public static Matrix4x4 fromPosition(Vector3 position)
    {
        return fromPosition(position.getX(), position.getY(), position.getZ());
    }
    
    public static Matrix4x4 fromPosition(Vector4 position)
    {
        return fromPosition(position.getX(), position.getY(), position.getZ());
    }
    
    public static Matrix4x4 fromEulerAngles(float x, float y, float z)
    {
        Matrix4x4 rx = new Matrix4x4();
        Matrix4x4 ry = new Matrix4x4();
        Matrix4x4 rz = new Matrix4x4();
        
        float sinX = (float)Math.sin(x);
        float cosX = (float)Math.cos(x);
        
        rx.set(1, 1, cosX);
        rx.set(2, 1, sinX);
        rx.set(1, 2, -sinX);
        rx.set(2, 2, cosX);
        
        float sinY = (float)Math.sin(y);
        float cosY = (float)Math.cos(y);
        
        ry.set(0, 0, cosY);
        ry.set(2, 0, sinY);
        ry.set(0, 2, -sinY);
        ry.set(2, 2, cosY);
        
        float sinZ = (float)Math.sin(z);
        float cosZ = (float)Math.cos(z);
        
        rz.set(0, 0, cosZ);
        rz.set(1, 0, sinZ);
        rz.set(0, 1, -sinZ);
        rz.set(1, 1, cosZ);
        
        return rz.mul(ry.mul(rx));
    }
    
    public static Matrix4x4 fromEulerAngles(Vector3 v3)
    {
        return fromEulerAngles(v3.getX(), v3.getY(), v3.getZ());
    }
    
    public static Matrix4x4 fromAxisAngle(float ax, float ay, float az, float angle)
    {
        Matrix4x4 out = new Matrix4x4();
        
        float sinA = (float)Math.sin(angle);
        float cosA = (float)Math.cos(angle);
        
        out.set(0, 0, cosA + ax*ax * (1 - cosA));
        out.set(0, 1, ax * ay * (1 - cosA) - az * sinA);
        out.set(0, 2, ax * az * (1 - cosA) + ay * sinA);
        out.set(1, 0, ay * ax * (1 - cosA) + az * sinA);
        out.set(1, 1, cosA + ay*ay * (1 - cosA));
        out.set(1, 2, ay * az * (1 - cosA) - ax * sinA);
        out.set(2, 0, az * ax * (1 - cosA) - ay * sinA);
        out.set(2, 1, az * ay * (1 - cosA) + ax * sinA);
        out.set(2, 2, cosA + az*az * (1 - cosA));
        
        return out;
    }
    
    public static Matrix4x4 fromAxisAngle(Vector3 axis, float angle)
    {
        return fromAxisAngle(axis.getX(), axis.getY(), axis.getZ(), angle);
    }
    
    public static Matrix4x4 fromAxisAngle(Vector4 axis, float angle)
    {
        return fromAxisAngle(axis.getX(), axis.getY(), axis.getZ(), angle);
    }
    
    public static Matrix4x4 fromAxes(Vector3 forward, Vector3 up)
    {
        Vector3 f = forward.unit();
        Vector3 r = up.unit();
        r = r.cross(f);
        Vector3 u = f.cross(r);
        
        return fromAxes(f, u, r);
    }
    
    public static Matrix4x4 fromAxes(Vector3 forward, Vector3 up, Vector3 right)
    {
        Matrix4x4 out = new Matrix4x4();
        
        out.set(0, 0, right.getX());
        out.set(0, 1, right.getY());
        out.set(0, 2, right.getZ());
        
        out.set(1, 0, up.getX());
        out.set(1, 1, up.getY());
        out.set(1, 2, up.getZ());
        
        out.set(2, 0, forward.getX());
        out.set(2, 1, forward.getY());
        out.set(2, 2, forward.getZ());
        
        return out;
    }
    
    public static Matrix4x4 fromAxes(Vector4 forward, Vector4 up)
    {
        Vector4 f = forward.unit();
        Vector4 r = up.unit();
        r = r.cross(f);
        Vector4 u = f.cross(r);
        
        return fromAxes(f, u, r);
    }
    
    public static Matrix4x4 fromAxes(Vector4 forward, Vector4 up, Vector4 right)
    {
        Matrix4x4 out = new Matrix4x4();
        
        out.set(0, 0, right.getX());
        out.set(0, 1, right.getY());
        out.set(0, 2, right.getZ());
        
        out.set(1, 0, up.getX());
        out.set(1, 1, up.getY());
        out.set(1, 2, up.getZ());
        
        out.set(2, 0, forward.getX());
        out.set(2, 1, forward.getY());
        out.set(2, 2, forward.getZ());
        
        return out;
    }
    
    public static Matrix4x4 fromQuaternion(float x, float y, float z, float w)
    {
        Vector3 forward =  new Vector3(2.0f * (x * z - w * y), 2.0f * (y * z + w * x), 1.0f - 2.0f * (x * x + y * y));
        Vector3 up = new Vector3(2.0f * (x * y + w * z), 1.0f - 2.0f * (x * x + z * z), 2.0f * (y * z - w * x));
        Vector3 right = new Vector3(1.0f - 2.0f * (y * y + z * z), 2.0f * (x * y - w * z), 2.0f * (x * z + w * y));
        
        return fromAxes(forward, up, right);
    }
    
    public static Matrix4x4 fromQuaternion(Quaternion qt)
    {
        return fromQuaternion(qt.getX(), qt.getY(), qt.getZ(), qt.getW());
    }
    
    public static Matrix4x4 fromScale(float x, float y, float z)
    {
        Matrix4x4 out = new Matrix4x4();
        
        out.set(0, 0, x);
        out.set(1, 1, y);
        out.set(2, 2, z);
        
        return out;
    }
    
    public static Matrix4x4 fromScale(Vector3 v3)
    {
        return fromScale(v3.getX(), v3.getY(), v3.getZ());
    }
    
    public static Matrix4x4 initPerspective(float fov, float aspectRatio, float zNear, float zFar)
    {
        Matrix4x4 out = new Matrix4x4();
        
        float tanHalfFOV = (float)Math.tan(fov / 2);
        float zRange = zNear - zFar;
        
        out.set(0, 0, 1.0f / (tanHalfFOV * aspectRatio));
        out.set(1, 1, 1.0f / tanHalfFOV);
        out.set(2, 2, (-zNear -zFar)/zRange);
        out.set(2, 3, 2 * zFar * zNear / zRange);
        out.set(3, 2, 1);
        out.set(3, 3, 0);
        
        return out;
    }
    
    public static Matrix4x4 initScreenSpaceTransform(float halfWidth, float halfHeight)
    {
        Matrix4x4 out = new Matrix4x4();
        
        out.set(0, 0, halfWidth);
        out.set(1, 1, -halfHeight);
        out.set(0, 3, halfWidth - 0.5f);
        out.set(1, 3, halfHeight - 0.5f);
        
        return out;
    }
    
    public Vector3 transform(Vector3 v3)
    {
        float nx = matrix[0][0] * v3.getX() + matrix[0][1] * v3.getY() + matrix[0][2] * v3.getZ() + matrix[0][3];
        float ny = matrix[1][0] * v3.getX() + matrix[1][1] * v3.getY() + matrix[1][2] * v3.getZ() + matrix[1][3];
        float nz = matrix[2][0] * v3.getX() + matrix[2][1] * v3.getY() + matrix[2][2] * v3.getZ() + matrix[2][3];

        return new Vector3(nx, ny, nz);
    }
    
    public Vector4 transform(Vector4 v4)
    {
        float nx = matrix[0][0] * v4.getX() + matrix[0][1] * v4.getY() + matrix[0][2] * v4.getZ() + matrix[0][3] * v4.getW();
        float ny = matrix[1][0] * v4.getX() + matrix[1][1] * v4.getY() + matrix[1][2] * v4.getZ() + matrix[1][3] * v4.getW();
        float nz = matrix[2][0] * v4.getX() + matrix[2][1] * v4.getY() + matrix[2][2] * v4.getZ() + matrix[2][3] * v4.getW();
        float nw = matrix[3][0] * v4.getX() + matrix[3][1] * v4.getY() + matrix[3][2] * v4.getZ() + matrix[3][3] * v4.getW();
        
        return new Vector4(nx, ny, nz, nw);
    }
    
    public Matrix4x4 mul(Matrix4x4 m4)
    {
        Matrix4x4 out = new Matrix4x4();
        
        for (int y = 0; y < 4; y++)
        {
            for (int x = 0; x < 4; x++)
            {
                out.set(y, x, matrix[y][0] * m4.get(0, x) +
                    matrix[y][1] * m4.get(1, x) +
                    matrix[y][2] * m4.get(2, x) +
                    matrix[y][3] * m4.get(3, x));
            }
        }
        
        return out;
    }
    
    public float get(int y, int x)
    {
        return matrix[y][x];
    }
    
    public void set(int y, int x, float value)
    {
        matrix[y][x] = value;
    }
    
    public String toString()
    {
        StringBuilder out = new StringBuilder();
        
        for (int y = 0; y < 4; y++)
        {
            for (int x = 0; x < 4; x++)
            {
                out.append(matrix[y][x]).append("\t");
            }
            
            out.append("\n");
        }
        
        return out.toString();
    }
}
