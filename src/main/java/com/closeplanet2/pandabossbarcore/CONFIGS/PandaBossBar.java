package com.closeplanet2.pandabossbarcore.CONFIGS;

import com.closeplanet2.pandabossbarcore.PandaBossBarCore;
import com.closeplanet2.pandaconfigcore.INTERFACE.IgnoreSave;
import com.closeplanet2.pandaconfigcore.INTERFACE.PandaConfig;
import com.closeplanet2.pandaspigotcore.PLAYER.PlayerAPI;
import com.closeplanet2.pandaspigotcore.PLAYER.TOGGLE_ACTIONS;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class PandaBossBar implements PandaConfig {

    @Override
    public String CONFIG_ID() { return barID; }

    @Override
    public Class<?> CLASS_TYPE() { return PandaBossBar.class; }

    @Override
    public Class<?> MAIN_CLASS() { return PandaBossBarCore.class; }

    public String barID;
    public HashMap<Integer, BarData> barData = new HashMap<>();
    public BossBar bossBar;
    public Integer highestStage = -1;
    @IgnoreSave
    public Integer currentCount = -1;

    public PandaBossBar(){}
    public PandaBossBar(String barID){
        this.barID = barID;
        LOAD();
    }
    public PandaBossBar(String barID, HashMap<Integer, BarData> barData, Integer highestStage, List<Player> players, BarFlag... barFlags){
        this.barID = barID;
        this.barData = barData;
        this.highestStage = highestStage;
        this.bossBar = Bukkit.createBossBar(
                ChatColor.translateAlternateColorCodes('&',
                        barData.get(0).barTitle), barData.get(0).barColor, barData.get(0).barStyle, barFlags
        );
        for(var player : players) AddPlayer(player);

        try { SAVE(); }
        catch (Exception exception) { exception.printStackTrace(); }

        PandaBossBarCore.BossBar_Register.put(barID, this);
    }

    public void LoadBar(List<Player> players, BarFlag... barFlags){
        this.bossBar = Bukkit.createBossBar(
                ChatColor.translateAlternateColorCodes('&',
                        barData.get(0).barTitle), barData.get(0).barColor, barData.get(0).barStyle, barFlags
        );
        for(var player : players) AddPlayer(player);
    }

    public void AddPlayer(Player... players){
        for(var player : players){
            if(!PlayerAPI.GET_TOGGLE_STAT(player, TOGGLE_ACTIONS.PlayerGetBossBars)) continue;
            if(PlayerLinked(player)) continue;
            bossBar.addPlayer(player);
        }
    }

    public boolean PlayerLinked(Player player){ return bossBar.getPlayers().contains(player); }
    public void RemovePlayer(){ for(var player : bossBar.getPlayers()) RemovePlayer(player); }
    public void RemovePlayer(Player... players){ for(var player : players) bossBar.removePlayer(player); }

    public void TickBossBar(){
        currentCount += 1;
        currentCount = currentCount > highestStage ? 0 : currentCount;
        if(barData.containsKey(currentCount)) barData.get(currentCount).DisplayData(bossBar);
    }

    public static Builder builder() { return new Builder(); }
    public static final class Builder {
        private String barID = "";
        private HashMap<Integer, BarData> barData = new HashMap<>();
        private List<Player> playersToAdd = new ArrayList<>();
        private Integer highestStage = -1;

        private Builder(){}

        public Builder barID(String barID){
            this.barID = barID;
            return this;
        }

        public Builder addStage(Integer firstStage, Integer amount, BarData barData){
            for(var i = 0; i < amount; i++){
                var index = i + firstStage;
                if(index > highestStage) highestStage = index;
                this.barData.put(index, BarData.CLONE_WITH_NEW_KEY(barData, index));
            }
            return this;
        }

        public Builder addPlayers(Player... players){
            Collections.addAll(playersToAdd, players);
            return this;
        }

        public PandaBossBar build(boolean override_stored, BarFlag... barFlags){
            if(override_stored) return new PandaBossBar(barID, barData, highestStage, playersToAdd, barFlags);
            var stored_bar = PandaBossBarCore.BossBar_Register.get(barID);
            if(stored_bar != null){
                stored_bar.LoadBar(playersToAdd, barFlags);
                return stored_bar;
            }
            return new PandaBossBar(barID, barData, highestStage, playersToAdd, barFlags);
        }
    }

}
