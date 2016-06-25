/**
 * Write a description of class Torus here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Torus extends MeshObject 
{
    public Torus() throws java.io.IOException
    {
        super(new Mesh("models/torus.obj"), new greenfoot.GreenfootImage("bricks.jpg"));
    }
}
