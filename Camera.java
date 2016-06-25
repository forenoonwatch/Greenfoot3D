/**
 * Write a description of class Camera here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Camera extends GameObject
{
    private Matrix4x4 viewProjection;
    
    public Camera(Matrix4x4 projection)
    {
        viewProjection = projection;
    }
    
    public Matrix4x4 getViewProjection()
    {
        Matrix4x4 camRot = Matrix4x4.fromQuaternion(getTransform().getTransformedRotation().conjugate());
        Matrix4x4 camPos = Matrix4x4.fromPosition(getTransform().getTransformedPosition().mul(-1));
        
        return viewProjection.mul(camRot.mul(camPos));
    }
}
