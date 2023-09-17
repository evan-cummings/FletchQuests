package fletchplugins.fletchquests.flairmanagers;

import org.bukkit.inventory.ItemStack;

public class FlairItem {
    private String flairText;
    private ItemStack icon;
    private String ID;

    public FlairItem(String flairText, ItemStack icon, String ID){
        this.flairText=flairText;
        this.icon=icon;
        this.ID=ID;
    }


    public String getFlairText() {
        return flairText;
    }

    public void setFlairText(String flairText) {
        this.flairText = flairText;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public void setIcon(ItemStack icon) {
        this.icon = icon;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
