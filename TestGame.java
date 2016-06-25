import greenfoot.*;

import java.awt.Color;

import java.io.IOException;

/**
 * Write a description of class TestGame here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TestGame extends World3D
{
    
    private Monkey monkey;
    private Torus torus;
    private BillboardObject billboard;
    private CollideCube cube;
    private ControlCube cCube;
    private JointLink testLink;
    
    private float counter;
    
    public TestGame()
    {
        try {
            monkey = new Monkey();
            torus = new Torus();
            cube = new CollideCube();
            cCube = new ControlCube();
        } catch(IOException e) {}
        
        //setCamera(new KeyCamera(getCamera().getViewProjection()));
        setCamera(new FPSCamera(this, getCamera().getViewProjection()));
        
        counter = 0;
        
        billboard = new BillboardObject(2, 1);
        billboard.setImage(new GreenfootImage("bricks.jpg"));
        billboard.getTransform().setPosition(0, 0, 5);
        
        cube.getTransform().setPosition(10, 0, 3);
        cube.setAnchored(true);
        
        testLink = new JointLink();
        testLink.setLinkObj(torus);
        testLink.getOffsetA().setPosition(3, 0, 0);
        
        monkey.add(testLink);
        
        add(billboard);
        
        add(monkey);
        add(cube);
        add(cCube);
        add(torus);
    }
    
    public void update(float deltaTime)
    {
        counter += deltaTime;
        
        billboard.setRotAngle((float)Math.sin(counter / 1000));
        
        testLink.getOffsetB().setRotation(new Quaternion(Vector3.RIGHT, (float)Math.toRadians(counter * 0.2f)));
        
        monkey.getTransform().setPosition((float)Math.sin(counter / 1200), 0, 5, 5);
        monkey.getTransform().setRotation(new Quaternion(Vector3.UP, (float)Math.toRadians(counter * 0.1f)));
    }
}
