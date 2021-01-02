

$( document ).ready(function() {



    var toHtml = function(token)
    {
        var html = "";
      
        var entity = undefined;
        switch(token.namedEntity)
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
            case 'DATE':
                entity = '<i class="fa fa-calendar"></i> '; 
                break;
            default:
                console.log("unknown entity",token.namedEntity);
                entity = '<i class="fa fa-question"></i> ';
                break;
        }

        var type = undefined;
        switch(token.partOfSpeech)
        {
            //https://universaldependencies.org/u/pos/all.html
            //label-default, .label-primary, .label-success, .label-info, .label-warning or .label-danger
            case 'proper-noun': //proper noun
            case 'pronoun': //pronoun
            case 'noun': //noun
            case 'adjective': //adjective
                type = 'success';
                break;
            case 'verb':
            case 'auxiliary-verb':
            case 'adverb':
                type = 'info';
                break;
            case 'adposition': //pre or post position 
            case 'punctuation':
            case 'determiner': //determiner
            case 'subordinating-conjunction': //subordinating conjunction
            case 'coordinating-conjunction':
            case 'particle':
            case 'numeral':
                    type = 'default';
                break;
            default:
                console.log("unknown type",token.partOfSpeech);
                type = 'danger';
                break;
        }
           
        html = ' <span class="label label-'+type+'" data-toggle="tooltip" data-placement="top" title="'+token.namedEntity+'/'+token.partOfSpeech+'">'+entity + token.text+'</span>';
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

                $('#result').empty();

                for(var i = 0;i < response.text.sentences.length;i++)
                {
                    var sentence = response.text.sentences[i];
                    for(var x = 0;x < sentence.tokens.length;x++)
                    {
                        var token = sentence.tokens[x];
                        console.log(token);
                        $('#result').append(toHtml(token));
                    }
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