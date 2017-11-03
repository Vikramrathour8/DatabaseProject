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
			$college_id = $_POST['clg_id'];
			$branch_id = $_POST['branch_id'];



			$query = "delete from register where std_id = '$student_id' and clg_id = 'college_id' and branch_id = 'branch_id' ";


		 	$result = $conn->query($query);

		 	
		    	echo "success";
		   
		   
			} else 
			{
		   		 echo "failed";
			
			}

		
			$conn->close();
			

?>