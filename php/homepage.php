<? php
    
    
            $username = "id3032504_project";
            $password = "project";
            $dbname = "id3032504_satish";
            $server = "localhost";
            
            $conn = new mysqli($server, $username, $password, $dbname);   
            /* check connection */
            if (mysqli_connect_errno()) {
                printf("Connect failed: %s\n", mysqli_connect_error());
                exit();
            }         

            $query = "SELECT * from name_and_age where age = 20";


            $result = $conn->query($query);



            if($result->num_rows > 0) {
            
            while($row = $result->fetch_assoc()) {
                     
                $data = array('Name' =>$row["Name"], 'Age' => $row["Age"]);
                echo json_encode($data);
            }
            } else 
            {
            echo "0 results";
            
            }
            $conn->close();
            
?>