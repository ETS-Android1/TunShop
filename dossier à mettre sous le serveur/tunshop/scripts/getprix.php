<?php
  include 'connexion.php'; 

if (isset($_GET["codep"]) && isset($_GET["libm"])) {
    $codep = $_GET['codep'];
	$mag=$_GET['libm'];
// check for post data 
    $query = "Select libp,prixp from magasin m,magasin_produit 
	c,produit p where (m.mid=c.mid) and (p.codep=c.codep) and (c.codep= '$codep') and (m.libm='$mag')";
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
        $post["prixp"]    = $row["prixp"];
		$post["libp"]    = $row["libp"];
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
}
else{
    $response["success"] = 0;
    $response["message"] = "No price is found";
    die(json_encode($response)); 
}
?>
}
?>