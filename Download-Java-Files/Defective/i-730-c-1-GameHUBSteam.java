package GUIs;

import javax.swing.JPanel;

import org.json.JSONException;

import APIS.TwitchTopGamesAPI;
import APIS.steamAPI;
import DAOs.GamesDAO;
import GUIs.GameListPanel.MyMouseListener;
import clout.managing.MainHub;

import java.awt.Color;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.Cursor;
import javax.swing.border.LineBorder;
import java.awt.FlowLayout;

public class GameHUBSteam extends JPanel {
	
	steamAPI steamAPI = new steamAPI();
	private final JPanel panelNews = new JPanel();
	private final JLabel labelTitle1 = new JLabel("Title");
	private final JLabel labelTitle2 = new JLabel("Title");
	private final JLabel labelTitle3 = new JLabel("Title");
	private String appID;
	private String gamePath;
	private String gameName;
	private final JLabel lblNews = new JLabel("Neuste Steam News");
	private final JLabel labelBackground = new JLabel("");
	private final JLabel label = new JLabel("");
	private final JLabel labelGameName = new JLabel("Spiel Name");
	private final JLabel label_1 = new JLabel("Spiel entfernen");
	private final JLabel label_2 = new JLabel("Spiel editieren");
	private final JLabel label_3 = new JLabel("");
	private final JLabel label_4 = new JLabel("");
	private final JLabel lblSpielStarten = new JLabel("Spiel starten");
	private final JLabel label_6 = new JLabel("");
	private GamesDAO gamesDAO;
	TwitchTopGamesAPI twitchTopGamesAPI = new TwitchTopGamesAPI();
	private final JPanel panelTwitch = new JPanel();
	private final JLabel labelTwitchTitle1 = new JLabel("Titel");
	private final JLabel labelTwitchCover1 = new JLabel("");
	private final JLabel labelTwitchUser1 = new JLabel("StreamerName");
	private final JLabel labelTwitchLogo1 = new JLabel("");
	private final JLabel labelTwitchViewer1 = new JLabel("Viewer");
	private final JLabel labelTwitchCover2 = new JLabel("");
	private final JLabel labelTwitchLogo2 = new JLabel("");
	private final JLabel labelTwitchViewer2 = new JLabel("Viewer");
	private final JLabel labelTwitchTitle2 = new JLabel("Titel");
	private final JLabel labelTwitchUser2 = new JLabel("StreamerName");
	private final JLabel labelTwitchCover3 = new JLabel("");
	private final JLabel labelTwitchLogo3 = new JLabel("");
	private final JLabel labelTwitchViewer3 = new JLabel("Viewer");
	private final JLabel labelTwitchTitle3 = new JLabel("Titel");
	private final JLabel labelTwitchUser3 = new JLabel("StreamerName");
	private MainHub mainHub;
	private final JLabel lblNotFound = new JLabel();
	private final JPanel panelButtons = new JPanel();

	
	/**
	 * Create the panel.
	 * @throws SQLException 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws JSONException 
	 */
	public GameHUBSteam() {
		initGUI();
		this.gamesDAO=new GamesDAO();
		{
			this.panelTwitch.setVisible(false);
			this.panelTwitch.setBounds(50, 91, 780, 193);
			this.panelTwitch.setBackground(Color.decode("#54575c").brighter());
			add(this.panelTwitch);
		}
		this.panelTwitch.setLayout(null);
		{
			this.labelTwitchViewer1.setForeground(Color.WHITE);
			this.labelTwitchViewer1.setBounds(33, 115, 46, 14);
			this.panelTwitch.add(this.labelTwitchViewer1);
		}
		{
			this.labelTwitchTitle1.setFont(new Font("Tahoma", Font.BOLD, 11));
			this.labelTwitchTitle1.setBounds(77, 144, 155, 14);
			this.panelTwitch.add(this.labelTwitchTitle1);
		}
		{
			this.labelTwitchCover1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			this.labelTwitchCover1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					do_labelTwitchCover1_mouseClicked(arg0);
				}
			});
			this.labelTwitchCover1.setBounds(27, 15, 225, 119);
			this.panelTwitch.add(this.labelTwitchCover1);
		}
		{
			this.labelTwitchUser1.setBounds(77, 162, 165, 14);
			this.panelTwitch.add(this.labelTwitchUser1);
		}
		{
			this.labelTwitchLogo1.setBounds(27, 143, 46, 32);
			this.panelTwitch.add(this.labelTwitchLogo1);
		}
		{
			this.labelTwitchViewer2.setForeground(Color.WHITE);
			this.labelTwitchViewer2.setBounds(287, 115, 46, 14);
			this.panelTwitch.add(this.labelTwitchViewer2);
		}
		{
			this.labelTwitchCover2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			this.labelTwitchCover2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					do_labelTwitchCover2_mouseClicked(e);
				}
			});
			this.labelTwitchCover2.setBounds(281, 15, 225, 119);
			this.panelTwitch.add(this.labelTwitchCover2);
		}
		{
			this.labelTwitchLogo2.setBounds(281, 143, 46, 32);
			this.panelTwitch.add(this.labelTwitchLogo2);
		}
		{
			this.labelTwitchTitle2.setFont(new Font("Tahoma", Font.BOLD, 11));
			this.labelTwitchTitle2.setBounds(331, 144, 155, 14);
			this.panelTwitch.add(this.labelTwitchTitle2);
		}
		{
			this.labelTwitchUser2.setBounds(331, 162, 165, 14);
			this.panelTwitch.add(this.labelTwitchUser2);
		}
		{
			this.labelTwitchViewer3.setForeground(Color.WHITE);
			this.labelTwitchViewer3.setBounds(541, 115, 46, 14);
			this.panelTwitch.add(this.labelTwitchViewer3);
		}
		{
			this.labelTwitchCover3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			this.labelTwitchCover3.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					do_labelTwitchCover3_mouseClicked(e);
				}
			});
			this.labelTwitchCover3.setBounds(535, 15, 225, 119);
			this.panelTwitch.add(this.labelTwitchCover3);
		}
		{
			this.labelTwitchLogo3.setBounds(535, 143, 46, 32);
			this.panelTwitch.add(this.labelTwitchLogo3);
		}
		{
			this.labelTwitchTitle3.setFont(new Font("Tahoma", Font.BOLD, 11));
			this.labelTwitchTitle3.setBounds(585, 144, 155, 14);
			this.panelTwitch.add(this.labelTwitchTitle3);
		}
		{
			this.labelTwitchUser3.setBounds(585, 162, 165, 14);
			this.panelTwitch.add(this.labelTwitchUser3);
		}
		{
			this.lblNotFound.setVisible(false);
			this.lblNotFound.setBorder(new LineBorder(new Color(128, 0, 0)));
			this.lblNotFound.setForeground(new Color(255, 255, 255));
			this.lblNotFound.setHorizontalAlignment(SwingConstants.CENTER);
			this.lblNotFound.setFont(new Font("Ubuntu", Font.PLAIN, 26));
			this.lblNotFound.setBounds(96, 291, 692, 100);
			add(this.lblNotFound);
		}
		{	
			this.panelButtons.setBackground(Color.decode("#54575c"));
			this.panelButtons.setBounds(75, 684, 732, 63);
			add(this.panelButtons);
		}
		this.panelButtons.setLayout(null);
		steamAPI = new steamAPI();
		twitchTopGamesAPI = new TwitchTopGamesAPI();
	}
	
	
	private void initGUI() {
		this.setBounds(315, 0, 879, 750);
		this.setBackground(Color.decode("#54575c"));
		setLayout(null);
		{
			this.labelGameName.setHorizontalAlignment(SwingConstants.CENTER);
			this.labelGameName.setForeground(Color.WHITE);
			this.labelGameName.setFont(new Font("Ubuntu", Font.BOLD, 22));
			this.labelGameName.setBounds(10, 23, 859, 40);
			add(this.labelGameName);
		}
		{
			this.panelNews.setVisible(false);
			this.panelNews.setBounds(137, 312, 610, 361);
			this.panelNews.setBackground(Color.decode("#54575c"));
			add(this.panelNews);
		}
		this.panelNews.setLayout(null);
		{
			this.labelTitle1.setForeground(Color.WHITE);
			this.labelTitle1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			this.labelTitle1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					try {
						do_labelTitle1_mouseClicked(arg0);
					} catch (IOException | JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			this.labelTitle1.setHorizontalAlignment(SwingConstants.CENTER);
			this.labelTitle1.setFont(new Font("Ubuntu", Font.PLAIN, 18));
			this.labelTitle1.setBounds(10, 89, 590, 60);
			this.panelNews.add(this.labelTitle1);
		}
		{
			this.labelTitle2.setForeground(Color.WHITE);
			this.labelTitle2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			this.labelTitle2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					try {
						do_labelTitle2_mouseClicked(e);
					} catch (IOException | JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			this.labelTitle2.setHorizontalAlignment(SwingConstants.CENTER);
			this.labelTitle2.setFont(new Font("Ubuntu", Font.PLAIN, 18));
			this.labelTitle2.setBounds(10, 179, 590, 60);
			this.panelNews.add(this.labelTitle2);
		}
		{
			this.labelTitle3.setForeground(Color.WHITE);
			this.labelTitle3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			this.labelTitle3.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					try {
						do_labelTitle3_mouseClicked(e);
					} catch (IOException | JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			this.labelTitle3.setHorizontalAlignment(SwingConstants.CENTER);
			this.labelTitle3.setFont(new Font("Ubuntu", Font.PLAIN, 18));
			this.labelTitle3.setBounds(10, 269, 590, 60);
			this.panelNews.add(this.labelTitle3);
		}
		{
			this.lblNews.setForeground(Color.WHITE);
			this.lblNews.setHorizontalAlignment(SwingConstants.CENTER);
			this.lblNews.setFont(new Font("Ubuntu", Font.BOLD, 18));
			this.lblNews.setBounds(10, 25, 590, 40);
			this.panelNews.add(this.lblNews);
		}
		{
			this.label.setBorder(new LineBorder(Color.LIGHT_GRAY));
			this.label.setBounds(157, 70, 300, 2);
			this.panelNews.add(this.label);
		}
		{
			this.labelBackground.setBounds(0, 0, 610, 361);
			this.panelNews.add(this.labelBackground);
		}
		{
			this.label_2.setBounds(300, 18, 142, 34);
			this.panelButtons.add(this.label_2);
			this.label_2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			this.label_2.setHorizontalAlignment(SwingConstants.CENTER);
			this.label_2.setForeground(Color.WHITE);
			this.label_2.setFont(new Font("Ubuntu", Font.BOLD, 16));
		}
		{
			this.label_4.setBounds(300, 18, 142, 34);
			this.panelButtons.add(this.label_4);
			this.label_4.setForeground(Color.WHITE);
			this.label_4.setBorder(new LineBorder(new Color(148, 0, 211), 34, true));
		}
		{
			this.label_1.setBounds(72, 18, 142, 34);
			this.panelButtons.add(this.label_1);
			this.label_1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					do_label_1_mouseClicked(e);
				}
			});
			this.label_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			this.label_1.setHorizontalAlignment(SwingConstants.CENTER);
			this.label_1.setForeground(Color.WHITE);
			this.label_1.setFont(new Font("Ubuntu", Font.BOLD, 16));
		}
		{
			this.label_3.setBounds(72, 18, 142, 34);
			this.panelButtons.add(this.label_3);
			this.label_3.setForeground(Color.WHITE);
			this.label_3.setBorder(new LineBorder(Color.RED, 34, true));
		}
		{
			this.lblSpielStarten.setBounds(519, 18, 142, 34);
			this.panelButtons.add(this.lblSpielStarten);
			this.lblSpielStarten.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			this.lblSpielStarten.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					try {
						do_lblSpielStarten_mouseClicked(e);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			this.lblSpielStarten.setHorizontalAlignment(SwingConstants.CENTER);
			this.lblSpielStarten.setForeground(Color.WHITE);
			this.lblSpielStarten.setFont(new Font("Ubuntu", Font.BOLD, 16));
		}
		{
			this.label_6.setBounds(519, 18, 142, 34);
			this.panelButtons.add(this.label_6);
			this.label_6.setForeground(Color.WHITE);
			this.label_6.setBorder(new LineBorder(new Color(0, 128, 128), 34, true));
		}
	}
	
	public void loadData(String appID, String gamePath, String gameName, MainHub mainHub) throws IOException {
		this.appID = appID;
		this.mainHub = mainHub;
		this.gamePath = gamePath;
		this.gameName = gameName;
		this.panelButtons.setBounds(75, 684, 732, 63);
		lblNotFound.setVisible(false);
		if(appID.equals("null")) {
			panelNews.setVisible(false);
			labelGameName.setText(gameName);
			panelTwitch.setVisible(true);
			panelTwitch.setBounds(50, 300, 788, 193);
			try {
				loadTwitchTopStreams(gameName);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			panelTwitch.setBounds(50, 91, 788, 193);
			panelNews.setVisible(true);
			panelTwitch.setVisible(true);
			String imageURL;
			try {
				labelGameName.setText("<html><center>"+ steamAPI.getSteamAppFullName(appID) +"<br/></html>");
				
				labelTitle1.setText("<html><center>"+ steamAPI.getSteamNewsTitle(appID, 1) +"<br/></html>");
				labelTitle2.setText("<html><center>"+ steamAPI.getSteamNewsTitle(appID, 2) +"<br/></html>");
				labelTitle3.setText("<html><center>"+ steamAPI.getSteamNewsTitle(appID, 3) +"<br/></html>");
				
				loadTwitchTopStreams(steamAPI.getSteamAppFullName(appID));
				imageURL = steamAPI.getSteamAppBackground(appID);
				URL urlImage = new URL(imageURL);
				BufferedImage iconImage = ImageIO.read(urlImage);
				Image scaledImage = iconImage.getScaledInstance(610, 361,  java.awt.Image.SCALE_SMOOTH); 
				labelBackground.setIcon(new ImageIcon(scaledImage));
			} catch (JSONException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	protected void do_labelTitle1_mouseClicked(MouseEvent arg0) throws IOException, JSONException {
		java.awt.Desktop.getDesktop().browse(java.net.URI.create(steamAPI.getSteamNewsURL(appID, 1)));
	}
	protected void do_labelTitle2_mouseClicked(MouseEvent e) throws IOException, JSONException{
		java.awt.Desktop.getDesktop().browse(java.net.URI.create(steamAPI.getSteamNewsURL(appID, 2)));
	}
	protected void do_labelTitle3_mouseClicked(MouseEvent e) throws IOException, JSONException{
		java.awt.Desktop.getDesktop().browse(java.net.URI.create(steamAPI.getSteamNewsURL(appID, 3)));
	}
	protected void do_lblSpielStarten_mouseClicked(MouseEvent e) throws IOException {
		if(this.gamePath.contains(".lnk")) {
			Process p = Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL " +
	                 gamePath);	
		} else {
			Runtime.getRuntime().exec(this.gamePath);	
		}
	}
	protected void do_label_1_mouseClicked(MouseEvent e) {
		try {
			gamesDAO.deleteGame(gameName);
			setVisible(false);
			mainHub.reopen();
		} catch (ClassNotFoundException | IOException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private void loadTwitchTopStreams(String gameName) throws IOException, JSONException {
		try {
			String statusTwitch1 = twitchTopGamesAPI.getTopStreamerStatus(gameName, 1);
			labelTwitchTitle1.setText(statusTwitch1);
			labelTwitchTitle1.setToolTipText(statusTwitch1);
			labelTwitchUser1.setText(twitchTopGamesAPI.getTopStreamerName(gameName, 1));
			
			labelTwitchViewer1.setText(twitchTopGamesAPI.getTopStreamerViewer(gameName, 1));
			
			String statusTwitch2 = twitchTopGamesAPI.getTopStreamerStatus(gameName, 2);
			labelTwitchTitle2.setText(statusTwitch2);
			labelTwitchTitle2.setToolTipText(statusTwitch2);
			labelTwitchUser2.setText(twitchTopGamesAPI.getTopStreamerName(gameName, 2));
			
			labelTwitchViewer2.setText(twitchTopGamesAPI.getTopStreamerViewer(gameName, 2));
			
			String statusTwitch3 = twitchTopGamesAPI.getTopStreamerStatus(gameName, 3);
			labelTwitchTitle3.setText(statusTwitch3);
			labelTwitchTitle3.setToolTipText(statusTwitch3);
			labelTwitchUser3.setText(twitchTopGamesAPI.getTopStreamerName(gameName, 3));
			labelTwitchViewer3.setText(twitchTopGamesAPI.getTopStreamerViewer(gameName, 3));
			
			labelTwitchCover1.setIcon(new ImageIcon(getTwitchCover(gameName, 1)));
			labelTwitchLogo1.setIcon(new ImageIcon(getTwitchLogo(gameName, 1)));
			labelTwitchCover2.setIcon(new ImageIcon(getTwitchCover(gameName, 2)));
			labelTwitchLogo2.setIcon(new ImageIcon(getTwitchLogo(gameName, 2)));
			labelTwitchCover3.setIcon(new ImageIcon(getTwitchCover(gameName, 3)));
			labelTwitchLogo3.setIcon(new ImageIcon(getTwitchLogo(gameName, 3)));	
			
		} catch (Exception e) {
			panelTwitch.setVisible(false);
			lblNotFound.setText("<html><center>"+ "Leider konnten wir zu " + gameName + " nichts finden, trotzdem k\u00F6nnen Sie das Spiel wie gewohnt starten, editieren oder entfernen" +"<br/></html>");
			lblNotFound.setVisible(true);
			this.panelButtons.setBounds(75, 550, 732, 63);
		}
		
		
	}
	
	private Image getTwitchCover(String gameName , int id) throws IOException, JSONException {
		
		String imageURL = twitchTopGamesAPI.getTopStreamerPreviewPicture(gameName, id).toString();
		imageURL = imageURL.replace("{width}x{height}", "1024x1024");
		URL url = new URL(imageURL);
		BufferedImage iconImage = ImageIO.read(url.openStream());
		Image scaledImage = iconImage.getScaledInstance(225, 119,  java.awt.Image.SCALE_SMOOTH); 
		return scaledImage;
	}
	
	private Image getTwitchLogo(String gameName , int id) throws IOException, JSONException {
		
		String imageURLTop = twitchTopGamesAPI.getTopStreamerLogo(gameName, id).toString();
		URL url = new URL(imageURLTop);
		BufferedImage iconImage = ImageIO.read(url.openStream());
		Image scaledImage = iconImage.getScaledInstance(46, 32,  java.awt.Image.SCALE_SMOOTH); 
		return scaledImage;
	}
	protected void do_labelTwitchCover1_mouseClicked(MouseEvent arg0) {
		try {
			if(appID.equals("null")) {
				java.awt.Desktop.getDesktop().browse(java.net.URI.create(twitchTopGamesAPI.getTopStreamerURL(gameName , 1)));
			} else {
				java.awt.Desktop.getDesktop().browse(java.net.URI.create(twitchTopGamesAPI.getTopStreamerURL(steamAPI.getSteamAppFullName(appID), 1)));
			}
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	protected void do_labelTwitchCover2_mouseClicked(MouseEvent e) {
		try {
			if(appID.equals("null")) {
				java.awt.Desktop.getDesktop().browse(java.net.URI.create(twitchTopGamesAPI.getTopStreamerURL(gameName , 2)));
			} else {
				java.awt.Desktop.getDesktop().browse(java.net.URI.create(twitchTopGamesAPI.getTopStreamerURL(steamAPI.getSteamAppFullName(appID), 2)));
			}
		} catch (IOException | JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	protected void do_labelTwitchCover3_mouseClicked(MouseEvent e) {
		try {
			if(appID.equals("null")) {
				java.awt.Desktop.getDesktop().browse(java.net.URI.create(twitchTopGamesAPI.getTopStreamerURL(gameName , 3)));
			} else {
				java.awt.Desktop.getDesktop().browse(java.net.URI.create(twitchTopGamesAPI.getTopStreamerURL(steamAPI.getSteamAppFullName(appID), 3)));
			}
		} catch (IOException | JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
