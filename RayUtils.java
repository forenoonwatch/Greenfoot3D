import java.util.ArrayList;

public class RayUtils 
{
    private static final float SMALL_NUMBER = 0.00000001f;

    public static Vector3 rayMeshIntersection(Ray ray, MeshObject meshObject)
    {
        Vertex[] vertices = meshObject.getTransformedVertices();
        Vector3 result = null;

        for (int i = 0; i < vertices.length; i += 3)
        {
            Vector3[] triangle = new Vector3[3];
            triangle[0] = vertices[i].getXYZ();
            triangle[1] = vertices[i + 1].getXYZ();
            triangle[2] = vertices[i + 2].getXYZ();
            result = rayTriangleIntersection(ray, triangle);

            if (result != null) 
            {
                return result;  
            }
        }

        return result;
    }

    public static Vector3 rayIntersectsBoundingBox(Ray ray, BoundingBox box) 
    {
        Vector3 rDir = ray.getDirection();
        Vector3 invDir = new Vector3(
                1/rDir.getX(),
                1/rDir.getY(),
                1/rDir.getZ());

        Vector3 corners[] = getBoundingBoxCorners(box);
        Vector3 ftrCorner = corners[1];
        Vector3 bblCorner = corners[0];

        float t1 = (bblCorner.getX() - ray.getOrigin().getX()) * invDir.getX();
        float t2 = (ftrCorner.getX() - ray.getOrigin().getX()) * invDir.getX();
        float t3 = (bblCorner.getY() - ray.getOrigin().getY()) * invDir.getY();
        float t4 = (ftrCorner.getY() - ray.getOrigin().getY()) * invDir.getY();
        float t5 = (bblCorner.getZ() - ray.getOrigin().getZ()) * invDir.getZ();
        float t6 = (ftrCorner.getZ() - ray.getOrigin().getZ()) * invDir.getZ();

        float tmin = Math.max(Math.max(Math.min(t1, t2), Math.min(t3, t4)), Math.min(t5, t6));
        float tmax = Math.min(Math.min(Math.max(t1, t2), Math.max(t3, t4)), Math.max(t5, t6));

        if (tmax < 0.0f || tmin > tmax)
        {
            return null;
        }

        return ray.getOrigin().add(ray.getDirection().mul(tmin));
    }

    public static Vector3[] getBoundingBoxCorners(BoundingBox box) 
    {
        Vector4 position = box.getParent().getTransform().getPosition();

        Vector3[] corners = new Vector3[2];
        corners[0] = new Vector3(position.getX() - box.getSize().getX(), position.getY() - box.getSize().getY(), position.getZ() - box.getSize().getZ());
        corners[1] = new Vector3(position.getX() + box.getSize().getX(), position.getY() + box.getSize().getY(), position.getZ() + box.getSize().getZ());

        return corners;
    }

    public static Vector3 rayTriangleIntersection(Ray ray, Vector3[] triangle)
    {
        Vector3 result;
        Vector3 u, v, norm;
        Vector3 dir, w0, w;
        float r, a, b;

        u = triangle[1].sub(triangle[0]);
        v = triangle[2].sub(triangle[0]);
        norm = u.cross(v);

        if (norm.getX() == 0.0f && norm.getY() == 0.0f && norm.getZ() == 0.0f)
        {
            return null;
        }

        w0 = ray.getOrigin().sub(triangle[0]);
        a = -norm.dot(w0);
        b = norm.dot(ray.getDirection());

        if (Math.abs(b) < SMALL_NUMBER)
        {
            if (a == 0.0f) 
            {
                return null;
            }
            else
            {
                return null;
            }
        }

        r = a / b;

        if (r < 0.0)
        {
            return null;
        }

        result = ray.getOrigin().add(ray.getDirection().mul(r));

        float uu, uv, vv, wu, wv, D;
        uu = u.dot(u);
        uv = u.dot(v);
        vv = v.dot(v);
        w = result.sub(triangle[0]);
        wu = w.dot(u);
        wv = w.dot(v);
        D = uv * uv - uu * vv;

        float s, t;
        s = (uv * wv - vv * wu) / D;

        if (s < 0.0f || s > 1.0f)
        {
            return null;
        }

        t = (uv * wu - uu * wv) / D;

        if (t < 0.0f || (s + t) > 1.0f)
        {
            return null;
        }

        return result;
    }
}
