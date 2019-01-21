var userId;

$.get('./users/current', function (data) {
    userId = data.id;

});

$(function () {


    $("#addButton").dxButton({
        icon: "add",
        onClick: function () {
            addOrEditSettC(false);
        }
    });

    $("#editButton").dxButton({
        icon: "edit",
        onClick: function () {
            var dataGrid = $("#settClientGrid").dxDataGrid("instance");
            if (dataGrid.getSelectedRowsData().length > 0) {
                addOrEditSettC(true);
                settCRefresh();
            } else {
                $("#noSelectedRowToast").dxToast({
                    message: "Wiersz nie został wybrany",
                    type: "error",
                    displayTime: 2000
                }).dxToast("show");

            }
        }
    });

    $("#deleteButton").dxButton({
        icon: "trash",
        onClick: function () {

            var dataGrid = $("#settClientGrid").dxDataGrid("instance");
            if (dataGrid.getSelectedRowsData().length > 0) {
                deleteSettC(true);

            } else {
                $("#noSelectedRowToast").dxToast({
                    message: "Wiersz nie został wybrany",
                    type: "error",
                    displayTime: 2000
                }).dxToast("show");

            }
        }

    });

    showSettCCGrid();
});

function showSettCCGrid(){

    $.get('./vegetable/centre/user?userId='+userId, function (result){

        var settCDataSource = new DevExpress.data.DataSource({


            load: function () {
                var params = {
                    p: result.id
                };
                var d = $.Deferred();
                $.getJSON('./client/sett/vegetable', {
                    vegetableId: params.p ? params.p : -1,

                }).done(function (r) {
                    d.resolve(r);
                });
                return d.promise();
            }
        });


        $("#settClientGrid").dxDataGrid({
            dataSource: settCDataSource,
            key: "id",
            columnAutoWidth: true,
            selection: {
                mode: "single"
            },
            scrolling: {
                "showScrollbar": "never"
            },
            showBorders: true,
            hoverStateEnabled: true,
            columns: [{
                caption: "Numer kontraktu",
                dataField: "lp"
            }, {
                caption: "Data",
                dataField: "date"
            }, {
                caption: "Ilość",
                dataField: "amount"

            }, {
                caption: "Cena",
                dataField: "price"

            }, {
                caption: "Status",
                dataField: "statusName"

            }]
        });
    })

};

function getContract(){
    var dataGrid = $("#settClientGrid").dxDataGrid("instance");
    if (dataGrid.getSelectedRowsData().length > 0) {
        return dataGrid.getSelectedRowsData()[0];
    } else {
        console.log("Nie wybrano");
    }
}



function settCRefresh() {

    var dataSource = $("#settClientGrid").dxDataGrid("getDataSource");
    dataSource.reload();
    var dataGridInstance = $("#settClientGrid").dxDataGrid("instance");
    dataGridInstance.clearSelection();

}

function addOrEditSettC(editTrueFlag) {

    var contract = getContract();

    $.get('./vegetable/centre/user?userId='+userId, function (result){


        $("#addOrEditPopup").dxPopup({
            height: 250,
            width: 900
        }).dxPopup("show");

        var newSettC = {
            date:"",
            amount:"",
            contractId:"",
            status:1,
            lp:""
        };

        if (editTrueFlag == true) {

            newSettC.date = contract.date;
            newSettC.amount = contract.amount;
            newSettC.contractId = contract.contractId;
            newSettC.status = contract.status;
            newSettC.lp=contract.lp;


        } else {

            $("#addOrEditSettClientForm").val("");

            contract ={
                date:"",
                amount: "",
                contractId: "",
                status: ""
            }
        }


        $("#addOrEditSettClientForm").dxForm({
            formData: newSettC,
            readOnly: false,
            colCount: 2,
            items: [{
                dataField: "contractId",
                editorType: "dxSelectBox",
                label: {
                    text: "Kontrakt"
                },
                validationRules: [{
                    type: "required",
                    message: "Wybierz kontrakt"
                }],
                editorOptions: {
                    dataSource: "./client/contract/vegetable?vegetableId="+result.id,
                    displayExpr: 'lp',
                    valueExpr: 'id',
                    placeholder:"",
                    value: contract.contractId,
                    showClearButton: false,
                    searchEnabled: true
                }
            },{
                dataField: "date",
                editorType: "dxDateBox",
                value: contract.date,
                label: {
                    text: "Data"
                },
                validationRules: [{
                    type: "required",
                    message: "Wybierz datę"
                }],
                editorOptions: {
                    invalidDateMessage: "Prawidłowy format daty to: yyyy-MM-dd",
                    displayFormat: "yyyy-MM-dd",
                    width: "100%"
                }
            },{
                dataField: "amount",
                label: {
                    text: "Ilość"
                },
                validationRules: [{
                    type: "required",
                    message: "Ilość jest wymagana"
                }]
            }]
        });

        $("#saveSettClientButton").dxButton({
            text: "Zapisz",
            width: "100px",
            onClick: function (e) {

                var f = $("#addOrEditSettClientForm").dxForm('instance');
                var newContractId = f.getEditor('contractId').option('value');
                var newDate = f.getEditor('date').option('value');
                var newAmount = f.getEditor('amount').option('value');


                var ret = f.validate();

                if (ret.isValid) {
                    newSettC.contractId = newContractId;
                    newSettC.date = newDate;
                    newSettC.amount = newAmount;

                    newSettC = JSON.stringify(newSettC);

                    if (editTrueFlag) {
                        $.ajax({
                            url: './contract/settlement/client/' + contract.id,
                            type: 'put',
                            dataType: 'json',
                            contentType: 'application/json',
                            success: function () {
                                settCRefresh();
                            },
                            data: newSettC
                        });
                    } else {
                        $.ajax({
                            url: './contract/settlement/client/add',
                            type: 'post',
                            dataType: 'json',
                            contentType: 'application/json',
                            success: function () {
                                settCRefresh();
                            },
                            data: newSettC
                        });
                    }
                    $("#addOrEditPopup").dxPopup("hide");
                }

            }
        });

        $("#cancelSettClientButton").dxButton({
            text: "Anuluj",
            width: "100px",
            onClick: function (e) {
                $("#addOrEditPopup").dxPopup("hide");
            }
        });
    })

}

function deleteSettC() {

    var contract = getContract();


    $.post('./sett/user/delete?id=' + contract.id, function (result) {
            if (result.errorMsg) {
                $("#errorDeleteToast").dxToast({
                    message: "Nastąpił błąd w trakcie usuwania",
                    type: "error",
                    displayTime: 2000
                }).dxToast("show");

            } else {
                $("#okDeleteToast").dxToast({
                    message: "Rozliczenie zostało poprawnie usunięte",
                    type: "success",
                    displayTime: 2000
                }).dxToast("show");
                settCRefresh();
            }
        }
    )

}