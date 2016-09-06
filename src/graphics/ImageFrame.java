package graphics;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.*;

public class ImageFrame extends JFrame
{
	private static final long serialVersionUID = 1L;

	ImagePanel imagePanel;

	public ImageFrame(String name, Image image)
	{
		setName(name);

		setLayout(new BorderLayout());

		updateImage(image);
	}

	public void updateImage(Image image)
	{
		if (imagePanel != null)
			remove(imagePanel);

		if (image != null)
		{
			imagePanel = new ImagePanel(image);
			add(imagePanel, BorderLayout.CENTER);
		}

		pack();

		repaint();
	}

	public void repaint()
	{
		if (imagePanel != null)
			imagePanel.repaint();
	}
}

class ImagePanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	Image image;

	public ImagePanel(Image image)
	{
		this.image = image;
	}

	protected void paintComponent(Graphics graphics)
	{
		super.paintComponent(graphics);

		if (image != null)
			// TODO change to below line once window size problem figured out :)
			// graphics.drawImage(image, 0, 0, getWidth(), getHeight(), this);
			graphics.drawImage(image, 0, 0, this);
	}
}
