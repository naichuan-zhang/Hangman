<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <meta charset="UTF-8">
        <title>Game</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    </head>
    <body>
        <form action="" class="buttons" method="post">
            <input type="submit" name="newGame" value="New Game"/>
        </form>
        <?php
            session_start();
            require 'soap.php';
            
            $pid = $_SESSION['pid'];
            
            if (isset($_POST['newGame'])) {
                
                //$gid = $proxy->newGame($pid);
                $_SESSION['gid'] = $gid;
                header("Location:play.php");
            }
        ?>
    </body>
</html>
