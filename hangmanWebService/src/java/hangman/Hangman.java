/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangman;

import database.DatabaseStuff;
import java.sql.SQLException;
import javax.jws.Oneway;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 * This is the Web Service providing the Controllers for the Model of the Hangman game. The game should be created using the
 * MVC (Model-View-Controller) paradigm and as the Model and Controllers have been provided to you, your job is to create
 * the Views that allow the user to interact with the system. You will need to build a system that has a minimum of four screens:
 * namely a login screen (to allow the user to login), a registration screen (to allow a new user to add their details to the 
 * system, a main menu screen where the user can create a new game after logging in successfully, and a game screen to allow them
 * to play the game. For today's lab please work on creating and getting to work the first three screens. Note: no more than
 * one screen should be visible to the user at any one time.
 * @author James Murphy
 */
@WebService(serviceName = "hangman")
public class Hangman {

     /**
     * This web service operation is used to check credentials of a user attempting to login. It uses the checkLogin 
     * method of the DatabaseStuff class to work. Please review the DatabaseStuff Javadoc for more details.
     * @param uname the username of the person attempting to login.
     * @param pword the password of the person attempting to login.
     * @return Please review the DatabaseStuff Javadoc entries for the checkLogin method for details on return values.
     */
    @WebMethod(operationName = "checkLogin")
    public int checkLogin(@WebParam(name = "uname") String uname, @WebParam(name = "pword") String pword) {
        int id = 0;
        DatabaseStuff h = new DatabaseStuff();
        id = h.checkLogin(uname, pword);
        h.shutdown();
        return id;
    }

    /**
     * This web service operation is used to get a random word for a new game. It uses the getRandomWord 
     * method of the DatabaseStuff class to work. Please review the DatabaseStuff Javadoc for more details.
     * @return Please review the DatabaseStuff Javadoc entries for the getRandomWord method for details on return values.
     */
    public String getRandomWord() {
        DatabaseStuff h = new DatabaseStuff();
        String myWord = h.getRandomWord();
        h.shutdown();
        return myWord;
    }

    /**
     * This web service operation is used to create a new game. It uses the addNewGame 
     * method of the DatabaseStuff class to work. Please review the DatabaseStuff Javadoc for more details.
     * @param p1 the unique user id of the player starting the new game.
     * @return Please review the DatabaseStuff Javadoc entries for the addNewGame method for details on return values.
     */
    @WebMethod(operationName = "newGame")
    public int newGame(@WebParam(name = "p1") int p1) {
        int gameId = 0;
        DatabaseStuff h = new DatabaseStuff();
        gameId = h.addNewGame(p1);
        h.shutdown();
        return gameId;
    }

    /**
     * This web service operation is used to add a new user to the system. It uses the register 
     * method of the DatabaseStuff class to work. Please review the DatabaseStuff Javadoc for more details.
     * @param name the name of the new user.
     * @param surname the surname of the new user.
     * @param username the username of the new user.
     * @param password the password for the new user.
     * @param email the email address of the new user.
     * @return Please review the DatabaseStuff Javadoc entries for the register method for details on return values.
     */
    @WebMethod(operationName = "registerUser")
    public int registerUser(
            @WebParam(name = "name") String name, 
            @WebParam(name = "surname") String surname, 
            @WebParam(name = "username") String username, 
            @WebParam(name = "password") String password, 
            @WebParam(name = "email") String email) {
        int uid = 0;
        DatabaseStuff h = new DatabaseStuff();
        uid = h.register(name, surname, username, password, email);
        h.shutdown();
        return uid;
    }

    /**
     * This web service operation is used to retrieve the word associated with a particular game. It uses the getWordFromGame 
     * method of the DatabaseStuff class to work. Please review the DatabaseStuff Javadoc for more details.
     * @param gid the unique game id of the game being played.
     * @return Please review the DatabaseStuff Javadoc entries for the getWordFromGame method for details on return values.
     */
    @WebMethod(operationName = "getWord")
    public String getWord(@WebParam(name = "gid") int gid) {
        String myword = "";
        DatabaseStuff h = new DatabaseStuff();
        myword = h.getWordFromGame(gid);
        h.shutdown();
        return myword;
    }

    /**
     * This web service operation is used to check a guess a user has made. It uses the getWordFromGame 
     * method of the DatabaseStuff class to find the word associated with a particular game and then checks the guess the 
     * user has made to see if it is correct. It then uses the guess method of the DatabaseStuff class to record the guess.
     * Please review the DatabaseStuff Javadoc for more details.
     * @param letter the guess the user has made.
     * @param player the unique user id of the player making the guess.
     * @param game the unique game id of the game the player is playing.
     * @return Please review the DatabaseStuff Javadoc entries for the getWordFromGame and guess methods for details on return values.
     */
    @WebMethod(operationName = "addGuess")
    public String addGuess(@WebParam(name = "letter") String letter, @WebParam(name = "player") int player, 
            @WebParam(name = "game") int game) {
        DatabaseStuff h = new DatabaseStuff();
        int correct = 0;
        String myword = h.getWordFromGame(game);
        String[] bits = myword .split("");
        String result = "";
        for(int i=0;i<bits.length;i++) {
            if(bits[i].equals(letter) == true) {
                result += i + ",";
            }
        }
        if(result.length() > 0) {
            correct = 1;
            result = result.substring(0,result.length()-1);
        }
        
        h.guess(letter, game, player, correct);
        h.shutdown();
        return result;
    }

    /**
     * This web service operation is used to check how many incorrect guesses a user has made for a particular game. 
     * It uses the guessesUsed method of the DatabaseStuff class to work. 
     * Please review the DatabaseStuff Javadoc for more details.
     * @param gID the unique game id of the game you are checking. 
     * @return Please see the DatabaseStuff Javadoc entry for return values.
     */
    @WebMethod(operationName = "guessesUsed")
    public int guessesUsed(@WebParam(name = "gID") int gID) {
        DatabaseStuff h = new DatabaseStuff();
        int guesses = h.guessesUsed(gID);
        h.shutdown();
        return guesses;
    }

    /**
     * This web service operation is used to get the username of a particular user. It uses the getUsername 
     * method of the DatabaseStuff class to work. Please review the DatabaseStuff Javadoc for more details.
     * @param uid the unique user id for the user whose username you are requesting.
     * @return Please see the DatabaseStuff Javadoc entry for getUsername method for return values 
     */
    @WebMethod(operationName = "getUsername")
    public String getUsername(@WebParam(name = "uid") int uid) {
        String username = "";
        DatabaseStuff h = new DatabaseStuff();
        username = h.getUsername(uid);
        h.shutdown();
        return username;
    
    }
}
