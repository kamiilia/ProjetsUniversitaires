<?php require_once('Connections/tusna.php'); ?>
<?php
if (!function_exists("GetSQLValueString")) {
function GetSQLValueString($theValue, $theType, $theDefinedValue = "", $theNotDefinedValue = "") 
{
  if (PHP_VERSION < 6) {
    $theValue = get_magic_quotes_gpc() ? stripslashes($theValue) : $theValue;
  }

  $theValue = function_exists("mysql_real_escape_string") ? mysql_real_escape_string($theValue) : mysql_escape_string($theValue);

  switch ($theType) {
    case "text":
      $theValue = ($theValue != "") ? "'" . $theValue . "'" : "NULL";
      break;    
    case "long":
    case "int":
      $theValue = ($theValue != "") ? intval($theValue) : "NULL";
      break;
    case "double":
      $theValue = ($theValue != "") ? doubleval($theValue) : "NULL";
      break;
    case "date":
      $theValue = ($theValue != "") ? "'" . $theValue . "'" : "NULL";
      break;
    case "defined":
      $theValue = ($theValue != "") ? $theDefinedValue : $theNotDefinedValue;
      break;
  }
  return $theValue;
}
}

mysql_select_db($database_tusna, $tusna);
$query_actu_aside = "SELECT `Titre_actu`,`Desc_actu`,date_format(`Date_actu`,'%d/%m/%y %h:%i') as 'date',`Photo_actu` FROM `actualites` where (`Date_actu`>= (curdate()-INTERVAL 7 DAY))and (`Date_actu`<=(curdate())) order by date_format(`Date_actu`,'%y/%m/%d %h:%i') desc ";
$actu_aside = mysql_query($query_actu_aside, $tusna) or die(mysql_error());


$row_actu_aside = mysql_fetch_assoc($actu_aside);
$totalRows_actu_aside = mysql_num_rows($actu_aside);
?>
<?php if( $row_actu_aside['Titre_actu']==null){echo 'Aaucune actualités pour la semaine';}else{ ?>
<?php do { ?>
  <div id="actualite">
  
  
  <div class="actu"> <img src="images/actualites/<?php echo $row_actu_aside['Photo_actu']; ?>" alt="actu" width="80" height="64" title="">
    <p><?php echo $row_actu_aside['Titre_actu']; ?></p>
    <p><?php echo $row_actu_aside['date']; ?></p>
    </div>
  <?php } while ($row_actu_aside = mysql_fetch_assoc($actu_aside)); }?>