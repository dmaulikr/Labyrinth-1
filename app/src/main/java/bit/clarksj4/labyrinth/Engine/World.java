package bit.clarksj4.labyrinth.Engine;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * World object equivalent to a single 'scene' in the game. World object holds reference to all
 * currently instantiated {@link GameObject}s.
 */
public class World extends UIDObject
{
    public static World current() { return current; }
    private static World current = new World();

    private ArrayList<GameObject> recycleBin;
    private LinkedList<GameObject> objects;

    private World()
    {
        objects = new LinkedList<>();
        recycleBin = new ArrayList<>();
    }

    public static void load(World world)
    {
        current.end();
        current = world;
    }

    public void start()
    {
        for (int i = 0; i < objects.size(); i++)
            objects.get(i).start();
    }

    public void update()
    {
        for(GameObject object : objects)
            object.update();
    }

    public void end() { /* World is ending, new world may be loading. */ }

    public void add(GameObject object) { objects.add(object); }

    public void destroy(GameObject toBeDestroyed) { recycleBin.add(toBeDestroyed); }

    public void close()
    {
        objects.clear();
        recycleBin.clear();
    }

    void recycle()
    {
        objects.removeAll(recycleBin);
        recycleBin.clear();
    }

    public GameObject findGameObjectByName(String name)
    {
        for (GameObject object : objects)
        {
            if (object.getName() != null &&
                    object.getName().equals(name))
                return object;
        }

        return null;
    }
}
