package proyectoFinal.SuperSteveBros.entities;

import static proyectoFinal.SuperSteveBros.utilz.Constants.EnemyConstants.*;

public class Zombie extends Enemy {

	public Zombie(float x, float y) {
		super(x, y, ZOMBIE_WIDTH, ZOMBIE_HEIGHT, ZOMBIE);
//		super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT, CRABBY);
	}
	
}
