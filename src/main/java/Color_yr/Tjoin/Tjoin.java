package Color_yr.Tjoin;

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

public class Tjoin extends JavaPlugin implements Listener {
	static boolean join = true;

	private String message;
	private int time;
	private boolean enable;
	private final Timer timer = new Timer();

	@Override
	public void onEnable() {
		if (!getDataFolder().exists()) {
			getDataFolder().mkdir();
		}
		File file = new File(getDataFolder(), "config.yml");
		if (!(file.exists())) {
			saveDefaultConfig();
		}
		reloadConfig();
		getLogger().info("Tjoin插件加载成功");
		getCommand("Tjoin").setExecutor(this);
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("Tjoin插件已启用");
	}

	@Override
	public void onDisable() {
		HandlerList.unregisterAll();
		getLogger().info("Tjoin插件已禁用");
	}

	public void reload() {
		this.reloadConfig();
		message = getConfig().getString("message");
		time = getConfig().getInt("time") * 1000;
		enable = getConfig().getBoolean("TjoinEnable", false);
	}

	public void timer1(int time) {
		timer.schedule(new TimerTask() {
			public void run() {
				join = true;
			}
		}, time);
	}

	@EventHandler
	public void onPlayerJoin(PlayerLoginEvent event) {
		if (enable) {
			if (!join) {
				event.disallow(PlayerLoginEvent.Result.KICK_OTHER,
						ChatColor.translateAlternateColorCodes('§', message));
			} else {
				join = false;
				timer1(time * 1000);
			}
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
		if (lable.equalsIgnoreCase("Tjoin")) {
			if (sender.hasPermission("Tjoin.reload") || sender.isOp()) {
				reload();
				sender.sendMessage("§a[Tjoin]重载成功");
				return true;
			}
			sender.sendMessage("§4[Tjoin]你没有使用此命令的权限");
			return true;
		}
		return false;
	}
}


