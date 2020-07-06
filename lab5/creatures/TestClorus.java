package creatures;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.awt.Color;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.Impassible;
import huglife.Empty;

public class TestClorus {
    @Test
    public void testBasics() {
        Clorus p = new Clorus(2);
        assertEquals(2, p.energy(), 0.01);
        assertEquals(new Color(34, 0, 231), p.color());
        p.move();
        assertEquals(1.97, p.energy(), 0.01);
        p.move();
        assertEquals(1.94, p.energy(), 0.01);
        p.stay();
        assertEquals(1.93, p.energy(), 0.01);
        p.stay();
        assertEquals(1.92, p.energy(), 0.01);
    }

    @Test
    public void testReplicate() {
        Clorus p = new Clorus(1.2);
        double expectedEnergy = p.energy() * 0.5;
        Clorus replicatedP = p.replicate();
        double actualEnergyP = p.energy();
        double actualEnergyReplicatedP = replicatedP.energy();

        assertEquals(expectedEnergy, actualEnergyP,0.01);
        assertEquals(expectedEnergy, actualEnergyReplicatedP,0.01);
        assertNotEquals(p, replicatedP);
    }

    @Test
    public void testAttack() {
        Clorus p = new Clorus(1.2);
        Plip plip = new Plip(1);
        double expectedEnergy = p.energy() + plip.energy();
        p.attack(plip);
        double actualEnergy = p.energy();

        assertEquals(expectedEnergy, actualEnergy, 0.01);
    }

    @Test
    public void testChoose() {
        // No empty adjacent spaces; stay.
        Clorus p = new Clorus(1.2);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        Action actual = p.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);

        // see plip, attack it
        p = new Clorus(1.2);
        HashMap<Direction, Occupant> bottomPlip = new HashMap<Direction, Occupant>();
        bottomPlip.put(Direction.TOP, new Empty());
        bottomPlip.put(Direction.BOTTOM, new Plip(1));
        bottomPlip.put(Direction.LEFT, new Impassible());
        bottomPlip.put(Direction.RIGHT, new Impassible());

        actual = p.chooseAction(bottomPlip);
        expected = new Action(Action.ActionType.ATTACK, Direction.BOTTOM);

        assertEquals(expected, actual);


        // Energy >= 1; replicate towards an empty space.
        p = new Clorus(1.2);
        HashMap<Direction, Occupant> topEmpty = new HashMap<Direction, Occupant>();
        topEmpty.put(Direction.TOP, new Empty());
        topEmpty.put(Direction.BOTTOM, new Impassible());
        topEmpty.put(Direction.LEFT, new Impassible());
        topEmpty.put(Direction.RIGHT, new Impassible());

        actual = p.chooseAction(topEmpty);
        expected = new Action(Action.ActionType.REPLICATE, Direction.TOP);

        assertEquals(expected, actual);

        // Energy < 1; move.
        p = new Clorus(.99);

        actual = p.chooseAction(topEmpty);
        expected = new Action(Action.ActionType.MOVE, Direction.TOP);

        assertEquals(expected, actual);


        // Energy >= 1; replicate towards an empty space.
        p = new Clorus(1.2);
        HashMap<Direction, Occupant> allEmpty = new HashMap<Direction, Occupant>();
        allEmpty.put(Direction.TOP, new Empty());
        allEmpty.put(Direction.BOTTOM, new Empty());
        allEmpty.put(Direction.LEFT, new Empty());
        allEmpty.put(Direction.RIGHT, new Empty());

        actual = p.chooseAction(allEmpty);
        Action unexpected = new Action(Action.ActionType.STAY);

        assertNotEquals(unexpected, actual);
    }
}
