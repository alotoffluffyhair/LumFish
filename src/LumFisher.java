
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

import org.powerbot.event.MessageEvent;
import org.powerbot.event.MessageListener;
import org.powerbot.event.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Manifest;
import org.powerbot.script.methods.Skills;

import Nodes.Bank;
import Nodes.Node;
import Nodes.Fish;
import Nodes.WalkBank;
import Nodes.WalkFish;
import Nodes.Dropper;
import Util.Antiban;
import Util.PriceWrapper;



@Manifest(authors = "botnaster115", description = "fly fishes at lumbridge and banks", name = "LumFisher")
public class LumFisher extends PollingScript implements PaintListener, MessageListener{

	long antibanTimer =System.currentTimeMillis();
	long startTime = System.currentTimeMillis();
	
	int salmonCaught, troutCaught, salmonV, troutV, featherV, startExp;
	private final Queue<Node> bankNodes = new ConcurrentLinkedQueue<>();
    private final Queue<Node> fishNodes = new ConcurrentLinkedQueue<>(); 
    private final Queue<Node> cleanUpNodes = new ConcurrentLinkedQueue<>();
    Antiban antiban = new Antiban(ctx);
    
    public LumFisher(){	
    		getExecQueue(State.START).offer(new Runnable() {

                @Override
                public void run() {
                	            	
    				final Node fishing = new Fish(ctx);
    				final Node toFishing = new WalkFish(ctx);             		        		        		        		        		        	
    				final Node bank = new Bank(ctx);
    				final Node toBank = new WalkBank(ctx);
    				final Node dropper = new Dropper(ctx);
            		
            		fishNodes.addAll(Arrays.asList(fishing, toFishing));
            		bankNodes.addAll(Arrays.asList(bank, toBank));
            		cleanUpNodes.addAll(Arrays.asList(dropper));
            		
            		PriceWrapper lookUp = new PriceWrapper();
            		featherV = lookUp.getPrice(314);
            		salmonV = lookUp.getPrice(331);
            		troutV = lookUp.getPrice(335);
            		startExp = ctx.skills.getExperience(Skills.FISHING);
            		
                }
            });		
    	}	

	@Override
	public int poll() {	
		if (System.currentTimeMillis()-antibanTimer > 30000){
			antiban.dontban();
			antibanTimer = System.currentTimeMillis();
		}
		for (Node node : cleanUpNodes){
			if(node.activate()){
				node.execute();
				break;
			}
		}
		for (Node node :  backpackFull() ? bankNodes : fishNodes) {
			if(node.activate()) {
				node.execute();
				break;
			} 
		}return 50;
	}
	

	@Override
	public void repaint(Graphics g1) {
		long runTime = System.currentTimeMillis()-startTime;
		final Graphics2D g = (Graphics2D) g1;
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, 200, 80);
		g.setColor(Color.BLACK);
		g.drawString(String.format("Fish caught/hr: %s / %s", (troutCaught+salmonCaught),
				(troutCaught+salmonCaught)*(3600000/runTime)), 5, 22);
		g.drawString(String.format("Profit/hr: %s / %s", (troutCaught*troutV)+(salmonCaught*salmonV)-((salmonCaught+troutCaught)*featherV), 
				((troutCaught*troutV)+(salmonCaught*salmonV)-((salmonCaught+troutCaught)*featherV))*(3600000/runTime) ), 5, 34);
		g.drawString(String.format("Experience gained/hr: %s / %s", (ctx.skills.getExperience(Skills.FISHING)-startExp),
				(ctx.skills.getExperience(Skills.FISHING)-startExp)*(3600000/runTime)),5,46);
		g.drawString(String.format("Time running: %02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(runTime),
				TimeUnit.MILLISECONDS.toMinutes(runTime) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(runTime)),
				TimeUnit.MILLISECONDS.toSeconds(runTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(runTime))),5,10);
		Point p = ctx.mouse.getLocation();
		g.setColor(Color.GREEN);
		g.drawLine(p.x+10, p.y, p.x-10, p.y);
		g.drawLine(p.x, p.y + 10, p.x, p.y -10);
		g.setColor(Color.CYAN);
	    g.drawOval(p.x-10, p.y-10, 20, 20);
	    
	    
	}	
	private boolean backpackFull() {
		return ctx.backpack.select().count() == 28;
	}

	@Override
	public void messaged(MessageEvent i) {
		String txt = i.getMessage();
		if(txt.contains("You catch a salmon")) salmonCaught++;
		if(txt.contains("You catch a trout")) troutCaught++;
	}

}
