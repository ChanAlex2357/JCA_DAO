package jca.dao.conn;

public class ConnConfig {
    private String  driver;
    private String  database;
    private String  host;
    private int     port;
    private String  dbname;
    private String  username;
    private String  password;
    private String  url;
    
    public ConnConfig(String driver, String database, String host, int port, String dbname, String username,
        String password) {
        setDriver(driver);
        setDatabase(database);
        setHost(host);
        setPort(port);
        setDbname(dbname);
        setUsername(username);
        setPassword(password);

        setUrl();
    }
    public String getDbname() {
        return dbname;
    }
    public void setDbname(String dbname) {
        this.dbname = dbname;
    }
    public String getDriver() {
        return driver;
    }
    public void setDriver(String driver) {
        this.driver = driver;
    }
    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getDatabase() {
        return database;
    }
    public void setDatabase(String database) {
        this.database = database;
    }

// URL DE CONNECTION
    protected String urlPattern(){
        return "jdbc:"+getDatabase()+"://"+getHost()+":"+getPort()+"/"+getDbname();
    }
    private void setUrl(){
        this.url = urlPattern();
    }
    public String getUrl(){
        return this.url;
    }
}