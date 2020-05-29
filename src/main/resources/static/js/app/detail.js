var dataSummary = {
    "pageId": +$('#modelPageId').val(),
    "summaryId": +$('#modelSummaryId').val()
}


initData("/api/scan/his", dataSummary);

