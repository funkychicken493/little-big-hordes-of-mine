package xyz.funky493.little_big_hordes_of_mine.horde;

import net.minecraft.util.Identifier;

import java.util.ArrayList;

/**
 * A wave is a table of participants that will be chosen at random.
 * Each participant has a weight, which is the chance of it being chosen, the higher the weight, the higher the chance.
 */
public class Wave {
    private Identifier id;
    private ArrayList<Participant> participantsTable;
    private float weight;
    private int selectionAmount;
    private int minDays;
    private int maxDays;
}
