package com.closeplanet2.pandabossbarcore.CONFIGS;

import com.closeplanet2.pandabossbarcore.PandaBossBarCore;
import com.closeplanet2.pandaconfigcore.INTERFACE.PandaConfig;

public class BossBarSettings implements PandaConfig {
    @Override
    public String CONFIG_ID() { return "BossBarSettings"; }

    @Override
    public Class<?> CLASS_TYPE() { return null; }

    @Override
    public Class<?> MAIN_CLASS() { return PandaBossBarCore.class; }

    public long refreshRate = 10;

    public BossBarSettings(){
        try { LOAD(); }
        catch (Exception exception) { exception.printStackTrace(); }
    }
}
