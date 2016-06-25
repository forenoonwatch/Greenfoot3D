/**
 * Write a description of class JointLink here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class JointLink extends GameComponent
{
    private Transform offsA, offsB;
    private GameObject linkObj;
    
    public JointLink()
    {
        offsA = new Transform();
        offsB = new Transform();
    }
    
    public JointLink(Transform offsA, Transform offsB)
    {
        this.offsA = offsA;
        this.offsB = offsB;
    }
    
    public Transform getOffsetA()
    {
        return offsA;
    }
    
    public Transform getOffsetB()
    {
        return offsB;
    }
    
    public GameObject getLinkObj()
    {
        return linkObj;
    }
    
    public void setLinkObj(GameObject obj)
    {
        linkObj = obj;
    }
    
    public void update(World3D world, float delta)
    {
        if (linkObj == null)
        {
            return;
        }
        
        linkObj.getTransform().setPosition(getTransform().getTransformation().transform(offsA.getPosition()));
        linkObj.getTransform().setRotation(offsB.getRotation());
        linkObj.getTransform().rotate(offsA.getRotation());
        linkObj.getTransform().rotate(getTransform().getRotation());
        linkObj.getTransform().setPosition(linkObj.getTransform().getTransformation().transform(offsB.getPosition()));
    }
}
