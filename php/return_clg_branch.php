<?php
	
	
			$username = "id3032504_satish";
            $password = "project2015";
            $dbname = "id3032504_counselling_system";
            $server = "localhost";
            
            $conn = new mysqli($server, $username, $password, $dbname);   
            /* check connection */
			if (mysqli_connect_errno()) {
			    printf("Connect failed: %s\n", mysqli_connect_error());
			    exit();
			}         

			$student_id = $_POST['std_id'];
			$query = "";


		 	$result = $conn->query($query);

		 	
		    $row = $result->fetch_assoc();
		    
		   	echo json_encode($row);
			

		
			$conn->close();
			

?>