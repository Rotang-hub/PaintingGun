package rotang.PaintingGun;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;
import rotang.PaintingGun.manager.Events;
import rotang.PaintingGun.manager.Manager;

public class PaintingGun extends JavaPlugin
{
	Manager manager;
	
	@Override
	public void onEnable() 
	{
		if(manager == null)
			manager = new Manager();
		
		getServer().getPluginManager().registerEvents(new Events(manager), this);
		getCommand("pg").setTabCompleter(new CommandTab());
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
	{
		if(label.equalsIgnoreCase("pg"))
		{
			if(args[0].equalsIgnoreCase("gun"))
			{
				((Player) sender).getInventory().addItem(manager.getGun());
			}
			
			if(args[0].equalsIgnoreCase("range"))
			{
				if(args.length == 2)
				{
					int range = Integer.parseInt(args[1]);
					manager.setRange(range);
					sender.sendMessage(ChatColor.GREEN + "범위를 " + ChatColor.YELLOW + manager.getRange() + ChatColor.GREEN + "로 설정했습니다.");
					
					return true;
				}
				
				sender.sendMessage(ChatColor.GREEN + "설정된 범위는 " + ChatColor.YELLOW + manager.getRange() + ChatColor.GREEN + "입니다.");
			}
		}
		
		return false;
	}
}
