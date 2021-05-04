package rotang.PaintingGun.manager;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;
import rotang.PaintingGun.PaintingGun;

public class Manager
{
	private Plugin plugin = PaintingGun.getPlugin(PaintingGun.class);
	
	private int range = 2;
	
	public int getRange() 
	{
		return range;
	}

	public void setRange(int range) 
	{
		this.range = range;
	}
	
	public ItemStack getGun()
	{
		ItemStack gun = new ItemStack(Material.BLAZE_ROD);
		ItemMeta meta = gun.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + "Brush");
		gun.setItemMeta(meta);
		
		return gun;
	}
	
	public boolean canUseColor(Material color)
	{
		if(color.toString().contains("CONCRETE"))
		{
			return true;
		}
		
		else		
			return false;
	}
	
	public void shotBullet(Player player, ItemStack color)
	{
		Location loc = player.getLocation();
		Vector dir = loc.getDirection().normalize();
		ArmorStand bullet = (ArmorStand) player.getWorld().spawnEntity(loc.add(dir.multiply(1)), EntityType.ARMOR_STAND);
		
		bullet.setGravity(false);
		bullet.setVisible(false);
		bullet.setSilent(true);
		bullet.getEquipment().setHelmet(color);
		
		new BukkitRunnable()
		{	
			@SuppressWarnings("deprecation")
			@Override
			public void run() 
			{
				Location bulletLoc = bullet.getLocation();
				Block block = bulletLoc.add(0, 1, 0).getBlock();
				
				if(block.getType().isSolid())
				{
					if(block.getType().toString().contains("CONCRETE"))
					{
						color.getData();
						drawCircle(block.getLocation(), color, color.getData().getData());
					}
					
					cancel();
					bullet.remove();
				}
				
				else
				{
					Location nextMove = bullet.getLocation().add(dir);
					bullet.teleport(nextMove);
				}
			}
		}.runTaskTimer(plugin, 0, 1);
	}
	
	public void drawCircle(Location center, ItemStack color, byte data)
	{
		new BukkitRunnable()
		{		
			int r = 0;
			
			@Override
			public void run() 
			{
				r++;
				
				int x;
				int y = center.getBlockY();
				int z;
				
				for(double i = 0.0; i <= 360; i += 5)
				{
					double angle = i * Math.PI / 180;
					x = (int) (center.getBlockX() + r * Math.cos(angle));
					z = (int) (center.getBlockZ() + r * Math.sin(angle));
					
					Block targetBlock = center.getWorld().getBlockAt(x, y, z);
					
					if(canUseColor(targetBlock.getType()))
					{
						targetBlock.setBlockData(getConcreteData(data));
						targetBlock.getState().update();
					}
				}
				
				if(r >= getRange())
				{
					cancel();
					return;
				}
			}
		}.runTaskTimer(plugin, 0, 1);
	}
	
	public BlockData getConcreteData(byte data)
	{
		switch (data) 
		{
		case 0:
			return plugin.getServer().createBlockData(Material.WHITE_CONCRETE);
		case 1:
			return plugin.getServer().createBlockData(Material.ORANGE_CONCRETE);
		case 2:
			return plugin.getServer().createBlockData(Material.MAGENTA_CONCRETE);
		case 3:
			return plugin.getServer().createBlockData(Material.LIGHT_BLUE_CONCRETE);
		case 4:
			return plugin.getServer().createBlockData(Material.YELLOW_CONCRETE);
		case 5:
			return plugin.getServer().createBlockData(Material.LIME_CONCRETE);
		case 6:
			return plugin.getServer().createBlockData(Material.PINK_CONCRETE);
		case 7:
			return plugin.getServer().createBlockData(Material.GRAY_CONCRETE);
		case 8:
			return plugin.getServer().createBlockData(Material.LIGHT_GRAY_CONCRETE);
		case 9:
			return plugin.getServer().createBlockData(Material.CYAN_CONCRETE);
		case 10:
			return plugin.getServer().createBlockData(Material.PURPLE_CONCRETE);
		case 11:
			return plugin.getServer().createBlockData(Material.BLUE_CONCRETE);
		case 12:
			return plugin.getServer().createBlockData(Material.BROWN_CONCRETE);
		case 13:
			return plugin.getServer().createBlockData(Material.GREEN_CONCRETE);
		case 14:
			return plugin.getServer().createBlockData(Material.RED_CONCRETE);
		case 15:
			return plugin.getServer().createBlockData(Material.BLACK_CONCRETE);
		default:
			return null;
		}
	}
}
