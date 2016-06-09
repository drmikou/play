


var mutexLocker = [];

function lockMutex(key) {
    mutexLocker.push(key);
}

function unlockMutex(key) {
    for (var i = 0; i < mutexLocker.length; i++){
        if (mutexLocker[i]==key){
            mutexLocker.splice(i,1);
            break;
        }
    }
}

window.onbeforeunload = function(e) {
    if (mutexLocker.length != 0) {
        return 'Des actions sont encore en cours sur cette page... ';
    }
};
