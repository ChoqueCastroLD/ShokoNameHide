package me.shokocc.plugins.namehide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import net.minecraft.server.v1_16_R2.EntityArmorStand;
import net.minecraft.server.v1_16_R2.PacketPlayOutEntityDestroy;

public class PlayerStand {
    Player owner;
    Player target;
    EntityArmorStand stand;

    public static Map<UUID, List<PlayerStand>> Stands = new HashMap<UUID, List<PlayerStand>>();

    public PlayerStand(Player owner, Player target, EntityArmorStand stand) {
        PlayerStand _stand = GetStandForPlayer(owner, target);
        if (_stand != null) return;
        this.owner = owner;
        this.target = target;
        this.stand = stand;
        if(Stands.get(owner.getUniqueId()) == null)
            Stands.put(owner.getUniqueId(), new ArrayList<PlayerStand>());
        List<PlayerStand> list = Stands.get(owner.getUniqueId());
        list.add(this);
        Stands.put(owner.getUniqueId(), list);
    }

    public void Remove() {
        PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(stand.getId());
        ((CraftPlayer)owner).getHandle().playerConnection.sendPacket(destroy);
        List<PlayerStand> list = Stands.get(owner.getUniqueId());
        list.remove(this);
        Stands.put(owner.getUniqueId(), list);
    }

    public static EntityArmorStand GetStandEntityForPlayer(Player owner, Player target) {
        if (Stands.get(owner.getUniqueId()) == null)
            return null;
        for(PlayerStand s : Stands.get(owner.getUniqueId())) {
            if (s != null && target != null)
                if (s.target.getUniqueId().equals(target.getUniqueId()))
                    return s.stand;
        }
        return null;
    }

    public static PlayerStand GetStandForPlayer(Player owner, Player target) {
        if (Stands.get(owner.getUniqueId()) == null)
            return null;
        for(PlayerStand s : Stands.get(owner.getUniqueId())) {
            if (s != null && target != null)
                if (s.target.getUniqueId().equals(target.getUniqueId()))
                    return s;
        }
        return null;
    }

    public static List<PlayerStand> GetStandForPlayer(Player owner) {
        if (Stands.get(owner.getUniqueId()) == null)
            return null;
        return Stands.get(owner.getUniqueId());
    }
}