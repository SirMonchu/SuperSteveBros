package proyectoFinal.SuperSteveBros.utils;

public class Contants {
	
	public static class PlayerConstants {
		public static final int RUNNING_RIGHT = 0;
		public static final int RUNNING_LEFT = 1;
		
		public static int GetSpriteAmount(int player_action) {
			
			switch (player_action) {
			case RUNNING_RIGHT:
				return 7;
			case RUNNING_LEFT:
				return 7;
			default:
				return 7;
			}
			
		}
	}
	
}
