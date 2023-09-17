package fletchplugins.fletchquests;

import org.bukkit.inventory.ItemStack;

public class QuestItem {
    private String id;
    private ItemStack icon;
    private String displayName;

    public QuestItem(String id, ItemStack icon, String displayName){
        this.id=id;
        this.icon=icon;
        this.displayName=displayName;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getId() {
        return id;
    }
}
