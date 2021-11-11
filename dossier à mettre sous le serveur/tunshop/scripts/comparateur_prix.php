<?php
   include 'connexion.php';
if (isset($_GET["codep"])) {
    $codep = $_GET['codep'];
// check for post data 
    $query = "Select distinct desprod,img,logo,ROUND(prixp,3) as 'prixp',libm,libp from magasin m,magasin_produit 
	c,produit p where (m.mid=c.mid) and (p.codep=c.codep) and c.codep= '$codep' order by prixp ASC";
try {

    $stmt   = $db->prepare($query);
    $result = $stmt->execute();
}

catch (PDOException $ex) {
    $response["success"] = 0;
	$response["error"] = "1";
    $response["message"] = "Database Error!";
    die(json_encode($response));

}
$rows = $stmt->fetchAll();
if ($rows) {
    $response["success"] = 1;
	$response["error"] = "0";
    $response["message"] = "Post Available!";
    $response["posts"]   = array();


    foreach ($rows as $row) {
        $post             = array();
        $post["libm"] = $row["libm"];
        $post["prixp"]    = $row["prixp"];
		$post["libp"] = $row["libp"];
		$post["logo"] = $row["logo"];
		$post["img"] = $row["img"];
		$post["desprod"] = $row["desprod"];
        //update our repsonse JSON data
        array_push($response["posts"], $post);

    }
    // echoing JSON response
    echo json_encode($response);

} else {
    $response["success"] = 0;
	$response["error"] = "0";
    $response["message"] = "No Post Available!";
    die(json_encode($response));

}
}
else{
    $response["success"] = 0;
	$response["error"] = "0";
    $response["message"] = "No codep is found";
    die(json_encode($response)); 
}
?>
}
?>