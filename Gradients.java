/**
 * Write a description of class Gradients here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Gradients  
{
    private Vector3 depth;
    private Vector3 oneOverZ;
    private Vector3 texCoordX;
    private Vector3 texCoordY;
    private Vector3 ambientLight;
    
    private Vector2 depthStep;
    private Vector2 oneOverZStep;
    private Vector2 texCoordXStep;
    private Vector2 texCoordYStep;
    private Vector2 ambientLightStep;
    
    public Gradients(Vertex minYVert, Vertex midYVert, Vertex maxYVert, Vector4 lightDir, float constantLight)
    {
        float dX = (((midYVert.getX() - maxYVert.getX()) * (minYVert.getY() - maxYVert.getY())) - ((minYVert.getX() - maxYVert.getX()) * (midYVert.getY() - maxYVert.getY())));
        float oneOverDX = 1.0f / dX;
        float oneOverDY = -oneOverDX;
        
        depth = new Vector3(minYVert.getPosition().getZ(), midYVert.getPosition().getZ(), maxYVert.getPosition().getZ());
        oneOverZ = new Vector3(1.0f / minYVert.getPosition().getW(), 1.0f / midYVert.getPosition().getW(), 1.0f / maxYVert.getPosition().getW());
        
        texCoordX = new Vector3(minYVert.getTexCoords().getX() * oneOverZ.getX(), midYVert.getTexCoords().getX() * oneOverZ.getY(), maxYVert.getTexCoords().getX() * oneOverZ.getZ());
        texCoordY = new Vector3(minYVert.getTexCoords().getY() * oneOverZ.getX(), midYVert.getTexCoords().getY() * oneOverZ.getY(), maxYVert.getTexCoords().getY() * oneOverZ.getZ());
            
        float lightAmt = 1 - constantLight;
        ambientLight = new Vector3(clamp(minYVert.getNormal().dot(lightDir.unit())) * lightAmt + constantLight, clamp(midYVert.getNormal().dot(lightDir.unit())) * lightAmt + constantLight, clamp(maxYVert.getNormal().dot(lightDir.unit())) * lightAmt + constantLight);
        
        depthStep = calcSteps(depth, minYVert, midYVert, maxYVert, oneOverDX, oneOverDY);
        oneOverZStep = calcSteps(oneOverZ, minYVert, midYVert, maxYVert, oneOverDX, oneOverDY);
        texCoordXStep = calcSteps(texCoordX, minYVert, midYVert, maxYVert, oneOverDX, oneOverDY);
        texCoordYStep = calcSteps(texCoordY, minYVert, midYVert, maxYVert, oneOverDX, oneOverDY);
        ambientLightStep = calcSteps(ambientLight, minYVert, midYVert, maxYVert, oneOverDX, oneOverDY);
    }
    
    private float clamp(float num)
    {
        if (num < 0)
        {
            return 0;
        }
        
        if (num > 1)
        {
            return 1;
        }
        
        return num;
    }
    
    private Vector2 calcSteps(Vector3 values, Vertex minYVert, Vertex midYVert, Vertex maxYVert, float oneOverDX, float oneOverDY)
    {
        return new Vector2(calcXStep(values, minYVert, midYVert, maxYVert, oneOverDX), calcYStep(values, minYVert, midYVert, maxYVert, oneOverDY));
    }
    
    private float calcXStep(Vector3 values, Vertex minYVert, Vertex midYVert, Vertex maxYVert, float oneOverDX)
    {
        return (((values.getY() - values.getZ()) * (minYVert.getY() - maxYVert.getY())) - ((values.getX() - values.getZ()) * (midYVert.getY() - maxYVert.getY()))) * oneOverDX;
    }
    
    private float calcYStep(Vector3 values, Vertex minYVert, Vertex midYVert, Vertex maxYVert, float oneOverDY)
    {
        return (((values.getY() - values.getZ()) * (minYVert.getX() - maxYVert.getX())) - ((values.getX() - values.getZ()) * (midYVert.getX() - maxYVert.getX()))) * oneOverDY;
    }
    
    public Vector3 getDepth()
    {
        return depth;
    }
    
    public Vector3 getOneOverZ()
    {
        return oneOverZ;
    }
    
    public Vector3 getTexCoordX()
    {
        return texCoordX;
    }
    
    public Vector3 getTexCoordY()
    {
        return texCoordY;
    }
    
    public Vector3 getAmbientLight()
    {
        return ambientLight;
    }
    
    public Vector2 getDepthStep()
    {
        return depthStep;
    }
    
    public Vector2 getOneOverZStep()
    {
        return oneOverZStep;
    }
    
    public Vector2 getTexCoordXStep()
    {
        return texCoordXStep;
    }
    
    public Vector2 getTexCoordYStep()
    {
        return texCoordYStep;
    }
    
    public Vector2 getAmbientLightStep()
    {
        return ambientLightStep;
    }
}
