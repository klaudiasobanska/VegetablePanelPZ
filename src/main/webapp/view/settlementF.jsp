<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Farmer Settlement</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
    <script>window.jQuery || document.write(decodeURIComponent('%3Cscript src="js/jquery.min.js"%3E%3C/script%3E'))</script>

    <link rel="stylesheet" type="text/css" href="https://cdn3.devexpress.com/jslib/18.1.4/css/dx.spa.css" />
    <link rel="stylesheet" type="text/css" href="https://cdn3.devexpress.com/jslib/18.1.4/css/dx.common.css" />
    <link rel="dx-theme" data-theme="generic.carmine" href="https://cdn3.devexpress.com/jslib/18.1.5/css/dx.carmine.css" />
    <script src="../static/settlementF.js"></script>
    <script src="../static/home.js"></script>
    <script src="https://cdn3.devexpress.com/jslib/18.1.4/js/dx.all.js"></script>
    <link rel="stylesheet" type="text/css" href="../static/styles.css" />

</head>
<body class="dx-viewport allBackground" >
<div dx-menu-item id="menuContainer" class = "menu">
    <div id="panelMenu" style="float: right"></div>
</div>



<div id="editButton" style="float: left;"></div>
<div id="addOrEditPopup">
    <div id="addOrEditSettFarmerForm"></div>
    <div id="buttonContainer" style="float: right;">
        <div id="saveSettFarmerButton"></div>
        <div id="cancelSettFarmerButton"></div>
    </div>
</div>

<div id="settFarmerGridContainer">
    <div id="settFarmerGrid"></div>
    <div id="noSelectedRowToast"></div>
    <div id="okDeleteToast"></div>
    <div id="errorDeleteToast"></div>
</div>

</body>
</html>