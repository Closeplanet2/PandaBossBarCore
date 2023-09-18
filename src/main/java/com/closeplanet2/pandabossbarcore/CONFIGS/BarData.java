package com.closeplanet2.pandabossbarcore.CONFIGS;

import com.closeplanet2.pandaconfigcore.INTERFACE.PandaClass;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

public class BarData implements PandaClass {

    @Override
    public String CLASS_ID() { return key.toString(); }

    @Override
    public Class<?> CLASS_TYPE() { return BarData.class; }

    public Integer key = -1;
    public String barTitle = "";
    public BarColor barColor = BarColor.RED;
    public BarStyle barStyle = BarStyle.SOLID;
    public double barProgress = 0;

    public static BarData CLONE_WITH_NEW_KEY(BarData old, Integer newKey){
        var bardata = new BarData(old.barTitle, old.barColor, old.barStyle, old.barProgress);
        bardata.key = newKey;
        return bardata;
    }

    public BarData(){}
    public BarData(String barTitle, BarColor barColor, BarStyle barStyle, double barProgress){
        this.barTitle = barTitle;
        this.barColor = barColor;
        this.barStyle = barStyle;
        this.barProgress = Math.max(0, Math.min(1, barProgress));
    }

    public void DisplayData(BossBar bossBar){
        if(bossBar == null) return;
        bossBar.setTitle(ChatColor.translateAlternateColorCodes('&', barTitle));
        bossBar.setColor(barColor);
        bossBar.setStyle(barStyle);
        bossBar.setProgress(barProgress);
    }


}
