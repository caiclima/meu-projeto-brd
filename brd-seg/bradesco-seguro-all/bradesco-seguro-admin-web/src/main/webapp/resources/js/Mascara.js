function mascara(o, f) {
	v_obj = o
	v_fun = f
	setTimeout("execmascara()", 1);
}
function execmascara() {
	v_obj.value = v_fun(v_obj.value);
}

function soNumeros(v) {
	return v.replace(/\D/g, "");
}

function mDecimal(v){
    v=v.replace(/\D/g,"");//Remove tudo o que não é dígito
    v=v.replace(/(\d)(\d{8})$/,"$1.$2");//coloca o ponto dos milhões
    v=v.replace(/(\d)(\d{5})$/,"$1.$2");//coloca o ponto dos milhares
 
    v=v.replace(/(\d)(\d{2})$/,"$1,$2");//coloca a virgula antes dos 2 últimos dígitos
    return v;
}

function validaHora(v) {
	var hora = v.replace(/\D/g, "");
	if( hora == '' ) {
		return '';
	}
	
	if( Number(hora) > 23 ) {
		return '';
	}
	
	return hora;
}

function validaMinuto(v) {
	var minuto = v.replace(/\D/g, "");
	if( minuto == '' ) {
		return '';
	}
	
	if( Number(minuto) > 59 ) {
		return '';
	}
	
	return minuto;
}

function soNumerosHora(v) {
	v = v.replace(/\D/g, "");
	v=v.replace(/^([0-9]{3}\.?){3}-[0-9]{2}$/,"$1.$2");
    // v=v.replace(/(\d{3})(\d)/g,"$1,$2")
	v=v.replace(/(\d)(\d{2})$/,"$1,$2"); // Coloca ponto antes dos 2 �ltimos
											// digitos
	return v;
}
function telefone(v) {
	v = v.replace(/\D/g, "");// Remove tudo o que n�o � d�gito
	v = v.replace(/^(\d\d)(\d)/g, "($1)$2"); // Coloca par�nteses em volta
												// dos dois primeiros d�gitos
	v = v.replace(/(\d{4})(\d)/, "$1-$2"); // Coloca h�fen entre o quarto e o
											// quinto d�gitos
	return v;
}

function hora(v) {
	v = v.replace(/\D/g, "");// Remove tudo o que n�o � d�gito
	v = v.replace(/^(\d\d)(\d)/g, "$1:$2"); // Coloca dois pontos depois do
	// segundo caracter

	if( v.length >= 5) {
		var str = v.substring(0, 5);
		var hora = str.substring(0, 2);
		var minuto = str.substring(3, 5);
		
		if( parseInt(hora) > 23 ) {
			str = '23:' + minuto;
		}
		if( parseInt(minuto) > 59) {
			str = hora + ':00';
		}
		
		return  str;
	} else {
		return v;	
	}
}

function soLetras(v) {
	var string2 = v.replace(/([0-9])/g, "");
	v = string2;
	return v;
}

function primeiraLetraMaiuscula(v) {
	v = v.toLowerCase();
	var str = v.substring(0,1);
	v = str.toUpperCase() + v.substring(1);
	// v = v.replace(str, str.toUpperCase());
	return v;
}


function soLetrasMaiusculas(v) {
	var string2 = v.replace(/([0-9])/g, "");
	v = string2.toUpperCase();
	return v;
}
function soLetrasMinusculas(v) {
	var string2 = v.replace(/([0-9])/g, "");
	v = string2.toLowerCase();
	return v;
}
function soLetrasMaiusculaseNumeros(v) {
	var caracteresEspeciais = new String("-+./\\@!?$%�&*()}{[]��;:|><,=");
	var descricaofinal = "";
	for ( var i = 0; i < v.length; i++) {
		if (caracteresEspeciais.indexOf(v.charAt(i)) == -1) {

			descricaofinal = descricaofinal + v.charAt(i).toUpperCase();
		} else {
			descricaofinal = descricaofinal.toUpperCase();
		}
	}
	v = descricaofinal;
	return v;
}
function soLetrasMinusculaseNumeros(v) {
	var caracteresEspeciais = new String("-+./\\@!?$%�&*()}{[]��;:|><,=");
	var descricaofinal = "";
	for ( var i = 0; i < v.length; i++) {
		if (caracteresEspeciais.indexOf(v.charAt(i)) == -1) {

			descricaofinal = descricaofinal + v.charAt(i).toLowerCase();
		} else {
			descricaofinal = descricaofinal.toUpperCase();
		}
	}
	v = descricaofinal;
	return v;
}

function cpf(v) {
	v = v.replace(/\D/g, "");
	v = v.replace(/(\d{3})(\d)/, "$1.$2");
	v = v.replace(/(\d{3})(\d)/, "$1.$2");
	v = v.replace(/(\d{3})(\d{1,2})$/, "$1-$2");
	return v;
}
function cep(v) {
	v = v.replace(/\D/g, "");
	v = v.replace(/^(\d{5})(\d)/, "$1-$2");
	return v;
}
function cnpj(v) {
	v = v.replace(/\D/g, "");
	v = v.replace(/^(\d{2})(\d)/, "$1.$2");
	v = v.replace(/^(\d{2})\.(\d{3})(\d)/, "$1.$2.$3");
	v = v.replace(/\.(\d{3})(\d)/, ".$1/$2");
	v = v.replace(/(\d{4})(\d)/, "$1-$2");
	return v;
}
function site(v) {
	v = v.replace(/^http:\/\/?/, "");
	dominio = v;
	caminho = "";
	if (v.indexOf("/") > -1)
		dominio = v.split("/")[0];
	caminho = v.replace(/[^\/]*/, "");
	dominio = dominio.replace(/[^\w\.\+-:@]/g, "");
	caminho = caminho.replace(/[^\w\d\+-@:\?&=%\(\)\.]/g, "");
	caminho = caminho.replace(/([\?&])=/, "$1");
	if (caminho != "")
		dominio = dominio.replace(/\.+$/, "");
	v = "http://" + dominio + caminho;
	return v;
}
function max200Caracteres( v ) {     
	
	var max = 200;
	if(v.length > max) {
		return v.substring(0, max);
	}
	
	return v;   
}    
function max4000Caracteres( v ) {     
	
	var max = 4000;
	if(v.length > max) {
		return v.substring(0, max);
	}
	
	return v;   
} 

var ancho=100;
function progresso_tecla(obj, max, idDiv) {
	
  var progreso = document.getElementById(idDiv);
  if (obj.value.length < max) {
	  
    progreso.style.backgroundColor = "#FFFFFF";    
    progreso.style.backgroundImage = "url(../resources/img/textarea.png)";    
    progreso.style.color = "#000000";
    var pos = ancho-parseInt((ancho*parseInt(obj.value.length))/max);
    progreso.style.backgroundPosition = "-"+pos+"px 0px";
    
  } else {
	  
    progreso.style.backgroundColor = "#CC0000";    
    progreso.style.backgroundImage = "url()";    
    progreso.style.color = "#FFFFFF";
    // obj.value = obj.value.substring(0, max);
  } 
  
  progreso.innerHTML = "("+obj.value.length+" / "+max+")";
}
