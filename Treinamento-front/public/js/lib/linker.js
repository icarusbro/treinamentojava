$('#gerar').click(function() {

	$("#alvo").empty();

	var text = $('#querygae').val();
	var qtd = $('#qtd').val();
	var qs= new QueryString(text);
	var offset= parseInt(qs.value('offset'));
	var limit= parseInt(qs.value('limit'));
	var select= qs.value('query');

	if(isNaN(limit) && select!=null){
		limit=200;
		offset=0;
		text=text+'&limit=200&offset=0';
	}

	if(limit<200){
		var wrongLimit='limit='+limit;
		text=text.replace(wrongLimit,'limit=200');
	}

    if(offset>0){
        var wrongLimit='offset='+offset;
        text=text.replace(wrongLimit,'offset=0');
        offset=0;
    }

	var link= text;

	var newOffset;
	var lastOffset=offset;
	var nextOffset=offset;

	var replaceold;
	var replacenext;

	var count=1;

	while(nextOffset<qtd){

		replaceold = 'offset='+lastOffset;
		replacenext = 'offset='+nextOffset;
		link=link.replace(replaceold,replacenext);	
		lastOffset = nextOffset;
		nextOffset = lastOffset+200;

		var meulink = $("<a></a>").attr("href", link).text(count+" ");
		$("#alvo").append(meulink);
		count++;
	}
});