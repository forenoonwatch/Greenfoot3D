import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class OBJModel
{
    private ArrayList<Vector4> positions;
    private ArrayList<Vector2> texCoords;
    private ArrayList<Vector4> normals;
    private ArrayList<OBJIndex> indices;
    
    private boolean hasTexCoords;
    private boolean hasNormals;

    public OBJModel(String fileName) throws IOException
    {
        positions = new ArrayList<Vector4>();
        texCoords = new ArrayList<Vector2>();
        normals = new ArrayList<Vector4>();
        indices = new ArrayList<OBJIndex>();
        
        hasTexCoords = false;
        hasNormals = false;

        BufferedReader meshReader = null;

        meshReader = new BufferedReader(new FileReader(fileName));
        String line;

        while((line = meshReader.readLine()) != null)
        {
            String[] tokens = line.split(" ");
            tokens = removeEmptyStrings(tokens);

            if (tokens.length == 0 || tokens[0].equals("#"))
            {
                continue;
            }
            else if (tokens[0].equals("v"))
            {
                positions.add(new Vector4(Float.valueOf(tokens[1]), Float.valueOf(tokens[2]), Float.valueOf(tokens[3])));
            }
            else if (tokens[0].equals("vt"))
            {
                texCoords.add(new Vector2(Float.valueOf(tokens[1]), 1.0f - Float.valueOf(tokens[2])));
            }
            else if (tokens[0].equals("vn"))
            {
                normals.add(new Vector4(Float.valueOf(tokens[1]), Float.valueOf(tokens[2]), Float.valueOf(tokens[3]), 0));
            }
            else if (tokens[0].equals("f"))
            {
                for (int i = 0; i < tokens.length - 3; i++)
                {
                    indices.add(parseOBJIndex(tokens[1]));
                    indices.add(parseOBJIndex(tokens[2 + i]));
                    indices.add(parseOBJIndex(tokens[3 + i]));
                }
            }
        }

        
        meshReader.close();
    }
    
    private static String[] removeEmptyStrings(String[] data)
    {
        ArrayList<String> result = new ArrayList<String>();
        
        for (int i = 0; i < data.length; i++)
        {
            if (!data[i].equals(""))
            {
                result.add(data[i]);
            }
        }
        
        String[] res = new String[result.size()];
        result.toArray(res);
        
        return res;
    }

    public IndexedModel toIndexedModel()
    {
        IndexedModel result = new IndexedModel();
        IndexedModel normalModel = new IndexedModel();
        HashMap<OBJIndex, Integer> resultIndexMap = new HashMap<OBJIndex, Integer>();
        HashMap<Integer, Integer> normalIndexMap = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> indexMap = new HashMap<Integer, Integer>();

        for (int i = 0; i < indices.size(); i++)
        {
            OBJIndex currentIndex = indices.get(i);

            Vector4 currentPosition = positions.get(currentIndex.getVertexIndex());
            Vector2 currentTexCoord;
            Vector4 currentNormal;

            if (hasTexCoords)
            {
                currentTexCoord = texCoords.get(currentIndex.getTexCoordIndex());
            }
            else
            {
                currentTexCoord = new Vector2();
            }

            if (hasNormals)
            {
                currentNormal = normals.get(currentIndex.getNormalIndex());
            }
            else
            {
                currentNormal = new Vector4(0, 0, 0, 0);
            }

            Integer modelVertexIndex = resultIndexMap.get(currentIndex);

            if (modelVertexIndex == null)
            {
                modelVertexIndex = result.getPositions().size();
                resultIndexMap.put(currentIndex, modelVertexIndex);

                result.getPositions().add(currentPosition);
                result.getTexCoords().add(currentTexCoord);
                
                if (hasNormals)
                {
                    result.getNormals().add(currentNormal);
                }
            }

            Integer normalModelIndex = normalIndexMap.get(currentIndex.getVertexIndex());

            if (normalModelIndex == null)
            {
                normalModelIndex = normalModel.getPositions().size();
                normalIndexMap.put(currentIndex.getVertexIndex(), normalModelIndex);

                normalModel.getPositions().add(currentPosition);
                normalModel.getTexCoords().add(currentTexCoord);
                normalModel.getNormals().add(currentNormal);
            }

            result.getIndices().add(modelVertexIndex);
            normalModel.getIndices().add(normalModelIndex);
            indexMap.put(modelVertexIndex, normalModelIndex);
        }

        if (!hasNormals)
        {
            normalModel.calcNormals();

            for (int i = 0; i < result.getPositions().size(); i++)
            {
                result.getNormals().add(normalModel.getNormals().get(indexMap.get(i)));
            }
        }

        return result;
    }

    private OBJIndex parseOBJIndex(String token)
    {
        String[] values = token.split("/");

        OBJIndex result = new OBJIndex();
        result.setVertexIndex(Integer.parseInt(values[0]) - 1);

        if (values.length > 1)
        {
            if (!values[1].isEmpty())
            {
                hasTexCoords = true;
                result.setTexCoordIndex(Integer.parseInt(values[1]) - 1);
            }

            if (values.length > 2)
            {
                hasNormals = true;
                result.setNormalIndex(Integer.parseInt(values[2]) - 1);
            }
        }

        return result;
    }
}
