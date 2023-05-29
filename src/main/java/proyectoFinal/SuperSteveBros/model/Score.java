package proyectoFinal.SuperSteveBros.model;

public class Score implements Comparable<Score> {
	
    private Player player;
    private Level level;
    private int score;

    public Score(Player player, Level level, int score) {
        this.player = player;
        this.level = level;
        this.score = score;
    }

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @param player the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * @return the level
	 */
	public Level getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(Level level) {
		this.level = level;
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public int compareTo(Score o) {
		// TODO Auto-generated method stub
		return Integer.compare(this.score, o.getScore());
	}
    
    
    
}

