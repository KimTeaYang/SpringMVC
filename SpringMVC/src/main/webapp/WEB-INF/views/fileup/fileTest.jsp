<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!doctype html>
<html>
<head>

<title>::FILE::</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="text/javascript"
	src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
<script type="text/javascript"
	src="http://netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<link
	href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">
<link
	href="http://pingendo.github.io/pingendo-bootstrap/themes/default/bootstrap.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div class="container">
		<div class="text-center">
			<h1>File Upload</h1>
			<form name="f" method="post" enctype="multipart/form-data"
				action="fileUp">
				<table class="table">
					<tr>
						<th>올린이</th>
						<td><input type="text" name="name" class="form-control">
						</td>
					</tr>
					<tr>
						<th>파일첨부</th>
						<td><input type="file" name="filename" class="form-control">
						</td>
					</tr>
					<tr>
						<td colspan="2" class="text-center">
							<button class="btn btn-success">Upload</button>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>