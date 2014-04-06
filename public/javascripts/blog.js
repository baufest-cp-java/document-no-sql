$("#like").on(function(){
	var permalink = this.name;
	$.post("/like/" + permalink,
	  null,
	  function(data,status){
		alert("SE ENVIO UN LIKE");
//	    alert("Data: " + data + "\nStatus: " + status);
	  });
})