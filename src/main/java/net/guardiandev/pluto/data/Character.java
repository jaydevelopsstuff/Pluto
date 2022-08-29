package net.guardiandev.pluto.data;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.guardiandev.pluto.util.TColor;

@Data
public class Character {
    private int skinVariant;
    private int hair;
    private String name;
    private int hairDye;
    private byte hideVisuals;
    private byte hideVisuals2;
    private byte hideMisc;
    private TColor hairColor;
    private TColor skinColor;
    private TColor eyeColor;
    private TColor shirtColor;
    private TColor undershirtColor;
    private TColor pantsColor;
    private TColor shoeColor;
    private Difficulty difficulty;
    private boolean extraAccessory;
    private byte torchFlags;

    @RequiredArgsConstructor
    public enum Difficulty {
        Journey((byte)0b00001000),
        Softcore((byte)0b00000001),
        Mediumcore((byte)0b00000010),
        Hardcore((byte)0b00000100);

        public final byte bit;
    }
}
