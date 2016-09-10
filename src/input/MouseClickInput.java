package input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main.Main;

public class MouseClickInput extends MouseAdapter
{
	@Override
	public void mouseClicked(MouseEvent mouseEvent)
	{
		System.out.println("Mouse clicked: " + mouseEvent.getX() + ", " + mouseEvent.getY());
		Main.onMouseClick(mouseEvent);
	}
}
