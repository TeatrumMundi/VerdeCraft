package verdecraft.verdecraft.handlers.blocks;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import verdecraft.verdecraft.Verdecraft;
import verdecraft.verdecraft.files.BlockLocations;
import verdecraft.verdecraft.items.BlockManager;

public class NuclearReactorDestroy implements Listener
{
    Verdecraft plugin;
    public NuclearReactorDestroy(Verdecraft plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onNuclearReactorDestroy(BlockBreakEvent event)
    {
        Block block = event.getBlock();
        Block upper = block.getWorld().getBlockAt(block.getX(), block.getY()+1, block.getZ());
        Block lower = block.getWorld().getBlockAt(block.getX(), block.getY()-1, block.getZ());

        if(MakeNuclearReactorWork.isNuclearReactor(block))
        {
            upper.setType(Material.AIR);
            block.getWorld().createExplosion(block.getLocation(),15,true);
            BlockLocations.deleteNuclearReactor(block.getLocation());
        }

        if(MakeNuclearReactorWork.isNuclearReactor(lower))
        {
            block.setType(Material.AIR);
            lower.getWorld().createExplosion(block.getLocation(),15,true);
            BlockLocations.deleteNuclearReactor(lower.getLocation());
        }
    }

    @EventHandler
    public void onNuclearReactorExplode(BlockExplodeEvent event)
    {
        for(Block block : event.blockList())
        {
            if(block.getType().equals(BlockManager.NuclearReactor.getType()))
            {
                block.setType(Material.AIR);
                Block upper = block.getWorld().getBlockAt(block.getX(), block.getY()+1, block.getZ());
                upper.setType(Material.AIR);
                BlockLocations.deleteNuclearReactor(block.getLocation());

            }

            if(block.getState() instanceof TileState)
            {
                Block lower = block.getWorld().getBlockAt(block.getX(), block.getY()-1, block.getZ());
                TileState state = (TileState) block.getState();
                PersistentDataContainer container = state.getPersistentDataContainer();
                NamespacedKey key = new NamespacedKey(Verdecraft.getPlugin(Verdecraft.class),"reactor-fuel");
                if(container.has(key,PersistentDataType.INTEGER))
                {
                    lower.setType(Material.AIR);
                    BlockLocations.deleteNuclearReactor(lower.getLocation());
                }
            }
        }

    }

    @EventHandler
    public void onNuclearReactorExplodeByEntity(EntityExplodeEvent event)
    {
        for(Block block : event.blockList())
        {
            if(block.getType().equals(BlockManager.NuclearReactor.getType()))
            {
                block.setType(Material.AIR);
                Block upper = block.getWorld().getBlockAt(block.getX(), block.getY()+1, block.getZ());
                upper.setType(Material.AIR);
                BlockLocations.deleteNuclearReactor(block.getLocation());

            }

            if(block.getState() instanceof TileState)
            {
                Block lower = block.getWorld().getBlockAt(block.getX(), block.getY()-1, block.getZ());
                TileState state = (TileState) block.getState();
                PersistentDataContainer container = state.getPersistentDataContainer();
                NamespacedKey key = new NamespacedKey(Verdecraft.getPlugin(Verdecraft.class),"reactor-fuel");
                if(container.has(key,PersistentDataType.INTEGER))
                {
                    lower.setType(Material.AIR);
                    BlockLocations.deleteNuclearReactor(lower.getLocation());
                }
            }
        }
    }

}
