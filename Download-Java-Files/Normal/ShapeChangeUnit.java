package nationGen.units;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.elmokki.Drawing;
import com.elmokki.Generic;

import nationGen.NationGen;
import nationGen.NationGenAssets;
import nationGen.entities.Filter;
import nationGen.entities.Pose;
import nationGen.entities.Race;
import nationGen.misc.Command;
import nationGen.misc.FileUtil;
import nationGen.naming.Name;
import nationGen.naming.NamePart;
import nationGen.nation.Nation;



public class ShapeChangeUnit extends Unit {

	public Unit otherForm;
	public ShapeShift thisForm;
	boolean sacred = false;
	public String shiftcommand = "#firstshape";
	private int gcost = 0;
	private NationGenAssets assets;
	
	public ShapeChangeUnit(NationGen nationGen, NationGenAssets assets, Race race, Pose pose, Unit otherForm, ShapeShift thisForm)
	{
		super(nationGen, race, pose);
		this.assets = assets;
		this.otherForm = otherForm;
		this.thisForm = thisForm.getCopy();
	}


	public void polish(NationGen n, Nation nation)
	{
		Filter sf = new Filter(n);
		sf.name = "Special unit";
		
		// Copy sacredness and gcost from main form
		if(otherForm != null)
		{
			for(Command c : otherForm.getCommands())
			{
				if(c.command.equals("#holy") && !thisForm.tags.contains("mount"))
				{
					sacred = true;
				}
				else if(c.command.equals("#holy") && thisForm.tags.contains("mount"))
				{
					if(otherForm.tags.contains("sacredmount"))
						sacred = true;
				}
				
				if(c.command.equals("#gcost") && !thisForm.tags.contains("nogcost"))
				{
					
					//System.out.println(c.args.get(0) + " ADDED " + " / " + otherForm.getGoldCost_DEBUG());
					gcost = Integer.parseInt(c.args.get(0));


				}
				


			}
		}
		
	
		

		
		// Copy commands from this form
		for(Command c : thisForm.commands)
		{
			if(c.command.equals("#name") && c.args.size() > 0)
			{
				c.args.set(0, "\"" + Generic.capitalize(c.args.get(0).replaceAll("\"", "")) + "\"");
				name = new Name();
				
				name.type = NamePart.newNamePart(Generic.capitalize(c.args.get(0).replaceAll("\"", "")), null);
			}
			
			if(!c.command.startsWith("#spr"))
				sf.commands.add(c);
			if(c.command.equals("#gcost") && thisForm.tags.contains("specifiedgcost"))
			{
				
				//System.out.println(c.args.get(0) + " ADDED " + " / " + otherForm.getGoldCost_DEBUG());
				sf.commands.add(c);
				gcost = Integer.parseInt(c.args.get(0));


			}
		
		}
		
		
			
		// ...and other Form
		if(otherForm != null)
		{

			
			// Inherit nametype and maxage
			
			boolean maxagefound = false;
			for(Command c : otherForm.getCommands())
				if(c.command.equals("#maxage") || c.command.equals("#nametype"))
				{
					sf.commands.add(c);
					if(c.command.equals("#maxage"))
						maxagefound = true;
				}
			
			if(!maxagefound)
			{
				sf.commands.add(new Command("#maxage", "50"));
				otherForm.commands.add(new Command("#maxage", "50"));
			}
			
		

			
			// Inherit from race/pose
			// Careful here since this stuff generally is definite instead of relative definitions
			List<Command> clist = new ArrayList<Command>();
			if(!otherForm.race.tags.contains("noinheritance"))
				clist.addAll(otherForm.race.unitcommands);
			if(!otherForm.pose.tags.contains("noinheritance"))
				clist.addAll(otherForm.pose.getCommands());
			for(Command c : clist)
			{
				if(assets.secondShapeRacePoseCommands.contains(c.command))
				{	
					sf.commands.add(c);
					//handleCommand(commands, c);
				}
			}
			
			// Inheritance from filter bonus abilities
			for(Filter f : otherForm.appliedFilters)
			{
				boolean shape = false;
				for(Command c : f.getCommands())
					if(c.command.contains("shape"))
						shape = true;
				
				if(f.tags.contains("noinheritance") != shape)
					continue;
				
				
				// Add filters
				for(Command c : f.getCommands())
				{
			
					if(assets.secondShapeNonMountCommands.contains(c.command) && !thisForm.tags.contains("mount"))
					{	
						sf.commands.add(c);
						//handleCommand(commands, c);
					}
					
					if(assets.secondShapeMountCommands.contains(c.command) && thisForm.tags.contains("mount"))
					{
						sf.commands.add(c);
						//handleCommand(commands, c);
					}
			
				}
				
			}
			
	

	
			
			/*
			// Add filters
			for(Command c : f.commands)
			{
		
				if(n.secondShapeNonMountCommands.contains(c.command) && !thisForm.tags.contains("mount"))
				{	
					handleCommand(commands, c, nation);
				}
				
				if(n.secondShapeMountCommands.contains(c.command) && thisForm.tags.contains("mount"))
				{
					//System.out.println(f.name + " -> " + c.command + " " + c.argument);
					handleCommand(commands, c, nation);
				}
		
			}
				*/
		
		
		}
		
		if(sf.commands.size() > 0)
		{
			appliedFilters.add(sf);
		}

	}
	
	
	

	
	private BufferedImage copyImage(BufferedImage image, int xoffset)
	{
		
		BufferedImage base = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		Graphics g = base.getGraphics();
		g.drawImage(image, xoffset, 0, null);
		
		
		
		if(Generic.containsTag(thisForm.tags, "recolormask"))
		{
			String file = Generic.getTagValue(thisForm.tags, "recolormask");
			BufferedImage mask = FileUtil.readImage(file);
			Drawing.recolor(mask, this.color);
			
			g.drawImage(mask, xoffset, 0, null);
		}
		
		return base;
	}
	
