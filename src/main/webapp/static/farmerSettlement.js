var userId;

$.get('./users/current', function (data) {
    userId = data.id;

});

$(function () {

    showMenuFarmer(3);



    $("#addButton").dxButton({
        icon: "add",
        onClick: function () {
            addOrEditSettF(false);
        }
    });

    $("#editButton").dxButton({
        icon: "edit",
        onClick: function () {
            var dataGrid = $("#settFGrid").dxDataGrid("instance");
            if (dataGrid.getSelectedRowsData().length > 0) {
                addOrEditSettF(true);
                settFRefresh();
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

            var dataGrid = $("#settFGrid").dxDataGrid("instance");
            if (dataGrid.getSelectedRowsData().length > 0) {
                deleteSettF(true);

            } else {
                $("#noSelectedRowToast").dxToast({
                    message: "Wiersz nie został wybrany",
                    type: "error",
                    displayTime: 2000
                }).dxToast("show");

            }
        }

    });

    showSettFGrid();
});

function showSettFGrid(){

    $.get('./farmer/user?userId='+userId, function (result) {

        var settFDataSource = new DevExpress.data.DataSource({


            load: function () {
                var params = {
                    p: result.id
                };
                var d = $.Deferred();
                $.getJSON('./farmer/sett', {
                    farmerId: params.p ? params.p : -1,

                }).done(function (r) {
                    d.resolve(r);
                });
                return d.promise();
            }
        });


        $("#settFGrid").dxDataGrid({
            dataSource: settFDataSource,
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
    var dataGrid = $("#settFGrid").dxDataGrid("instance");
    if (dataGrid.getSelectedRowsData().length > 0) {
        return dataGrid.getSelectedRowsData()[0];
    } else {
        console.log("Nie wybrano");
    }
}



function settFRefresh() {

    var dataSource = $("#settFGrid").dxDataGrid("getDataSource");
    dataSource.reload();
    var dataGridInstance = $("#settFGrid").dxDataGrid("instance");
    dataGridInstance.clearSelection();

}

function addOrEditSettF(editTrueFlag) {

    var contract = getContract();

    $.get('./farmer/user?userId='+userId, function (result) {


        $("#addOrEditPopup").dxPopup({
            height: 250,
            width: 900
        }).dxPopup("show");

        var newSettFarmer = {
            date:"",
            amount:"",
            contractId:"",
            status:1,
            lp:""
        };

        if (editTrueFlag == true) {

            newSettFarmer.date = contract.date;
            newSettFarmer.amount = contract.amount;
            newSettFarmer.contractId = contract.contractId;
            newSettFarmer.status = contract.status;
            newSettFarmer.lp=contract.lp;


        } else {

            $("#addOrEditSettFForm").val("");

            contract ={
                date:"",
                amount: "",
                contractId: "",
                status: ""
            }
        }


        $("#addOrEditSettFForm").dxForm({
            formData: newSettFarmer,
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
                    dataSource: "./farmer/get/contract?farmerId="+result.id,
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

        $("#saveSettFButton").dxButton({
            text: "Zapisz",
            width: "100px",
            onClick: function (e) {

                var f = $("#addOrEditSettFForm").dxForm('instance');
                var newContractId = f.getEditor('contractId').option('value');
                var newDate = f.getEditor('date').option('value');
                var newAmount = f.getEditor('amount').option('value');


                var ret = f.validate();

                if (ret.isValid) {
                    newSettFarmer.contractId = newContractId;
                    newSettFarmer.date = newDate;
                    newSettFarmer.amount = newAmount;

                    newSettFarmer = JSON.stringify(newSettFarmer);

                    if (editTrueFlag) {
                        $.ajax({
                            url: './contract/settlement/farmer/' + contract.id,
                            type: 'put',
                            dataType: 'json',
                            contentType: 'application/json',
                            success: function () {
                                settFRefresh();
                            },
                            data: newSettFarmer
                        });
                    } else {
                        $.ajax({
                            url: './contract/settlement/farmer/add',
                            type: 'post',
                            dataType: 'json',
                            contentType: 'application/json',
                            success: function () {
                                settFRefresh();
                            },
                            data: newSettFarmer
                        });
                    }
                    $("#addOrEditPopup").dxPopup("hide");
                }

            }
        });

        $("#cancelSettFButton").dxButton({
            text: "Anuluj",
            width: "100px",
            onClick: function (e) {
                $("#addOrEditPopup").dxPopup("hide");
            }
        });
    })

}

function deleteSettF() {

    var contract = getContract();


    $.post('./sett/farmer/delete?id=' + contract.id, function (result) {
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
                settFRefresh();
            }
        }
    )

}