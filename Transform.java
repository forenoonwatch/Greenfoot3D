/**
 * Write a description of class Transform here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Transform  
{
    private Transform parent;
    private Matrix4x4 parentMatrix;
    
    private Vector4 position;
    private Quaternion rotation;
    
    public Transform()
    {
        this(new Vector4(), new Quaternion(0, 0, 0, 1));
    }
    
    public Transform(Vector4 position)
    {
        this(position, new Quaternion(0, 0, 0, 1));
    }
    
    public Transform(Quaternion rotation)
    {
        this(new Vector4(), rotation);
    }
    
    public Transform(Vector4 position, Quaternion rotation)
    {
        this.position = position;
        this.rotation = rotation;
        
        parentMatrix = new Matrix4x4();
    }
    
    public Transform(Transform trans)
    {
        position = trans.getPosition();
        rotation = trans.getRotation();
        
        parentMatrix = new Matrix4x4();
    }
    
    public void rotate(Vector4 axis, float angle)
    {
        rotation = new Quaternion(axis, angle).mul(rotation).unit();
    }
    
    public void rotate(Quaternion qt)
    {
        rotation = qt.mul(rotation).unit();
    }
    
    public void lookAt(Vector4 point, Vector4 up)
    {
        rotation = getLookAtRotation(point, up);
    }
    
    public void lookAt(Vector4 point)
    {
        lookAt(point, Vector4.UP);
    }
    
    public Quaternion getLookAtRotation(Vector4 point, Vector4 up)
    {
        return new Quaternion(Matrix4x4.fromAxes(point.sub(position).unit(), up));
    }
    
    public Matrix4x4 getTransformation()
    {
        Matrix4x4 pos = Matrix4x4.fromPosition(position);
        Matrix4x4 rot = Matrix4x4.fromQuaternion(rotation);
        
        return getParentMatrix().mul(pos.mul(rot));
    }
    
    public Matrix4x4 getParentMatrix()
    {
        if (parent != null)
        {
            parentMatrix = parent.getTransformation();
        }
        
        return parentMatrix;
    }
    
    public Vector4 getPosition()
    {
        return position;
    }
    
    public Quaternion getRotation()
    {
        return rotation;
    }
    
    public Vector4 getTransformedPosition()
    {
        return getParentMatrix().transform(position);
    }
    
    public Quaternion getTransformedRotation()
    {
        Quaternion parentRotation = new Quaternion(0, 0, 0, 1);
        
        if (parent != null)
        {
            parentRotation = parent.getTransformedRotation();
        }
        
        return parentRotation.mul(rotation);
    }
    
    public void setParent(Transform parent)
    {
        this.parent = parent;
    }
    
    public void setPosition(float x, float y, float z)
    {
        position = new Vector4(x, y, z);
    }
    
    public void setPosition(float x, float y, float z, float w)
    {
        position = new Vector4(x, y, z, w);
    }
    
    public void setPosition(Vector3 v3)
    {
        position = new Vector4(v3);
    }
    
    public void setPosition(Vector4 v4)
    {
        position = v4;
    }
    
    public void setRotation(Quaternion rotation)
    {
        this.rotation = rotation;
    }
}
