// validate form

function getLocalhost() {
    return window.location.host;
}

function scan() {
    var url = document.getElementById("url").value;
    if(!url.startsWith("https://")){
        url = "https://".concat(url);
    }
    console.log('url',url);
    // if (!validURL(document.getElementById("url").value)) {
    if (!validURL(url)) {
        swal(
            'Error',
            "URL is valid",
            'error'
        );
        return false;
    }


    let obj = {
        // "url": document.getElementById("url").value,
        "url": url,
        "time": 10
    }
    initData('/api/scan', obj, "Scanning");
}

function validURL(str) {
    var pattern = new RegExp('^(https?:\\/\\/)?' + // protocol
        '((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|' + // domain name
        '((\\d{1,3}\\.){3}\\d{1,3}))' + // OR ip (v4) address
        '(\\:\\d+)?(\\/[-a-z\\d%_.~+]*)*' + // port and path
        '(\\?[;&a-z\\d%_.~+=-]*)?' + // query string
        '(\\#[-a-z\\d_]*)?$', 'i'); // fragment locator
    return !!pattern.test(str);
}

