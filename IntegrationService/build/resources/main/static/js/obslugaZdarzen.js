mapaMain = new MapaMain();
var wartosc;
var pogoda_subskrypcja = mapaMain.pobierzCookie('pogoda_subskrypcja');
var miasto_subskrypcja = mapaMain.pobierzCookie('miasto_subskrypcja');

$(document).ready(function(){

	mapaMain.ustawMape();
	mapaMain.pobierzWspolrzedneGPS();

	$( "#draggable" ).draggable({
		containment: "parent"
	});

	//mapaMain.wyswietlPowiadomienie('test','success');

	if(pogoda_subskrypcja === ''){
		$('.usunSubskrypcje').hide();
	}else{
		$('.zapiszSubskrypcje').hide();
		$('#email').val(pogoda_subskrypcja);
		mapaMain.pobierzWspolrzedneMiasta(miasto_subskrypcja);
	}

});

$(document).on('click','.sprawdzPogode',function(){
	mapaMain.sprawdzPogode();

//	$('#myModal').modal('show');
//	$('#pogodaMiasto').text($('#miejscowosc').val());
});

$(document).on('click','.wczytajDaneMiasta',function(){
	var miejscowosc = $('#miejscowosc').val();

	if(miejscowosc === ''){
		mapaMain.wyswietlPowiadomienie('Wpisz miejscowoúÊ!','danger');
		return;
	}

	mapaMain.pobierzWspolrzedneMiasta($('#miejscowosc').val());
});

$(document).on('focus','#miejscowosc',function(){
	$('.sprawdzPogode').hide();
	$('.wczytajDaneMiasta').show();

	$(this).keypress(function(e){
		if(e.keyCode === 13){
			mapaMain.pobierzWspolrzedneMiasta($(this).val());
			$(this).blur();
		}
	});

});

$(document).on('click','.menuGornePrzycisk',function(){
	$('#menuGorne').toggleClass('wysunMenu');
	$(this).toggleClass('aktywneMenu');
	if(!$(this).hasClass('aktywneMenu')){
		$('.mgpe_1').animate({ top: "0px"}, "slow" );
		$('.mgpe_3').animate({ bottom: "0px"}, "slow" );
	}
});

$(document).on('click','.ukryjPaseklokalizacja',function(){
	$('.lokalizacjaInfo').animate({ left: "-800px"}, "slow" ,function(){
		$('#pasekLokalizacja').animate({ opacity: "1"}, "slow" );
	});
});

$(document).on('click','#pasekLokalizacja',function(){
	$('#pasekLokalizacja').animate({ opacity: "0"}, "slow" ,function(){
		$('.lokalizacjaInfo').animate({ left: "0px"}, "slow");
	});
});

$(document).on('click','.zapiszSubskrypcje',function(){
	var miejscowosc = $('#miejscowosc').val();
	var latitude = $('#latitude').val();
	var longitude = $('#longitude').val();
	var wojewodztwo = $('#wojewodztwo').val();
	var email = $('#email').val();

	if(!mapaMain.sprawdzEmail(email)){
		mapaMain.wyswietlPowiadomienie('Wprowadü poprawny adres email!','danger');
		return;
	}

	if(email === ''){
		mapaMain.wyswietlPowiadomienie('Wprowadü adres email!','danger');
		return;
	}

	if(miejscowosc === '' || latitude === '' || longitude === '' || wojewodztwo === ''){
		mapaMain.wyswietlPowiadomienie('Wybierz lokalizacje!','danger');
		return;
	}

	mapaMain.zapiszSubskrypcje(email, miejscowosc, wojewodztwo);


});

$(document).on('click','.usunSubskrypcje',function(){
    var email = $('#email').val();

	if(!mapaMain.sprawdzEmail(email)){
		mapaMain.wyswietlPowiadomienie('Wprowadü poprawny adres email!','danger');
		return;
	}

	if(email === ''){
		mapaMain.wyswietlPowiadomienie('Wprowadü adres email!','danger');
		return;
	}

    mapaMain.usunSubsktypcje(email);

});

$(document).on('keypress','#email', function(event) {
    return mapaMain.maskujKlawisze(event, 'abcdefghijklmnoprstuwyzx0123456789@-.');
});

$(document).on('keypress','#miejscowosc', function(event) {
    return mapaMain.maskujKlawisze(event, 'Aa•πBbCc∆ÊDdEe ÍFfGgHhIiJjKkLl£≥MmNn—ÒOo”ÛPpRrSsåúTtUuWwYyZzèüØøXx');
});