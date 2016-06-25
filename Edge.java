/**
 * Write a description of class Edge here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Edge  
{
    private float x;
    private float xStep;
    
    private int yStart;
    private int yEnd;
    
    private float texCoordX;
    private float texCoordXStep;
    private float texCoordY;
    private float texCoordYStep;
    
    private float depth;
    private float depthStep;
    
    private float oneOverZ;
    private float oneOverZStep;
    
    private float ambientLight;
    private float ambientLightStep;
    
    public Edge(Gradients gradients, Vertex minYVert, Vertex maxYVert, int minYVertIndex)
    {
        yStart = (int)Math.ceil(minYVert.getY());
        yEnd = (int)Math.ceil(maxYVert.getY());
        
        float xDist = maxYVert.getX() - minYVert.getX();
        float yDist = maxYVert.getY() - minYVert.getY();
        
        float yPrestep = yStart - minYVert.getY();
        xStep = (float)xDist / (float)yDist;
        x = minYVert.getX() + yPrestep * xStep;
        float xPrestep = x - minYVert.getX();
        
        depth = getInitialValue(gradients.getDepth().get(minYVertIndex), gradients.getDepthStep(), xPrestep, yPrestep);
        depthStep = getXStepValue(gradients.getDepthStep());
        oneOverZ = getInitialValue(gradients.getOneOverZ().get(minYVertIndex), gradients.getOneOverZStep(), xPrestep, yPrestep);
        oneOverZStep = getXStepValue(gradients.getOneOverZStep());
        
        texCoordX = getInitialValue(gradients.getTexCoordX().get(minYVertIndex), gradients.getTexCoordXStep(), xPrestep, yPrestep);
        texCoordXStep = getXStepValue(gradients.getTexCoordXStep());
        texCoordY = getInitialValue(gradients.getTexCoordY().get(minYVertIndex), gradients.getTexCoordYStep(), xPrestep, yPrestep);
        texCoordYStep = getXStepValue(gradients.getTexCoordYStep());
        
        ambientLight = getInitialValue(gradients.getAmbientLight().get(minYVertIndex), gradients.getAmbientLightStep(), xPrestep, yPrestep);
        ambientLightStep = getXStepValue(gradients.getAmbientLightStep());
    }
    
    public void step()
    {
        x += xStep;
        
        depth += depthStep;
        oneOverZ += oneOverZStep;
        
        texCoordX += texCoordXStep;
        texCoordY += texCoordYStep;
        
        ambientLight += ambientLightStep;
    }
    
    private float getInitialValue(float value, Vector2 step, float xPrestep, float yPrestep)
    {
        return value + step.getX() * xPrestep + step.getY() * yPrestep;
    }
    
    private float getXStepValue(Vector2 step)
    {
        return step.getY() + step.getX() * xStep;
    }
    
    public float getX()
    {
        return x;
    }
    
    public int getYStart()
    {
        return yStart;
    }
    
    public int getYEnd()
    {
        return yEnd;
    }
    
    public float getDepth()
    {
        return depth;
    }
    
    public float getOneOverZ()
    {
        return oneOverZ;
    }
    
    public float getTexCoordX()
    {
        return texCoordX;
    }
    
    public float getTexCoordY()
    {
        return texCoordY;
    }
    
    public float getAmbientLight()
    {
        return ambientLight;
    }
}
