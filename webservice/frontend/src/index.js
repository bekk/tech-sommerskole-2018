import './index.less';

function component() {
    var element = document.createElement('div');
    element.innerHTML = ['Hello', 'BEKK!'].join(' ');
    return element;
}  
document.body.appendChild(component());