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
	private boolean bankSuccess1, bankSuccess2;
	
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
		bankSuccess1 = false;
		bankSuccess2 = false;
		ctx.objects.select().id(79036).nearest();
		for (final GameObject chest : ctx.objects){
			if (chest.isOnScreen()){
					chest.interact("Use");
					sleep(Random.nextInt(700,1400));
			} else ctx.camera.turnTo(chest);
		}
		if (ctx.bank.isOpen()){
			sleep(Random.nextInt(300,900));
			while(bankSuccess1 == false || bankSuccess2 == false ){
				if (ctx.bank.deposit(335, Amount.ALL)){
				sleep(Random.nextInt(200,600));
				bankSuccess1 = true;
				}
				if (ctx.bank.deposit(331, Amount.ALL)){
					ctx.bank.close();
					bankSuccess2 = true;
					sleep(Random.nextInt(50, 100));
				}
				
			}	
		}
	}
}
