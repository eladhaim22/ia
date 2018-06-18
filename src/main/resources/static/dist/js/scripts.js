$( document ).ready(function() {
    //index.html
    var person = {};
    actualDiagnostic = {};

    $("#srch-term").focus();

    $("#srch-term").keypress(function(event){

        var keycode = (event.keyCode ? event.keyCode : event.which);
        if(keycode == '13'){
            $("#searchFormButton").click();
        }
        event.stopPropagation();
    });

    $("#searchFormButton").click(function() {
        $('#panel-parameters').addClass("hide");
        $('#person-details').addClass("hide");
        $('.parameters').addClass("hide");
        $.ajax({
            type: 'GET',
            url: '/person/' + $('#srch-term').val(),
            dataType: 'json',
            success: function(data){
                    person.id = data.id;
                    person.firstName = data.firstName;
                    person.lastName = data.lastName;
                    person.dni = data.dni;
                    person.diagnosticList = data.diagnosticList;
                    refreshFields();
                    $('#panel-parameters').removeClass("hide");
                    $('#person-details').removeClass("hide");
                },
                error:function(error) {
                    if (!$('span#search-message').length > 0) {
                        $('.login-panel.panel.panel-default .panel-body').append('<span id="search-message">No existe el paciente. <a href="/alta-usuario">Cargar usuario</a></span>')
                    }
                }
        });
    });

    $('#srch-term').change(function(){
        $('#search-message').remove();
    });

    function refreshFields(){
        $('#dni').val(person.dni);
        $('#firstName').val(person.firstName);
        $('#lastName').val(person.lastName);
        $('#disabledSelect')
            .find('option')
            .remove()
            .end()
            .append('<option value="">---</option>')
            .append('<option value="new">Nueva</option>');
        $.each(person.diagnosticList, function (i, item) {
            $('#disabledSelect').append($('<option>', {
                value: item.id,
                text : moment(item.date).format('lll')
            }));
        });
    }

    $('#disabledSelect').on('change', function() {
        if(this.value && this.value != "new") {
            actualDiagnostic = person.diagnosticList.filter(d => d.id == this.value)[0];
            $(".parameters").removeClass('hide');
            refreshDiagnostic();
        }
        else if(this.value == "new"){
            actualDiagnostic={};
            $(".parameters").removeClass('hide');
            resetDiagnostic();
            refreshDiagnostic();
        }
        else{
            $(".parameters").addClass('hide');
        }
    });

    function resetDiagnostic(){
        actualDiagnostic.antecedentes = 'nose';
        actualDiagnostic.cuidados = 'nose';
        actualDiagnostic.motivoConsulta = 'nose';
        actualDiagnostic.stain = {};
        actualDiagnostic.stain.color = 'nose';
        actualDiagnostic.stain.evolucion = 'nose';
        actualDiagnostic.stain.origin = 'nose';
        actualDiagnostic.stain.sintoma= 'nose';
        actualDiagnostic.stain.pelos = 'nose';
        actualDiagnostic.stain.rasposa = 'nose';
        actualDiagnostic.form = {};
        actualDiagnostic.form.asimetria = 'nose';
        actualDiagnostic.form.superficie = 'nose';
        actualDiagnostic.form.diametro = 0.0;
        actualDiagnostic.form.elevada = 'nose';
        actualDiagnostic.form.borde= 'nose';
        actualDiagnostic.nombre = '';
        actualDiagnostic.resultado = '';
        actualDiagnostic.accion = '';
        actualDiagnostic.image=""
    }

    function refreshDiagnostic(){
        $('#paciente-antecedentes').val(actualDiagnostic.antecedentes);
        $('#paciente-cuidados').val(actualDiagnostic.cuidados);
        $('#paciente-motivo-consulta').val(actualDiagnostic.motivoConsulta);
        $('#mancha-color').val(actualDiagnostic.stain.color);
        $('#mancha-evolucion').val(actualDiagnostic.stain.evolucion);
        $('#mancha-origen').val(actualDiagnostic.stain.origin);
        $('#mancha-sintoma').val(actualDiagnostic.stain.sintoma);
        $('#mancha-pelos').val(actualDiagnostic.stain.pelos);
        $('#mancha-rasposa').val(actualDiagnostic.stain.rasposa);
        $('#mancha-asimetria').val(actualDiagnostic.form.asimetria);
        $('#mancha-superficie').val(actualDiagnostic.form.superficie);
        $('#mancha-diametro').val(actualDiagnostic.form.diametro);
        $('#mancha-elevada').val(actualDiagnostic.form.elevada);
        $('#mancha-borde').val(actualDiagnostic.form.borde);
        $('h1#nombre').text(actualDiagnostic.nombre);
        $('p#resultado').text(actualDiagnostic.resultado);
        $('p#accion').text(actualDiagnostic.accion);
        if(actualDiagnostic.image) {
            $('#image').attr("src", "/images/" + actualDiagnostic.image);
            $('#image').css({height: '200px',float: 'right'});
        }
        else{
            $('#image').attr("src","");
            $('#image').css({height: '',float: ''});
        }
    }

    function getDiagnosticFromField(){
        actualDiagnostic.antecedentes = $('#paciente-antecedentes').val();
        actualDiagnostic.cuidados = $('#paciente-cuidados').val();
        actualDiagnostic.motivoConsulta = $('#paciente-motivo-consulta').val();
        actualDiagnostic.stain = {};
        actualDiagnostic.stain.color = $('#mancha-color').val();
        actualDiagnostic.stain.evolucion = $('#mancha-evolucion').val();
        actualDiagnostic.stain.origin = $('#mancha-origen').val();
        actualDiagnostic.stain.sintoma= $('#mancha-sintoma').val();
        actualDiagnostic.stain.pelos = $('#mancha-pelos').val();
        actualDiagnostic.stain.rasposa = $('#mancha-rasposa').val();
        actualDiagnostic.form = {};
        actualDiagnostic.form.asimetria = $('#mancha-asimetria').val();
        actualDiagnostic.form.superficie = $('#mancha-superficie').val();
        actualDiagnostic.form.diametro = $('#mancha-diametro').val();
        actualDiagnostic.form.elevada = $('#mancha-elevada').val();
        actualDiagnostic.form.borde= $('#mancha-borde').val();
    }

    $('#diagnosticButton').click(function(){
        getDiagnosticFromField();
        if($('#disabledSelect option:selected').attr("value") == "new") {
            actualDiagnostic.date = new Date();
            person.diagnosticList.push(actualDiagnostic);
            actualDiagnostic.personId = person.id;
        }
        $.ajax({
            type: 'POST',
            url: '/diagnostic/diagnostic',
            data:JSON.stringify(actualDiagnostic),
            dataType: 'json',
            contentType:'application/json',
            success: function(data){
                actualDiagnostic.id = data.id;
                actualDiagnostic.resultado = data.resultado;
                actualDiagnostic.accion = data.accion;
                actualDiagnostic.nombre = data.nombre;
                actualDiagnostic.image = data.image;
                refreshDiagnostic();
                if($('#disabledSelect option:selected').attr("value") == "new") {
                    $('#disabledSelect').append($('<option>', {
                        value: actualDiagnostic.id,
                        text : moment(actualDiagnostic.date).format('lll')
                    }));
                    $('#disabledSelect').val(actualDiagnostic.id);
                }
            }
        });
    });

    //alta-usuario.html
    $('#savePersonButton').click(function () {
        var newPerson ={};
        newPerson.firstName = $('#personNombre').val();
        newPerson.lastName = $('#personApellido').val();
        newPerson.dni = $('#personDni').val();
        if(newPerson.firstName && newPerson.lastName && newPerson.dni) {
            $.ajax({
                type: 'POST',
                url: '/person/',
                data: JSON.stringify(newPerson),
                contentType: 'application/json',
                success: function (data) {
                    alert('La persona ha creado exitosamente.');
                },
                error:function(error){
                    alert('La persona ya existe.')
                },
                complete: function (data) {
                    var newPerson ={};
                    $('#personNombre').val('');
                    $('#personApellido').val('');
                    $('#personDni').val('');
                }
            });
        }
    });
});