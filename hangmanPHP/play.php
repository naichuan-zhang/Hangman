<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <meta charset="UTF-8">
        <title>Play</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    </head>
    <body>
        <?php
            session_start();
            include 'soap.php';
            include 'images.php';
            echo("<img src=\"$image[0]\" />");
            $guessesUsed = 1;
        ?>
        <div>
            <p>The number of guesses you used:<?php echo $guessesUsed; ?></p>
        </div>
        <form>
            Guess:<input type="text" name="guessWord"/>
            <input type="submit" name="guessButton" value="Guess"/>
        </form>
        <?php 
            
        ?>
    </body>
</html>
