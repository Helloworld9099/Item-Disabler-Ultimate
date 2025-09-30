package itemcontrol.Itemcontrol;

import org.bukkit.plugin.java.JavaPlugin;
import itemcontrol.Itemcontrol.managers.PluginManager;
import itemcontrol.Itemcontrol.listeners.PlayerListener;

public class Itemcontrol extends JavaPlugin {
    
    @Override
    public void onEnable() {

        saveDefaultConfig();
        
        // Initialize managers
        PluginManager.getInstance().initialize();
        
        // Register listeners
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        
        getLogger().info("Itemcontrol has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Itemcontrol has been disabled!");
    }
    
}