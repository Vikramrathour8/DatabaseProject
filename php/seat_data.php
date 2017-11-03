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

			student_id = $_POST['std_id'];
			$query = "SELECT clg_id, branch_id FROM college, branch";


		 	$result = $conn->query($query);

		 	if($result->num_rows > 0) {
		    
		    while($row = $result->fetch_assoc()) {
		             
				$query= "insert into '$row->'clg_id'', '$row->'branch_id'', 5  ";

					$conn->query($query);
		 
		    }
		   
			} else 
			{
		    echo "0 results";
			
			}

		
			$conn->close();
			

?>