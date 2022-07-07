import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MyFrame extends JFrame implements ActionListener{
 
	private static final long serialVersionUID = 1L;

	JButton button;
	JButton upgradeButton1;
	JButton rebirthButton;
	JButton autoUpgrade;
	JButton audioButton;
	JLabel moneyLabel;
	JLabel moneyGainLabel;
	JLabel moneyMultLabel;
	JLabel priceLabel;
	JLabel prestigePriceLabel;
	JLabel autoBuyLabel;
	boolean autoUpgradePurchased = false;
	double money = 0;
	int price = 10;
	int moneyPerClick = 1;
	double moneyMult = 1;
	
	File file = new File("Sunset Dream - Cheel.wav");
	AudioInputStream audioStream;
	Clip clip = null;
	MyFrame(String name){
 		 
		try {
			audioStream = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (UnsupportedAudioFileException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		button = new JButton();
		button.setBounds(200,180,200,70);
		button.addActionListener(this);
		button.setBackground(Color.gray);
		button.setText("Click for money!");
		button.setFocusable(false);
		
		upgradeButton1 = new JButton();
		upgradeButton1.setBounds(500,100,180,60);
		upgradeButton1.addActionListener(this);
		upgradeButton1.setBackground(Color.gray);
		upgradeButton1.setText("Upgrade money gain!");
		upgradeButton1.setFocusable(false);

		rebirthButton = new JButton();
		rebirthButton.setBounds(500,180,180,60);
		rebirthButton.addActionListener(this);
		rebirthButton.setBackground(Color.gray);
		rebirthButton.setText("Prestige!");
		rebirthButton.setFocusable(false);

		autoUpgrade = new JButton();
		autoUpgrade.setBounds(500,260,180,60);
		autoUpgrade.addActionListener(this);
		autoUpgrade.setBackground(Color.gray);
		autoUpgrade.setText("Auto upgrades!");
		autoUpgrade.setFocusable(false);
		
		audioButton = new JButton();
		audioButton.setBounds(500,340,180,60);
		audioButton.addActionListener(this);
		audioButton.setBackground(Color.gray);
		audioButton.setText("Turn Sound Off");
		audioButton.setFocusable(false);

		moneyLabel = new JLabel();
		moneyLabel.setText(String.format("Money: %.0f", money));
		moneyLabel.setBounds(10,0,200,50);
		moneyLabel.setForeground(Color.white);
		
		moneyGainLabel = new JLabel();
		moneyGainLabel.setText("Money/Click: " + moneyPerClick);
		moneyGainLabel.setBounds(10,40,200,40);
		moneyGainLabel.setForeground(Color.white);

		moneyMultLabel = new JLabel();
		moneyMultLabel.setText(String.format("Prestige mult: %.1f x money", moneyMult));
		moneyMultLabel.setBounds(10,80,200,40);
		moneyMultLabel.setForeground(Color.white);
		
		priceLabel = new JLabel();
		priceLabel.setText("Price: " + price);
		priceLabel.setBounds(500,65,200,50);
		priceLabel.setForeground(Color.white);
		
		prestigePriceLabel = new JLabel();
		prestigePriceLabel.setText(String.format("Your M/C have to extend 50"));
		prestigePriceLabel.setBounds(500, 145,200,50);
		prestigePriceLabel.setForeground(Color.white);
		
		autoBuyLabel = new JLabel();
		autoBuyLabel.setText(String.format("Price: 100"));
		autoBuyLabel.setBounds(500, 225,200,50);
		autoBuyLabel.setForeground(Color.white);

		
		this.setLayout(null);
		this.getContentPane().setBackground(Color.DARK_GRAY);
		this.setSize(750,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(name);
		this.add(moneyLabel);
		this.add(moneyGainLabel);
		this.add(moneyMultLabel);
		this.add(priceLabel);
		this.add(button);
		this.add(upgradeButton1);
		this.add(rebirthButton);
		this.add(autoUpgrade);
		this.add(prestigePriceLabel);
		this.add(autoBuyLabel);
		this.add(audioButton);
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == button) {
			money += moneyPerClick * moneyMult;
			moneyLabel.setText(String.format("Money: %.0f", money));
			if(autoUpgradePurchased) {
				if(money-price >= 0) {
					int amount = (int) (money / price);
					money -= price * amount;
					price += 10 * amount;
					moneyPerClick += 1 * amount;
					this.updateLabels();
				}
			}
		}
		
		else if(e.getSource() == upgradeButton1) {
			if(money-price >= 0) {
				int amount = (int) (money / price);
				money -= price * amount;
				price += 10 * amount;
				moneyPerClick += 1 * amount;
				this.updateLabels();
			}
		}
		
		else if(e.getSource() == rebirthButton) {
			if(moneyPerClick >= 50) {
				moneyMult += moneyPerClick / 500.0;
				moneyPerClick = 1;
				money = 0;
				price = 10;
				this.updateLabels();
			}
		}
		
		else if(e.getSource() == autoUpgrade) {
			if(money - 100 >= 0) {
				money -= 100;
				autoUpgradePurchased = true;
				moneyLabel.setText(String.format("Money: %.0f", money));
				autoUpgrade.setEnabled(false);
			}
		}
		
		else if(e.getSource() == audioButton) {
			if(clip.isRunning()) {
				clip.stop();
				audioButton.setText("Turn audio on");
			}
			else {
				clip.loop(Clip.LOOP_CONTINUOUSLY);
				audioButton.setText("Turn audio off");
			}
		}
		

	}
	
	public void updateLabels() {
		moneyMultLabel.setText(String.format("Prestige mult: %.1f x money", moneyMult));
		priceLabel.setText("Price: "+price);
		moneyLabel.setText(String.format("Money: %.0f", money));
		moneyGainLabel.setText("Money/Click: " + moneyPerClick);
	}

}
