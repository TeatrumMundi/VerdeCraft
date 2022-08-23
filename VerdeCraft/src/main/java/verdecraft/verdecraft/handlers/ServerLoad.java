package verdecraft.verdecraft.handlers;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.scheduler.BukkitRunnable;
import verdecraft.verdecraft.Verdecraft;
import verdecraft.verdecraft.files.BlockLocations;
import verdecraft.verdecraft.handlers.blocks.MakeNuclearReactorWork;

import java.util.ArrayList;
import java.util.List;

public class ServerLoad implements Listener
{
    Verdecraft plugin;
    public ServerLoad(Verdecraft plugin) {this.plugin = plugin;}

    @EventHandler
    public void onServerLoad(ServerLoadEvent event)
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                List<Location> check_reactors = new ArrayList<>((List<Location>) BlockLocations.get().getList("NuclearReactors"));
                for(Location NuclearReactor : check_reactors)
                {
                    Block reactor = NuclearReactor.getBlock();
                    Block reactor_chest = reactor.getWorld().getBlockAt(reactor.getX(), reactor.getY()+1, reactor.getZ());
                    if(MakeNuclearReactorWork.isNuclearReactor(reactor))
                    {
                        System.out.println("wznawiam prace reaktora");
                        new MakeNuclearReactorWork(plugin,reactor,reactor_chest);
                    }
                    else
                    {
                        System.out.println("usuwam nieistniejacy reaktor");
                        BlockLocations.deleteNuclearReactor(NuclearReactor);
                    }
                }
            }
        }.runTaskLater(plugin,20*10);

    }

}
