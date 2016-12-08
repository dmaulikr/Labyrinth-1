package bit.clarksj4.labyrinth.Engine;

// TODO: on game start need to: make timer, init context, display

import android.content.Context;
import android.view.SurfaceView;

/**
 * Game base class. Descend from this class to make a game object for each platform.
 */
public class Game
{
    /** The default unit size in the game */
    public static final int UNIT = 32;

    /** How frequently the game updates in milliseconds */
    private static final long UPDATE_FREQUENCY = 30;

    private World world;
    private GameOverListener gameOverListener;
    private boolean isPaused;
    private GameContext context;

    public static Game Android(Context context, SurfaceView view)
    {
        Game game = new Game(new AndroidGameContext(context));

        Graphics.getInstance().addDisplay(new Display(view, context.getResources().getDisplayMetrics()));
        AndroidAssetInstaller.install(context);

        return game;
    }

    /**
     * A new game
     */
    private Game(GameContext context)
    {
        this.context = context;
        Assets.init(context);

        Time.getInstance().addTickListener(new GameCycle());
        world = new World();
    }

    /**
     * Populates the world in the manner described by the given WorldLoader object
     * @param loader An object that describes what to load into the world
     */
    public void loadWorld(WorldLoader loader) { loader.load(world); }

    public void accelerometerInput(float[] values) { Input.getInstance().setAccelerometerInput(values); }


    /**
     * Starts the game
     */
    public void start()
    {
        world.start();
        Time.getInstance().start(UPDATE_FREQUENCY);
    }

    /**
     * Stops the game
     */
    public void stop()
    {
        Time.getInstance().stop();

        Assets.commit();
    }

    /**
     * Gets whether the game is currently paused
     * @return Whether the game is currently paused
     */
    public boolean isPaused() { return isPaused; }

    /**
     * Gets whether the game has started
     * @return Whether the game has started
     */
    public boolean isStarted() { return Time.getInstance().isRunning(); }

    /**
     * Sets whether the game is currently paused or not
     * @param isPaused Whether the game is currently paused
     */
    public void setPaused(boolean isPaused)
    {
        // If paused state is changing
        if (this.isPaused != isPaused)
        {
            // Pause or resume timer
            if (isPaused) Time.getInstance().pause();
            else Time.getInstance().resume();

            // Remember current state
            this.isPaused = isPaused;
        }
    }

    public GameContext getContext()
    {
        return context;
    }

    /**
     * Interface for listening for when the game has ended
     */
    interface GameOverListener
    {
        /**
         * Called when the game has ended.
         */
        void gameOver();
    }

    private class GameCycle implements Time.OnTickListener
    {
        @Override
        public void tick()
        {
            world.update();

            Physics.getInstance().update();
            Graphics.getInstance().draw();

            world.recycle();    // Recycle all destroyed objects now that update is complete
        }
    }
}
