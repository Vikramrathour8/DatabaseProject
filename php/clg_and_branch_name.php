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
			$query = "SELECT clg_name, branch_name FROM college, branch WHERE branch_id IN ( SELECT branch_id FROM register where std_id = 100) AND clg_id IN ( SELECT clg_id FROM register where std_id = student_id)";


		 	$result = $conn->query($query);

		 	if($result->num_rows > 0) {
		    
		    while($row = $result->fetch_assoc()) {
		             
				$data[]=$row;
		 
		    }
		    echo json_encode($data);
			} else 
			{
		    echo "0 results";
			
			}

		
			$conn->close();
			

?>