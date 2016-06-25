
public class Vector2
{
    private final float x, y;
    
    public Vector2()
    {
        x = 0;
        y = 0;
    }
    
    public Vector2(float x, float y)
    {
        this.x = x;
        this.y = y;
    }
    
    public Vector2(Vector2 v2)
    {
        x = v2.getX();
        y = v2.getY();
    }
    
    public float magnitude()
    {
        return (float)Math.sqrt(x*x + y*y);
    }
    
    public float dot(Vector2 v2)
    {
        return x * v2.getX() + y * v2.getY();
    }
    
    public float cross(Vector2 v2)
    {
        return x * v2.getY() - y * v2.getX();
    }
    
    public Vector2 unit()
    {
        float mag = magnitude();
        
        if (mag == 0)
        {
            return this;
        }
            
        return new Vector2(x / mag, y / mag);
    }
    
    public Vector2 rotate(float angle)
    {
        double cosA = Math.cos(angle);
        double sinA = Math.sin(angle);
        
        return new Vector2((float)(x * cosA - y * sinA), (float)(x * sinA + y * cosA));
    }
    
    public Vector2 dirTo(Vector2 pt)
    {
        return pt.sub(this).unit();
    }
    
    public Vector2 lerp(Vector2 to, float rate)
    {
        return to.sub(this).mul(rate).add(this);
    }
    
    public float max()
    {
        return Math.max(x, y);
    }
    
    public float min()
    {
        return Math.min(x, y);
    }
    
    public Vector2 abs()
    {
        return new Vector2(Math.abs(x), Math.abs(y));
    }
    
    public Vector2 add(Vector2 v2)
    {
        return new Vector2(x + v2.getX(), y + v2.getY());
    }
    
    public Vector2 add(float n)
    {
        return new Vector2(x + n, y + n);
    }
    
    public Vector2 sub(Vector2 v2)
    {
        return new Vector2(x - v2.getX(), y - v2.getY());
    }
    
    public Vector2 sub(float n)
    {
        return new Vector2(x - n, y - n);
    }
    
    public Vector2 mul(Vector2 v2)
    {
        return new Vector2(x * v2.getX(), y * v2.getY());
    }
    
    public Vector2 mul(float n)
    {
        return new Vector2(x * n, y * n);
    }
    
    public Vector2 div(Vector2 v2)
    {
        return new Vector2(x / v2.getX(), y / v2.getY());
    }
    
    public Vector2 div(float n)
    {
        return new Vector2(x / n, y / n);
    }
    
    public boolean equals(Vector2 v2)
    {
        return x == v2.getX() && y == v2.getY();
    }
    
    public float getX()
    {
        return x;
    }
    
    public float getY()
    {
        return y;
    }
    
    public float get(int index)
    {
        switch (index)
        {
            case 0:
                return x;
            case 1:
                return y;
            default:
                throw new IndexOutOfBoundsException();
        }
    }
    
    public String toString()
    {
        return String.format("(%s, %s)", x, y);
    }
}