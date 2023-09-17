package fletchplugins.fletchquests;

import fletchplugins.fletchquests.flairmanagers.FlairGui;
import org.bukkit.plugin.java.JavaPlugin;

public final class FletchQuests extends JavaPlugin {

    private static QuestGui questGui=new QuestGui();
    private static FlairGui flairGui=new FlairGui();
    @Override
    public void onEnable() {
        this.getLogger().info("Starting up FletchQuests!");

        this.getCommand("quests").setExecutor(new CommandClass());
        this.getCommand("createquest").setExecutor(new CommandClass());
        this.getCommand("deletequest").setExecutor(new CommandClass());
        this.getCommand("editquest").setExecutor(new CommandClass());
        this.getCommand("grantquest").setExecutor(new CommandClass());
        this.getCommand("flairs").setExecutor(new CommandClass());
        this.getCommand("baba").setExecutor(new CommandClass());
        this.getServer().getPluginManager().registerEvents(questGui, this);
        this.getServer().getPluginManager().registerEvents(flairGui, this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static QuestGui getQuestGui(){
        return questGui;
    }
    public static FlairGui getFlairGui(){ return flairGui;}
}
