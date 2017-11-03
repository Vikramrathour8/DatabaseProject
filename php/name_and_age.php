<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <meta charset="UTF-8">
        <title></title>
    </head>
    <body>
        <?php
            $username = "project";
            $password = "project2015";
            $dbname = "id3032504_counsellingsystem";
            $server = "localhost";
            
            $conn = mysqli($server, $username, $password, $dbname);
            
            if($conn->connect_error){
                die("Connection failed ".$conn->connect_error);
            }
            
            $query= "insert into name_and_age values( " + $_POST["name"] + "," +$_POST["number"] + ")";
                
            if($conn -> query($query)){
                echo "new record inserted successfully ";
            }
            else{
                echo "error";
            }
       
            $conn->close();
        ?>
        
        
        <form method="post">
            name = <input type="text" name="name">
            age = <input type="number" name="number">
             
        </form>
         
            
    </body>
</html>
