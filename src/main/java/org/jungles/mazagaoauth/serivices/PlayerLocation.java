package org.jungles.mazagaoauth.serivices;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerLocation {

    private static final NamespacedKey lastLocationKey = new NamespacedKey("mazagao_auth", "last_location");

    public static void setLastLocation(Player player){
        Location lastLocation = player.getLocation();
        PersistentDataContainer persistentDataContainer = player.getPersistentDataContainer();
        persistentDataContainer.set(lastLocationKey, PersistentDataType.STRING, lastLocation.toString());
    }

    public static void setLastLocation(Player player, Location lastLocation){

        if(lastLocation == null){
            lastLocation = player.getWorld().getSpawnLocation();
        }

        PersistentDataContainer persistentDataContainer = player.getPersistentDataContainer();
        persistentDataContainer.set(lastLocationKey, PersistentDataType.STRING, lastLocation.toString());
    }

    public static Location getLastLocation(Player player){
        PersistentDataContainer persistentDataContainer = player.getPersistentDataContainer();
        String result = persistentDataContainer.get(lastLocationKey, PersistentDataType.STRING);

        Location lastLocation = stringToLocation(result);

        if (lastLocation == null){
            lastLocation = player.getWorld().getSpawnLocation();
        }

        return lastLocation;
    }

    public static void teleportToLoginLocation(Player player){
        player.teleport(new Location(player.getWorld(), 0, -200, 0));
    }

    public static void teleportToLastLocation(Player player){

        Location lastLocation = getLastLocation(player);

        player.setFallDistance(0);
        player.teleport(lastLocation);
    }

    private static Location stringToLocation(String locationString) {

        if( locationString == null){
            System.out.println("Invalid Location provided: null");
            return null;
        }

        Pattern pattern = Pattern.compile("Location\\{world=CraftWorld\\{name=(.*?)}," +
                "x=(.*?),y=(.*?),z=(.*?),pitch=(.*?),yaw=(.*?)\\}");

        Matcher matcher = pattern.matcher(locationString);

        if (matcher.find() && matcher.groupCount() == 6) {
            String worldName = matcher.group(1);
            double x = Double.parseDouble(matcher.group(2));
            double y = Double.parseDouble(matcher.group(3));
            double z = Double.parseDouble(matcher.group(4));
            float pitch = Float.parseFloat(matcher.group(5));
            float yaw = Float.parseFloat(matcher.group(6));

            return new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);

        } else {
            System.out.println("Invalid Location provided: " + locationString);
            return null;
        }
    }
}
