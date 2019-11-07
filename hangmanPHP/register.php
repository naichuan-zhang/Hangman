<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <meta charset="UTF-8">
        <title>Register</title>
        <link rel="stylesheet" href="main.css" media="all"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script>
            
        </script>
    </head>
    <body>
        <div id="header">
            <p>Register</p>
        </div>
        <form name="register" method="post">
            Name:<br/>
            <input type="text" name="name" id="name"/>
            <br/>
            Surname:<br/>
            <input type="text" name="surname" id="surname"/>
            <br/>
            Username:<br/>
            <input type="text" name="username" id="username"/>
            <br/>
            Password:<br/>
            <input type="password" name="password" id="password"/>
            <br/>
            Email:<br/>
            <input type="text" name="email" id="email"/>
            <br/>
            <input type="submit" value="Register" name="registerButton"/>
        </form>
        <?php
            require 'soap.php';
            session_start();
            
            if (isset($_POST['registerButton'])) {
                $xml_array['name'] = $_POST['name'];
                $xml_array['surname'] = $_POST['surname'];
                $xml_array['username'] = $_POST['username'];
                $xml_array['password'] = $_POST['password'];
                $xml_array['email'] = $_POST['email'];
                
                if (!empty($xml_array['name']) && !empty($xml_array['surname']) && !empty($xml_array['username'])
                        && !empty($xml_array['password']) && !empty($xml_array['email'])) {

                    $pid = $proxy->registerUser($xml_array);
                    $isRegistered = true;
                    $_SESSION['pid'] = $pid;
                    header("Location:game.php");
                    
                } else {
                    echo "Please fill all blanks!";
                    $isRegistered = false;
                    die();
                }
            }
        ?>
    </body>
</html>
