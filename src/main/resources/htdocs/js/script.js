

$( document ).ready(function() {



    var toHtml = function(token,translation)
    {
        var html = "";
        for(var i = 0;i < token.length;i++)
        {
            var entity = undefined;
            switch(token[i].namedEntity)
            {
                case 'O':
                case 'NUMBER':
                    entity = '';
                    break;
                case 'PERSON':
                    entity = '<i class="fa fa-user"></i> ';
                    break;
                case 'ORGANIZATION':
                    entity = '<i class="fa fa-users"></i> ';
                    break;
                case 'CITY':
                    entity = '<i class="fa fa-building"></i> ';
                    break;
                case 'LOCATION':
                    entity = '<i class="fa fa-map-marker"></i> '; 
                    break;
                case 'COUNTRY':
                    entity = '<i class="fa fa-globe"></i> '; 
                    break;
                default:
                    console.log("unknown entity",token[i].namedEntity);
                    entity = '<i class="fa fa-question"></i> ';
                    break;
            }

            var type = undefined;
            switch(token[i].partOfSpeech)
            {
                //https://universaldependencies.org/u/pos/all.html
                //label-default, .label-primary, .label-success, .label-info, .label-warning or .label-danger
                case 'PROPN': //proper noun
                case 'PRON': //pronoun
                case 'NOUN': //noun
                case 'ADJ': //adjective
                    type = 'success';
                    break;
                case 'VERB':
                case 'AUX':
                case 'ADV':
                    type = 'info';
                    break;
                case 'ADP': //pre or post position 
                case 'PUNCT':
                case 'DET': //determiner
                case 'SCONJ': //subordinating conjunction
                case 'CONJ':
                case 'NUM': //number

                    type = 'default';
                    break;
                default:
                    console.log("unknown type",token[i].partOfSpeech);
                    type = 'danger';
                    break;
            }
           
            html += ' <span class="label label-'+type+'">'+entity + token[i].text+'</span>';;
        }

        var grouptype = token.length == 1?"single":"multi";
        if(translation)
        {
            html = '<span class="group group-link-'+grouptype+'" data-toggle="tooltip" data-placement="top" title="'+translation+'">' + html + '</span>';
        }
        else
        {
            html = '<span class="group group-nolink-'+grouptype+'">' + html + '</span>';
        }

        return html;
    }


    console.log( "ready!" );

    $('form').submit(function(ev) {


        ev.preventDefault();

        var form = $(this);
    
        $.ajax({
                type: "POST",
                url: form.attr('action'),
                data: form.serialize(),
                headers : {
                    Authorization : 'Bearer ' + 'limited-guest-access'
                },
                success : function(response) {
                    console.log(response);

                    var groups = response['groups'];
                    var translation = response['translation'];

                    $('#result').empty();

                    for(var i = 0;i < groups.length;i++)
                    {
                        var token = groups[i].tokens;
                        var trans = translation[i] == null ? undefined : translation[i].candidates[0];
                        
                        console.log(token,"==",trans);


                        $('#result').append(toHtml(token,trans));


                    }

                    $('[data-toggle="tooltip"]').tooltip();

                },
                error : function(xhr, status, error) {
                    var err = eval("(" + xhr.responseText + ")");
                    console.log(err);                   
                }
        }); 

    });


});