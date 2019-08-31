<?php
$servername = "localhost";
$username = "root";
$password = "";
$database="dibapp";
 
try {
    $connection = new PDO("mysql:host=$servername;dbname=$database", $username, $password);
    // set the PDO error mode to exception
    $connection->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
}
catch(PDOException $e) {
    echo json_encode("Connection failed: " . $e->getMessage());
}
?>