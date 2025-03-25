package model;
import java.sql.Timestamp;

public class Player {
    // 1) 私有字段
    private int playerID;         // 主键
    private String username;
    private String email;
    private String serverRegion;
    private TimeStamp createdAt;
    // 如果还有别的字段，比如:
    // private Timestamp createdAt;
    // private int level;
    // ...

    public Player(int playerID, String username, String email, String serverRegion) {
        this.playerID = playerID;
        this.username = username;
        this.email = email;
        this.serverRegion = serverRegion;
    }

    public Player() {} // 无参构造

    // 3) Getter / Setter
    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getServerRegion() {
        return serverRegion;
    }

    public void setServerRegion(String serverRegion) {
        this.serverRegion = serverRegion;
    }
}
