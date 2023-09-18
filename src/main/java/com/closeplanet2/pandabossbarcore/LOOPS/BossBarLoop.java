package com.closeplanet2.pandabossbarcore.LOOPS;

import com.closeplanet2.pandabossbarcore.CONFIGS.PandaBossBar;
import com.closeplanet2.pandabossbarcore.PandaBossBarCore;
import com.closeplanet2.pandaspigotcore.LOOPS.LoopValues;
import com.closeplanet2.pandaspigotcore.LOOPS.PandaLoop;
import com.closeplanet2.pandaspigotcore.PLAYER.MOVEMENT.MovementAPI;
import com.closeplanet2.pandaspigotcore.PLAYER.MOVEMENT.TeleportObject;
import com.closeplanet2.pandaspigotcore.PLAYER.VanishAPI;
import com.closeplanet2.pandaspigotcore.PandaSpigotCore;
import org.bukkit.Bukkit;

@PandaLoop
public class BossBarLoop implements LoopValues {
    @Override
    public String ReturnID() { return "BossBarLoop"; }

    @Override
    public int RegisterLoop() {
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(PandaSpigotCore.INSTANCE, new Runnable() {
            @Override
            public void run() {
                for(var barId : PandaBossBarCore.BossBar_Register.keySet())
                    PandaBossBarCore.BossBar_Register.get(barId).TickBossBar();
            }
        }, 0L, PandaBossBarCore.bossBarSettings.refreshRate);
    }
}
