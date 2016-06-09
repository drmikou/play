/**
 * This script re-synchronize latest modified documents when network errors occurred during the first synchronizing
 * @author Jean-Baptiste WATENBERG <jbwatenberg@juniorisep.com>
 */
$(document).ready(function () {
    if (localStorage.getItem("syncIt")) {
        var sync = JSON.parse(localStorage.getItem("syncIt"));
        for(var i = 0; i< sync.length; i++) {
            var fileName = sync[i];
            if (localStorage.getItem(fileName)) {
                lockMutex("saving " + fileName);
                coreJsRoutes.controllers.core.FileManager.asyncUploadDraft(fileName).ajax({
                    type : "POST",
                    contentType : 'text/plain; charset=utf-8',
                    data : localStorage.getItem(fileName),
                    success : function(data) {
                        unlockMutex("saving " + fileName);
                        localStorage.removeItem(fileName);
                        alert("Le fichier " + fileName + " à été syncronisé avec le back end !");
                    },
                    error : function () {
                        unlockMutex("saving " + fileName);
                        console.error("error on ajax save document");
                    }
                });
            }
        }
    }
});
