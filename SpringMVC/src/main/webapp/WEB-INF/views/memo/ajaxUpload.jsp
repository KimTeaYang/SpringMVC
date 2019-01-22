<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<style>
	.fileDrop{
		width:100%;
		height:200px;
		border: 2px dashed blue;
		overflow: scroll;
	}
</style>
    <H1>Ajax File Upload Form</H1>
    <div>
    	<form id="frm" action="insertData">
    		
    	</form>
    
    </div>
    
    <div class="fileDrop"></div>
    <div class="uploadedList"></div>
    <script type="text/javascript">
		$('.fileDrop').on("dragenter dragover", function(evt){
			evt.preventDefault();
		});
		$('.fileDrop').on('drop',function(evt){
			event.preventDefault();
			var files=evt.originalEvent.dataTransfer.files;
			var file=files[0];
			console.log(file); 
			
			var formData=new FormData();
			//HTML5에서 지원하는 FormData객체를 사용하면 form태그로
			//만든 데이터 전송방식과 동일하게 파일 데이터를 전송할 수 있따.
			formData.append("file",file);
			alert(JSON.stringify(formData));
			
			$.ajax({
				type:'POST',
				url:'ajaxUpload',
				data:formData,
				dataType:'text',
				processData:false, 
				/*
				processData는 데이터를 일반적인 쿼리스트링으로 변환할 것인지를 결정함
				processData의 기본값은 true. 이는 기본값으로 
				'application/x-www-form-urlencode'타입으로 전송함.
				다른 타입으로 보내기 위해서는 false를 지정*/
				contentType:false,
				/*contentType의 기본값 역시 'application/x-www-form-urlencode'이므로
				multipart/form-data로 보내려면 false를 지정해야 함*/
				success:function(data){
					//alert(data);
					if(setInput(data)){
						showImage(data);
					}
				}
				
			});
			/* $.each(files,function(i, file){
				console.log(file);
			}); */
			
		});
		var cnt=0;
		function setInput(data){
			if(cnt<3){
				var str="";
				str+="<input type='text' name='file' value='"+data+"' multiple>";
				$('#frm').append(str);
				cnt++;
				return true;
			}else{
				alert('업로드 파일 갯수는 3개까지 입니다');
				return false;
			}
		}//--------------------------------
		
		function showImage(data){
			var str="";		
			if(checkImageType(data)){
				str="<span>";
				str+="<img src='Upload/"+data+"' class='img-thumbnail' style='width:25%'>"+data+"</span>";
				$('.fileDrop').append(str);
			}else{
				str="<div>"+data+"</div>";
				$('.fileDrop').append(str);
			}
		}
		
		function checkImageType(fileName){
			var pattern=/jpg|gif|png|jpeg/i;
			return fileName.match(pattern);
		}
	</script>
    
    
    
    