import greenfoot.GreenfootImage;

import java.awt.Color;
import java.awt.image.DataBufferInt;

import java.util.Arrays;
import java.util.ArrayList;

/**
 * Write a description of class RenderContext here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RenderContext extends GreenfootImage
{
    private final World3D world;
    
    private final float[] zBuffer;
    
    private final Matrix4x4 screenSpaceTransform;
    private final Matrix4x4 identityMatrix;
    
    private boolean cullBackFace;
    private boolean ambientLighting;
    
    private final int[] imageData;
    
    public RenderContext(int width, int height, World3D world)
    {
        super(width, height);
        this.world = world;
        
        zBuffer = new float[width * height];
        
        screenSpaceTransform = Matrix4x4.initScreenSpaceTransform(getWidth() / 2, getHeight() / 2);
        identityMatrix = new Matrix4x4();
        
        imageData = ((DataBufferInt)getAwtImage().getRaster().getDataBuffer()).getData();
        
        cullBackFace = true;
        ambientLighting = true;
    }
    
    public void clearZBuffer()
    {
        Arrays.fill(zBuffer, Float.MAX_VALUE);
    }
    
    public void clear()
    {
        Arrays.fill(imageData, -16777216);
    }
    
    public void setCullBackFace(boolean b)
    {
        cullBackFace = b;
    }
    
    public void setAmbientLighting(boolean b)
    {
        ambientLighting = b;
    }
    
    public void drawTriangle(Vertex v0, Vertex v1, Vertex v2, GreenfootImage texture)
    {
        if (v0.isInsideViewFrustum() && v1.isInsideViewFrustum() && v2.isInsideViewFrustum())
        {
            fillTriangle(v0, v1, v2, texture);
            return;
        }
        
        ArrayList<Vertex> vertices = new ArrayList<Vertex>();
        ArrayList<Vertex> auxList = new ArrayList<Vertex>();
        
        vertices.add(v0);
        vertices.add(v1);
        vertices.add(v2);
        
        if (clipPolygonAxis(vertices, auxList, 0) && clipPolygonAxis(vertices, auxList, 1) && clipPolygonAxis(vertices, auxList, 2))
        {
            Vertex initialVertex = vertices.get(0);
            
            for (int i = 1; i < vertices.size() - 1; i++)
            {
                fillTriangle(initialVertex, vertices.get(i), vertices.get(i + 1), texture);
            }
        }
    }
    
    private void fillTriangle(Vertex v0, Vertex v1, Vertex v2, GreenfootImage texture)
    {
        Vertex minYVert = v0.transformBy(screenSpaceTransform, identityMatrix).perspectiveDivide();
        Vertex midYVert = v1.transformBy(screenSpaceTransform, identityMatrix).perspectiveDivide();
        Vertex maxYVert = v2.transformBy(screenSpaceTransform, identityMatrix).perspectiveDivide();
        
        if (triangleAreaX2(minYVert, maxYVert, midYVert) >= 0 && cullBackFace)
        {
            return;
        }
        
        if(maxYVert.getY() < midYVert.getY())
        {
            Vertex temp = maxYVert;
            maxYVert = midYVert;
            midYVert = temp;
        }
        
        if(midYVert.getY() < minYVert.getY())
        {
            Vertex temp = midYVert;
            midYVert = minYVert;
            minYVert = temp;
        }

        if(maxYVert.getY() < midYVert.getY())
        {
            Vertex temp = maxYVert;
            maxYVert = midYVert;
            midYVert = temp;
        }
        
        scanTriangle(minYVert, midYVert, maxYVert, triangleAreaX2(minYVert, maxYVert, midYVert) >= 0, texture);
    }
    
    private void scanTriangle(Vertex minYVert, Vertex midYVert, Vertex maxYVert, boolean side, GreenfootImage texture)
    {
        Gradients gradients = new Gradients(minYVert, midYVert, maxYVert, world.getLightDirection(), world.getConstantLight());
        
        Edge topToBottom = new Edge(gradients, minYVert, maxYVert, 0);
        Edge topToMiddle = new Edge(gradients, minYVert, midYVert, 0);
        Edge middleToBottom = new Edge(gradients, midYVert, maxYVert, 1);
        
        scanEdges(gradients, topToBottom, topToMiddle, side, texture);
        scanEdges(gradients, topToBottom, middleToBottom, side, texture);
    }
    
    private void scanEdges(Gradients gradients, Edge a, Edge b, boolean side, GreenfootImage texture)
    {
        Edge left = a;
        Edge right = b;
        
        if (side)
        {
            Edge temp = left;
            left = right;
            right = temp;
        }
        
        int yStart = b.getYStart();
        int yEnd = b.getYEnd();
        
        for (int y = yStart; y < yEnd; y++)
        {
            drawScanLine(gradients, left, right, y, texture);
            left.step();
            right.step();
        }
    }
    
    private void drawScanLine(Gradients gradients, Edge left, Edge right, int y, GreenfootImage texture)
    {
        int xMin = (int)Math.ceil(left.getX());
        int xMax = (int)Math.ceil(right.getX());
        float xPrestep = xMin - left.getX();
        
        Vector2 depthStep = gradients.getDepthStep();
        Vector2 oneOverZStep = gradients.getOneOverZStep();
        Vector2 texCoordXStep = gradients.getTexCoordXStep();
        Vector2 texCoordYStep = gradients.getTexCoordYStep();
        Vector2 ambientLightStep = gradients.getAmbientLightStep();
        
        float depth = left.getDepth() + depthStep.getX() * xPrestep;
        float oneOverZ = left.getOneOverZ() + oneOverZStep.getX() * xPrestep;
        float texCoordX = left.getTexCoordX() + texCoordXStep.getX() * xPrestep;
        float texCoordY = left.getTexCoordY() + texCoordYStep.getX() * xPrestep;
        float ambientLight = left.getAmbientLight() + ambientLightStep.getX() * xPrestep;
        
        for (int x = xMin; x < xMax; x++)
        {
            int index = x + y * getWidth();
            
            if (depth < zBuffer[index])
            {
                float z = 1.0f / oneOverZ;
                
                int srcX = (int)((texCoordX * z) * (float)(texture.getWidth() - 1) + 0.5f);
                int srcY = (int)((texCoordY * z) * (float)(texture.getHeight() - 1) + 0.5f);
                
                if (srcX < 0 || srcX > texture.getWidth() - 1 || srcY < 0 || srcY > texture.getHeight() - 1)
                {
                  continue;
                }
                
                Color color = readImageData(texture, srcX, srcY);
                
                if (ambientLighting)
                {
                    color = new Color((int)(color.getRed() * ambientLight), (int)(color.getGreen() * ambientLight), (int)(color.getBlue() * ambientLight));
                }
                
                if (color.getAlpha() == 255)
                {
                    zBuffer[index] = depth;
                    writeImageData(x, y, color);
                }
            }
            
            depth += depthStep.getX();
            oneOverZ += oneOverZStep.getX();
            texCoordX += texCoordXStep.getX();
            texCoordY += texCoordYStep.getX();
            ambientLight += ambientLightStep.getX();
        }
    }
    
    private void writeImageData(int x, int y, Color color)
    {
        int index = y * getWidth() + x;
        imageData[index] = color.getRGB();
    }
    
    private Color readImageData(GreenfootImage img, int x, int y)
    {
        int index = y * img.getWidth() + x;
        int rgb = ((DataBufferInt)img.getAwtImage().getRaster().getDataBuffer()).getData()[index];
        
        return new Color(rgb);
    }
    
    private boolean clipPolygonAxis(ArrayList<Vertex> vertices, ArrayList<Vertex> auxList, int componentIndex)
    {
        clipPolygonComponent(vertices, componentIndex, 1.0f, auxList);
        vertices.clear();
        
        if (auxList.isEmpty())
        {
            return false;
        }
            
        clipPolygonComponent(auxList, componentIndex, -1.0f, vertices);
        auxList.clear();
        
        return !vertices.isEmpty();
    }
    
    private void clipPolygonComponent(ArrayList<Vertex> vertices, int componentIndex, float componentFactor, ArrayList<Vertex> result)
    {
        Vertex previousVertex = vertices.get(vertices.size() - 1);
        float previousComponent = previousVertex.get(componentIndex) * componentFactor;
        boolean previousInside = previousComponent <= previousVertex.getPosition().getW();
        
        for (Vertex currentVertex : vertices)
        {
            float currentComponent = currentVertex.get(componentIndex) * componentFactor;
            boolean currentInside = currentComponent <= currentVertex.getPosition().getW();
            
            if (currentInside ^ previousInside)
            {
                float lerpAmt = (previousVertex.getPosition().getW() - previousComponent) / ((previousVertex.getPosition().getW() - previousComponent) - (currentVertex.getPosition().getW() - currentComponent));
                
                result.add(previousVertex.lerp(currentVertex, lerpAmt));
            }
            
            if (currentInside)
            {
                result.add(currentVertex);
            }
            
            previousVertex = currentVertex;
            previousComponent = currentComponent;
            previousInside = currentInside;
        }
    }
    
    private float triangleAreaX2(Vertex v0, Vertex v1, Vertex v2)
    {
        float x1 = v1.getX() - v0.getX();
        float y1 = v1.getY() - v0.getY();
        
        float x2 = v2.getX() - v0.getX();
        float y2 = v2.getY() - v0.getY();
        
        return x1 * y2 - x2 * y1;
    }
}
