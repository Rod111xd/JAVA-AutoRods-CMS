//aula de Ajax
function iniciandoAjax(){ //iniciando Ajax
	var objAjax = false; //variavel que vai receber o objeto ajax
	if(window.XMLHttpRequest){// Firefox e demais browsers
		objAjax = new XMLHttpRequest();
	}else if(window.ActiveXObject){ //verifica se é o IE                              
		try{// versão mais atualizada da Microsoft
			objAjax = new ActiveXObject("Msxml.XMLHTTP");
		}catch(e){
			try{
				objAjax = new ActiveXObject("Microsoft.XMLHTTP");
			}catch(e){
				objAjax = false;
			}
		}
	}else{
		alert("Seu browser não suporta AJAX.");
	}
	return objAjax;
}
function carregando(container){ // Recebe Elemento como argumento
	// Verifica se elemento possui nós filhos
	while(container.hasChildNodes()){ 
		// Remove último elemento filho
		container.removeChild(container.lastChild);
	}
	// Cria elemento IMG
	var imagem = document.createElement("img");
	// Define os atributos
	imagem.setAttribute("src","foto_p/preload.gif");
	imagem.setAttribute("style","width: 20px;height: 20px;display:block;margin-left: auto;margin-right:auto;margin-bottom:auto;");
	imagem.setAttribute("width","100");
	imagem.setAttribute("height","100");
	// Adiciona imagem como nó filho do elemento
	container.appendChild(imagem);
}
function mostraResposta(ajax, elemento){//passando o elemento como parâmetro e o ajax
	if(ajax.readyState == 4){
		if(ajax.status >= 200 && ajax.status < 300){
			elemento.innerHTML= ajax.responseText;
		}else{
			elemento.innerHTML = "erro no servidor"
		}
	}
};
function requisitarArquivo(elemento, arquivo){
	var ajax = iniciandoAjax();
    if(ajax){
		ajax.onreadystatechange = function(){ //é executado quando o estado da requisição muda
			mostraResposta(ajax, elemento);
		}
	}
	ajax.open("GET", arquivo);
	ajax.send(null); 
}
