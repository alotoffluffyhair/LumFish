package Nodes;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.methods.MethodProvider;
import org.powerbot.script.util.Random;
import org.powerbot.script.wrappers.Area;
import org.powerbot.script.wrappers.Npc;
import org.powerbot.script.wrappers.Player;
import org.powerbot.script.wrappers.Tile;


public class Fish extends MethodProvider implements Node  {

	private final Area fish = new Area(new Tile(3239,3256,0), new Tile(3243,3237,0));
	private final Player local = ctx.players.local();
	
	public Fish(MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		return local.getAnimation() == -1 && !ctx.backpack.select().id(314).isEmpty()
			   && fish.contains(local) && !local.isInMotion();
		}

	@Override
	public void execute() {
		System.out.println("Fish");
		if(!ctx.npcs.select().id(329).within(15).isEmpty() && local.getAnimation() == -1){
			for (final Npc spot : ctx.npcs.nearest()){
				if (spot.isOnScreen()){
					spot.interact("Lure");
					sleep(Random.nextInt(700,1200));
				} else ctx.camera.turnTo(spot);
			} while(local.isInMotion()){
				sleep(Random.nextInt(100, 250));
			}
			
		}


	}
}
