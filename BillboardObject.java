import greenfoot.GreenfootImage;

import java.awt.Color;

import java.util.ArrayList;

/**
 * Write a description of class BillboardObject here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BillboardObject extends GameObject 
{
    private GreenfootImage image;
    private final Mesh mesh;
    
    private final float width, height;
    
    private float rotAngle;
    
    public BillboardObject()
    {
        this(1, 1);
    }
    
    public BillboardObject(float width, float height)
    {
        image = new GreenfootImage(1, 1);
        image.setColor(Color.WHITE);
        image.fill();
        
        mesh = genMesh(width, height);
        
        rotAngle = 0;
        
        this.width = width;
        this.height = height;
    }
    
    public void update(World3D world, float delta)
    {
        getTransform().setRotation(world.getCamera().getTransform().getTransformedRotation());
        getTransform().rotate(new Vector4(getTransform().getTransformedRotation().getRight()), (float)Math.toRadians(180));
        
        if (rotAngle != 0)
        {
            getTransform().rotate(new Vector4(getTransform().getTransformedRotation().getForward()), rotAngle);
        }
        
        world.getRenderContext().setAmbientLighting(false);
        mesh.draw(world.getRenderContext(), getTransform().getTransformation(), world.getCamera().getViewProjection(), image);  
        world.getRenderContext().setAmbientLighting(true);
    }
    
    private static Mesh genMesh(float width, float height)
    {
        float halfWidth = width / 2;
        float halfHeight = height / 2;
        
        ArrayList<Vertex> vertArray = new ArrayList<Vertex>();
        
        vertArray.add(new Vertex(new Vector4(-halfWidth, -halfHeight, 0), new Vector4(0, 0, 1, 0), new Vector2()));
        vertArray.add(new Vertex(new Vector4(-halfWidth, halfHeight, 0), new Vector4(0, 0, 1, 0), new Vector2(0, 1)));
        vertArray.add(new Vertex(new Vector4(halfWidth, -halfHeight, 0), new Vector4(0, 0, 1, 0), new Vector2(1, 0)));
        vertArray.add(new Vertex(new Vector4(halfWidth, halfHeight, 0), new Vector4(0, 0, 1, 0), new Vector2(1, 1)));

        int[] indices = {
            0, 3, 1,
            3, 0, 2
        };
        
        Vertex[] vertices = calcNormals(vertArray, indices);
        
        return new Mesh(vertices, indices);
    }
    
    private static Vertex[] calcNormals(ArrayList<Vertex> vertices, int[] indices)
    {
        Vertex[] out = new Vertex[vertices.size()];
        
        for (int i = 0; i < indices.length; i += 3)
        {
            int i0 = indices[i];
            int i1 = indices[i + 1];
            int i2 = indices[i + 2];

            Vector4 v1 = vertices.get(i1).getPosition().sub(vertices.get(i0).getPosition());
            Vector4 v2 = vertices.get(i2).getPosition().sub(vertices.get(i0).getPosition());

            Vector4 normal = v1.cross(v2).unit();
            
            vertices.set(i0, new Vertex(vertices.get(i0).getPosition(), vertices.get(i0).getNormal().add(normal), vertices.get(i0).getTexCoords()));
            vertices.set(i1, new Vertex(vertices.get(i1).getPosition(), vertices.get(i1).getNormal().add(normal), vertices.get(i1).getTexCoords()));
            vertices.set(i2, new Vertex(vertices.get(i2).getPosition(), vertices.get(i2).getNormal().add(normal), vertices.get(i2).getTexCoords()));
        }
        
        for (int i = 0; i < vertices.size(); i++)
        {
            vertices.set(i, new Vertex(vertices.get(i).getPosition(), vertices.get(i).getNormal().unit(), vertices.get(i).getTexCoords()));
        }
        
        vertices.toArray(out);
        return out;
    }
    
    public float getWidth()
    {
        return width;
    }
    
    public float getHeight()
    {
        return height;
    }
    
    public GreenfootImage getImage()
    {
        return image;
    }
    
    public void setImage(GreenfootImage image)
    {
        this.image = image;
    }
    
    public float getRotAngle()
    {
        return rotAngle;
    }
    
    public void setRotAngle(float angle)
    {
        rotAngle = angle;
    }
}
