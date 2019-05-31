package com.scs.rogueframework.map;

import java.util.List;

import com.scs.rogueframework.ecs.entities.DrawableEntity;
import com.scs.rogueframework.ecs.entities.FrameworkMob;
import com.scs.rogueframework.map.FrameworkMapSquare.VisType;

public interface IMapSquare {

	VisType isVisible();
	
	void addEntity(DrawableEntity o);
	
	void removeEntity(DrawableEntity o);
	
	boolean isTraversable();
	
	boolean isTransparent();
	
	String getName();
	
	DrawableEntity getEntity(int i);
	
	int getType();
	
	List<DrawableEntity> getEntities();
	
	FrameworkMob getUnit(); // todo - rename
	
	DrawableEntity getEntityOfType(Class c);
}
