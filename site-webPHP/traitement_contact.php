<?php
//verifier si on a cliqué sur le bouton envoyer
if(isset($_POST['envoyer'])){
	
//récupération des données du formulaire
$nom=$_POST['nom'];//récupération du nom
$prenom=$_POST['prenom'];//récupération du prenom
$email=$_POST['email'];//récupération du email
$sujet=$_POST['sujet'];//récupération du sujet	
$statut=$_POST['statut'];//récupération du statut
$message="Le statut est:".$statut."<br><br>".$_POST['message'];//récupération du message

$header="From:".$nom." ".$prenom." <".$email.">";

$destination="souhila.harrat@gmail.com";//initialisation de la destination

	//envoyer l'email
	mail($destination,$sujet,$message,$header);
	
	
	if(mail($destination,$sujet,$message,$header)){//si la fonction envoyé renvoi true
		
		header("Location:contact.php?error=0");// renvoyer a la page contact avec une variable d'url qui indique pas d'erreur
		
		}
		else{
			
			header("Location:contact.php?error=1");
			
			}
	
	
	}
?>