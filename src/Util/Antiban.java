package Util;
import java.awt.Point;

import org.powerbot.script.util.Random;
import org.powerbot.script.methods.Hud;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.methods.MethodProvider;


public class Antiban extends MethodProvider {

	public Antiban(MethodContext ctx) {
		super(ctx);
	}

	public void dontban() {
		int a = Random.nextInt(1, 20);
		
		switch(a) {
			case 1:
				ctx.camera.setPitch(Random.nextInt(0, 90));
				System.out.println("Antiban");
				break;
			case 2:
				ctx.camera.setAngle(Random.nextInt(0, 180));
				System.out.println("Antiban");
				break;
			case 3:
				ctx.camera.setPitch(Random.nextInt(0, 90));
				ctx.camera.setAngle(Random.nextInt(0, 180));
				System.out.println("Antiban");
				break;
			case 4:
				ctx.mouse.move(Random.nextInt(-50, 1800), Random.nextInt(-10, 1000));
				System.out.println("Antiban");
				break;
			case 5:
				if (!ctx.hud.isOpen(Hud.Window.SKILLS)){
					ctx.hud.view(Hud.Window.SKILLS);
				} else if (!ctx.hud.isVisible(Hud.Window.SKILLS)){
					ctx.hud.view(Hud.Window.SKILLS);
				}
				Point p = ctx.widgets.get(1466).getComponent(6).getChild(8).getAbsoluteLocation();
				p.translate(30+Random.nextInt(-15, 15), 10+Random.nextInt(-7, 8));
				ctx.mouse.move(p);
				sleep(Random.nextInt(2500, 3000));
				ctx.hud.view(Hud.Window.BACKPACK);
				System.out.println("Antiban");
				break;
			default:
				break;
				
		}
	}	
}
