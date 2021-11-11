<?php
   include 'connexion.php';
// check for post data 
	if (isset($_GET["libm"])) {
	$mag = $_GET["libm"];
    $query = "Select libm,adresse,ouverture,fermeture,lat,lng,ville,logo from magasin m,pointsvente c,geo_point g where 
	(m.mid=c.idm) and (g.id_g=c.id_g) and (libm like '$mag'); ";
try {
    $stmt   = $db->prepare($query);
    $result = $stmt->execute();
	$rows = $stmt->fetchAll();
if ($rows) {
    $response["success"] = 1;
    $response["message"] = "Post Available!";
    $response["posts"]   = array();

    foreach ($rows as $row) {
        $post             = array();
         $post["libm"] = $row["libm"];
        $post["adresse"]    = $row["adresse"];
        $post["ouverture"]    = $row["ouverture"];
         $post["fermeture"]    = $row["fermeture"];
         $post["lat"] = $row["lat"];
        $post["lng"]    = $row["lng"];
		$post["logo"]    = $row["logo"];
		$post["ville"]    = $row["ville"];
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
catch (PDOException $ex) {
    $response["success"] = 0;
    $response["message"] = "Database Error!";
    die(json_encode($response));

}
}
else{
echo "no result !!!";
$response["success"] = 0;
$response["message"] = "champs vide !!";
}