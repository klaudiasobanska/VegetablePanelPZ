$(function () {

    showMenu(2);

    $("#searchWarehouse").dxTextBox({
        placeholder: "Wyszukaj",
        showClearButton: true,
        mode: "search",
        onEnterKey: function (o) {
            $('#warehouseList').dxList('instance').reload();
        }
    });

    $("#documentDateBox").dxDateBox({
        type: "date",
        showClearButton: true,
        displayFormat: "yyyy-MM-dd",
        onEnterKey: function (e) {
            showDocumentGrid();
        }
    });

    $("#addDocumentButton").dxButton({
        icon: "add",
        onClick: function (d) {
            if($("#warehouseList").dxList("instance").option("selectedItems").length > 0) {
                addOrEditDocument(false);
            }else{
                $("#noWarehouseToast").dxToast({
                    message: "Magazyn nie został wybrany",
                    type: "error",
                    displayTime: 2000
                }).dxToast("show")
            }
        }
    });

    $("#deleteDocumentButton").dxButton({
        icon: "trash",
        onClick: function (d) {
            if($("#documentGrid").dxDataGrid("instance").getSelectedRowsData().length > 0){
                deleteDocument();
            }else{
                $("#noWarehouseToast").dxToast({
                    message: "Document nie został wybrany",
                    type: "error",
                    displayTime: 2000
                }).dxToast("show")
            }
        }
    });

    $("#editDocumentButton").dxButton({
        icon: "edit",
        onClick: function (d) {
            if($("#warehouseList").dxList("instance").option("selectedItems").length > 0 &&
                $("#documentGrid").dxDataGrid("instance").getSelectedRowsData().length > 0) {
                addOrEditDocument(true);
            }else{
                $("#noWarehouseToast").dxToast({
                    message: "Document nie został wybrany",
                    type: "error",
                    displayTime: 2000
                }).dxToast("show")
            }
        }
    });

    var warehouseDataSource = new DevExpress.data.DataSource({

        load: function () {
            var params = {
                param: $("#searchWarehouse").dxTextBox('instance').option('value')
            };
            var d = $.Deferred();
            $.getJSON('./warehouse/search', {
                param: params.param ? params.param : ""
            }).done(function (result) {
                d.resolve(result);
            });
            return d.promise();
        }
    });

    $("#warehouseList").dxList({
        selectionMode:"single",
        tabIndex: 0,
        dataSource: warehouseDataSource,
        itemTemplate: function(data) {
            return $("<div>").text(data.name);
        },
        onItemClick: function (e) {
            showDocumentGrid();
        },
        onContentReady: function (d) {
            this.selectItem(0);

        }
    });

    showDocumentGrid();

});


function getWarehouse() {
    var warehouseDate = $("#warehouseList").dxList("instance");
    if (warehouseDate.option("selectedItems").length > 0){
        return  $("#warehouseList").dxList("instance").option("selectedItems")[0].id;
    }
    else {
        console.log("Nie wybrano");
    }

}

function dateFormat(d) {

    date = new Date(d);
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    var day = date.getDate() ;
    return y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (day < 10 ? ('0' + day) : day);
}

