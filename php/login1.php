
        <?php
            $username = "id3032504_project";
            $password = "project";
            $dbname = "id3032504_satish";
            $server = "localhost";
            
            $conn = new mysqli($server, $username, $password, $dbname);
            
            if($conn->connect_error){
                die("Connection failed ".$conn->connect_error);
            }


            $query= " select * from login_table where login_username = '$_POST["username"]'' and login_password = '$_POST["password"]'";
            $result =$conn -> query($query) ;

            if(mysqli_num_rows($result) > 0) {
            
                $data = "one";
                echo json_encode($data);
           
            } else 
            {
                $data = "empty";
                echo json_encode($data);
            
            }
       
            $conn->close();
        ?>
        
     