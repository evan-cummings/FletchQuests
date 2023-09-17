package fletchplugins.fletchquests.flairmanagers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class FlairGui implements Listener {
    private final Inventory flairInventory;
    private ArrayList<FlairItem> flairs=new ArrayList<>();

    public FlairGui() {

        flairInventory = Bukkit.createInventory(null, 36, "Flairs!");
        createFlair(Material.BARRIER, ChatColor.RED+"Reset Flair", "reset", ChatColor.WHITE+"Remove Current Flair");
        createFlair(Material.EGG, ChatColor.LIGHT_PURPLE+"Baba", "baba", "This is a flair or something");

    }

    public void initializeItems(){
        flairInventory.clear();
        for(FlairItem i: flairs){
            flairInventory.addItem(i.getIcon());
        }
    }

    protected void createFlair(final Material material, final String name, final String id, final String... description){
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta=item.getItemMeta();

        meta.setDisplayName(name);

        meta.setLore(Arrays.asList(description));

        item.setItemMeta(meta);
        //public FlairItem(String flairText, ItemStack icon, String description, String ID){
        FlairItem flair=new FlairItem(name, item, id);
        flairs.add(flair);
        initializeItems();
    }

    public void openMenu(final Player player){
        player.openInventory(flairInventory);
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e){
        if(!e.getInventory().equals(flairInventory)){
            return;
        }
        //force them to drop the item instant its picked up
        e.setCancelled(true);

        //quest they clicked
        final ItemStack clickedItem=e.getCurrentItem();

        if(clickedItem == null || clickedItem.getType().isAir()){
            return; //make sure item is real item, not air or null
        }

        //already know only players can click
        final Player p=(Player) e.getWhoClicked();

        //raw slot is pretty useful
        //p.sendMessage("Wowie! You clicked" + e.getRawSlot());
        //p.sendMessage("Wowie! You clicked" + e.getRawSlot());
        int flairIndex=e.getRawSlot();
        if(flairIndex==0){
            ///lp user FletchlingBoy meta removeprefix
            String finalCommand = "lp user " + p.getName() + " meta removeprefix 100";
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), finalCommand);
            p.sendMessage(ChatColor.LIGHT_PURPLE+"Flair removed successfully!");
        }
        else {
            String clickedFlair = flairs.get(flairIndex).getFlairText();
            //lp user <playername> meta addprefix <weight> <flair>
            String finalCommand = "lp user " + p.getName() + " meta setprefix 100 \"" + clickedFlair+" &r\"";
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), finalCommand);
            p.sendMessage(ChatColor.LIGHT_PURPLE+"Flair set successfully!");
        }
    }

    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e){
        if(e.getInventory().equals(flairInventory)){
            e.setCancelled(true);
        }
    }
}
