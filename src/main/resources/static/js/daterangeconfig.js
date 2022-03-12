	$('#date_range').daterangepicker();
	let today = new Date()
	let tomorrow = new Date(today)
		tomorrow.setDate(tomorrow.getDate() + 1)
		$(function () {
		    $('#date_range').daterangepicker({
		        "locale": {
		            "format": "DD/MM/YYYY",
		            "separator": "-",
		            "applyLabel": "Guardar",
		            "cancelLabel": "Cancelar",
		            "fromLabel": "Desde",
		            "toLabel": "Hasta",
		            "customRangeLabel": "Personalizar",
		            "daysOfWeek": [
		                "Do",
		                "Lu",
		                "Ma",
		                "Mi",
		                "Ju",
		                "Vi",
		                "Sa"
		            ],
		            "monthNames": [
		                "Enero",
		                "Febrero",
		                "Marzo",
		                "Abril",
		                "Mayo",
		                "Junio",
		                "Julio",
		                "Agosto",
		                "Setiembre",
		                "Octubre",
		                "Noviembre",
		                "Diciembre"
		            ],
		            "firstDay": 1
		        },
		        "startDate": today,
		        "endDate" : today,
		        "opens": "center"
		    });
	});
	$('#date_range2').daterangepicker();
	let today2 = new Date()
	let tomorrow2 = new Date(today)
		tomorrow2.setDate(tomorrow2.getDate() + 1)
		$(function () {
		    $('#date_range2').daterangepicker({
		        "locale": {
		            "format": "DD/MM/YYYY",
		            "separator": "-",
		            "applyLabel": "Guardar",
		            "cancelLabel": "Cancelar",
		            "fromLabel": "Desde",
		            "toLabel": "Hasta",
		            "customRangeLabel": "Personalizar",
		            "daysOfWeek": [
		                "Do",
		                "Lu",
		                "Ma",
		                "Mi",
		                "Ju",
		                "Vi",
		                "Sa"
		            ],
		            "monthNames": [
		                "Enero",
		                "Febrero",
		                "Marzo",
		                "Abril",
		                "Mayo",
		                "Junio",
		                "Julio",
		                "Agosto",
		                "Setiembre",
		                "Octubre",
		                "Noviembre",
		                "Diciembre"
		            ],
		            "firstDay": 1
		        },
		        "startDate": today,
		        "endDate" : today,
		        "opens": "center"
		    });
	});