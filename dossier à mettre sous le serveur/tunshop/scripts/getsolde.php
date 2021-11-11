<?php
  include 'connexion.php';
  
if (isset($_GET["code_carte"])) {
    $code = $_GET['code_carte'];
// check for post data 
    $query = "Select nbrpoints,validite from cartefid WHERE(code_carte= '$code')";
try {

    $stmt   = $db->prepare($query);
    $result = $stmt->execute();
}

catch (PDOException $ex) {
    $response["success"] = 0;
    $response["message"] = "Database Error!";
    die(json_encode($response));

}
$rows = $stmt->fetchAll();
if ($rows) {
    $response["success"] = 1;
    $response["message"] = "Post Available!";
    $response["posts"]   = array();


    foreach ($rows as $row) {
        $post             = array();
        $post["nbrpoints"]    = $row["nbrpoints"];
		$post["validite"]    = $row["validite"];
        //update our repsonse JSON data
        array_push($response["posts"], $post);

    }
    // echoing JSON response
    echo json_encode($response);

} else {

    $response["success"] = 0;
    $response["message"] = "Nothins Available!";
    die(json_encode($response));

}
}
else{
    $response["success"] = 0;
    $response["message"] = "No points are found";
    die(json_encode($response)); 
}
?>
}
?>