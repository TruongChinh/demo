function timeChange(e) {
    var val = e.value;
    if (val < 0) {
        e.value = 7200;
    } else if (val > 7200) {
        e.value = 0;
    }
}

var obj = {};

initData();

function initData() {
    showLoading();
    axios.get("/api/app/time_scan").then(function (res) {
        console.log(res);
        if (res.data.http == 200) {
            let data = res.data.data;
            obj = data;
            $('#time').val(data.code);
        } else {
            stopLoading();
            swal(
                'Error',
                res.data.data.message,
                'error'
            );
        }
    }, function (err) {
        stopLoading();
    });
    stopLoading();
}


function save() {
    showLoading();
    obj.code = $('#time').val();
    axios.post("/api/app", obj).then(function (res) {
        console.log(res);
        if (res.data.http == 200) {
            stopLoading();
        } else {
            stopLoading();
            swal(
                'Error',
                res.data.data.message,
                'error'
            );
        }
    }, function (err) {
        stopLoading();
    });

}