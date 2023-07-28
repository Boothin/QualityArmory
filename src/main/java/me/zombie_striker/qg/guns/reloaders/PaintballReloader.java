package me.zombie_striker.qg.guns.reloaders;

import me.zombie_striker.qg.QAMain;
import me.zombie_striker.qg.guns.Gun;
import me.zombie_striker.qg.guns.utils.WeaponSounds;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PaintballReloader implements ReloadingHandler{


    public PaintballReloader() {
        ReloadingManager.add(this);
    }

    List<UUID> timeR = new ArrayList<>();
    @Override
    public boolean isReloading(Player player) {
        return timeR.contains(player.getUniqueId());
    }

    @Override
    public double reload(Player player, Gun g, int amountReloading) {
        QAMain.DEBUG("Reloading Paintball");
        timeR.add(player.getUniqueId());
        QAMain.DEBUG("Playing 1st sound: " + WeaponSounds.RELOAD_CLICK.getSoundName());
        player.getWorld().playSound(player.getLocation(), WeaponSounds.RELOAD_CLICK.getSoundName(), 1, 1f);
        new BukkitRunnable() {
            @Override
            public void run() {
                QAMain.DEBUG("Playing 2nd sound: " + g.getReloadingSound());
                player.getWorld().playSound(player.getLocation(), g.getReloadingSound(), 1, 1f);
                timeR.remove(player.getUniqueId());
            }
        }.runTaskLater(QAMain.getInstance(), 4);
        return g.getReloadTime();
    }

    @Override
    public String getName() {
        return ReloadingManager.PAINTBALL_RELOAD;
    }

    @Override
    public String getDefaultReloadingSound() {
        return WeaponSounds.RELOAD_PAINTBALL.getSoundName();
    }
}
