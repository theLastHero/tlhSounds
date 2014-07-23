package net.thelasthero.tlhSounds;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;



import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.Packets;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ConnectionSide;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.injector.PacketConstructor;

public class tlhSounds extends JavaPlugin implements Listener{


	public HashMap<UUID, String> soundsPlayerHitAnything = new HashMap<UUID, String>();
	public HashMap<UUID, String> soundsPlayerHitPlayer = new HashMap<UUID, String>();
	public HashMap<UUID, String> soundsPlayerHitMob = new HashMap<UUID, String>();
	public HashMap<UUID, String> soundsPlayerKillPlayer = new HashMap<UUID, String>();
	public HashMap<UUID, String> soundsPlayerKillMob = new HashMap<UUID, String>();
	
	public List<String> soundLists = new ArrayList<String>();
	public List<String> changeSoundLists = new ArrayList<String>();
	
	private ProtocolManager protocolManager;
	
	 private PacketConstructor soundPacket;

	@Override
	public void onLoad() {

		
	}
	
	/*
	@SuppressWarnings("deprecation")
	@EventHandler(priority=EventPriority.HIGHEST)
	  public void onHit(EntityDamageByEntityEvent event)
	  {
	    Entity damager = event.getDamager();
	    Entity damagee = event.getEntity();
	    
	    if (event.getCause().equals("fire")) return;
	    
	    if ((!(damager instanceof Player))) return;
	    
	    final Player p = Bukkit.getPlayer(damager.getUniqueId());
	    
	    //hitMob
	    if(soundsPlayerHitMob.containsKey(damager.getUniqueId())  ){
	    	final String soundString = soundsPlayerHitMob.get(p.getUniqueId());
	    	if(soundLists.contains(soundString)){
	    	
	    	ProtocolLibrary.getProtocolManager().addPacketListener(
					new PacketAdapter(this, ConnectionSide.SERVER_SIDE,
							Packets.Server.NAMED_SOUND_EFFECT) {
						
						@Override
						public void onPacketSending(PacketEvent e) {
							String soundName = e.getPacket().getStrings().read(0);
			
							if(soundName.contains("hurt")){
								e.setCancelled(true);
								Bukkit.getPlayer(p.getUniqueId()).getWorld().playSound(Bukkit.getPlayer(p.getUniqueId()).getLocation(),convertSound(soundString), 1, 0);
								
							}
							
						}
					
					});
	    
	    }
	    	
    	}
	    
	    }
	*/
	
	/*
	 * @SuppressWarnings("deprecation")
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onEntityDeath(EntityDeathEvent e) {
		
		
		 Entity damager = e.getEntity().getKiller();
		 Entity damagee = e.getEntity();
		 
		 //if its not kileld by a player then return
		 if (!(damager instanceof Player)) return;
		// if (damagee instanceof Player) return;
		 
		 final Player p = Bukkit.getPlayer(damager.getUniqueId());
		 
		 if(soundsPlayerKillMob.containsKey(damager.getUniqueId())){
			 
			 Bukkit.broadcastMessage("aaaaaaaaaaaaaa");
			 
			 ProtocolLibrary.getProtocolManager().addPacketListener(
						new PacketAdapter(this, ConnectionSide.SERVER_SIDE,
								Packets.Server.NAMED_SOUND_EFFECT) {
							
							@Override
							public void onPacketSending(PacketEvent e) {
								String soundName = e.getPacket().getStrings().read(0);
				
									final String soundString = soundsPlayerKillMob.get(p.getUniqueId());
								
									Bukkit.broadcastMessage(soundName);
									 
									
									//e.setCancelled(true);
									//Bukkit.getPlayer(p.getUniqueId()).getWorld().playSound(Bukkit.getPlayer(p.getUniqueId()).getLocation(),convertSound(soundString), 1, 0);
									
								
								
							}
						
						});
			 
			 
		 }
		
        
    }
	
	*/
	
