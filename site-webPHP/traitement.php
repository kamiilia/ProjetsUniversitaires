<?php 
//si on clique sur le bouton envoyer
if(isset($_POST['envoyer'])){
	
	//récupération des données du formulaire
$nom=$_POST['nom'];//récupération du nom
$prenom=$_POST['prenom'];//récupération du prenom
$email=$_POST['email'];//récupération du email
$sujet=$_POST['sujet'];//récupération du sujet	
$statut=$_POST['statut'];//récupération du statut
$message=$_POST['message'];//récupération du message

$header="From:".$nom." ".$prenom." (".$statut.") <".$email.">";
$destination="souhila.harrat@gmail.com";//initialisation de la destination

	mail($destination,$sujet,$message,$header);//envoyer le message
	
	if(mail($destination,$sujet,$message,$header)){
		header("Location:contact.php?error=0");
		
		}else{
			header("Location:contact.php?error=1");
			}
	
	}

?>