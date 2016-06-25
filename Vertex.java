/**
 * Write a description of class Vertex here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Vertex  
{
    private Vector4 position;
    private Vector4 normal;
    private Vector2 texCoords;
    
    public Vertex(Vector4 position, Vector4 normal, Vector2 texCoords)
    {
        this.position = position;
        this.normal = normal;
        this.texCoords = texCoords;
    }
    
    public Vertex transformBy(Matrix4x4 trans, Matrix4x4 normalTrans)
    {
        return new Vertex(trans.transform(position), normalTrans.transform(normal).unit(), texCoords);
    }
    
    public Vertex perspectiveDivide()
    {
        Vector4 posOut = new Vector4(position.getX() / position.getW(), position.getY() / position.getW(), position.getZ() / position.getW(), position.getW());
        
        return new Vertex(posOut, normal, texCoords);
    }
    
    public boolean isInsideViewFrustum()
    {
        double absW = Math.abs(position.getW());
        return Math.abs(position.getX()) <= absW && Math.abs(position.getY()) <= absW && Math.abs(position.getZ()) <= absW;
    }
    
    public Vertex lerp(Vertex to, float inc)
    {
        return new Vertex(position.lerp(to.getPosition(), inc), normal.lerp(to.getNormal(), inc), texCoords.lerp(to.getTexCoords(), inc));
    }
    
    public Vector4 getPosition()
    {
        return position;
    }
    
    public Vector4 getNormal()
    {
        return normal;
    }
    
    public Vector2 getTexCoords()
    {
        return texCoords;
    }
    
    public float getX()
    {
        return position.getX();
    }
    
    public float getY()
    {
        return position.getY();
    }

    public float getZ()
    {
        return position.getZ();
    }
    
    public Vector3 getXYZ()
    {
        return new Vector3(position.getX(), position.getY(), position.getZ());
    }
    
    public float get(int i)
    {
        switch (i)
        {
            case 0:
                return position.getX();
            case 1:
                return position.getY();
            case 2:
                return position.getZ();
            case 3:
                return position.getW();
            default:
                throw new IndexOutOfBoundsException();
        }
    }
}
