package fletchplugins.fletchquests;

import fletchplugins.fletchquests.flairmanagers.FlairGui;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Locale;

public class CommandClass implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player=(Player) sender;

            if(command.getName().equalsIgnoreCase("quests")){
                QuestGui questGui=FletchQuests.getQuestGui();
                questGui.openMenu(player);
            }
            else if(command.getName().equalsIgnoreCase("createquest")){
                if(player.hasPermission("fletchquests.create")){
                    ///createquest <icon> <name> <action> <number(if applicable> <reward>
                    if(args.length==0){
                        player.sendMessage(ChatColor.RED+"You're missing something!\n"+ChatColor.LIGHT_PURPLE+"Run "+ChatColor.AQUA+"/createquest" +
                                " help "+ChatColor.LIGHT_PURPLE+ "for more info!");
                    }
                    if(args.length!=0){
                        if(args[0].equalsIgnoreCase("help")) {
                            if (args.length == 1) {
                                player.sendMessage(ChatColor.GREEN + "Hi! Welcome to the quest plugin. Seems like you're trying to create a quest.");
                                player.sendMessage(ChatColor.GREEN + "The main way to do this is:");
                                player.sendMessage(ChatColor.GREEN + "Run " + ChatColor.LIGHT_PURPLE + "/createquest <icon> <ID> <DisplayName>" + ChatColor.GREEN +
                                        "to make a barebones quest that you can edit later.");
                                player.sendMessage(ChatColor.GREEN + "If you need more info, run " + ChatColor.LIGHT_PURPLE + "/createquest help syntax");
                            } else if (args.length == 2 && args[1].equalsIgnoreCase("syntax")) {
                                player.sendMessage(ChatColor.GREEN + "Looks like you need help with the syntax!");
                                player.sendMessage(ChatColor.GREEN + "Remember, the command is " + ChatColor.LIGHT_PURPLE + "/createquest <icon> <DisplayName> <ID>");
                                player.sendMessage(ChatColor.AQUA + "<icon> " + ChatColor.GREEN + "is the icon that will represent your quest, just pick any item in the game," +
                                        " but format it like a /give command!");
                                player.sendMessage(ChatColor.AQUA + "<ID> " + ChatColor.GREEN + "is very important. It will be used to access your quest for editing later" +
                                        ", so make it something you'll remember. I suggest a simpler version of your quest's name!"+
                                        ChatColor.RED+"\nDO NOT INCLUDE SPACES IN THE ID, USE UNDERSCORES.");
                                player.sendMessage(ChatColor.AQUA + "<DisplayName> " + ChatColor.GREEN + "is what players will see when they look at your quest in the menu!");
                                player.sendMessage(ChatColor.GREEN + "Here's an example!");
                                player.sendMessage(ChatColor.AQUA + "/createquest egg egg_collect Collect Some Eggs!");
                            } else {
                                player.sendMessage(ChatColor.RED + "Sorry, I don't know what command you're trying to run." +
                                        "Try running " + ChatColor.LIGHT_PURPLE + "/createquest help" + ChatColor.RED +
                                        " or " + ChatColor.LIGHT_PURPLE + "/createquest help syntax");
                            }
                        }
                        else{
                            String iconString=args[0].toUpperCase(Locale.ROOT);
                            String idString=null;
                            String displayName=null;
                            //Material iconMat=Material.valueOf(iconString);
                            Material iconMat=null;
                            try {
                                iconMat = Material.valueOf(iconString);
                                //player.getWorld().dropItem(player.getLocation(), item);
                            }
                            catch (Exception e){
                                player.sendMessage(ChatColor.RED+"Sorry, I don't recognize that item!");
                            }
                            //if icon isn't null, they got past the first part
                            if(iconMat != null){
                               if(args.length==1){
                                   player.sendMessage(ChatColor.RED+"You're missing an ID and DisplayName!");
                               }
                               else {
                                   idString = args[1];
                                   if(args.length==2){
                                       player.sendMessage(ChatColor.RED+"You're still missing a DisplayName!");
                                   }
                                   else{
                                       displayName=String.join(" ", Arrays.asList(args).subList(2, args.length).toArray(new String[]{}));
                                       //protected void createQuest(final Material material, final String name, final String id, final String... description)
                                       if(displayName.contains("&")){
                                           displayName.replace("&", "§");
                                       }
                                       displayName="§r"+displayName;
                                       FletchQuests.getQuestGui().createQuest(iconMat, displayName, idString);
                                       player.sendMessage(ChatColor.GREEN+"Quest \""+displayName+ChatColor.GREEN+"\" successfully added!");
                                   }
                               }
                            }
                        }
                    }


                    // /createquest <item> <name> <description> <QUEST> <thingToQuest> <number> <rewards>
                    //createquest <item> <name> <description>
                    //editquest <name> rewards
                    //QuestGui questGui=FletchQuests.getQuestGui();
                    //questGui.createQuest(Material.ACACIA_BOAT, "Boat","boat funny");
                }
                else{
                    player.sendMessage(ChatColor.RED+"Sorry! You don't have permission to run this command!");
                }
            }
            else if(command.getName().equalsIgnoreCase("deletequest")){
                if(player.hasPermission("fletchquests.delete")) {
                    if (args.length == 0) {
                        player.sendMessage(ChatColor.RED + "Need to give a quest ID");
                    }
                    if (args.length == 1) {
                        FletchQuests.getQuestGui().deleteQuest(args[0], player);
                    }
                    if (args.length > 1) {
                        player.sendMessage(ChatColor.RED + "Too many arguments! Just specify the ID");
                    }
                }
                else{
                    player.sendMessage(ChatColor.RED+"Sorry! You don't have permission to run this command!");
                }
            }
            else if(command.getName().equalsIgnoreCase("editquest")){
                if(player.hasPermission("fletchquests.edit")){
                    if(args.length==0){
                        player.sendMessage(ChatColor.RED+"You're missing something! Try running "+ChatColor.LIGHT_PURPLE+"/editquest help");
                    }
                    if(args.length>=1 && args[0].equalsIgnoreCase("help")){
                        if(args.length==1){
                            player.sendMessage(ChatColor.DARK_PURPLE+"====================HELP====================");
                            player.sendMessage(ChatColor.GREEN+"Hi! Seems like you want to edit a quest");
                            player.sendMessage(ChatColor.GREEN+"Run the command "+ChatColor.LIGHT_PURPLE+"/editquest <id> <thingToEdit> <newValues>");
                            player.sendMessage(ChatColor.GREEN+"Your options for "+ChatColor.LIGHT_PURPLE+"<thingToEdit>"+ChatColor.GREEN+" are: " +
                                    ChatColor.LIGHT_PURPLE+"icon, DisplayName, ID, rewards, questPurpose");
                            player.sendMessage(ChatColor.GREEN+"For more information about these, run "+ChatColor.LIGHT_PURPLE+"/editquest help thingYouNeedInfoOn");
                            player.sendMessage(ChatColor.GREEN+"EX: "+ChatColor.LIGHT_PURPLE+"/editquest help rewards");
                        }
                        else if(args.length==2){
                            if(args[1].equalsIgnoreCase("icon")){
                                player.sendMessage(ChatColor.DARK_PURPLE+"===================ICON===================");
                                player.sendMessage(ChatColor.GREEN+"Sounds like you want to edit the icon!");
                                player.sendMessage(ChatColor.GREEN+"Just run "+ChatColor.LIGHT_PURPLE+"/editquest <id> icon <newIcon>");
                                player.sendMessage(ChatColor.GREEN+"EX: "+ChatColor.LIGHT_PURPLE+"/editquest collect_eggs icon diamond_sword");
                            }
                            else if(args[1].equalsIgnoreCase("displayname")){
                                player.sendMessage(ChatColor.DARK_PURPLE+"================DISPLAYNAME================");
                                player.sendMessage(ChatColor.GREEN+"Sounds like you want to edit the DisplayName!");
                                player.sendMessage(ChatColor.GREEN+"Just run "+ChatColor.LIGHT_PURPLE+"/editquest <id> displayname <newName>");
                                player.sendMessage(ChatColor.GREEN+"EX: "+ChatColor.LIGHT_PURPLE+"/editquest collect_eggs displayname Collect MORE Eggs!");
                            }
                            else if(args[1].equalsIgnoreCase("id")){
                                player.sendMessage(ChatColor.DARK_PURPLE+"====================ID====================");
                                player.sendMessage(ChatColor.GREEN+"Sounds like you want to edit the quest ID!");
                                player.sendMessage(ChatColor.GREEN+"Just run "+ChatColor.LIGHT_PURPLE+"/editquest <oldId> id <newID>");
                                player.sendMessage(ChatColor.GREEN+"EX: "+ChatColor.LIGHT_PURPLE+"/editquest collect_eggs id eat_eggs");
                            }
                            else if(args[1].equalsIgnoreCase("rewards")){
                                player.sendMessage(ChatColor.DARK_PURPLE+"=================REWARDS=================");
                                player.sendMessage(ChatColor.GREEN+"Sounds like you want to edit the rewards!");
                                player.sendMessage(ChatColor.GREEN+"Just run "+ChatColor.LIGHT_PURPLE+"/editquest <id> rewards <action> <rewardInfo>");
                                player.sendMessage(ChatColor.GREEN+"The section"+ChatColor.LIGHT_PURPLE+" <action> "+ChatColor.GREEN+"represents what you're doing, "+
                                        "accepted values include "+ChatColor.LIGHT_PURPLE+"add, delete");
                                player.sendMessage(ChatColor.GREEN+"The section"+ChatColor.LIGHT_PURPLE+" <rewardInfo> "+ChatColor.GREEN+"represents more info about the reward. "+
                                        "\nIf you're deleting a reward, <rewardInfo> is a number, i.e. you can delete reward \"2\" out of 3 existing rewards."
                                        +"\nEX: "+ChatColor.LIGHT_PURPLE+"/editquest collect_eggs rewards delete 2"
                                        +ChatColor.GREEN+"\nIf you are adding a reward, "+ChatColor.LIGHT_PURPLE+"<rewardInfo> includes <rewardType>, and <count> or <text> (if applicable)"+
                                        ChatColor.GREEN+"\nReward Types include "+ChatColor.LIGHT_PURPLE+"flair, item "+ChatColor.GREEN+"(requires Count) and "+ChatColor.LIGHT_PURPLE+"runCommand."+
                                        ChatColor.GREEN+"\nFlair gives the player a custom flair, item rewards them with a \"count\" amount of that item, runCommand runs the command specified in <text>."+
                                        "\nNote: <command> follows <count>, if you are not rewarding with \"item\" <count> can be any number. \n If you're not doing runCommand, <command> can be blank"+
                                        "\nExamples: "+ChatColor.LIGHT_PURPLE+"/editquest collect_eggs rewards add item 4 egg"+
                                        "\n/editquest collect_eggs rewards add runcommand 0 /give @p egg"+
                                        //Change this to be flair IDs
                                        "\n/editquest collect_eggs rewards add flair 0 &r&4[Egg Collector]");
                            }
                            else if(args[1].equalsIgnoreCase("questpurpose")){
                                player.sendMessage(ChatColor.DARK_PURPLE+"===============QUESTPURPOSE===============");
                                player.sendMessage(ChatColor.GREEN+"Sounds like you want to edit the quest purpose!"+
                                        "\nThis is how you edit the \"What the player does\" section of the quest, but I forgot what to call that so its questpurpose now.");
                                player.sendMessage(ChatColor.GREEN+"Just run "+ChatColor.LIGHT_PURPLE+"/editquest <id> questpurpose <purpose> <PurposeInfo>");
                                player.sendMessage(ChatColor.GREEN+"The section"+ChatColor.LIGHT_PURPLE+" <purpose> "+ChatColor.GREEN+"represents what the main directive of" +
                                        "the quest is.");
                                player.sendMessage(ChatColor.GREEN+"Your options for this are "+ChatColor.LIGHT_PURPLE+"kill, break, collect, hidden.");
                                player.sendMessage(ChatColor.GREEN+"The section"+ChatColor.LIGHT_PURPLE+" <PurposeInfo> "+ChatColor.GREEN+"contains more info about each quest." +
                                        "\nIt will contain what mobs to kill/ items to collect, how many of each, or a short hint to a hidden quest.");
                                player.sendMessage(ChatColor.BLUE+"===============QuestTypes===============");
                                player.sendMessage(ChatColor.GREEN+"For QuestType "+ChatColor.LIGHT_PURPLE+"kill"+ChatColor.GREEN+", you want the player to kill some amount of mobs." +
                                        "\nFormat this like "+ChatColor.LIGHT_PURPLE+"/editquest <id> questpurpose kill <mob> <count>" +
                                        ChatColor.GREEN+"\nEX: "+ChatColor.LIGHT_PURPLE+"/editquest kill_zombies questpurpose kill zombie 200" +
                                        ChatColor.GREEN+" to make a quest for 200 zombie kills.");
                                player.sendMessage(ChatColor.GREEN+"For QuestType "+ChatColor.LIGHT_PURPLE+"break"+ChatColor.GREEN+", you want the player to break some amount of blocks." +
                                        "\nFormat this like "+ChatColor.LIGHT_PURPLE+"/editquest <id> questpurpose break <block> <count>" +
                                        ChatColor.GREEN+"\nEX: "+ChatColor.LIGHT_PURPLE+"/editquest break_rocks questpurpose break stone 200" +
                                        ChatColor.GREEN+" to make a quest for 200 stone blocks broken.");
                                player.sendMessage(ChatColor.GREEN+"For QuestType "+ChatColor.LIGHT_PURPLE+"collect"+ChatColor.GREEN+", you want the player to collect some amount of items." +
                                        "\nFormat this like "+ChatColor.LIGHT_PURPLE+"/editquest <id> questpurpose collect <item> <count>" +
                                        ChatColor.GREEN+"\nEX: "+ChatColor.LIGHT_PURPLE+"/editquest get_eggs questpurpose collect egg 200" +
                                        ChatColor.GREEN+" to make a quest for 200 eggs collected." +
                                        ChatColor.RED+"\nNOTE: this is AT ONCE. The player needs to have this many eggs in their inventory or a chest.");
                                player.sendMessage(ChatColor.GREEN+"For QuestType "+ChatColor.LIGHT_PURPLE+"hidden"+ChatColor.GREEN+", you want the player to do some hidden quest????" +
                                        "\nFormat this like "+ChatColor.LIGHT_PURPLE+"/editquest <id> questpurpose hidden" +
                                        ChatColor.GREEN+"\nEX: "+ChatColor.LIGHT_PURPLE+"/editquest secret_thing questpurpose hidden" +
                                        ChatColor.GREEN+"\nto make a quest for some secret event. This will make a quest that shows up in the menu, but provides no information to players." +
                                        "\nThis can be used to make hidden quests like \"complete the secret lobby parkour\", which can be granted to players via command block.");


                            }
                            else{
                                player.sendMessage(ChatColor.RED+"Sorry! I don't know what you're asking for help with!");
                            }
                        }
                    }
                }
                else{
                    player.sendMessage(ChatColor.RED+"Sorry! You don't have permission to run this command!");
                }
            }
            else if(command.getName().equalsIgnoreCase("grantquest")){
                ///grantquest <id> <player>
                if(player.hasPermission("fletchquests.grant")){
                    if(args.length==0){
                        player.sendMessage(ChatColor.RED + "You're missing a questID and player to grant it to!");
                    }
                    else if(args.length==1){
                        player.sendMessage(ChatColor.RED + "Need to provide a player to grant the quest to");
                    }
                    else if(args.length==2){
                        String id=args[0];
                        Player grantPlayer=null;
                        try{
                            grantPlayer=player.getServer().getPlayer(args[1]);
                        }
                        catch (Exception e){
                            player.sendMessage(ChatColor.RED + "That player doesn't exist!");
                        }
                    }
                    else{
                        player.sendMessage(ChatColor.RED + "Too many arguments! Just provide a questID and player name.");
                    }
                }
            }
            else if(command.getName().equalsIgnoreCase("baba")){
                player.chat(ChatColor.LIGHT_PURPLE+"baba");
            }
            else if(command.getName().equalsIgnoreCase("flairs")){
                FletchQuests.getFlairGui().openMenu(player);
            }
        }
        return true;
    }
}
