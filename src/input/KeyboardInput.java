package input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import main.Main;

public class KeyboardInput extends KeyAdapter
{
	@Override
	public void keyPressed(KeyEvent keyEvent)
	{
		System.out.println("Key down: " + KeyEvent.getKeyText(keyEvent.getKeyCode()));
		Main.onKeyDown(keyEvent);
	}
}
