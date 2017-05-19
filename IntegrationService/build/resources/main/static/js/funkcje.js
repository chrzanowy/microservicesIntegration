function MapaMain(){
	var latitude = '52.2296756'
	var longitude = '21.0122287'
	var url;
	var lokalizacjaDane;
	var wojewodztwo;
	var miejscowosc;	
	var mapa;
	var marker;
	var apiKey = 'AIzaSyB_eahyIgdd-zOZ0bakcZajZc0cAUKcV8A';
	var wspolrzedne;
	var mapOptions;
	var klawiszeSterujace = [ 8,13,0,32,99,118 ];
	
	this.ustawMape = function(){
		wspolrzedne = new google.maps.LatLng(latitude, longitude);
 
		mapOptions = {
			zoom: 12,
			center: wspolrzedne,
			mapTypeControl: false,
			disableDefaultUI: true,
			mapTypeId: google.maps.MapTypeId.ROADMAP
		};
 
		mapa = new google.maps.Map(
			document.getElementById('mapka'), mapOptions
		);
 
		marker = new google.maps.Marker({
						  position: wspolrzedne,
						  map: mapa
		});
	
		google.maps.event.addListener(mapa,'click',function(e){
			
			latitude = e.latLng.lat();
			longitude = e.latLng.lng();
			
			marker.setPosition(e.latLng);
			mapaMain.pobierzDaneMiasta();
			
		});
				
	},
	
	this.pobierzWspolrzedneGPS = function(){
		if (navigator.geolocation){
			navigator.geolocation.getCurrentPosition(this.ustawWspolrzedne);		
		}else{
			alert('twoja przeglądarka nie wspiera geolokacji...');
		}
	},
	
	this.ustawWspolrzedne = function(_position){
		latitude = _position.coords.latitude;
		longitude = _position.coords.longitude;
		
		mapaMain.ustawMape();
		mapaMain.pobierzDaneMiasta();
	},
	
	this.wyswietlDaneMiasta = function(){
		$('#latitude').val(lokalizacjaDane.latitude);
		$('#longitude').val(lokalizacjaDane.longitude);
		$('#miejscowosc').val(lokalizacjaDane.miejscowosc);
		$('#wojewodztwo').val(lokalizacjaDane.wojewodztwo);
		
		$('.lokalizacjaDane').show();
		$('.wczytajDaneMiasta').hide();
		$('.sprawdzPogode').show();
	},
	
	this.usunPolskieZnaki = function(_string){
		return _string.replace(/ą/g, 'a').replace(/Ą/g, 'A')
        .replace(/ć/g, 'c').replace(/Ć/g, 'C')
        .replace(/ę/g, 'e').replace(/Ę/g, 'E')
        .replace(/ł/g, 'l').replace(/Ł/g, 'L')
        .replace(/ń/g, 'n').replace(/Ń/g, 'N')
        .replace(/ó/g, 'o').replace(/Ó/g, 'O')
        .replace(/ś/g, 's').replace(/Ś/g, 'S')
        .replace(/ż/g, 'z').replace(/Ż/g, 'Z')
        .replace(/ź/g, 'z').replace(/Ź/g, 'Z');
	},
	
	this.znajdzDane = function(_dane, _klucz){
		var liczbaWynikow = _dane.length;
		var liczbaElementow
			
		for(var i = 0;i<liczbaWynikow;i++){
			liczbaElementow = _dane[i].address_components.length;
			for(var j = 0; j<liczbaElementow;j++){
				if(_dane[i].address_components[j].types[0] == _klucz){
					return _dane[i].address_components[j].long_name;
				}
			}		
		}
	},
	
	this.wyslijZadanie = function(_url, _ustawMape){		
		$.ajax({
            url: _url
        }).done(function(dane) {
				
			if(dane.status == 'INVALID_REQUEST'){
				mapaMain.wyswietlPowiadomienie('Wpisz poprawną nazwę miejscowości!','danger');
				return;
			}
			
			if(dane.status == 'ZERO_RESULTS'){
				mapaMain.wyswietlPowiadomienie('Lokalizacja nie została odnaleziona, wybierz inną.','danger');
				return;
			}
			
			wojewodztwo = mapaMain.znajdzDane(dane.results, 'administrative_area_level_1').replace('Województwo ','');
			miejscowosc = mapaMain.znajdzDane(dane.results, 'locality');	
			
			if(miejscowosc == undefined){
				miejscowosc = mapaMain.znajdzDane(dane.results, 'administrative_area_level_3');	
			}	
			
			if(_ustawMape){
				latitude = dane.results[0].geometry.location.lat;
				longitude = dane.results[0].geometry.location.lng;
				
				mapa.setCenter(new google.maps.LatLng(latitude, longitude));
				marker.setPosition(new google.maps.LatLng(latitude, longitude));
			}			
			
			lokalizacjaDane = {
				'latitude' : latitude
				,'longitude' : longitude
				,'miejscowosc' : miejscowosc
				,'miejscowoscBezPolskichZnakow' : mapaMain.usunPolskieZnaki(miejscowosc.toLowerCase())
				,'wojewodztwo' : wojewodztwo
				,'wojewodztwoBezPolskichZnakow' : mapaMain.usunPolskieZnaki(wojewodztwo.toLowerCase())

			};
			
			mapaMain.wyswietlDaneMiasta();
			
        }).fail(function(ajaxContext) {
            console.log(ajaxContext.responseText);
        });
	},
	
	this.pobierzDaneMiasta = function(){
		url = 'https://maps.google.com/maps/api/geocode/json?latlng='+latitude+','+longitude+'&key='+apiKey;

		this.wyslijZadanie(url,false);
	},
	
	this.pobierzWspolrzedneMiasta = function(_adres){
		url = 'https://maps.google.com/maps/api/geocode/json?address='+_adres+'&key='+apiKey;
		
		this.wyslijZadanie(url,true);
	},
	
	this.wyswietlLoader = function(_rodzic){
		$(_rodzic).append('<div class="loaderTlo"><div class="loader"></div></div>');
	},
	
	this.ukryjLoader = function(){
		$('.loaderTlo').remove();
	},
	
	this.wyswietlPowiadomienie = function(_wiadomosc, _rodzaj){
		$.notify({
			// options
			message: _wiadomosc 
		},{
			// settings
			type: _rodzaj
			,newest_on_top: true
			,placement: {
				from: "bottom",
				align: "right"
			}
			,animate: {
				enter: 'animated fadeInDown',
				exit: 'animated fadeOutUp'
			}
			,template: '<div data-notify="container" class="powiadomienie alert alert-{0}" role="alert">' +
							'<button type="button" aria-hidden="true" class="close" data-notify="dismiss">×</button>' +
							'<span data-notify="title">{1}</span> ' +
							'<span data-notify="message">{2}</span>' +
						'</div>'
		});
	},
	
	this.ustawCookie = function(nazwa, wartosc, dni) {
		var d = new Date();
		d.setTime(d.getTime() + (dni*24*60*60*1000));
		var expires = "expires="+ d.toUTCString();
		document.cookie = nazwa + "=" + wartosc + ";" + expires + ";path=/";
	},

	this.pobierzCookie = function(nazwa) {
		var name = nazwa + "=";
		var decodedCookie = decodeURIComponent(document.cookie);
		var ca = decodedCookie.split(';');
		for(var i = 0; i <ca.length; i++) {
			var c = ca[i];
			while (c.charAt(0) == ' ') {
				c = c.substring(1);
			}
			if (c.indexOf(name) == 0) {
				return c.substring(name.length, c.length);
			}
		}
		return "";
	},
	
	this.maskujKlawisze = function(zdarzenie, maska) {
        var kodKlawisza;
        if (window.Event) {
           kodKlawisza = zdarzenie.which;
        } else {
           kodKlawisza = zdarzenie.keyCode;
        }

        if(this.in_array(kodKlawisza, klawiszeSterujace)){
            return true;
        }

        var klawisz = String.fromCharCode(kodKlawisza);

        if (maska.indexOf(klawisz) === -1) {
            return false;
        } else {
            return true;
        }
    },
	
	this.in_array = function(element, array){
        if(jQuery.inArray(element, array) < 0){
            return false;
        }
        return true;
    },

	this.sprawdzEmail = function(email) {
        var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

        if(reg.test(email) === false){
            return false;
        }else {
            return true;
        }
    };

	this.sprawdzPogode = function(){
		$.ajax({
			type: 'GET'
            ,url: 'http://localhost:8080/weather?latitude='+ latitude +'&longitude=' + longitude
        }).done(function(dane) {

            console.log(dane);
            var odpowiedz = dane;

            $('.temperatura').text(odpowiedz['main'].temp);
            $('.wiatr').text(odpowiedz['wind'].speed);
            $('.zachmurzenie').text(odpowiedz['clouds'].all);
            $('.cisnienie').text(odpowiedz['main'].pressure);
            $('.wilgotnosc').text(odpowiedz['main'].humidity);
            $('.wschodSlonca').text(mapaMain.formatujCzas(odpowiedz['sys'].sunrise));
            $('.zachodSloncas').text(mapaMain.formatujCzas(odpowiedz['sys'].sunset));

			$('#myModal').modal('show');
			$('#pogodaMiasto').text(lokalizacjaDane['miejscowosc']);
			//console.log(dane);
		}).fail(function(ajaxContext) {
            console.log(ajaxContext.responseText);
        });
	};

	this.formatujCzas = function(time){
	    var formatedDate = new Date(0);
        formatedDate.setUTCSeconds(time);
	    return this.wiodaceZero(formatedDate.getHours()) + ":"+ this.wiodaceZero(formatedDate.getMinutes());
	};

	this.wiodaceZero = function(time){
    	    return (time<10)? '0'+time : time;
    	};

    this.zapiszSubskrypcje = function(email, miejscowosc, wojewodztwo){


            var formData = {
                'email' : email
                ,'city' : mapaMain.usunPolskieZnaki(miejscowosc.toLowerCase())
                ,'state' : mapaMain.usunPolskieZnaki(wojewodztwo.toLowerCase())
            }

        $.ajax({
                type: 'POST'
                ,url: 'http://localhost:8080/subscribe'
                ,data : JSON.stringify(formData)
                    ,dataType: 'json'
                    ,cache : false
                    ,processData: false
                    ,contentType: "application/json"
                    ,statusCode: {
                        400: function() {
                          mapaMain.ustawCookie('pogoda_subskrypcja', email, 365);
                          mapaMain.ustawCookie('miasto_subskrypcja', miejscowosc, 365);
                          $('.zapiszSubskrypcje').hide();
                          $('.usunSubskrypcje').show();
                          mapaMain.wyswietlPowiadomienie('Adres email jest zapisany do subskrypcji','danger');
                        },
                        201: function(){
                            mapaMain.ustawCookie('pogoda_subskrypcja', email, 365);
                            mapaMain.ustawCookie('miasto_subskrypcja', miejscowosc, 365);
                            $('.zapiszSubskrypcje').hide();
                            $('.usunSubskrypcje').show();
                            mapaMain.wyswietlPowiadomienie('Zostałeś zapisany do subskrypcji.','success');
                        }
                      }
            }).done().fail(function(ajaxContext) {
                console.log(ajaxContext.responseText);
            });
    };


    this.usunSubsktypcje = function(email){
        var formData = {
                        'email' : email
                    }

            $.ajax({
                    type: 'DELETE'
                    ,url: 'http://localhost:8080/subscribe'
                    ,data : JSON.stringify(formData)
                    ,dataType: 'json'
                    ,cache : false
                    ,processData: false
                    ,contentType: "application/json"
                    ,statusCode: {
                        200: function() {
                          $('#email').val('');
                          pogoda_subskrypcja = '';
                          miasto_subskrypcja = '';
                          mapaMain.ustawCookie('pogoda_subskrypcja', '', 365);
                          mapaMain.ustawCookie('miasto_subskrypcja', '', 365);
                          $('.zapiszSubskrypcje').show();
                          $('.usunSubskrypcje').hide();
                          mapaMain.wyswietlPowiadomienie('Zostałeś usunięty ze subskrypcji.','success');
                        },
                        400 : function(){
                              pogoda_subskrypcja = '';
                              miasto_subskrypcja = '';
                              mapaMain.ustawCookie('pogoda_subskrypcja', '', 365);
                              mapaMain.ustawCookie('miasto_subskrypcja', '', 365);
                              $('.zapiszSubskrypcje').show();
                              $('.usunSubskrypcje').hide();
                              mapaMain.wyswietlPowiadomienie('Najpierw musisz sie zapisac do subskrypcji.','danger');
                        }
                      }
            }).done().fail(function(ajaxContext) {
                console.log(ajaxContext.responseText);
            });

    };
}

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
		mapaMain.wyswietlPowiadomienie('Wpisz miejscowość!','danger');
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
		mapaMain.wyswietlPowiadomienie('Wprowadź poprawny adres email!','danger');
		return;
	}

	if(email === ''){
		mapaMain.wyswietlPowiadomienie('Wprowadź adres email!','danger');
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
		mapaMain.wyswietlPowiadomienie('Wprowadź poprawny adres email!','danger');
		return;
	}

	if(email === ''){
		mapaMain.wyswietlPowiadomienie('Wprowadź adres email!','danger');
		return;
	}

    mapaMain.usunSubsktypcje(email);

});

$(document).on('keypress','#email', function(event) {
    return mapaMain.maskujKlawisze(event, 'abcdefghijklmnoprstuwyzx0123456789@-.');
});

$(document).on('keypress','#miejscowosc', function(event) {
    return mapaMain.maskujKlawisze(event, 'AaĄąBbCcĆćDdEeĘęFfGgHhIiJjKkLlŁłMmNnŃńOoÓóPpRrSsŚśTtUuWwYyZzŹźŻżXx');
});




















