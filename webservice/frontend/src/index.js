import './index.css';

function component() {
    var element = document.createElement('div');
    element.innerHTML = ['Hello', 'webpack'].join(' ');
    return element;
}  
document.body.appendChild(component());