package xyz.funky493.little_big_hordes_of_mine.datapack;

import xyz.funky493.little_big_hordes_of_mine.horde.Horde;
import xyz.funky493.little_big_hordes_of_mine.horde.Wave;

import java.util.ArrayList;

// Contains all data loaded from any datapacks
public class LoadedData {
    public ArrayList<Wave> waves;
    public ArrayList<Horde> hordes;
    public LoadedData() {
        waves = new ArrayList<>();
        hordes = new ArrayList<>();
    }
}
