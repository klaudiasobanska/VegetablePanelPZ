var userId;

$.get('./users/current', function (data) {
    userId = data.id;

});

$(function () {

    showMenu(8);

    $("#searchBox").dxTextBox({
        placeholder: "Wyszukaj",
        showClearButton: true,
        mode: "search",
        onEnterKey: function (o) {
            contractFRefresh();
        }
    });

    $("#addButton").dxButton({
        icon: "add",
        onClick: function () {
            addOrEditContractF(false);
        }
    });

    $("#editButton").dxButton({
        icon: "edit",
        onClick: function () {
            var dataGrid = $("#contractFGrid").dxDataGrid("instance");
            if (dataGrid.getSelectedRowsData().length > 0) {
                addOrEditContractF(true);
                contractFRefresh();
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

            var dataGrid = $("#contractFGrid").dxDataGrid("instance");
            if (dataGrid.getSelectedRowsData().length > 0) {
                deleteContractF(true);

            } else {
                $("#noSelectedRowToast").dxToast({
                    message: "Wiersz nie został wybrany",
                    type: "error",
                    displayTime: 2000
                }).dxToast("show");

            }
        }

    });

    showContractFGrid();
});

function showContractFGrid(){
    $.get('./vegetable/centre/user?userId='+userId, function (result) {

        var contractFDataSource = new DevExpress.data.DataSource({


            load: function (loadOptions) {
                var params = {
                    p: $("#searchBox").dxTextBox('instance').option('value'),
                    centreId: result.id
                };
                var d = $.Deferred();
                $.getJSON('./contract/farmer/search', {
                    param: params.p ? params.p : "",
                    centreId: params.centreId ? params.centreId:-1,
                    page: loadOptions.skip / loadOptions.take,
                    size: loadOptions.take,
                    sort: sortString(loadOptions)
                }).done(function (result) {
                    d.resolve(result.content, {
                        totalCount: result.totalElements

                    });
                });
                return d.promise();
            }
        });


        /*show grid with clients contracts */
        $("#contractFGrid").dxDataGrid({
            dataSource: contractFDataSource,
            key: "id",
            columnAutoWidth: true,
            remoteOperations: {groupPaging: true},
            selection: {
                mode: "single"
            },
            scrolling: {
                "showScrollbar": "never"
            },
            paging: {
                pageSize: 3
            },
            pager: {
                showPageSizeSelector: true,
                allowedPageSizes: [3, 6, 12]
            },
            showBorders: true,

            hoverStateEnabled: true,

            columns: [{
                caption: "Lp",
                dataField: "lp"
            },{
                caption: "Farmer",
                dataField: "farmerName"
            }, {
                caption: "Data rozpoczęcia",
                dataField: "startDate"
            }, {
                caption: "Data zakończenia",
                dataField: "endDate"

            }, {
                caption: "Ilość",
                dataField: "amount"

            },{
                caption: "Cena",
                dataField: "price"
            }, {
                caption: "Product",
                dataField: "productName"

            }, {
                caption: "Dostawca",
                dataField: "providerName"

            }, {
                caption: "Status",
                dataField: "statusName"

            }]
        });
    });

};

function sortString(loadOptions) {

    if(!loadOptions.sort) return 'id,asc';

    var sort=loadOptions.sort[0].selector;
    var sort1= loadOptions.sort[0].desc===true?',desc':',asc';
    if (sort == "farmerId"){
        sort = "farmer_id"
    }
    if (sort == "vegetableCentreId"){
        sort = "vegetable_centre_id"
    }
    if (sort == "startDate"){
        sort = "start_date"
    }
    if (sort == "endDate"){
        sort = "end_date"
    }
    if (sort == "productId"){
        sort = "product_id"
    }
    if (sort == "providerId"){
        sort = "provider_id"
    }


    var sort2 = sort + sort1;

    return sort2;

}
function getContract(){
    var dataGrid = $("#contractFGrid").dxDataGrid("instance");
    if (dataGrid.getSelectedRowsData().length > 0) {
        return dataGrid.getSelectedRowsData()[0];
    } else {
        console.log("Nie wybrano");
    }
}

function contractFRefresh() {

    var dataSource = $("#contractFGrid").dxDataGrid("getDataSource");
    dataSource.reload()
    var dataGridInstance = $("#contractFGrid").dxDataGrid("instance");
    dataGridInstance.clearSelection();

}

function addOrEditContractF(editTrueFlag) {

    var contract = getContract();

    $.get('./vegetable/centre/user?userId='+userId, function (result) {

        var makeAsyncDataSource = function (path) {
            return new DevExpress.data.CustomStore({
                loadMode: "raw",
                key: "id",
                load: function () {
                    return $.getJSON(path);
                }
            });
        };

        $("#addOrEditPopup").dxPopup({
            height: 400,
            width: 900
        }).dxPopup("show");

        var newContractFarmer = {
            farmerId: "",
            startDate:"",
            endDate:"",
            amount:"",
            price:"",
            productId:"",
            providerId:"",
            status:1,
            vegetableCentreId: result.id

        };

        if (editTrueFlag == true) {

            newContractFarmer.farmerId = contract.farmerId;
            newContractFarmer.startDate = contract.startDate;
            newContractFarmer.endDate = contract.endDate;
            newContractFarmer.amount = contract.amount;
            newContractFarmer.price = contract.price;
            newContractFarmer.productId = contract.productId;
            newContractFarmer.providerId = contract.providerId;
            newContractFarmer.status = contract.status;


        } else {
            $("#addOrEditPopup").dxPopup({
                title: "Nowy Kontrakt"
            });
            $("#addOrEditContractCForm").val("");

            contract ={
                farmerId:"",
                productId: "",
                providerId: "",
                startDate: "",
                endDate:""
            }



        }

        var dataGrid, dataGridProvider;

        $("#addOrEditContractFForm").dxForm({
            formData: newContractFarmer,
            readOnly: false,
            colCount: 2,
            items: [{
                dataField: "farmerId",
                editorType: "dxSelectBox",
                label: {
                    text: "Farmer"
                },
                validationRules: [{
                    type: "required",
                    message: "Wybierz Klienta"
                }],
                editorOptions: {
                    dataSource: result.centreFarmer,
                    displayExpr: 'name',
                    valueExpr: 'id',
                    placeholder:"",
                    value: contract.farmerId,
                    showClearButton: false,
                    searchEnabled: true
                }
            },{
                dataField: "price",
                label: {
                    text: "Cena"
                },
                validationRules: [{
                    type: "required",
                    message: "Cena jest wymagana"
                }]
            },{
                dataField: "providerId",
                editorType: "dxDropDownBox",
                colSpan: 2,
                label: {
                    text: "Dostawca"
                },
                validationRules: [{
                    type: "required",
                    message: "Wybierz Dostawcę"
                }],
                editorOptions: {
                    dataSource: makeAsyncDataSource("./provider/vegetable?centreId=" + result.id),
                    value: contract.providerId,
                    displayExpr: 'name',
                    valueExpr: 'id',
                    showClearButton: false,
                    searchEnabled: true,
                    contentTemplate: function (e) {
                        var value = e.component.option("value");
                        $dataGridProvider = $("<div>").dxDataGrid({
                            dataSource: e.component.option("dataSource"),
                            columns: [{
                                dataField: "name",
                                caption: "Nazwa",
                                width: 200
                            }, {
                                dataField: "nip",
                                caption: "NIP",
                                width: 140
                            }, {
                                dataField: "fullAddress",
                                caption: "Address"
                            }],
                            height: 200,
                            selection: {mode: "single"},
                            scrolling: {mode: "infinite"},
                            searchPanel: {
                                visible: true,
                                width: 220,
                                placeholder: "Wyszukaj"
                            },
                            hoverStateEnabled: true,
                            selectedRowKeys: value,
                            onSelectionChanged: function (selectedItems) {
                                var keys = selectedItems.selectedRowKeys;
                                e.component.option("value", keys);
                                e.component.close();
                            }
                        });
                        dataGridProvider = $dataGridProvider.dxDataGrid("instance");

                        return $dataGridProvider;
                    }
                }
            },{
                dataField: "productId",
                editorType: "dxDropDownBox",
                colSpan: 2,
                label: {
                    text: "Towar"
                },
                validationRules: [{
                    type: "required",
                    message: "Wybierz towar"
                }],
                editorOptions: {
                    dataSource: makeAsyncDataSource("./product/vegetable?centreId=" + result.id),
                    value: contract.productId,
                    valueExpr: "id",
                    displayExpr: "name",
                    showClearButton: false,
                    searchEnabled: true,
                    contentTemplate: function (e) {
                        var value = e.component.option("value");
                        $dataGrid = $("<div>").dxDataGrid({
                            dataSource: e.component.option("dataSource"),
                            columns: [{
                                dataField: "name",
                                caption: "Nazwa"
                            }, {
                                dataField: "unitName",
                                caption: "Jednostka"
                            }, {
                                dataField: "price",
                                caption: "Cena"
                            }],
                            height: 220,
                            selection: {mode: "single"},
                            scrolling: {mode: "infinite"},
                            searchPanel: {
                                visible: true,
                                width: 200,
                                placeholder: "Wyszukaj"
                            },
                            hoverStateEnabled: true,
                            selectedRowKeys: value,
                            onSelectionChanged: function (selectedItems) {
                                var f = $("#addOrEditDocumentForm").dxForm('instance');
                                var keys = selectedItems.selectedRowKeys;
                                e.component.option("value", keys);
                                e.component.close();
                            }
                        });
                        dataGrid = $dataGrid.dxDataGrid("instance");

                        return $dataGrid;
                    }
                }
            },{
                dataField: "startDate",
                editorType: "dxDateBox",
                value: contract.startDate,
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
                dataField: "endDate",
                editorType: "dxDateBox",
                value: contract.endDate,
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
            }, {
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
                    dataSource: "./status/contract/all",
                    displayExpr: 'name',
                    valueExpr: 'id',
                    value: contract.status,
                    showClearButton: false,
                    searchEnabled: true
                }
            }]
        });

        $("#saveContractFButton").dxButton({
            text: "Zapisz",
            width: "100px",
            onClick: function (e) {

                var f = $("#addOrEditContractFForm").dxForm('instance');
                var newFarmerId = f.getEditor('farmerId').option('value');
                var newProviderId = f.getEditor('providerId').option('value');
                var newProduct = f.getEditor('productId').option('value');
                var newStartDate = f.getEditor('startDate').option('value');
                var newEndDate = f.getEditor('endDate').option('value');
                var newAmount = f.getEditor('amount').option('value');
                var newStatus = f.getEditor('status').option('value');
                var newPrice = f.getEditor('price').option('value');

                if (newProduct.length > 0) {
                    newProduct = newProduct[0];
                }
                if (newProviderId.length > 0) {
                    newProviderId = newProviderId[0];
                }

                var ret = f.validate();

                if (ret.isValid) {
                    newContractFarmer.farmerId = newFarmerId;
                    newContractFarmer.providerId = newProviderId;
                    newContractFarmer.productId = newProduct;
                    newContractFarmer.startDate = newStartDate;
                    newContractFarmer.endDate = newEndDate;
                    newContractFarmer.amount = newAmount;
                    newContractFarmer.status = newStatus;
                    newContractFarmer.price = newPrice;



                    newContractFarmer = JSON.stringify(newContractFarmer);

                    if (editTrueFlag) {
                        $.ajax({
                            url: './contract/farmer/' + contract.id,
                            type: 'put',
                            dataType: 'json',
                            contentType: 'application/json',
                            success: function () {
                                contractFRefresh();
                            },
                            data: newContractFarmer
                        });
                    } else {
                        $.ajax({
                            url: './contract/farmer/add',
                            type: 'post',
                            dataType: 'json',
                            contentType: 'application/json',
                            success: function () {
                                contractFRefresh();
                            },
                            data: newContractFarmer
                        });
                    }
                    $("#addOrEditPopup").dxPopup("hide");
                }

            }
        });

        $("#cancelContractFButton").dxButton({
            text: "Anuluj",
            width: "100px",
            onClick: function (e) {
                $("#addOrEditPopup").dxPopup("hide");
            }
        })
    });

}

function deleteContractF() {

    var contract = getContract();


    $.post('./contract/farmer/delete?id=' + contract.id, function (result) {
            if (result.errorMsg) {
                $("#errorDeleteToast").dxToast({
                    message: "Nastąpił błąd w trakcie usuwania",
                    type: "error",
                    displayTime: 2000
                }).dxToast("show");

            } else {
                $("#okDeleteToast").dxToast({
                    message: "Farmer został poprawnie usunięty",
                    type: "success",
                    displayTime: 2000
                }).dxToast("show");
                contractFRefresh();

            }
        }
    )

}