	@Override
	public void writeSprites(String spritedir) {
		// Handle sprites
		
		BufferedImage spr1 = null;
		for(Command c : thisForm.commands) {
			// First sprite
			if (c.command.equals("#spr1")) {
				if (c.args.get(0).equals("greyscale")) {
					
					int greyscaleunits = 0;
					if (c.args.size() > 1)
						greyscaleunits = Integer.parseInt(c.args.get(1));
					
					spr1 = Drawing.greyscale(otherForm.render(), greyscaleunits);
				} else {
					spr1 = copyImage(FileUtil.readImage(c.args.get(0)), 0);
				}
				FileUtil.writeTGA(spr1, "/mods/" + spritedir + "/shapechange_" + id + "_a.tga");
			}
		}
		
		for (Command c : thisForm.commands) {
			if(c.command.equals("#spr2"))
			{
				BufferedImage spr2;
				if(c.args.get(0).equals("shift"))
				{
					if (spr1 == null) {
						throw new IllegalStateException("Can't shift attack sprite because no #spr1 command found for shapechange unit!");
					}
					
					spr2 = copyImage(spr1, -5);
				}
				else
				{
					spr2 = copyImage(FileUtil.readImage(c.args.get(0)), 0);
				}
				FileUtil.writeTGA(spr2, "/mods/" + spritedir + "/shapechange_" + id + "_b.tga");
			}
		}
	}
	
	@Override
	public List<String> writeLines(String spritedir)
	{
		
		List<String> lines = new ArrayList<>();

		// Write the unit text
		if(otherForm != null)
			lines.add("--- Shapechange form for " + otherForm.getName());
		else
			lines.add("--- Special unit " + getName());
		
		lines.add("#newmonster " + id);


		List<Command> commands = getCommands();
		
		boolean hasDescriptionSpecified = false;
		boolean hasItemSlots = false;

		// Own non-gcost commands first due to #copystats
		for(Command c : commands)
		{
			if(c.command.equals("#gcost"))
			{
				// Do nothing
			}
			else if(c.command.equals("#itemslots"))
			{
				hasItemSlots = true;
			}
			else
			{
				lines.add(c.command + " " + Generic.listToString(c.args));
			}
			
			if(c.command.equals("#descr") && thisForm.tags.contains("specifieddescr"))
			{
				hasDescriptionSpecified = true;
			}
		}
		if (thisForm.commands.stream().anyMatch(c -> c.command.equals("#spr1"))) {
			lines.add("#spr1 \"" + spritedir + "/shapechange_" + id + "_a.tga" + "\"");
		}
		if (thisForm.commands.stream().anyMatch(c -> c.command.equals("#spr2"))) {
			lines.add("#spr2 \"" + spritedir + "/shapechange_" + id + "_b.tga" + "\"");
		}

		if(!hasDescriptionSpecified)
		{
			lines.add("#descr \"No description\"");
		}
		
		if(thisForm.keepname && otherForm != null)
			lines.add("#name \"" + otherForm.name + "\"");
		

		if(!shiftcommand.equals("") && !thisForm.tags.contains("nowayback") && otherForm != null)
		{
			lines.add(shiftcommand + " " + otherForm.id);
		}
		if(sacred)
			lines.add("#holy");
		
		if(gcost != 0)
			lines.add("#gcost " + gcost);

		
		// If there's no #copystats or defined body type, define body type (as probably humanoid unless shenanigans have been done)
		writeBodytypeLine().ifPresent(lines::add);
		
		// Write itemslots if they were skipped before
		if(hasItemSlots)
			lines.add("#itemslots " + this.getItemSlots());

		
		lines.add("#end");
		lines.add("");
		
		return lines;
	}
	

	
}
