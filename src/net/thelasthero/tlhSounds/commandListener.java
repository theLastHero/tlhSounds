package net.thelasthero.tlhSounds;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class commandListener implements CommandExecutor{

	tlhSounds pl;

	public commandListener(tlhSounds plugin) {
		pl = plugin;
	}
	
	
	public String listArrayValues(List<String> changeSoundLists){
		
		String constructString = "";
		
		for (int i=0; i < changeSoundLists.size(); i++)
		{
			
			
			constructString = constructString + changeSoundLists.get(i);
			
			if((i+1) < changeSoundLists.size()){
				constructString = constructString + ", ";
			}
			
			  
		}
		
		return constructString;
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String s,
			String[] args) {
		
		//if not sent by a player ignore
				if (!(sender instanceof Player)) {
					return true;
				}
				
				Player p = (Player) sender;
				
				
				
				//SOUNDS
				//----------------------
				if (cmd.getName().equalsIgnoreCase("sounds")) {
					if(args.length <=1){
						p.sendMessage(" ");
						p.sendMessage(" ");
						p.sendMessage(ChatColor.DARK_GRAY + "the" + ChatColor.DARK_RED.toString() + ChatColor.BOLD.toString() + "Last" + ChatColor.RED + "Hero" + ChatColor.YELLOW + " - Sounds");
						p.sendMessage(ChatColor.GREEN + "--------------");
						p.sendMessage(" ");
						p.sendMessage(ChatColor.GREEN + "/sounds reset " + ChatColor.GRAY + "<- Reset to all sounds.");
				        p.sendMessage(ChatColor.GREEN + "/sounds <SOUND_TO_CHANGE> <SOUND_TO_USE>" + ChatColor.GRAY + "<- .");
				        
				        p.sendMessage(ChatColor.WHITE + "Changeable Sounds: " + ChatColor.GRAY + listArrayValues(pl.changeSoundLists));
				        
				        p.sendMessage(ChatColor.WHITE + "Useable Sounds: " + ChatColor.GRAY + listArrayValues(pl.soundLists)); //"xp_orb, bass, bass_drum, bass_guitar, snare_drum ");
				       
				        
					} else {
						
						if(pl.changeSoundLists.contains(args[0]) && pl.soundLists.contains(args[1])){
					
						String changeSound = args[0];
						String useSound = args[1];
						
						if (changeSound.equalsIgnoreCase("onhitmob")){
							pl.soundsPlayerHitMob.put(p.getUniqueId(), useSound);
						} else if (changeSound.equalsIgnoreCase("onhitplayer")){
							pl.soundsPlayerHitPlayer.put(p.getUniqueId(), useSound);
						} else if (changeSound.equalsIgnoreCase("onkillmob")){
							pl.soundsPlayerKillMob.put(p.getUniqueId(), useSound);
						} else if (changeSound.equalsIgnoreCase("onkillplayer")){
							pl.soundsPlayerKillPlayer.put(p.getUniqueId(), useSound);
						}  
						
						
						p.sendMessage(ChatColor.RED.toString() + "Sound changed for " + ChatColor.AQUA.toString() + changeSound + ChatColor.RED.toString() + " to " + ChatColor.AQUA.toString() + useSound);
							
							try {
								SLAPI.save(pl.soundsPlayerHitMob,"plugins/thelasthero/phm.bin");
						            } catch(Exception e) {
						                 e.printStackTrace();
						}
						
						} else {
							p.sendMessage("ERROR");
						}
						
					}
	}
				return false;

}
}
