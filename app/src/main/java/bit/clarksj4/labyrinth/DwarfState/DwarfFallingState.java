package bit.clarksj4.labyrinth.DwarfState;

import bit.clarksj4.labyrinth.Engine.AnimationController;
import bit.clarksj4.labyrinth.Labyrinth.Dwarf;
import bit.clarksj4.labyrinth.Engine.Time;
import bit.clarksj4.labyrinth.Engine.Vector;

/**
 * Created by Stephen on 27/05/2016.
 */
public class DwarfFallingState extends DwarfState
{
    private static final float DURATION = 0.25f;

    private Vector startingScale;
    private Vector startingPosition;
    private Vector destination;
    private float time;

    public DwarfFallingState(Dwarf dwarf, Vector destination)
    {
        super(dwarf);

        startingScale = dwarf.getTransform().getScale();
        startingPosition = dwarf.getTransform().getPosition();
        this.destination = destination;

        AnimationController animController = dwarf.getGameObject().getComponent(AnimationController.class);
        animController.playAnimation("Hurt");
    }

    @Override
    public void update()
    {
        super.update();

        time += Time.getInstance().getDeltaTime();
        float t = time / DURATION;

        Vector scale = Vector.lerp(startingScale, Vector.zero(), t);
        dwarf.getTransform().setLocalScale(scale);

        Vector position = Vector.lerp(startingPosition, destination, t);
        dwarf.getTransform().setPosition(position);

        if (time > DURATION)
            dwarf.reset();
    }

    @Override
    public void stateClosing() { /* Nothing - falling state does not listen to anything */ }
}
