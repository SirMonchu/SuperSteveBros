package proyectoFinal.SuperSteveBros.utilz;

import proyectoFinal.SuperSteveBros.Game;

public class Constants {
	
	public static class UI{
		public static class Buttons {
			public static final int BUTTONS_WIDTH_DEF = 140;
			public static final int BUTTONS_HEIGHT_DEF = 56;
			public static final int BUTTONS_WIDTH = (int) (BUTTONS_WIDTH_DEF * Game.SCALE);
			public static final int BUTTONS_HEIGHT = (int) (BUTTONS_HEIGHT_DEF * Game.SCALE);
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
