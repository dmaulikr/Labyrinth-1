package bit.clarksj4.labyrinth.Engine;

/**
 * Base class for objects responsible for populating the world with game objects
 */
public abstract class WorldLoader
{
    protected Game game;

    /**
     * A new loader object that will load objects to the given game
     * @param game The game that this loader will load objects to
     */
    public WorldLoader(Game game) { this.game = game; }

    /**
     * Loads objects to the given world
     * @param world The world this laoder will load objects to
     */
    public abstract void load(World world);
}
