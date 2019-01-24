<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>
<head>
<title>::메모장 수정::</title>
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
		<div class="rows">
			<div class="col-md-8 col-md-offset-2">

				<form role="form" name="memo" method="post" action="edit"
					class="form-horizontal">

					<h1 class="text text-center">:::Spring's 한줄 메모장 Edit:::</h1>
					<div>
						<div style="min-height: 250px;">
							<img src="../images/${memo.filename}"
								class="img-thumbnail img-responsive">
						</div>
					</div>
					<label for="idx" class="control-label"> 글 번 호 : </label> <input
						type="text" name="idx" id="idx" class="form-control"
						value="${memo.idx}" readonly /> 
						<label for="name"> 작 성 자
						: </label> <input type="text" name="name" id="name" class="form-control"
						value="${memo.name}" /> 
						<label for="msg"> 메모내용: </label> 
						<input type="text"
						name="msg" id="msg" class="form-control" value="${memo.msg}" /> 
						<label for="wdate">작성일:</label> <span><fmt:formatDate value="${memo.wdate}" pattern="yyyy-MM-dd hh:mm:ss"/></span>
						
					<p></p>

					<button type="submit" class="btn btn-primary">
						<i class="fa fa-edit fa-fw fa-lg"></i> Edit
					</button>
					<a href="memos" class="btn btn-danger"> 
					<i class="fa fa-align-justify fa-fw fa-lg"></i> List
					</a>
					<button type="button" onclick="fileDown('${memo.filename}')" class="btn btn-success">
						<i class="fa fa-edit fa-fw fa-lg"></i> Download
					</button>
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		function fileDown(fname){
			// IE의 경우 한글에 해당하는 부분을 자바스크립트에서 encodeURIComponent(); 처리를 해준다.
			location.href="../fileDown?fname="+encodeURIComponent(fname);
		}
	</script>
</body>

</html>