function showDocumentGrid(){



    var docDate = $("#documentDateBox").dxDateBox('instance').option('value');
    if (docDate!=null){
        docDate = dateFormat(docDate);
    }else{
        docDate = "";
    }

    var documentDataSource = new DevExpress.data.DataSource({



        load: function (loadOptions) {
            var params = {
                warehouseId: getWarehouse(),
                docDate: docDate
            };
            var d = $.Deferred();
            $.getJSON('./view/document/product/search', {
                warehouseId: params.warehouseId ? params.warehouseId: 1,
                docDate: params.docDate ? params.docDate: "",
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

    $("#documentGrid").dxDataGrid({
        dataSource: documentDataSource,
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
            pageSize: 2
        },
        pager: {
            showPageSizeSelector: true,
            allowedPageSizes: [3, 6, 12]
        },
        showBorders: true,

        hoverStateEnabled: true,

        columns: [{
            caption: "Dostawca",
            dataField: "providerName"
        },{
            caption: "Towar",
            dataField: "productName"
        }, {
            caption: "Cena",
            dataField: "docPrice"
        }, {
            caption: "Ilość",
            dataField: "amount"
        },{
            caption: "Data",
            dataField: "docDate"
        }]
    });

}

function sortString(loadOptions) {
    if(!loadOptions.sort) return 'id,asc';

    var sort=loadOptions.sort[0].selector;
    var sort1= loadOptions.sort[0].desc===true?',desc':',asc';
    if (sort == "warehouseId"){
        sort = "warehouse_id"
    }

    var sort2 = sort + sort1;

    return sort2;
}

function getDocument() {
    var dataGrid = $("#documentGrid").dxDataGrid("instance");
    if (dataGrid.getSelectedRowsData().length > 0) {
        return dataGrid.getSelectedRowsData()[0];
    } else {
        console.log("Nie wybrano");
    }
}

function documentRefresh(){
    var dataSource = $("#documentGrid").dxDataGrid("getDataSource");
    dataSource.reload()
    var dataGridInstance = $("#documentGrid").dxDataGrid("instance");
    dataGridInstance.clearSelection();
}



function addOrEditDocument(editTrueFlag) {



    var makeAsyncDataSource = function(path){
        return new DevExpress.data.CustomStore({
            loadMode: "raw",
            key:"id",
            load: function() {
                return $.getJSON(path);
            }
        });
    };

    var makeAsyncDataSourceProvider = function(path){
        return new DevExpress.data.CustomStore({
            loadMode: "raw",
            key:"id",
            load: function() {
                return $.getJSON(path);
            }
        });
    };

    $("#addOrEditDocumentPopup").dxPopup({
        height: 320,
        width: 800
    }).dxPopup("show");

    var newDocument = {
        providerId: "",
        docDate: "",
        productId: "",
        amount: "",
        docPrice: "",
        unit:"",
        warehouseId: getWarehouse()
    };

    if(editTrueFlag == true){
        var docuemntData = getDocument();
        newDocument.providerId = docuemntData.providerId;
        newDocument.docDate = docuemntData.docDate;
        newDocument.productId = docuemntData.productId;
        newDocument.amount = docuemntData.amount;
        newDocument.docPrice = docuemntData.docPrice;
        newDocument.unit = docuemntData.unitName;


    }else{
        $("#addOrEditDocumentForm").val("");
        var docuemntData = {
            productId: "",
            providerId: "",
            unit:"",
        }
        newDocument.docDate = dateFormat(new Date);

    }
    var dataGrid, dataGridProvider;

    $("#addOrEditDocumentForm").dxForm({
        formData: newDocument,
        readOnly: false,
        colCount: 2,
        items: [{
            dataField: "providerId",
            editorType: "dxDropDownBox",
            colSpan: 2,
            label: {
                text: "Dostawca"
            },
            validationRules: [{
                type: "required",
                message: "Wybierz dostawcę"
            }],
            editorOptions: {
                dataSource: makeAsyncDataSourceProvider("./client/all"),
                value: docuemntData.providerId,
                displayExpr: 'name',
                valueExpr: 'id',
                showClearButton: false,
                searchEnabled: true,
                contentTemplate: function(e) {
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
                        scrolling: { mode: "infinite" },
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
                dataSource: makeAsyncDataSource("./product/all"),
                value: docuemntData.productId,
                valueExpr: "id",
                displayExpr: "name",
                showClearButton: false,
                searchEnabled: true,
                contentTemplate: function(e) {
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
                        scrolling: { mode: "infinite" },
                        searchPanel: {
                            visible: true,
                            width: 200,
                            placeholder: "Wyszukaj"
                        },
                        hoverStateEnabled: true,
                        selectedRowKeys: value,
                        onSelectionChanged: function (selectedItems) {
                            var f = $("#addOrEditDocumentForm").dxForm('instance');
                            f.getEditor('docPrice').option('value',selectedItems.selectedRowsData[0].price);
                            f.getEditor('unit').option('value',selectedItems.selectedRowsData[0].unitName);
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
            dataField: "docDate",
            editorType: "dxDateBox",
            value: docuemntData.docDate,
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
                width:"100%"
            }
        },{
            dataField: "docPrice",
            label: {
                text: "Cena"
            },
            validationRules: [{
                type: "required",
                message: "Cena jest wymagana"
            }]

        },{
            dataField: "unit",
            disabled: true,
            label: {
                text: "Jednostka"
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



    $("#saveDocumentButton").dxButton({
        text:"Zapisz",
        width: "100px",
        onClick: function (e) {

            var f = $("#addOrEditDocumentForm").dxForm('instance');
            var newProviderId = f.getEditor('providerId').option('value');
            var newDocDate = f.getEditor('docDate').option('value');
            var newProductId = f.getEditor('productId').option('value');
            var newAmount = f.getEditor('amount').option('value');
            var newDocPrice = f.getEditor('docPrice').option('value');

            if(newProductId.length > 0){
                newProductId = newProductId[0];
            }
            if(newProviderId.length > 0){
                newProviderId = newProviderId[0];
            }

            var ret = f.validate();

            if (ret.isValid) {
                newDocument.docDate = newDocDate;
                newDocument.productId = newProductId;
                newDocument.providerId = newProviderId;
                newDocument.amount = newAmount;
                newDocument.docPrice = newDocPrice;

                newDocument = JSON.stringify(newDocument);

                if (editTrueFlag) {
                    $.ajax({
                        url: './document/' + docuemntData.id,
                        type: 'put',
                        dataType: 'json',
                        contentType: 'application/json',
                        success: function (result) {
                            documentRefresh();
                        },
                        data: newDocument
                    });
                } else {

                    $.ajax({
                        url: './document/add',
                        type: 'post',
                        dataType: 'json',
                        contentType: 'application/json',
                        success: function () {
                            documentRefresh();
                        },
                        data: newDocument
                    });
                }
                $("#addOrEditDocumentPopup").dxPopup("hide");

            }

        }
    });

    $("#cancelDocumentButton").dxButton({
        text:"Anuluj",
        width: "100px",
        onClick: function (e) {
            $("#addOrEditDocumentPopup").dxPopup("hide");
        }
    })

}

function deleteDocument() {

    var document = getDocument();

    $.post('/document/delete?id=' + document.id, function (result) {
            if(result.errorMsg){
                $("#errorDeleteToast").dxToast({
                    message: "Nastąpił błąd w trakcie usuwania",
                    type: "error",
                    displayTime: 2000
                }).dxToast("show");

            } else {
                $("#okDeleteToast").dxToast({
                    message: "Document został poprawnie usunięty",
                    type: "success",
                    displayTime: 2000
                }).dxToast("show");
                documentRefresh();

            }
        }
    )

}