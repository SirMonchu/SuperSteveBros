package proyectoFinal.SuperSteveBros.utilz;

import proyectoFinal.SuperSteveBros.Game;

public class Constants {
	
	public static class EnemyConstants {
		public static final int ZOMBIE = 0;

		public static final int MOVING_RIGHT = 0;
		public static final int MOVING_LEFT = 1;
		public static final int ATTACK_RIGHT = 2;
		public static final int ATTACK_LEFT = 3;
		public static final int IDLE_RIGHT = 4;
		public static final int IDLE_LEFT = 5;
		public static final int DIE_RIGHT = 6;
		public static final int DIE_LEFT = 7;
		
		public static final int ZOMBIE_WIDTH_DEF = 40;
		public static final int ZOMBIE_HEIGHT_DEF = 56;
		public static final int ZOMBIE_WIDTH = (int) (ZOMBIE_WIDTH_DEF * Game.SCALE);
		public static final int ZOMBIE_HEIGHT = (int) (ZOMBIE_HEIGHT_DEF * Game.SCALE);
//		
		
//		public static final int CRABBY = 0;
//
//		public static final int IDLE = 0;
//		public static final int RUNNING = 1;
//		public static final int ATTACK = 2;
//		public static final int HIT = 3;
//		public static final int DEAD = 4;
//
//		public static final int CRABBY_WIDTH_DEFAULT = 72;
//		public static final int CRABBY_HEIGHT_DEFAULT = 32;
//
//		public static final int CRABBY_WIDTH = (int) (CRABBY_WIDTH_DEFAULT * Game.SCALE);
//		public static final int CRABBY_HEIGHT = (int) (CRABBY_HEIGHT_DEFAULT * Game.SCALE);

		public static int GetSpriteAmount(int enemyType, int enemyState) {
			switch (enemyType) {
			case ZOMBIE:
				switch (enemyState) {
				case MOVING_RIGHT:
				case MOVING_LEFT:
					return 14;
				case ATTACK_RIGHT:
				case ATTACK_LEFT:
					return 11;
				case IDLE_RIGHT:
				case IDLE_LEFT:
				case DIE_RIGHT:
				case DIE_LEFT:
					return 1;
				}
			}
			return 0;
		}
		
//		switch (enemyType) {
//		case CRABBY:
//			switch (enemyState) {
//			case IDLE:
//				return 9;
//			case RUNNING:
//				return 6;
//			case ATTACK:
//				return 7;
//			case HIT:
//				return 4;
//			case DEAD:
//				return 5;
//			}
//		}
//
//		return 0;
//
//	}
	}
	
	public static class UI{
		public static class Buttons {
			public static final int BUTTONS_WIDTH_DEF = 140;
			public static final int BUTTONS_HEIGHT_DEF = 56;
			public static final int BUTTONS_WIDTH = (int) (BUTTONS_WIDTH_DEF * Game.SCALE);
			public static final int BUTTONS_HEIGHT = (int) (BUTTONS_HEIGHT_DEF * Game.SCALE);
		}
		
		public static class PauseButtons {
			public static final int SOUND_SIZE_DEF = 42;
			public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEF * Game.SCALE);
		}
		
		public static class ControlButtons {
			public static final int CONTROL_SIZE_DEF = 56;
			public static final int CONTROL_SIZE = (int) (CONTROL_SIZE_DEF * Game.SCALE);
		}
		
		public static class VolumeButton {
			public static final int VOLUME_WIDTH_DEF = 28;
			public static final int VOLUME_HEIGHT_DEF = 44;
			public static final int SLIDER_WIDTH_DEF = 215;

			public static final int VOLUME_WIDTH = (int) (VOLUME_WIDTH_DEF * Game.SCALE);
			public static final int VOLUME_HEIGHT = (int) (VOLUME_HEIGHT_DEF * Game.SCALE);
			public static final int SLIDER_WIDTH = (int) (SLIDER_WIDTH_DEF * Game.SCALE);
		}
	}
	
	public static class Directions {
		
		public static final int LEFT = 0;
		public static final int	UP = 1;
		public static final int	RIGHT = 2;
		public static final int	DOWN = 3;
		public static final int	SHIFT = 4;
		public static final int	CONTROL = 5;
	}
	
	public static class PlayerConstants {
		public static final int WALKING_RIGHT = 0;
		public static final int WALKING_LEFT = 1;
		public static final int RUNNING_RIGHT = 2;
		public static final int RUNNING_LEFT = 3;
		public static final int IDLE = 4;
		public static final int DAMAGE = 5;
		public static final int SNEAKING_RIGHT = 6;
		public static final int SNEAKING_LEFT = 7;
		
		public static int GetSpriteAmount(int player_action) {
			
			switch (player_action) {
			case WALKING_RIGHT:
			case WALKING_LEFT:
				return 11;
			case RUNNING_RIGHT:
			case RUNNING_LEFT:
				return 10;
			case IDLE:
			case DAMAGE:
			case SNEAKING_RIGHT:
			case SNEAKING_LEFT:
				return 1;
			default:
				return 1;
			}
			
		}
	}
	
}
