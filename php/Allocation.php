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

			$query = "SELECT * FROM register";

			$result = $conn->query($query);


		 	if($result->num_rows > 0) {
		    
		    while($row = $result->fetch_assoc()) {
		             
				$data[] = array($row['branch_id'], $row['student_id'], $row['college_id']);
		
		    }
			

		 	
		
			$conn->close();
			

?>