let symbol = new RegExp( "[~!@#$%^&*()\\-+=|\/';:,.]");
let validatePass = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('Please input the password'));
    } else {
        if(symbol.exec(value)!==null){
            callback(new Error('Недопустимые символы: ~!@#$%^&*()-+=|  / \';:,.'));
        }else
            callback();
    }
};