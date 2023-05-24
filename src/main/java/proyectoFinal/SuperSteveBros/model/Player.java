package proyectoFinal.SuperSteveBros.model;

public class Player {
    private int id;
    private String username;
    private String password;

    public Player(String username, String password) {
        this.username = username;
        this.password = password;
    }

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	@Override
	public String toString() {
		return "Player [id=" + id + ", username=" + username + ", password=" + password + "]";
	}
    
}

