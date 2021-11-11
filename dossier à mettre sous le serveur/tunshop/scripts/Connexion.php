<?php
$host='localhost';
$dsn = 'tunshop';
$user = 'root';
$password = '';
// array for JSON response
$response = array();
try {
  $db = new PDO("mysql:host=$host;dbname=$dsn;",$user, $password);
  $response["error"] = "0";
} catch (PDOException $e) {
    $response["error"] = "1";
   die(json_encode($response));
}
?>