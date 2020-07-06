package creatures;
import huglife.*;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

/**
 * An implementation of a a fierce blue-colored predator
 * that enjoys nothing more than snacking on Plips.
 *
 * Author: Hedy He
 */
public class Clorus extends Creature {
    /**
     * red color.
     */
    private int r = 34;
    /**
     * green color.
     */
    private int g = 0;
    /**
     * blue color.
     */
    private int b = 231;

    /**
     * fraction of energy to retain when replicating.
     */
    private double repEnergyRetained = 0.5;
    /**
     * fraction of energy to bestow upon offspring.
     */
    private double repEnergyGiven = 0.5;

    /**
     * probability of taking a move when ample space available.
     */
    private double moveProbability = 0.5;

    /**
     * creates Clorus with energy equal to E.
     */
    public Clorus(double e) {
        super("clorus");
        energy = e;
    }

    /**
     * creates a Clorus with energy equal to 1.
     */
    public Clorus() {
        this(1);
    }

    /**
     * Color doesn't change
     */
    public Color color() {
        return color(r, g, b);
    }

    /**
     * Clorus are fierce.
     * If a Clorus attacks another creature,
     * it should gain that creature’s energy.
     */
    public void attack(Creature c) {
        energy = energy + c.energy();
    }

    /**
     * Clorus should lose 0.03 units of energy when moving.
     */
    public void move() {
//        energy = Math.max(energy - 0.03,0);
        energy = energy - 0.03;
    }


    /**
     * Clorus loss 0.01 energy units of energy when staying .
     */
    public void stay() {
//        energy = Math.max(energy - 0.01, 0);
        energy = energy - 0.01;
    }

    /**
     * Clorus and their offspring each get 50% of the energy, with none
     * lost to the process. Now that's efficiency! Returns a baby
     * Clorus.
     */
    public Clorus replicate() {
        double babyEnergy = energy * repEnergyGiven;
        energy = energy * repEnergyRetained;
        return new Clorus(babyEnergy);
    }

    /**
     * Clorus take exactly the following actions based on NEIGHBORS:
     * 1. If there are no empty squares, the Clorus will STAY
     *     (even if there are Plips nearby they could attack since plip squares do not count as empty squares).
     * 2. Otherwise, if any Plips are seen, the Clorus will ATTACK one of them randomly.
     * 3. Otherwise, if the Clorus has energy greater than or equal to one,
     *    it will REPLICATE to a random empty square.
     * 4. Otherwise, the Clorus will MOVE to a random empty square.
     * <p>
     * Returns an object of type Action. See Action.java for the
     * scoop on how Actions work. See SampleCreature.chooseAction()
     * for an example to follow.
     */
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> plipNeighbors = new ArrayDeque<>();

        // Rule 1
        for (Direction key : neighbors.keySet()) {
            if(neighbors.get(key).name().equals("plip")){
                plipNeighbors.addFirst(key);
            }else if(neighbors.get(key).name().equals("empty")){
                emptyNeighbors.addFirst(key);
            }
        }

        if (plipNeighbors.isEmpty() && emptyNeighbors.isEmpty()){
            return new Action(Action.ActionType.STAY);
        }

        // Rule 2
        if (! plipNeighbors.isEmpty()) {
            Direction attackDirection = HugLifeUtils.randomEntry(plipNeighbors);
            return new Action(Action.ActionType.ATTACK, attackDirection);
        }

        // Rule 3
        Direction emptyDirection = HugLifeUtils.randomEntry(emptyNeighbors);
        if (! emptyNeighbors.isEmpty() && energy >= 1) {
            return new Action(Action.ActionType.REPLICATE, emptyDirection);
        }

        // Rule 4
        return new Action(Action.ActionType.MOVE, emptyDirection);
    }
}
