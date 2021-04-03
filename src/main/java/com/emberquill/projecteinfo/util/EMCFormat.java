package com.emberquill.projecteinfo.util;

import net.minecraft.client.gui.GuiScreen;

import java.text.StringCharacterIterator;

public class EMCFormat {

    public static String formatEMC(long emc) {
        //TODO: Add ability to press Shift to show full EMC value
        if (emc < 999_950) {
            return String.format("%d", emc);
        }
        StringCharacterIterator ci = new StringCharacterIterator("kMGTPE");
        while (emc >= 999_950) {
            emc /= 1000;
            ci.next();
        }
        return String.format("%.2f %c", emc / 1000.0, ci.current());
    }
}
