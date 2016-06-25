import java.util.ArrayList;

public class IndexedModel
{
    private ArrayList<Vector4> positions;
    private ArrayList<Vector2> texCoords;
    private ArrayList<Vector4> normals;
    private ArrayList<Integer>  indices;

    public IndexedModel()
    {
        positions = new ArrayList<Vector4>();
        texCoords = new ArrayList<Vector2>();
        normals = new ArrayList<Vector4>();
        indices = new ArrayList<Integer>();
    }

    public void calcNormals()
    {
        for (int i = 0; i < indices.size(); i += 3)
        {
            int i0 = indices.get(i);
            int i1 = indices.get(i + 1);
            int i2 = indices.get(i + 2);

            Vector4 v1 = positions.get(i1).sub(positions.get(i0));
            Vector4 v2 = positions.get(i2).sub(positions.get(i0));

            Vector4 normal = v1.cross(v2).unit();

            normals.set(i0, normals.get(i0).add(normal));
            normals.set(i1, normals.get(i1).add(normal));
            normals.set(i2, normals.get(i2).add(normal));
        }

        for(int i = 0; i < normals.size(); i++)
        {
            normals.set(i, normals.get(i).unit());
        }
    }

    public ArrayList<Vector4> getPositions()
    {
        return positions;
    }
    
    public ArrayList<Vector2> getTexCoords()
    {
        return texCoords;
    }
    
    public ArrayList<Vector4> getNormals()
    {
        return normals;
    }
    
    public ArrayList<Integer>  getIndices()
    {
        return indices;
    }
}
