/*
 * Copyright (C) 2017 The MoonLake Authors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


package com.minecraft.moonlake.nofirstfalldamage;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class NoFirstFallDamagePlugin extends JavaPlugin implements Listener {

    private List<String> eventList;

    public NoFirstFallDamagePlugin() {
    }

    @Override
    public void onEnable() {
        this.eventList = new ArrayList<>();
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getLogger().info("没有第一次摔落伤害 NoFirstFallDamage 插件 v" + getDescription().getVersion() + " 成功加载.");
    }

    @Override
    public void onDisable() {
        this.eventList.clear();
        this.eventList = null;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamage(EntityDamageEvent event) {
        // 处理玩家摔落伤害事件
        Entity entity = event.getEntity();

        if(!(entity instanceof Player) || event.getCause() != EntityDamageEvent.DamageCause.FALL)
            return;
        // 只有受伤的实体为玩家并且原因是摔落伤害则处理
        String name = entity.getName();

        if(!eventList.contains(name)) {
            // 没有存在列表则视为第一次摔落伤害则阻止并 put
            eventList.add(name);
            event.setCancelled(true);
        }
    }
}
