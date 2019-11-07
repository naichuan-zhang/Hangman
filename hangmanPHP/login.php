<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <meta charset="UTF-8">
        <title>Login</title>
        <link rel="stylesheet" href="main.css" media="all" />
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        
    </head>
    <body>
        <div id="header">
            <p>Login</p>
        </div>
        <form id="login" method="post">
            Username:<br/>
            <input type="text" name="usernameLogin" id="usernameLogin"/>
            <br/>
            Password:<br/>
            <input type="password" name="passwordLogin" id="passwordLogin"/>
            <br/>
            <input type="submit" value="Login" name="loginButton" id="loginButton"/>
        </form>
        <?php
            require 'soap.php';
            session_start();
            
            if (isset($_POST['loginButton'])) {
                
                $usernameLogin = $_POST['usernameLogin'];
                $passwordLogin = $_POST['passwordLogin'];
                

                if (!empty($usernameLogin) && !empty($passwordLogin)) {

                    $pid = $proxy->checkLogin($usernameLogin, $passwordLogin);
                    
                    if ($pid == 0) {
                        echo "Login Failed!";
                    } else if ($pid < 0) {
                        echo "Login Failed!";
                    } else {
                        $_SESSION['pid'] = $pid;
                        header('Location:game.php');
                    }
                    
                } else {
                    echo "Please fill all blanks!";
                    $isRegistered = false;
                    die();
                }
            }
        ?>
    </body>
</html>
