package com.scs.rogueframework;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.scs.astrocommander.Settings;
import com.scs.rogueframework.FrameworkMapSquare.VisType;
import com.scs.rogueframework.ecs.entities.AbstractMob;
import com.scs.rogueframework.ecs.entities.DrawableEntity;

import ssmith.util.SortedArrayList;

public abstract class FrameworkMapSquare {

	public enum VisType {Hidden, Seen, Visible};

	public VisType visible = VisType.Hidden;
	public int x, y;

	protected static TextCharacter hiddenChar = new TextCharacter(' ', TextColor.ANSI.BLACK, TextColor.ANSI.BLACK); 
	protected TextCharacter seenChar; 
	protected TextCharacter visibleChar; 

	protected SortedArrayList<DrawableEntity> entities = new SortedArrayList<>();
	protected List<DrawableEntity> entitiesToAdd = new ArrayList<>(); // todo - remove these!
	protected List<DrawableEntity> entitiesToRemove = new ArrayList<>();

	public FrameworkMapSquare(int _x, int _y) {
		x = _x;
		y = _y;
	}


	protected void processItems() {
		for (DrawableEntity de : this.entities) {
			de.process();
		}
	}


	public void updateItemList() {
		boolean calcChar = this.entitiesToAdd.size() > 0 || this.entitiesToRemove.size() > 0;
		this.entities.removeAll(this.entitiesToRemove);
		entitiesToRemove.clear();
		this.entities.addAllWithSort(this.entitiesToAdd);
		entitiesToAdd.clear();

		if (calcChar) {
			this.calcChar();
		}
	}

	
	public abstract void calcChar();

	
	public TextCharacter getChar() {
		if (Settings.DEBUG) {
			return this.visibleChar;
		}
		if (this.visible == VisType.Hidden) {
			return hiddenChar;//' ';
		} else if (this.visible == VisType.Seen) { 
			return this.seenChar;
		} else {
			return this.visibleChar;
		}
	}


	public void addEntity(DrawableEntity de) {
		de.x = x;
		de.y = y;
		this.entitiesToAdd.add(de);
		//this.calcChars();
	}


	public void removeEntity(DrawableEntity de) {
		this.entitiesToRemove.add(de);
		//this.calcChars();
	}


	public DrawableEntity getEntity(int i) {
		return this.entities.get(i);
	}


	public List<DrawableEntity> getEntities() {
		return this.entities;
	}


	public Iterator<DrawableEntity> getIterator() {
		return this.entities.iterator();
	}


	public DrawableEntity getEntityOfType(Class c) {
		for (DrawableEntity e : getEntities()) {
			if (e.getClass() == c) {
				return e;
			}
		}
		return null;
	}


	public AbstractMob getUnit() {
		for (DrawableEntity de : this.getEntities()) {
			if (de instanceof AbstractMob) {
				AbstractMob unit = (AbstractMob) de;
				if (unit.isAlive()) {
					return unit;
				}
			}
		}
		return null;
	}


	public abstract boolean isTraversable();
	
	protected abstract boolean isTraversable_Sub();

	public abstract boolean isTransparent();
	
	protected abstract boolean isTransparent_Sub();

	protected abstract char getFloorChar();

	protected abstract Color getBackgroundColour();

	public abstract String getName();
	
	public abstract String getHelp();


}
