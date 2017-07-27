package Tjoin;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;


public class Tjoin extends JavaPlugin implements Listener
{
	static boolean join=true;
    public void onEnable() 
    {
    	if(!getDataFolder().exists()) 
    	   {getDataFolder().mkdir();}
    	  File file=new File(getDataFolder(),"config.yml");
    	  if (!(file.exists())) {this.saveDefaultConfig();}
    	  this.reloadConfig(); 	 
    	  
    	getServer().getPluginManager().registerEvents(this, this);   	
    	getLogger().info("Tjoin������سɹ�");
    	
    	if(this.getConfig().getBoolean("TjoinEnable")==true)
		{this.getLogger().info("Tjoin���������");}
    	else
    	{this.getLogger().info("Tjoin����ѽ���");}
    }

	public void onDisable() {
    	HandlerList.unregisterAll();
    	getLogger().info("Tjoin����ѽ���");
    }
	
    public static void timer1(int time) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
          public void run() {
            join=true;
          }
        }, time);// �趨ָ����ʱ��time,�˴�Ϊk����
      }


    @EventHandler
    public void onPlayerJoin(PlayerLoginEvent event) 	
    {   
    	String message= this.getConfig().getString("message");
    	int time=this.getConfig().getInt("time")*1000;
    	if(this.getConfig().getBoolean("TjoinEnable")==true)
    	{	
    		if(join==false)
    		{
    			event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, ChatColor.translateAlternateColorCodes('��', message));
    		}
    		else{join=false;timer1(time);}
    	}
    }
    @Override
    public boolean onCommand(CommandSender sender,Command cmd, String lable, String[] args){
		if(lable.equalsIgnoreCase("Tjoin")){
			if(sender.hasPermission("Tjoin.reload")||sender.isOp()){
				this.reloadConfig();
				sender.sendMessage("��a[Tjoin]���سɹ�");
				if(this.getConfig().getBoolean("TjoinEnable")==true)
	    		{getLogger().info("Tjoin���������");}
		    	else{getLogger().info("Tjoin����ѽ���");}
				return true;
			}
			sender.sendMessage("��4[Tjoin]��û��ʹ�ô������Ȩ��");
			return true;
		}
		return false;
	}
}


