package database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 * This class is used to connect to <a href="http://pslc.ul.ie/hangman.sql">the hangman database</a> 
 * and perform a set of fixed operations on this database. These operations are then used by the hangman
 * Web Service to provide functionality to allow you to create and play the game.
 * @author James Murphy
 */
public class DatabaseStuff {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    /**
     * The constructor for the class. This connects to a copy of the database on your local machine and
     * initializes the connection and statement objects.
     */
    public DatabaseStuff() {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            
            // Setup the connection with the DB
            connect = DriverManager
                .getConnection("jdbc:mysql://localhost/hangman?"
                + "user=root&password=");

            statement = connect.createStatement();
            
            
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * This method is used to check login credentials against the database and returns the unique user id the supplied details if correct.
     * @param user the username you use to login - this is a string.
     * @param pass the password you use to login - this is a string.
     * @return either:<ul> 
     *          <li>the unique user id of the logged in user (this will be an integer greater than 0).</li>
     *          <li>0 (if no user with the credentials can be found.</li>
     *          <li>-2 if there is a problem connecting to the DB or DBMS.</li>
     *          <li>-1 if there is a general failure for other reasons.</li></ul>
     */
    public int checkLogin(String user, String pass) {
        String sql = "SELECT autoID FROM users WHERE username = ? AND password = PASSWORD(?) AND isactive = 1;";
        try {
            preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, pass);
            resultSet = preparedStatement.executeQuery();    
            if(resultSet.next() == false) {
                return 0;
            } else {
                String id = resultSet.getString("autoID");
                return Integer.parseInt(id);
            }
        } catch(SQLException s) {
            return -2;
        } catch(Exception i) {
            return -1;
        }
    }
    /**
     * This method is used to get the username for a supplied user id.
     * @param id the unique user id that you wish to find a name for
     * @return either:<ul>
     *          <li>the username of the user id you supplied</li>
     *          <li>"EXCEPTION_ERROR" if there is a failure for any reason.</li></ul>
     */
    public String getUsername(int id) {
        String user = "";
        String sql = "SELECT username FROM users WHERE autoID = ?";
        try {
            preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next() != false) {
                user = resultSet.getString("username");
            }
        } catch(Exception e) {
            return "EXCEPTION_ERROR";
        } 
        return user;
    }
    /**
     * This method is used to create a new user on the database.
     * @param name the name of the new user - this is a string.
     * @param surname the surname of the new user - this is a string.
     * @param username the username of the new user - this is a string.
     * @param password the password of the new user - this is a string.
     * @param email the email address of the new user - this is a string.
     * @return either:<ul>
     *          <li>the unique user id of the new user (this will be an integer greater than 0).</li>
     *          <li>0 if the details could not be entered on the DB for some reason.</li>
     *          <li>-1 if there is a general failure.</li></ul>
     */
    public int register(String name, String surname, String username, String password, String email) {
        try {
            String sql = "INSERT INTO users(name, surname, username, password, email) VALUES(?,?,?,PASSWORD(?),?)";
            preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, username);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, email);
            preparedStatement.executeUpdate();
            
