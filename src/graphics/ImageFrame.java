package graphics;

import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.*;

import input.KeyboardInput;
import input.MouseClickInput;

public class ImageFrame extends JFrame
{
	private static final long serialVersionUID = 1L;

	JPanel imagePanel;
	JLabel imageLabel;

	public ImageFrame(String name, Image image)
	{
		setName(name);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setResizable(false);
		setUndecorated(true);
		setLayout(new FlowLayout(FlowLayout.LEADING, -5, -5));

		addMouseListener(new MouseClickInput());
		addKeyListener(new KeyboardInput());

		setVisible(true);

		updateImage(image);
	}

	public void updateImage(Image image)
	{
		if (imageLabel != null && imageLabel.getParent() != null)
			imageLabel.getParent().remove(imageLabel);

		if (imagePanel != null && imagePanel.getParent() != null)
			imagePanel.getParent().remove(imagePanel);

		if (image != null)
		{
			imagePanel = new JPanel();
			// imagePanel.setBorder(BorderFactory.createEmptyBorder());

			imageLabel = new JLabel(new ImageIcon(image, "fractal"));
			// imageLabel.setBorder(BorderFactory.createEmptyBorder());

			imagePanel.add(imageLabel);
			add(imagePanel);

			pack();
		}
	}
}
