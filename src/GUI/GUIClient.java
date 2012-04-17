package GUI;

import Client.GameClient;
import Client.PlayerClient;
import Client.UserClient;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import Remote.IPlayer;
import Remote.RemoteGame;
import Remote.UserObject;
import Remote.ICard;
import Remote.IGameTable;
import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 *
 * This class contains all the methods that are called by the GUI to access the
 * data stored in the server.
 * 
* @author mattvertescher & Peter
 */
public class GUIClient {

    private static String IP = "192.168.1.101"; // specifies which IP to connect
// to
    private int userId; // keeps a local copy of the user ID
    private UserClient currentUserClient; // sets up RMI
// connection
    private UserObject currentUser; // keeps a local copy of the logged on user
    private GameClient currentGameTableClient;
    private IGameTable usersGameTable;
    private PlayerClient currentPlayerClient;
    private ServerListener sl;
// Added by Matt - temp
    private ImageIcon localAvatar = new ImageIcon(
            GUIClient.class.getResource("avatars/haibo.JPG"));

    /**
     * This a constructor for the GUIClient class which retrieves data from the
     * server and displays it in the GUI
     */
    public GUIClient(ServerListener sl) {

        this.sl = sl;

        try {
            currentUserClient = new UserClient(sl);
        } catch (RemoteException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        currentGameTableClient = new GameClient();

    }

    public UserClient getCurrentUserClient() {
        return this.currentUserClient;
    }

    public GUIClient(String ip) {
    }

    public ServerListener getServerListener() {
        return this.sl;
    }

    /*
     * Update Methods
     */
    public boolean clientGameFrameDoneInitializing() {
        return true;
    }

    public boolean clientNeedsGameFrameUpdate() {
        return true;
    }

// #BeginLoginandUserMethods
    /**
     * This allows the user to change his username if he is logged on
     *     
* @param newUsername
     * @return: true if successful, false if not
     * @author Peter
     */
    public boolean setUsername(String newUsername) throws RemoteException,
            SQLException {
        if (this.currentUser != null) {
            this.currentUser.setName(newUsername);
            currentUserClient.getUserProxy().editProfile(currentUser);
            return true;
        }

        return false;
    }

    /**
     * This gets the username of the current user
     *     
* @return: either username, or message saying could not acquire name
     * @author Peter
     */
    public String getUsername() {

        if (currentUser != null) {
            return this.currentUser.getName();
        }

        return "Could not get name.";
    }

    /**
     * This allows the user to purchase more chips
     *     
* @param amount
     * @return
     * @author Peter
     */
    public boolean purchaseChips(double amount) {
        if (amount < 0.0 || amount > 999999999.9) {
            return false;
        }
        int i = -1;
        try {
            i = currentUserClient.getUserProxy().purchaseChips(currentUser,
                    amount);
        } catch (RemoteException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (i == 0) {
            return true;
        }

        return true;
    }

    /**
     * Returns the amount of money a user has
     *     
* @param username
     * @return
     */
    public String getUsersWorth(String username) throws RemoteException,
            SQLException {
        String worth = "Broke";
        UserObject desiredUser = currentUserClient.getUserProxy().getUserObject(username);
        try {
            worth = "" + desiredUser.getChips();
        } catch (NullPointerException e) {
        }
// Returns the worth of a particular player

        return worth;
    }

    /**
     * Returns whether a user is online or not
     *     
* @param username
     * @return
     * @author Peter
     */
    public boolean userOnline(String username) {
        try {
            return currentUserClient.getUserProxy().getUserObject(username).isOnline();
        } catch (RemoteException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Method tells server that a user has logged out.
     *     
* @return: boolean, successful or not
     */
    public boolean logout() throws RemoteException, SQLException {
        int i = currentUserClient.getUserProxy().logout(currentUser);
        currentUser = null;
        if (i == 0) {
            return true;
        }
        return false;
    }

    /**
     * Setter for email.
     *     
* @return
     * @author Peter
     */
    public boolean setEmail(String email) throws RemoteException, SQLException {

        currentUser.setEmail(email);
        int i = currentUserClient.getUserProxy().editProfile(currentUser);
        if (i == 0) {
            return true;
        }
        return false;
    }

    /**
     * Getter for email.
     *     
* @return
     * @author Peter
     */
    public String getEmail() {
        return currentUser.getEmail();
    }

    /**
     * Setter for password.
     *     
* @param password
     * @return
     * @author Peter
     */
    public boolean setPassword(String password) throws RemoteException,
            SQLException {
        currentUser.setPassword(password);
        int i = currentUserClient.getUserProxy().editProfile(currentUser);
        if (i == 0) {
            return true;
        }
        return false;
    }

    /**
     * Gets the password.
     *     
* @return
     * @author Peter
     */
    public String getPassword() {

        return currentUser.getPassword();
    }

    /**
     * Setter for account number.
     *     
* @param accountNumber
     * @return
     */
    public boolean setAccountNumber(String accountNumber) {

// Sets account number

        return true;
    }

    /**
     * Getter for account number.
     *     
* @return account number string
     */
    public String getAccountNumber() {

        return "BF34NFs3Ffvt345";
    }

    /**
     * This returns a list of the friends usernames of a certain user
     *     
* @param username
     * @return
     */
    public String[] getFriends(String username) throws RemoteException,
            SQLException {
        userId = currentUser.getId();
        ArrayList<UserObject> friends = currentUserClient.getUserProxy().getFriends(userId);
        String[] friendsNames = new String[friends.size()];
        int i = 0;
        for (UserObject element : friends) {
            friendsNames[i] = new String(element.getName());
            i++;
        }
        return friendsNames;

    }

    /**
     * Adds the selected user as a friend to the logged on user
     *     
* @param friend
     * @return: true or false if the add was successful
     */
    public boolean addFriend(String friend) throws RemoteException,
            SQLException {
        UserObject friendObject = currentUserClient.getUserProxy().getUserObject(friend); // need getUserObject from username
        int i = currentUserClient.getUserProxy().addFriend(userId,
                friendObject.getId());
        if (i == 0) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the current user is friends with the given username
     *     
* @param friend
     * @return true if friends
     */
    public boolean hasFriend(String friend) throws RemoteException,
            SQLException {
        String[] userFriends = getFriends(getUsername());
        for (int i = 0; i < userFriends.length; i++) {
            if (userFriends[i].equals(friend)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes a friend from a players list of friends.
     *     
* @param friend
     * @return
     * @throws SQLException
     * @throws RemoteException
     */
    public boolean deleteFriend(String friend) throws RemoteException,
            SQLException {
        UserObject friendObject = currentUserClient.getUserProxy().getUserObject(friend); // need getUserObject from username
        int i = currentUserClient.getUserProxy().deleteFriend(userId,
                friendObject.getId());
        if (i == 0) {
            return true;
        }
        return false;

    }

    /**
     * TODO: Implement Server methods for AVATAR to implement this
     *     
* @param avatar
     * @return
     * @author Peter
     */
    public boolean setAvatar(ImageIcon avatar) {
        localAvatar = avatar;
// Sets a users avatar

        return true;
    }

    /**
     * Gets the avatar for the user TODO: Need server implementation to complete
     * method
     *     
* @param username
     * @author Peter
     */
    public ImageIcon getAvatar(String username) {
        java.net.URL path = GUIClient.class.getResource("avatars/default.jpg");

        if (username.equals(getUsername())) {
            return localAvatar;
        }

        ImageIcon avatar = new ImageIcon(path);

// Returns the avatar for a particular user

        return avatar;
    }

    /**
     * Sets the avatar icon for the current user.
     *     
* @param avatar
     * @return completed
     */
    public static boolean setAvatarIcon(ImageIcon avatar) {

        return true;
    }

    /**
     * Gets the 50 x 50 avatar icon for a particular user.
     *
     * @param username
     * @return
     */
    public static ImageIcon getAvatarIcon(String username) {
        ImageIcon avatarIcon = new ImageIcon(
                GUIClient.class.getResource("images/person_icon.png"));
        return avatarIcon;
    }

    /**
     * Registers a user on the database with the given information
     *     
* @param accountInfo
     * @return: true if successful, false if not
     * @author Peter
     */
    public boolean registerNewAccount(String[] accountInfo)
            throws RemoteException, SQLException {
        UserObject newUser = new UserObject(accountInfo[2], accountInfo[4],
                accountInfo[3], 500);
// automatically gives the player 500 chips for signing up
        int i = currentUserClient.getUserProxy().signup(newUser);
        if (i == 0) {
            return true;
        }
        return false;
    }

    /**
     * This logs the user in if the email password pair is correct, sets the
     * current user to the logged in user
     *     
* @param email
     * @param password
     * @return: true if successful, false if not
     * @author Peter
     */
    public boolean testEmailPassword(String email, String password)
            throws RemoteException, SQLException {

        this.currentUser = currentUserClient.getUserProxy().login(email,
                password);
        if (currentUser != null) {
            this.userId = currentUser.getId();
            currentUserClient.registerWithServer(userId);
        }
        if (this.currentUser != null) {
            return true;
        }

        return false;
    }

    /**
     * Tells server to send a password recovery email to user.
     *     
* @param email
     * @author Peter
     */
    public boolean sendPasswordRecoveryEmail(String email)
            throws RemoteException, SQLException {
        currentUserClient.getUserProxy().recoverPassword(email);
        return true;
    }

// #ENDLOGINANDUSERMETHODS
// #BEGINGAMELOBBYMETHODS
    /**
     * This gets the list of GameTableId's from the server and returns it as an
     * array of Strings
     *     
* @return
     * @author Peter
     */
    public String[] getListOfGameTables() {
        String[] gameTables = null;
        try {
            if (currentGameTableClient.getUserProxy().getAllTables().size() != 0) {
                gameTables = new String[currentGameTableClient.getUserProxy().getAllTables().size() + 1];
                int i = 1;
                for (IGameTable element : currentGameTableClient.getUserProxy().getAllTables()) {
                    gameTables[i] = element.getName();
                    i++;
                }
            } else {
                gameTables = new String[1];
            }
        } catch (RemoteException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }

        gameTables[0] = "Default Table";

// Returns a list of the game tables on the server

        return gameTables;
    }

    /**
     * Returns whether there is already a table with that name
     *     
* @param name
     * @return
     * @throws RemoteException
     */
    public boolean checkTableName(String name) {
        try {
            return currentGameTableClient.getUserProxy().tableWithName(name);
        } catch (RemoteException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Returns the host of a particular table.
     *     
* @param table
     * @return the host of the table
     */
    public String getTableHost(String table) {
        IGameTable desiredGameTable = null;
        try {
            if (currentGameTableClient.getUserProxy().getAllTables().size() != 0
                    && !table.equals("Default Table")) {
                try {
                    desiredGameTable = currentGameTableClient.getUserProxy().getTable(table);
                } catch (RemoteException e) {
// TODO Auto-generated catch block
                    e.printStackTrace();
                }
                String hostname = null;
                try {
                    hostname = currentUserClient.getUserProxy().getUserObject(desiredGameTable.getHostId()).getName();
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (hostname != null) {
                    return hostname;
                }
            }
        } catch (RemoteException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "You are all the host.";
    }

    /**
     * This gets the players at a selected table
     *     
* @param table
     * @return: an array of stings of players in the game
     * @author Peter
     */
    public String[] getPlayersAtGameTable(String tableId) {
        IGameTable desiredTable = null;
        String[] listOfPlayers = {"Empty", "Empty", "Empty", "Empty", "Empty"};
        try {
            if (currentGameTableClient.getUserProxy().getAllTables().size() != 0
                    && !tableId.equals("Default Table")) {
                try {

                    desiredTable = currentGameTableClient.getUserProxy().getTable(tableId);

                } catch (RemoteException e) {
// TODO Auto-generated catch block
                    e.printStackTrace();
                }
                ArrayList<IPlayer> players = null;
                players = desiredTable.getPlayers();

                int i = 0;

                for (IPlayer element : players) {
                    try {
                        listOfPlayers[i] = currentUserClient.getUserProxy().getUserObject(element.getUserId()).getName();
                    } catch (RemoteException e) {
// TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (SQLException e) {
// TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    i++;
                }
            }
        } catch (RemoteException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }

// Returns a list of players names at a particular table

        return listOfPlayers;
    }

    /**
     * This gets the players currently in the game, if you're folded you are no
     * longer in the game
     *     
* @param tableId
     * @return
     * @author Peter
     */
    public String[] getPlayersInGame(String tableId) {
        RemoteGame desiredGame = null;
        String[] listOfPlayers = {"Empty", "Empty", "Empty", "Empty", "Empty"};
        try {
            if (currentGameTableClient.getUserProxy().getAllTables().size() != 0
                    && !tableId.equals("Default Table")) {
                try {

                    desiredGame = currentGameTableClient.getUserProxy().getTable(tableId).getGame();

                } catch (RemoteException e) {
// TODO Auto-generated catch block
                    e.printStackTrace();
                }
                ArrayList<IPlayer> players = null;
                players = desiredGame.getPlayers();

                int i = 0;

                for (IPlayer element : players) {
                    try {
                        listOfPlayers[i] = currentUserClient.getUserProxy().getUserObject(element.getUserId()).getName();
                    } catch (RemoteException e) {
// TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (SQLException e) {
// TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    i++;
                }
            }
        } catch (RemoteException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }

// Returns a list of players names at a particular table

        return listOfPlayers;
    }

    /**
     * This gets the players that aren't the user in the game.
     *     
* @param username
     * @param table
     * @return
     * @author Peter
     */
    public String[] getOpponentsInGame(String username, String table) {
        String[] listOfOpponents = {"Empty", "Empty", "Empty", "Empty"};
        String[] listOfPlayers = getPlayersInGame(table);
        int i = 0;
        int j = 0;
        while (i < 5) {
            if (!listOfPlayers[i].equals(username)) {
                listOfOpponents[j] = listOfPlayers[i];
                j++;
            }
            i++;
        }

        return listOfOpponents;
    }

    /**
     * Gets only the opponents other than the current user at a table
     *     
* @param username
     * @param table
     * @return
     * @author Peter
     */
    public String[] getOpponentsAtGameTable(String username, String table) {
        String[] listOfOpponents = {"Empty", "Empty", "Empty", "Empty"};
        String[] listOfPlayers = getPlayersAtGameTable(table);
        int i = 0;
        int j = 0;
        while (i < 5) {
            if (!listOfPlayers[i].equals(username)
                    && !listOfPlayers[i].equals("Empty")) {
                listOfOpponents[j] = listOfPlayers[i];
                j++;
            }
            i++;
        }

        return listOfOpponents;
    }

    public int[] getFoldedPlayers(String table) {
        ArrayList<IPlayer> foldedPlayers = new ArrayList<IPlayer>();
        int[] foldedPlayerNames = null;

        try {
            ArrayList<IPlayer> playersInTable = currentGameTableClient.getUserProxy().getTable(table).getPlayers();

            for (IPlayer element : playersInTable) {
                if (!currentGameTableClient.getUserProxy().getTable(table).getGame().isPlayerIn(element.getUserId())) {
                    foldedPlayers.add(element);
                }
            }
            if (foldedPlayers != null) {
                foldedPlayerNames = new int[foldedPlayers.size()];
            }
        } catch (RemoteException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }

        int i = 0;
        if (foldedPlayerNames != null) {
            for (IPlayer element : foldedPlayers) {
                try {
                    foldedPlayerNames[i] = element.getUserId();
                    i++;
                } catch (RemoteException e) {
// TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return foldedPlayerNames;
    }

    /**
     * Create a new game table on the server.
     *     
* @param newTableInfo = {tableNameString, anteString, bringInString}
     * @return: boolean successful or not
     * @author Peter
     */
    public boolean createNewTable(String[] newTableInfo) {
        ArrayList<Integer> playersInGame = new ArrayList<Integer>();
        playersInGame.add(currentUser.getId());
        double ante = java.lang.Double.parseDouble(newTableInfo[1]);
        double bringIn = java.lang.Double.parseDouble(newTableInfo[2]);
        IGameTable desiredTable = null;
        try {
            desiredTable = currentGameTableClient.getUserProxy().createTable(
                    (int) ante, currentUser.getId(), playersInGame, bringIn,
                    newTableInfo[0]);
        } catch (RemoteException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (desiredTable != null) {
            this.usersGameTable = desiredTable;
            try {
                currentGameTableClient.getUserProxy().getTable(desiredTable.getName()).initiateGame();
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            try {
                currentPlayerClient = PlayerClient.createPlayerProxy(userId, currentGameTableClient.getUserProxy().getTable(desiredTable.getName()).getGame(), sl);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    /**
     * This returns the ante for a given table
     *     
* @param table
     * @return
     * @author Peter
     */
    public int getTableAnte(String table) {
        try {
            if (currentGameTableClient.getUserProxy().getAllTables().size() != 0
                    && !table.equals("Default Table")) {
                return currentGameTableClient.getUserProxy().getTable(table).getAnte();
            }
        } catch (RemoteException e) {

            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Gets the bring in for a particular table.
     *     
* @param table
     * @return
     * @author Peter
     */
    public double getTableBringIn(String table) {
        try {
            if (currentGameTableClient.getUserProxy().getAllTables().size() != 0
                    && !table.equals("Default Table")) {
                return currentGameTableClient.getUserProxy().getTable(table).getBringIn();
            }
        } catch (RemoteException e) {

            e.printStackTrace();
        }
        return -1;
    }

    /**
     * This allows the player to join a Game Table, adds him to the game table
     * on the server and updates his status in the Game Table
     *     
* @param username
     * @param table
     * @return
     * @author Peter
     */
    public boolean joinGameTable(String table) {
        int i = -1;
        try {
            i = currentGameTableClient.getUserProxy().getTable(table).addPlayer(userId);

            this.usersGameTable = currentGameTableClient.getUserProxy().getTable(table);


            currentPlayerClient = PlayerClient.createPlayerProxy(userId,
                    currentGameTableClient.getUserProxy().getTable(table).getGame(), sl);
        } catch (RemoteException e) {
            
                    / e.printStackTrace();
        }
        if (i == 0 && this.usersGameTable != null) {
            return true;
        }
        return false;
    }

    /**
     * Return the current table a user is apart of.
     *     
* @return table name string
     * @author Peter
     */
    public String getCurrentTable() {
        if (this.usersGameTable != null) {
            try {
                return usersGameTable.getName();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Tells the server that a user has left the table
     *     
* @param table
     * @return boolean: successful or not
     * @author Peter
     */
    public boolean leaveGameTable(String table) {
        if (usersGameTable != null) {
            try {
                currentGameTableClient.getUserProxy().getTable(table).getGame().removePlayerProxy(userId);
                currentGameTableClient.getUserProxy().getTable(table).removePlayer(userId);

            } catch (RemoteException e) {
                e.printStackTrace();
            }
            usersGameTable = null;
            return true;
        }

        return false;
    }

    /**
     * Method called if a host wishes to start the game.
     *     
* @param table
     * @return successful or not
     * @author Peter
     */
    public boolean startGameRequest(String table) {
        try {
            if (usersGameTable != null && usersGameTable.getHostId() == userId) {
                try {
                    currentGameTableClient.getUserProxy().getTable(table).startGame();

                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                return true;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Sends a table invite to the server.
     *     
* @param table
     * @param friend
     * @return successful or not
     * @author Peter
     */
    public boolean inviteFriendToTable(String table, String friend) {
        int friendId = 0;
        try {
            friendId = currentUserClient.getUserProxy().getUserObject(friend).getId();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String msg = currentUser.getName()
                + " has invited you to the Game Table: " + table
                + ". You should go join him!";
        try {
            currentUserClient.getUserProxy().invite(userId, friendId, msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return true;
    }

// #ENDGAMELOBBYMETHODS
// #BEGINGAMEMETHODS
    /**
     * This gets the leader board from the server
     *     
* @return arrayOfStrings [20][5]
     * @author Peter
     * @throws SQLException
     */
    public String[][] retrieveStatistics() throws SQLException {
        try {
            return currentUserClient.getUserProxy().leaderBoardDisplay();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This sends the players bet to the Server
     *     
* @param bet
     * @return
     * @author Peter
     */
    public boolean sendBet(double bet) {
        try {
            if (usersGameTable.getGame() != null) {
                try {
                    if (bet == currentGameTableClient.getUserProxy().getTable(usersGameTable.getName()).getGame().getPlayer(userId).getChips()) {
                        int i = currentGameTableClient.getUserProxy().getTable(usersGameTable.getName()).getGame().allIn(userId);
                        if (i == 0) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                    if (bet > currentGameTableClient.getUserProxy().getTable(usersGameTable.getName()).getGame().getCurBet()) {
                        int i = currentGameTableClient.getUserProxy().getTable(usersGameTable.getName()).getGame().raise(userId, bet);
                        if (i == 0) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                try {
                    if (bet == currentGameTableClient.getUserProxy().getTable(usersGameTable.getName()).getGame().getCurBet()) {
                        int i = currentGameTableClient.getUserProxy().getTable(usersGameTable.getName()).getGame().call(userId);
                        if (i == 0) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This folds the player from the current game
     *     
* @return
     * @author Peter
     */
    public boolean fold() {
        if (usersGameTable != null) {
            try {
                currentGameTableClient.getUserProxy().getTable(usersGameTable.getName()).getGame().removePlayer(userId);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    /**
     * This puts the player all in
     *     
* @return
     * @author Peter
     */
    public boolean allIn() {
        if (usersGameTable != null) {
            int i = -1;
            try {
                i = currentGameTableClient.getUserProxy().getTable(usersGameTable.getName()).getGame().allIn(userId);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            if (i == 0) {
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * Gets chips for a particular user.
     *     
* @param username
     * @return
     * @author Peter
     */
    public String getChips(String username) {
        if (usersGameTable != null) {
            String i = "";
            UserObject desiredPlayer = null;
            try {
                desiredPlayer = currentUserClient.getUserProxy().getUserObject(
                        username);
            } catch (RemoteException e1) {
                e1.printStackTrace();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            try {
                if (currentGameTableClient.getUserProxy().getTable(usersGameTable.getName()).getGame().isPlayerIn(desiredPlayer.getId())) {
                    i = ""
                            + currentGameTableClient.getUserProxy().getTable(usersGameTable.getName()).getGame().getPlayer(desiredPlayer.getId()).getChips();
                } else {
                    i = ""
                            + currentUserClient.getUserProxy().getUserObject(username).getChips();
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return i;
        }
        return "0";
    }

    /**
     * Gets the running pot of a table.
     *     
* @param table
     * @return
     * @author Peter
     */
    public String getPot(String table) {
        if (usersGameTable != null) {
            String i = "";
            try {
                i = ""
                        + currentGameTableClient.getUserProxy().getTable(usersGameTable.getName()).getGame().getPot();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            return i;
        }
        return "Couldn't get pot";
    }

    /**
     * Returns the current minimum bet at a table.
     *     
* @param table
     * @return
     * @author Peter
     */
    public String getMinimumBet(String table) {
        if (usersGameTable != null) {
            try {
                String i = ""
                        + currentGameTableClient.getUserProxy().getTable(usersGameTable.getName()).getGame().getCurBet();
                return i;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return "0";
    }

    /**
     * This gets the image addresses to the players cards
     *     
* @return
     * @author
     */
    public String[] getPlayerCards() {

        if (usersGameTable != null) {
            ArrayList<ICard> cards;
            try {
                cards = currentGameTableClient.getUserProxy().getTable(usersGameTable.getName()).getGame().getPlayer(userId).getHand().getCards();
                if (currentGameTableClient.getUserProxy().getTable(usersGameTable.getName()).getGame().getPlayer(userId).getFaceDownCard() != null
                        && cards.size() < 5) {
                    cards.add(currentGameTableClient.getUserProxy().getTable(usersGameTable.getName()).getGame().getPlayer(userId).getFaceDownCard());
                }
                if (cards != null) {
                    int i = 0;
                    String[] cardStrings = new String[cards.size()];
                    for (ICard element : cards) {
                        String rank = "Nothing";
                        String r = element.getRank().toString();
                        if (r.equals("Deuce")) {
                            rank = "2";
                        }
                        if (r.equals("Three")) {
                            rank = "3";
                        }
                        if (r.equals("Four")) {
                            rank = "4";
                        }
                        if (r.equals("Five")) {
                            rank = "5";
                        }
                        if (r.equals("Six")) {
                            rank = "6";
                        }
                        if (r.equals("Seven")) {
                            rank = "7";
                        }
                        if (r.equals("Eight")) {
                            rank = "8";
                        }
                        if (r.equals("Nine")) {
                            rank = "9";
                        }
                        if (r.equals("Ten")) {
                            rank = "10";
                        }
                        if (r.equals("Jack")) {
                            rank = "j";
                        }
                        if (r.equals("Queen")) {
                            rank = "q";
                        }
                        if (r.equals("King")) {
                            rank = "k";
                        }
                        if (r.equals("Ace")) {
                            rank = "a";
                        }

                        cardStrings[i] = "cards150px/"
                                + element.getSuit().toString().toLowerCase()
                                + "-" + rank + "-150.png";
                        i++;
                    }
                    return cardStrings;
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * This gets the card image references
     *     
* @param username
     * @return
     */
    public String[] getOpponentCards(String username) {
        ArrayList<ICard> cards = null;
        if (usersGameTable != null) {
            int playerId;
            try {
                playerId = currentUserClient.getUserProxy().getUserObject(username).getId();

                cards = currentGameTableClient.getUserProxy().getTable(usersGameTable.getName()).getGame().getPlayer(playerId).getHand().getCards();

                if (cards != null) {
                    int i;
                    String[] cardStrings;
                    if (cards.size() < 5) {
                        cardStrings = new String[cards.size() + 1];
                        cardStrings[0] = "cards75px/back-blue-75-1.png";
                        i = 1;
                    } else {
                        cardStrings = new String[cards.size()];
                        i = 0;
                    }
                    for (ICard element : cards) {
                        String rank = "Nothing";
                        String r = element.getRank().toString();
                        if (r.equals("Deuce")) {
                            rank = "2";
                        }
                        if (r.equals("Three")) {
                            rank = "3";
                        }
                        if (r.equals("Four")) {
                            rank = "4";
                        }
                        if (r.equals("Five")) {
                            rank = "5";
                        }
                        if (r.equals("Six")) {
                            rank = "6";
                        }
                        if (r.equals("Seven")) {
                            rank = "7";
                        }
                        if (r.equals("Eight")) {
                            rank = "8";
                        }
                        if (r.equals("Nine")) {
                            rank = "9";
                        }
                        if (r.equals("Ten")) {
                            rank = "10";
                        }
                        if (r.equals("Jack")) {
                            rank = "j";
                        }
                        if (r.equals("Queen")) {
                            rank = "q";
                        }
                        if (r.equals("King")) {
                            rank = "k";
                        }
                        if (r.equals("Ace")) {
                            rank = "a";
                        }
                        cardStrings[i] = "cards75px/"
                                + element.getSuit().toString().toLowerCase()
                                + "-" + rank + "-75.png";
                        i++;
                    }
                    return cardStrings;
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    /**
     * This removes the player from playing the game, if he folds or exits the
     * current game
     *
     * @return
     * @author Peter
     */
    public boolean leaveGame() {
        try {
            currentGameTableClient.getUserProxy().getTable(usersGameTable.getName()).getGame().removePlayer(userId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

// #ENDGAMEMETHODS
// #BEGINCHATMETHODS
    /**
     * Sends a chat message to server.
     *
     * @param usernameSender @usernameRecipients @message
     * @return boolean successful or not
     * @author Peter
     */
    public boolean sendChatMessage(String usernameSender,
            String[] usernameRecipients, String message) {
        try {
            currentGameTableClient.getUserProxy().getTable(usersGameTable.getName()).getGame().sendMessage(usernameSender, message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return true;
    }

// #ENDCHATMETHODS
// #BEGINCONNECTIONMETHODS
    /**
     * Setter for IP.
     */
    public static void setIp(String ip) {
        IP = ip;
    }

    /**
     * Getter for IP.
     */
    public static String getIp() {
        return IP;
    }

    /**
     * Method pings server to check connection.
     */
    public static boolean ping() {

        return false;
    }
// End Connection Methods
}
