package Nodes;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.methods.MethodProvider;
import org.powerbot.script.util.Random;
import org.powerbot.script.wrappers.Area;
import org.powerbot.script.wrappers.LocalPath;
import org.powerbot.script.wrappers.Player;
import org.powerbot.script.wrappers.Tile;

import Nodes.Node;

public class WalkBank extends MethodProvider implements Node {

	private Tile tile = new Tile(Random.nextInt(3212, 3214),Random.nextInt(3254, 3259));
	private final Area bank = new Area(new Tile(3215,3261,0), new Tile(3212,3254,0));
	private final Player local = ctx.players.local();
	
	
	public WalkBank(MethodContext ctx) {
		super(ctx);
		
	}

	@Override
	public boolean activate() {
		return !bank.contains(local);
		}

	@Override
	public void execute() {	
		System.out.println("walkbank");
		if (!ctx.movement.isRunning() && ctx.movement.getEnergyLevel() > 30){
			ctx.movement.setRunning(true);	
		}
		LocalPath path = ctx.movement.findPath(tile);
		path.traverse();
		sleep(Random.nextInt(900,1500));	
	}
}
