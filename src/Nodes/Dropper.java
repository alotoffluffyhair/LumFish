package Nodes;

import org.powerbot.script.lang.Filter;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.methods.MethodProvider;
import org.powerbot.script.util.Random;
import org.powerbot.script.wrappers.Item;
import org.powerbot.script.wrappers.Player;

public class Dropper extends MethodProvider implements Node{

	private final Player local = ctx.players.local();
	private final int[] aryItems = {314,331,335};
	public Dropper(MethodContext ctx) {
		super(ctx);
	}

		
	@Override
	public void execute() {
		for (Item i : ctx.backpack.select().select(itemFilter)) {
		    i.interact("Drop");
		    System.out.println("Dropping");
		}
		sleep(Random.nextInt(100, 250));
	}


	@Override
	public boolean activate() {
		if (local.getAnimation() == -1){
			for (Item i : ctx.backpack.select().select(itemFilter)) {
				return true;
			}
		}
		return false;
	}  
	
	Filter<Item> itemFilter = new Filter<Item>(){
		@Override
		public boolean accept(Item item) {
            for (int i : aryItems) {
                if (item.getId() == i) {
                    return false;
                }
            }
            return true;
        }
    };
	
}