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

			
			$query = "SELECT std_id FROM score order by (rank)";

		 	$result = $conn->query($query);

		
		    
		   	while($row = $result->fetch_assoc()) {
		             
				$query= "select clg_id,branch_id from register where std_id = $row['std_id']";

				$result1  = $conn->query($query);

				while($row1 = $result1->fetch_assoc()){

						echo $row1['clg_id']." ".$row1['branch_id'].<br>;


				}
		 
		    }
		   
		
			$conn->close();
			

?>