            sql = "SELECT autoID FROM users WHERE username = ?";
            preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next() == false) {
                return 0;
            } else {
                return resultSet.getInt("autoID");
            }
        } catch(Exception e) {
            return -1;
        }
    }
    /**
     * This method returns the word for a particular game indicated by the game id supplied.
     * @param gid the unique id of a game on the system.
     * @return either:<ul>
     *          <li>"EXCEPTION_ERROR" if there is some general failure.</li>
     *          <li>the word for the game you indicated.</li></ul>
     */
    public String getWordFromGame(int gid) {
        String new_word = "NO_WORD_FOUND";
        String sql = "SELECT theword FROM games WHERE autoID = ?";
        try {
            preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setInt(1, gid);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next() != false) {
                new_word = resultSet.getString("theword");
            }
        } catch(Exception e) {
            return "EXCEPTION_ERROR";
        } 
        return new_word;
    }
    /**
     * This method is used to generate a random word for a new game from the list stored on the database.
     * @return either:<ul>
     *          <li>"EXCEPTION_ERROR" if there is some general failure.</li>
     *          <li>the random word</li></ul>
     */
    public String getRandomWord() {
        String new_word = "";
        String sql = "SELECT theword FROM words WHERE RAND() <= 0.00025 LIMIT 1";
        try {
            preparedStatement = connect.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next() != false) {
                new_word = resultSet.getString("theword");
            }
        } catch(Exception e) {
            return "EXCEPTION_ERROR";
        } 
        return new_word;
    }
    /**
     * This method creates a new game listing on the database for a supplied user id.
     * @param p the user id of the player of the new game
     * @return either:<ul>
     *          <li>the unique game id of the new game.</li>
     *          <li>-1 if the game could not be created on the database for some reason.</li></ul>
     */
    public int addNewGame(int p) {
        int gamestate = 0;
        String newWord = "";
        while(newWord.length() == 0) {
            newWord = getRandomWord().toLowerCase();
        }
            
        try {
            String sql = "INSERT INTO games(p1, gameState, theword) VALUES(?,?,?)";
            preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setInt(1, p);
            preparedStatement.setInt(2, gamestate);
            preparedStatement.setString(3, newWord);
            preparedStatement.executeUpdate();
            
            sql = "SELECT autoID from games WHERE gameState = ? AND p1 = ? AND theword = ?";
            preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setInt(1, gamestate);
            preparedStatement.setInt(2, p);
            preparedStatement.setString(3, newWord);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("autoID");
        } catch(Exception e) {
            return -1;
        }
    }
    /**
     * This method is used to indicate a user's guess of a letter in the game
     * @param l the letter the user is guessing is in the word.
     * @param gid the unique id of the game they are playing.
     * @param pid the players unique user id.
     * @param correct a value indicating if the guess is correct or not.
     * This method returns no values.
     */
    public void guess(String l, int gid, int pid, int correct) {
        try {
            String sql = "INSERT INTO moves(pID, gID, guess, correct) VALUES(?,?,?,?)";
            preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setInt(1, pid);
            preparedStatement.setInt(2, gid);
            preparedStatement.setString(3, l);
            preparedStatement.setInt(4,correct);
            preparedStatement.executeUpdate();
        } catch(Exception e) {
            
        }
    }
    /**
     * This method is used to resign a particular game.
     * @param p the unique user id of the player.
     * @param g the unique game id of the game.
     * This method returns no values.
     */
    public void resign(int p, int g) {
        try {
            String sql = "UPDATE games SET gameState = ? WHERE autoID = ?";
            preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setInt(1, p);
            preparedStatement.setInt(2, g);
            preparedStatement.executeUpdate();
        } catch(Exception e) {
            
        }
    }
    /**
     * This method is used to see how many incorrect guesses the user has made.
     * @param g the unique game id of the game we are checking.
     * @return either:<ul>
     *          <li>the number of incorrect guesses the user has made.</li>
     *          <li>-1 if there is a general failure.</li></ul>
     */
    public int guessesUsed(int g) {
       int guesses = 0;
        try {
            String sql = "SELECT COUNT(*) AS num_guesses FROM moves WHERE correct = 0 AND gID = ?";
            preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setInt(1, g);
            resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next() == false) {
                guesses = 0;
            } else {
                guesses = resultSet.getInt("num_guesses");
            }
        } catch(Exception e) {
            guesses = -1;
        } 
        return guesses;
    }
    /**
     * This method is used to close the connection to the DB and DBMS.
     */
    public void shutdown() {
        try {
            resultSet = null;
            preparedStatement = null;
            statement = null;
            connect.close();
            connect = null;
        } catch(Exception e) {
            
        }
    }
}