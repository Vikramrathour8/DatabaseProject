<?php
            echo "Hello";
            $username = "root";
            $password = "";
            $dbname = "id3032504_satish";
            $server = "localhost";
            
            $conn = new mysqli($server, $username, $password, $dbname);   
            /* check connection */
			if (mysqli_connect_errno()) {
			    printf("Connect failed: %s\n", mysqli_connect_error());
			    exit();
			}         

            if(isset($_POST['college_id']) && isset($_POST['student_id']) && isset($_POST['branch_id']))
            {   
            	echo "hi";
	            $student_id = $_POST['student_id'];
	            $college_id = $_POST['college_id'] ;
	            $branch_id = $_POST['branch_id']; 
	            
	            $query = " insert into register values ('$student_id', '$college_id, '$branch_id' ) ";
             	if ($conn->query($query) === TRUE) {
				    echo "New record created successfully";
				} else {
				    echo "Error: " . $sql . "<br>" . $conn->error;
				}
				        
            }
?>