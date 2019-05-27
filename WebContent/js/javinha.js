function mudarFormulario(atual){
	if(atual == "login"){
		$.ajax({
			url: "registroForm.xhtml",
			before: function(){
				$(".loginContent").attr("uk-spinner", "");
			},
			success: function(resposta){
				$(".loginContent").removeAttr("uk-spinner");
				$(".loginContent").html(resposta);
			}
		})
	}else{
		$.ajax({
			url: "loginForm.xhtml",
			before: function(){
				$(".loginContent").attr("uk-spinner", "");
			},
			success: function(resposta){
				$(".loginContent").removeAttr("uk-spinner");
				$(".loginContent").html(resposta);
			}
		})
	}
	
	
}