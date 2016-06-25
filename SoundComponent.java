import greenfoot.GreenfootSound;

/**
 * Write a description of class SoundComponent here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SoundComponent extends GameComponent 
{
    private GreenfootSound sound;
    
    private float baseVolume;
    private int volume;
    private float range;
    
    public SoundComponent(String fileName)
    {
        this(new GreenfootSound(fileName));
    }
    
    public SoundComponent(GreenfootSound sound)
    {
        this.sound = sound;
        
        baseVolume = 1;
        volume = 1;
        range = 10;
    }
    
    public void update(World3D world, float delta)
    {
        volume = calcSoundVolume(world);
    }
    
    public void play()
    {
        if (sound.isPlaying() || range == 0)
        {
            return;
        }
        
        sound.setVolume(volume);
        sound.play();
    }
    
    public void playLoop()
    {
        if (sound.isPlaying() || range == 0)
        {
            return;
        }
        
        sound.setVolume(volume);
        sound.playLoop();
    }
    
    public void pause()
    {
        sound.pause();
    }
    
    public void stop()
    {
        sound.stop();
    }
    
    public boolean isPlaying()
    {
        return sound.isPlaying();
    }
    
    private int calcSoundVolume(World3D world)
    {
        float dist = getTransform().getTransformedPosition().sub(world.getCamera().getTransform().getTransformedPosition()).magnitude();
        float percent = dist / range;
        
        int out = (int)(percent * 100);
        
        if (out < 0)
        {
            out = 0;
        }
        
        if (out > 100)
        {
            out = 100;
        }
        
        return out;
    }
    
    public GreenfootSound getSound()
    {
        return sound;
    }
    
    public void setSound(GreenfootSound sound)
    {
        this.sound = sound;
    }
    
    public float getBaseVolume()
    {
        return baseVolume;
    }
    
    public void setBaseVolume(float vol)
    {
        baseVolume = vol;
    }
    
    public float getRange()
    {
        return range;
    }
    
    public void setRange(float range)
    {
        this.range = range;
    }
}
