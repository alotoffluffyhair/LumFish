package Nodes;

import org.powerbot.script.methods.Bank.Amount;
import org.powerbot.script.util.Random;
import org.powerbot.script.wrappers.Area;
import org.powerbot.script.wrappers.GameObject;
import org.powerbot.script.wrappers.Player;
import org.powerbot.script.wrappers.Tile;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.methods.MethodProvider;

public class Bank extends MethodProvider implements Node {

	private final Area bank = new Area(new Tile(3218,3265,0), new Tile(3210,3250,0));
	private final Player local = ctx.players.local();
	
	public Bank(MethodContext ctx) {
		super(ctx);
	}	

	@Override
	public boolean activate() {
		return bank.contains(local);
	}

	@Override
	public void execute() {
		System.out.println("Bank");
		ctx.objects.select().id(79036).nearest();
		for (final GameObject chest : ctx.objects){
			if (chest.isOnScreen()){
					chest.interact("Use");
					sleep(Random.nextInt(700,1400));
			} else ctx.camera.turnTo(chest);
		}
		sleep(Random.nextInt(300,900));
		ctx.bank.deposit(335, Amount.ALL);
		sleep(Random.nextInt(200,600));
		ctx.bank.deposit(331, Amount.ALL);
		ctx.bank.close();
		sleep(Random.nextInt(50, 100));
		}	
	}	
