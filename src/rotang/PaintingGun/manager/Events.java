package rotang.PaintingGun.manager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class Events implements Listener
{
	private Manager manager;
	
	public Events(Manager manager) 
	{
		this.manager = manager;
	}
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		ItemStack item = event.getItem();
		ItemStack color = player.getInventory().getItemInOffHand();
		Action action = event.getAction();
		
		if(item == null || color == null)
			return;
		
		if(!manager.canUseColor(color.getType()))
		{	
			return;
		}
		
		if(item.equals(manager.getGun()))
		{
			if(event.getHand().equals(EquipmentSlot.HAND))
			{
				if(action.equals(Action.RIGHT_CLICK_AIR))
				{
					manager.shotBullet(player, color);
				}
			}
		}
	}
}
