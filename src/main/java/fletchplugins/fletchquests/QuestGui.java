package fletchplugins.fletchquests;

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

public class QuestGui implements Listener {
    private final Inventory inventory;
    private ArrayList<ItemStack> quests=new ArrayList<>();
    private ArrayList<String> questIds=new ArrayList<>();

    public QuestGui(){
        inventory= Bukkit.createInventory(null, 36, "Quests!");
        createQuest(Material.EGG, "EGG", "get_egg", "find egg");

        initializeItems();
    }

    public void initializeItems(){
        inventory.clear();
        for(ItemStack i: quests){
            inventory.addItem(i);
        }
    }

    protected void createQuest(final Material material, final String name, final String id, final String... description){
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta=item.getItemMeta();

        meta.setDisplayName(name);

        meta.setLore(Arrays.asList(description));

        item.setItemMeta(meta);

        questIds.add(id);
        quests.add(item);
        initializeItems();
    }
    protected void deleteQuest(final String id, Player player){
        if(!questIds.contains(id)){
            player.sendMessage(ChatColor.RED+"Not a valid quest ID!");
        }
        else {
            int questIndex = questIds.indexOf(id);
            questIds.remove(id);
            quests.remove(questIndex);
            initializeItems();
            player.sendMessage(ChatColor.GREEN+"Quest \""+id+ChatColor.GREEN+"\" successfully deleted!");
        }
    }

    protected void grantQuest(final String id, Player player){
        if(!questIds.contains(id)){
            player.sendMessage(ChatColor.RED+"Not a valid quest ID!");
        }
        else{
            int questIndex=questIds.indexOf(id);

        }
    }

    public void openMenu(final Player player){
        player.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e){
        if(!e.getInventory().equals(inventory)){
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
        p.sendMessage("Wowie! You clicked" + e.getRawSlot());
    }

    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e){
        if(e.getInventory().equals(inventory)){
            e.setCancelled(true);
        }
    }


}
