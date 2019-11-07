<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <meta charset="UTF-8">
        <title>Home</title>
        <link rel="stylesheet" href="main.css" media="all" />
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    </head>
    <body>
        <div id="header">
            <p>Hangman</p>
        </div>
        <div id="welcome">
            <p>Welcome to Hangman Game!</p>
        </div>
        <div id="buttons">
            <form action="" method="post">
                <input type="submit" name="registerButton"
                       id="registerButton" value="Register" />
                <input type="submit" name="loginButton"
                       id="loginButton" value="Login" />
            </form>
        </div>
        <div id="message">
        </div>
        <?php
            if (isset($_POST['registerButton'])) {
                header('Location:register.php');
            }
            if (isset($_POST['loginButton'])) {
                header('Location:login.php');
            }
        ?>
    </body>
</html>
