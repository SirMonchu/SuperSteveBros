package proyectoFinal.SuperSteveBros.utils;

public class ConnectionData {
    private String server;
    private String database;
    private String username;
    private String password;

    public ConnectionData(String server, String database, String username, String password) {
        this.server = server;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    public String getServer() {
        return server;
    }

    public String getDatabase() {
        return database;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
