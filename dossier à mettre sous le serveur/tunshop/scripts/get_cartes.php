<?php
  include 'connexion.php';
    $query = "Select distinct libm,libf,logo FROM magasin m ,cartefid f where (f.mid=m.mid)";
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
        $post["libm"] = $row["libm"];
        $post["libf"]    = $row["libf"];
		 $post["logo"]    = $row["logo"];
        //update our repsonse JSON data
        array_push($response["posts"], $post);

    }
    // echoing JSON response
    echo json_encode($response);

} else {

    $response["success"] = 0;
    $response["message"] = "No Post Available!";
    die(json_encode($response));

}
?>
}
?>