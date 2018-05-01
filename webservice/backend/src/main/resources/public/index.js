function appendText(text){
    var p = document.createElement("p");
    p.innerText = text;
    document.body.appendChild(p);
}

async function init(){
    var url = "/greeting";
    var response = await fetch(url);
    var text = await response.text();
    appendText(text);
}