	@Override
	public void onDisable() {
	}

	
	
	
	@Override
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, this);

		getCommand("sounds").setExecutor(new commandListener(this));
		
		//list of changableSounds
				//changeSoundLists.add("onhit");
				//changeSoundLists.add("onhitplayer");
				changeSoundLists.add("onhitmob");
				//changeSoundLists.add("onkillplayer");
				//changeSoundLists.add("onkillmob");
				
				//list of sounds
				soundLists.add("random.orb");
				soundLists.add("note.bass");
				soundLists.add("note.snare");
				soundLists.add("piano");
				soundLists.add("note.pling");
				soundLists.add("random.burp");
				soundLists.add("ambient.weather.thunder");
				soundLists.add("fireworks.blast");
				
				 try {
						soundsPlayerHitMob = SLAPI.load("plugins/thelasthero/phm.bin");
				            } catch(Exception e) {
				                //handle the exception
				                e.printStackTrace();
				            }

				 
				 
				 
				 ProtocolLibrary.getProtocolManager().addPacketListener(
						 new PacketAdapter(this, ConnectionSide.SERVER_SIDE, Packets.Server.NAMED_SOUND_EFFECT) {
							 @Override
							 public void onPacketSending(PacketEvent event) {
								 
								 Player p = event.getPlayer();
								 UUID uuid = p.getPlayer().getUniqueId();
								 
								 //Bukkit.broadcastMessage("UUID="+p.getPlayer().getUniqueId().toString());
								 if(soundsPlayerHitMob.containsKey(p.getPlayer().getUniqueId())){
								
									 String useSound =soundsPlayerHitMob.get(uuid);
									 
									 PacketContainer packet = event.getPacket();
								 
									 String soundName = packet.getStrings().read(0);
									 /*
									 
									 double x = (packet.getIntegers().read(0) / 8.0);
									 double y = (packet.getIntegers().read(1) / 8.0);
									 double z = (packet.getIntegers().read(2) / 8.0);
									 Location loc = new Location(world, x, y, z);
						 			*/
						 
									 if (soundName.contains("hurt")) {
										 event.setCancelled(true);
										// String soundString = soundsPlayerKillMob.get(event.getPlayer().getUniqueId());
										 //p.getWorld().playSound(p.getLocation(),convertSound(useSound), 1, 0);
										 playSound(p, p.getLocation(), useSound, 1, 1);
									 }
							 }
							 }
						 	});
						 	
				 
				 
				 
	}
	
    
    public void playSound(Player reciever, Location location, String sound, float volume, float pitch) {
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        double x = location.getBlockX() + 0.5D;
        double y = location.getBlockY() + 0.5D;
        double z = location.getBlockZ() + 0.5D;
        
        // This allows us to call one of the constructors of Packet62NamedSoundEffect
        if (soundPacket == null) {
            soundPacket = manager.createPacketConstructor(
                Packets.Server.NAMED_SOUND_EFFECT, sound, x, y, z, volume, pitch);
        }
        
        try {
            manager.sendServerPacket(reciever, soundPacket.createPacket(sound, x, y, z, volume, pitch));
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Cannot invoke Packet62NamedSoundEffect constructor.", e);
        }
    
}
	
	// -------------------------------------------------------------------------------------
		// convertSound
		// -------------------------------------------------------------------------------------
		public Sound convertSound(String soundString){
		
			//get color into a string
			Sound returnSound = Sound.ANVIL_BREAK;
		 
			if (soundString.equalsIgnoreCase("xp_orb")){
				returnSound = Sound.ORB_PICKUP;
			}else if (soundString.equalsIgnoreCase("bass_guitar")){
				returnSound = Sound.NOTE_BASS_GUITAR;
			}else if (soundString.equalsIgnoreCase("snare_drum")){
				returnSound = Sound.NOTE_SNARE_DRUM;
			}else if (soundString.equalsIgnoreCase("piano")){
				returnSound = Sound.NOTE_PIANO;
			}else if (soundString.equalsIgnoreCase("pling")){
				returnSound = Sound.NOTE_PLING;
			}else if (soundString.equalsIgnoreCase("burp")){
				returnSound = Sound.BURP;
			}else if (soundString.equalsIgnoreCase("thunder")){
				returnSound = Sound.AMBIENCE_THUNDER;
			}else if (soundString.equalsIgnoreCase("level_up")){
				returnSound = Sound.LEVEL_UP;
			}
			
			return returnSound;
		}
		
		
}


