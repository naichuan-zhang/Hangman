<?php

    $wsdl = "http://localhost:8080/hangmanWebService/hangman?WSDL";
    $trace = true;
    $exceptions = true;
    
    global $isRegistered;
    $isRegistered = false;

    try {
	$proxy = new SoapClient($wsdl, 
                array('trace' => $trace, 'exceptions' => $exceptions));
        
    } catch (Exception $ex) {
	echo $ex->getMessage();
    }
