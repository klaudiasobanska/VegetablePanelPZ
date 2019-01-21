var userId;

$.get('./users/current', function (data) {
    userId = data.id;

});

$(function () {

    $("#editButton").dxButton({
        icon: "edit",
        onClick: function () {
            var dataGrid = $("#settFarmerGrid").dxDataGrid("instance");
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


    showSettFFGrid();
});

function showSettFFGrid(){

    $.get('./vegetable/centre/user?userId='+userId, function (result){

        var settFDataSource = new DevExpress.data.DataSource({


            load: function () {
                var params = {
                    p: result.id
                };
                var d = $.Deferred();
                $.getJSON('./farmer/sett/vegetable', {
                    vegetableId: params.p ? params.p : -1,

                }).done(function (r) {
                    d.resolve(r);
                });
                return d.promise();
            }
        });


        $("#settFarmerGrid").dxDataGrid({
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
    var dataGrid = $("#settFarmerGrid").dxDataGrid("instance");
    if (dataGrid.getSelectedRowsData().length > 0) {
        return dataGrid.getSelectedRowsData()[0];
    } else {
        console.log("Nie wybrano");
    }
}



function settFRefresh() {

    var dataSource = $("#settFarmerGrid").dxDataGrid("getDataSource");
    dataSource.reload();
    var dataGridInstance = $("#settFarmerGrid").dxDataGrid("instance");
    dataGridInstance.clearSelection();

}

function addOrEditSettF(editTrueFlag) {

    var contract = getContract();

    $.get('./vegetable/centre/user?userId='+userId, function (result){


        $("#addOrEditPopup").dxPopup({
            height: 250,
            width: 900
        }).dxPopup("show");

        var newSettF = {
            date:"",
            amount:"",
            contractId:"",
            status:"",
            lp:""
        };

        if (editTrueFlag == true) {

            newSettF .date = contract.date;
            newSettF .amount = contract.amount;
            newSettF .contractId = contract.contractId;
            newSettF .status = contract.status;
            newSettF .lp=contract.lp;

        }


        $("#addOrEditSettFarmerForm").dxForm({
            formData: newSettF ,
            readOnly: false,
            colCount: 2,
            items: [{
                dataField: "contractId",
                label: {
                    text: "Kontrakt"
                },
                editorOptions: {
                    disabled: true
                }
            },{
                dataField: "date",
                editorType: "dxDateBox",
                value: contract.date,
                editorOptions: {
                    disabled:true
                }
            },{
                dataField: "amount",
                label: {
                    text: "Ilość"
                },
                editorOptions: {
                    disabled:true
                }
            },{
                dataField: "status",
                editorType: "dxSelectBox",
                label: {
                    text: "Status"
                },
                validationRules: [{
                    type: "required",
                    message: "Statu jest wymagany"
                }],
                editorOptions: {
                    dataSource: "./status/sett/all",
                    displayExpr: 'name',
                    valueExpr: 'id',
                    value: contract.status,
                    showClearButton: false,
                    searchEnabled: true
                }
            }]
        });

        $("#saveSettFarmerButton").dxButton({
            text: "Zapisz",
            width: "100px",
            onClick: function (e) {

                var f = $("#addOrEditSettFarmerForm").dxForm('instance');
                var ret = f.validate();

                if (ret.isValid) {
                    $.ajax({
                        url: './farmer/sett/update?id=' + contract.id +"&status="+ f.getEditor('status').option('value'),
                        type: 'post',
                        dataType: 'json',
                        contentType: 'application/json',
                        success: function () {
                            settFRefresh();
                        },
                    });
                }
                $("#addOrEditPopup").dxPopup("hide");
            }

        });

        $("#cancelSettFarmerButton").dxButton({
            text: "Anuluj",
            width: "100px",
            onClick: function (e) {
                $("#addOrEditPopup").dxPopup("hide");
            }
        });
    })

}