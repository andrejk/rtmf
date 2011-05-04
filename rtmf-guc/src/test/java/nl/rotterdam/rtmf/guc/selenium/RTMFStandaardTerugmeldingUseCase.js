<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<!--
 * Copyright (c) 2009-2011 Gemeente Rotterdam
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the European Union Public Licence (EUPL),
 * version 1.1 (or any later version).
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * European Union Public Licence for more details.
 *
 * You should have received a copy of the European Union Public Licence
 * along with this program. If not, see
 * http://www.osor.eu/eupl/european-union-public-licence-eupl-v.1.1
-->
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head profile="http://selenium-ide.openqa.org/profiles/test-case">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="selenium.base" href="" />
<title>TmfSmokeTest</title>
</head>
<body>
<table cellpadding="1" cellspacing="1" border="1">
<thead>
<tr><td rowspan="1" colspan="3">TmfSmokeTest</td></tr>
</thead><tbody>
<tr>
	<td>open</td>
	<td>/terugmelding_BR.htm</td>
	<td></td>
</tr>
<tr>
	<td>click</td>
	<td>link=Start</td>
	<td></td>
</tr>
<tr>
	<td>click</td>
	<td>link=Terugmelden</td>
	<td></td>
</tr>
<tr>
	<td>click</td>
	<td>//input[@value='Volgende &gt;&gt;']</td>
	<td></td>
</tr>
<tr>
	<td>type</td>
	<td>BRObjectID</td>
	<td>123</td>
</tr>
<tr>
	<td>click</td>
	<td>//input[@value='Volgende &gt;&gt;']</td>
	<td></td>
</tr>
<tr>
	<td>type</td>
	<td>oldOSB-PERSOON-VOORNAAM</td>
	<td>Petet</td>
</tr>
<tr>
	<td>type</td>
	<td>newOSB-PERSOON-VOORNAAM</td>
	<td>Peter</td>
</tr>
<tr>
	<td>type</td>
	<td>oldOSB-PERSOON-TUSSENVOEGSEL</td>
	<td>vab</td>
</tr>
<tr>
	<td>type</td>
	<td>newOSB-PERSOON-TUSSENVOEGSEL</td>
	<td>van</td>
</tr>
<tr>
	<td>type</td>
	<td>oldOSB-PERSOON-ACHTERNAAM</td>
	<td>Oort</td>
</tr>
<tr>
	<td>type</td>
	<td>newOSB-PERSOON-ACHTERNAAM</td>
	<td>Oord</td>
</tr>
<tr>
	<td>click</td>
	<td>//input[@value='Volgende &gt;&gt;']</td>
	<td></td>
</tr>
<tr>
	<td>type</td>
	<td>reason</td>
	<td>Naam klopt niet</td>
</tr>
<tr>
	<td>type</td>
	<td>meldingkenmerk</td>
	<td>P123</td>
</tr>
<tr>
	<td>type</td>
	<td>name</td>
	<td>PP Bakker</td>
</tr>
<tr>
	<td>type</td>
	<td>phonenumber</td>
	<td>020-123</td>
</tr>
<tr>
	<td>type</td>
	<td>email</td>
	<td>pp@abc</td>
</tr>
<tr>
	<td>click</td>
	<td>//input[@value='Volgende &gt;&gt;']</td>
	<td></td>
</tr>
<tr>
	<td>click</td>
	<td>//input[@value='Versturen']</td>
	<td></td>
</tr>

</tbody></table>
</body>
</html>
