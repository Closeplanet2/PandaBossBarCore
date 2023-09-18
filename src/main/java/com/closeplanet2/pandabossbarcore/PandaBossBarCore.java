package com.closeplanet2.pandabossbarcore;

import com.closeplanet2.pandabossbarcore.CONFIGS.BossBarSettings;
import com.closeplanet2.pandabossbarcore.CONFIGS.PandaBossBar;
import com.closeplanet2.pandaconfigcore.API.ConfigAPI;
import com.closeplanet2.pandaspigotcore.CONSOLE.ConsoleAPI;
import com.closeplanet2.pandaspigotcore.JAVA_CLASS.JavaClassAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class PandaBossBarCore extends JavaPlugin {
    public static HashMap<String, PandaBossBar> BossBar_Register = new HashMap<>();
    public static BossBarSettings bossBarSettings;

    public static void REMOVE_PLAYER(Player... player){ for(var uuid : BossBar_Register.keySet()) BossBar_Register.get(uuid).RemovePlayer(player); }
    public static void REMOVE_PLAYER(){ for(var uuid : BossBar_Register.keySet()) BossBar_Register.get(uuid).RemovePlayer(); }

    @Override
    public void onEnable() {
        bossBarSettings = new BossBarSettings();
        RegisterClasses();
        LoadAllBossBars();
    }

    private void RegisterClasses(){
        try { JavaClassAPI.Register(this, "com.closeplanet2.pandabossbarcore"); }
        catch (Exception exception) { exception.printStackTrace(); }
    }

    private void LoadAllBossBars(){
        for(var className : ConfigAPI.RETURN_ALL_CONFIG_NAMES(new PandaBossBar())){
            var pandaBossBar = new PandaBossBar(className);
            BossBar_Register.put(pandaBossBar.barID, pandaBossBar);
        }
    }
}
