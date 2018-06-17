$( document ).ready(function() {
    //index.html
    var person = {};
    actualDiagnostic = {};
    $("#searchFormButton").click(function() {
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
            .append('<option>Nueva</option>');
        $.each(person.diagnosticList, function (i, item) {
            $('#disabledSelect').append($('<option>', {
                value: item.id,
                text : new Date(item.date).toDateString()
            }));
        });
    }

    $('#disabledSelect').on('change', function() {
        if(this.value) {
            actualDiagnostic = person.diagnosticList.filter(d => d.id == this.value)[0];
        }
        else{
            actualDiagnostic={};
            resetDiagnostic();
        }
        refreshDiagnostic();
    });

    function resetDiagnostic(){
        actualDiagnostic.antecedentes = 'NoSe';
        actualDiagnostic.cuidados = 'NoSe';
        actualDiagnostic.motivoConsulta = 'NoSe';
        actualDiagnostic.stain.color = 'NoSe';
        actualDiagnostic.stain.evolucion = 'NoSe';
        actualDiagnostic.stain.origin = 'NoSe';
        actualDiagnostic.stain.sintoma= 'NoSe';
        actualDiagnostic.stain.pelos = 'NoSe';
        actualDiagnostic.stain.rasposa = 'NoSe';
        actualDiagnostic.form.asimetria = 'NoSe';
        actualDiagnostic.form.superficie = 'NoSe';
        actualDiagnostic.form.diametro = 'NoSe';
        actualDiagnostic.form.elevada = 'NoSe';
        actualDiagnostic.form.borde= 'NoSe';
        actualDiagnostic.nombre = '';
        actualDiagnostic.resultado = '';
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
        $.ajax({
            type: 'POST',
            url: '/diagnostic/diagnostic',
            data:JSON.stringify(actualDiagnostic),
            dataType: 'json',
            contentType:'application/json',
            success: function(data){
                actualDiagnostic.resultado = data.resultado;
                actualDiagnostic.accion = data.accion;
                actualDiagnostic.nombre = data.nombre;
                refreshDiagnostic();
            },
            error:function(error){
                $('.login-panel.panel.panel-default .panel-body').append('<span id="search-message">No existe el paciente. <a href="/alta-usuario">Cargar usuario</a></span>')
            }
        });
    });

    $('#diagnosticSaveButton').click(function () {
        getDiagnosticFromField();
        if(!$('#disabledSelect option:selected').attr("value")) {
            actualDiagnostic.date = new Date();
            person.diagnosticList.push(actualDiagnostic);
            actualDiagnostic.personId = person.id;
        }
        $.ajax({
            type: 'POST',
            url: '/diagnostic/diagnostic',
            data:JSON.stringify(actualDiagnostic),
            dataType: 'json',
            contentType: 'application/json',
            success: function(data) {
                actualDiagnostic.id = data.id;
                actualDiagnostic.resultado = data.resultado;
                actualDiagnostic.accion = data.accion;
                actualDiagnostic.nombre = data.nombre;
                refreshDiagnostic();
                if(!$('#disabledSelect option:selected').attr("value")) {
                    $('#disabledSelect').append($('<option>', {
                        value: actualDiagnostic.id,
                        text: new Date(actualDiagnostic.date).toDateString()
                    }));
                    $('#disabledSelect').val(actualDiagnostic.id);
                }
                alert('El diagnostico se salvo exitosamente');
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
                dataType: 'json',
                contentType: 'application/json',
                success: function (data) {
                    alert('La persona ha creado exitosamente.')
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