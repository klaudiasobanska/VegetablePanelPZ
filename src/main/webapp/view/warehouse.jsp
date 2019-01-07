<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<html>
<head>
    <title>Warehouse</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
    <script>window.jQuery || document.write(decodeURIComponent('%3Cscript src="js/jquery.min.js"%3E%3C/script%3E'))</script>

    <link rel="stylesheet" type="text/css" href="https://cdn3.devexpress.com/jslib/18.1.4/css/dx.spa.css" />
    <link rel="stylesheet" type="text/css" href="https://cdn3.devexpress.com/jslib/18.1.4/css/dx.common.css" />
    <link rel="dx-theme" data-theme="generic.carmine" href="https://cdn3.devexpress.com/jslib/18.1.5/css/dx.carmine.css" />
    <script src="./static/warehouse.js"></script>
    <script src="./static/home.js"></script>
    <script src="https://cdn3.devexpress.com/jslib/18.1.4/js/dx.all.js"></script>
    <link rel="stylesheet" type="text/css" href="./static/styles.css" />

</head>
<body class="dx-viewport allBackground" >
<div dx-menu-item id="menuContainer" class = "menu">
    <div id="panelMenu" style="float: right"></div>
</div>
<div id="warehouseListContainer">
    <div id="warehouseList"></div>
    <div id="searchWarehouse" style="float: bottom;"></div>
</div>

<div id="menuButtonContainer">
    <div id="documentDateBox" style="float: right;"></div>
    <div id="addDocumentButton" style="float: left;"></div>
    <div id="deleteDocumentButton" style="float: left;"></div>
    <div id="editDocumentButton" ></div>
</div>
<div id="leftPanel">
    <div id="documentGridContainer">
        <div id="documentGrid"></div>
    </div>
</div>

<div id="addOrEditDocumentPopup">
    <div id="addOrEditDocumentForm"></div>
    <div id="buttonContainer" style="float: right;">
        <div id="saveDocumentButton"></div>
        <div id="cancelDocumentButton"></div>
    </div>
</div>
<div id="noWarehouseToast"></div>
<div id="okDeleteToast"></div>
<div id="errorDeleteToast"></div>

</body>
</html>
