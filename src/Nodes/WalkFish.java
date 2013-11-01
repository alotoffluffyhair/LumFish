package Nodes;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.methods.MethodProvider;
import org.powerbot.script.util.Random;
import org.powerbot.script.wrappers.Area;
import org.powerbot.script.wrappers.LocalPath;
import org.powerbot.script.wrappers.Player;
import org.powerbot.script.wrappers.Tile;

import Nodes.Node;

public class WalkFish extends MethodProvider implements Node {

	private Tile tile = new Tile(Random.nextInt(3240, 3242),Random.nextInt(3244, 3248));
	private final Area fish = new Area(new Tile(3239,3256,0), new Tile(3243,3237,0));
	private final Player local = ctx.players.local();
	
	public WalkFish(MethodContext ctx) {
		super(ctx);	
	}

	@Override
	public boolean activate() {
		return !ctx.backpack.select().id(314).isEmpty() && !fish.contains(local);
	}


	@Override
	public void execute() {	
		System.out.println("walkFish");
		if (!ctx.movement.isRunning() && ctx.movement.getEnergyLevel() > 30){
			ctx.movement.setRunning(true);	
		}
		LocalPath path = ctx.movement.findPath(tile);
		path.traverse();
		sleep(Random.nextInt(900,1500));	
	
	}
}
