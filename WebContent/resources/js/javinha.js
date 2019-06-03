function mudarFormulario(direcao){
	if(direcao == "login"){
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
	}else if(direcao=="registro"){
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
			url: "adminLoginForm.xhtml",
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